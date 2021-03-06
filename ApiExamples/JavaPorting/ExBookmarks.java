// Copyright (c) 2001-2020 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

package ApiExamples;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.BookmarkCollection;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.Assert;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Paragraph;
import com.aspose.words.Run;
import com.aspose.words.BookmarkStart;
import com.aspose.words.BookmarkEnd;
import java.util.Iterator;
import com.aspose.words.Bookmark;
import com.aspose.ms.System.msConsole;
import com.aspose.words.DocumentVisitor;
import com.aspose.words.VisitorAction;
import com.aspose.words.NodeType;
import com.aspose.words.Row;
import com.aspose.words.ControlChar;


@Test
public class ExBookmarks extends ApiExampleBase
{
    //ExStart
    //ExFor:Bookmark
    //ExFor:Bookmark.Name
    //ExFor:Bookmark.Text
    //ExFor:Bookmark.Remove
    //ExFor:Bookmark.BookmarkStart
    //ExFor:Bookmark.BookmarkEnd
    //ExFor:BookmarkStart
    //ExFor:BookmarkStart.#ctor
    //ExFor:BookmarkEnd
    //ExFor:BookmarkEnd.#ctor
    //ExFor:BookmarkStart.Accept(DocumentVisitor)
    //ExFor:BookmarkEnd.Accept(DocumentVisitor)
    //ExFor:BookmarkStart.Bookmark
    //ExFor:BookmarkStart.GetText
    //ExFor:BookmarkStart.Name
    //ExFor:BookmarkEnd.Name
    //ExFor:BookmarkCollection
    //ExFor:BookmarkCollection.Item(Int32)
    //ExFor:BookmarkCollection.Item(String)
    //ExFor:BookmarkCollection.Count
    //ExFor:BookmarkCollection.GetEnumerator
    //ExFor:Range.Bookmarks
    //ExFor:DocumentVisitor.VisitBookmarkStart 
    //ExFor:DocumentVisitor.VisitBookmarkEnd
    //ExSummary:Shows how to add bookmarks and update their contents.
    @Test //ExSkip
    public void createUpdateAndPrintBookmarks() throws Exception
    {
        // Create a document with 3 bookmarks: "MyBookmark 1", "MyBookmark 2", "MyBookmark 3"
        Document doc = createDocumentWithBookmarks();
        BookmarkCollection bookmarks = doc.getRange().getBookmarks();

        // Check that we have 3 bookmarks
        msAssert.areEqual(3, bookmarks.getCount());
        msAssert.areEqual("MyBookmark 1", bookmarks.get(0).getName()); //ExSkip
        msAssert.areEqual("Text content of MyBookmark 2", bookmarks.get(1).getText()); //ExSkip

        // Look at initial values of our bookmarks
        printAllBookmarkInfo(bookmarks);

        // Obtain bookmarks from a bookmark collection by index/name and update their values
        bookmarks.get(0).setName("Updated name of " + bookmarks.get(0).getName());
        bookmarks.get("MyBookmark 2").setText("Updated text content of " + bookmarks.get(1).getName());
        // Remove the latest bookmark
        // The bookmarked text is not deleted
        bookmarks.get(2).remove();

        bookmarks = doc.getRange().getBookmarks();
        // Check that we have 2 bookmarks after the latest bookmark was deleted
        msAssert.areEqual(2, bookmarks.getCount());
        msAssert.areEqual("Updated name of MyBookmark 1", bookmarks.get(0).getName()); //ExSkip
        msAssert.areEqual("Updated text content of MyBookmark 2", bookmarks.get(1).getText()); //ExSkip

        // Look at updated values of our bookmarks
        printAllBookmarkInfo(bookmarks);
    }

    /// <summary>
    /// Create a document with bookmarks using the start and end nodes.
    /// </summary>
    private static Document createDocumentWithBookmarks() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Document doc = builder.getDocument();

        // An empty document has just one empty paragraph by default
        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();

        // Add several bookmarks to the document
        for (int i = 1; i <= 3; i++)
        {
            String bookmarkName = "MyBookmark " + i;

            p.appendChild(new Run(doc, "Text before bookmark."));

            p.appendChild(new BookmarkStart(doc, bookmarkName));
            p.appendChild(new Run(doc, "Text content of " + bookmarkName));
            p.appendChild(new BookmarkEnd(doc, bookmarkName));

            p.appendChild(new Run(doc, "Text after bookmark.\r\n"));
        }

        return builder.getDocument();
    }

    /// <summary>
    /// Use an iterator and a visitor to print info of every bookmark from within a document.
    /// </summary>
    private static void printAllBookmarkInfo(BookmarkCollection bookmarks) throws Exception
    {
        // Create a DocumentVisitor
        BookmarkInfoPrinter bookmarkVisitor = new BookmarkInfoPrinter();

        // Get the enumerator from the document's BookmarkCollection and iterate over the bookmarks
        Iterator<Bookmark> enumerator = bookmarks.iterator();
        try /*JAVA: was using*/
        {
            while (enumerator.hasNext())
            {
                Bookmark currentBookmark = enumerator.next();

                // Accept our DocumentVisitor it to print information about our bookmarks
                if (currentBookmark != null)
                {
                    currentBookmark.getBookmarkStart().accept(bookmarkVisitor);
                    currentBookmark.getBookmarkEnd().accept(bookmarkVisitor);

                    // Prints a blank line
                    System.out.println(currentBookmark.getBookmarkStart().getText());
                }
            }
        }
        finally { if (enumerator != null) enumerator.close(); }
    }

    /// <summary>
    /// Visitor that prints bookmark information to the console.
    /// </summary>
    public static class BookmarkInfoPrinter extends DocumentVisitor
    {
        public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart) throws Exception
        {
            msConsole.writeLine("BookmarkStart name: \"{0}\", Content: \"{1}\"", bookmarkStart.getName(),
                bookmarkStart.getBookmark().getText());
            return VisitorAction.CONTINUE;
        }

        public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd)
        {
            msConsole.writeLine("BookmarkEnd name: \"{0}\"", bookmarkEnd.getName());
            return VisitorAction.CONTINUE;
        }
    }
    //ExEnd

    @Test
    public void tableColumnBookmarks() throws Exception
    {
        //ExStart
        //ExFor:Bookmark.IsColumn
        //ExFor:Bookmark.FirstColumn
        //ExFor:Bookmark.LastColumn
        //ExSummary:Shows how to get information about table column bookmark.
        Document doc = new Document(getMyDir() + "TableColumnBookmark.doc");
        for (Bookmark bookmark : doc.getRange().getBookmarks())
        {
            msConsole.writeLine("Bookmark: {0}{1}", bookmark.getName(), bookmark.isColumn() ? " (Column)" : "");
            if (bookmark.isColumn())
            {
                if (bookmark.getBookmarkStart().getAncestor(NodeType.ROW) instanceof Row row &&
                    bookmark.FirstColumn < row.Cells.Count)
                {
                    // Print text from the first and last cells containing in bookmark
                    msConsole.WriteLine(row.Cells[bookmark.getFirstColumn()].GetText().TrimEnd(ControlChar.CELL_CHAR));
                    msConsole.WriteLine(row.Cells[bookmark.getLastColumn()].GetText().TrimEnd(ControlChar.CELL_CHAR));
                }
            }
        }
        //ExEnd

        Bookmark firstTableColumnBookmark = doc.getRange().getBookmarks().get("FirstTableColumnBookmark");
        Bookmark secondTableColumnBookmark = doc.getRange().getBookmarks().get("SecondTableColumnBookmark");

        Assert.assertTrue(firstTableColumnBookmark.isColumn());
        msAssert.areEqual(1, firstTableColumnBookmark.getFirstColumn());
        msAssert.areEqual(3, firstTableColumnBookmark.getLastColumn());

        Assert.assertTrue(secondTableColumnBookmark.isColumn());
        msAssert.areEqual(0, secondTableColumnBookmark.getFirstColumn());
        msAssert.areEqual(3, secondTableColumnBookmark.getLastColumn());
    }

    @Test
    public void clearBookmarks() throws Exception
    {
        //ExStart
        //ExFor:BookmarkCollection.Clear
        //ExSummary:Shows how to remove all bookmarks from a document.
        // Open a document with 3 bookmarks: "MyBookmark1", "My_Bookmark2", "MyBookmark3"
        Document doc = new Document(getMyDir() + "Bookmarks.docx");

        // Remove all bookmarks from the document
        // The bookmarked text is not deleted
        doc.getRange().getBookmarks().clear();
        //ExEnd

        // Verify that the bookmarks were removed
        msAssert.areEqual(0, doc.getRange().getBookmarks().getCount());
    }

    @Test
    public void removeBookmarkFromBookmarkCollection() throws Exception
    {
        //ExStart
        //ExFor:BookmarkCollection.Remove(Bookmark)
        //ExFor:BookmarkCollection.Remove(String)
        //ExFor:BookmarkCollection.RemoveAt
        //ExSummary:Shows how to remove bookmarks from a document using different methods.
        // Open a document with 3 bookmarks: "MyBookmark1", "My_Bookmark2", "MyBookmark3"
        Document doc = new Document(getMyDir() + "Bookmarks.docx");

        // Remove a particular bookmark from the document
        Bookmark bookmark = doc.getRange().getBookmarks().get(0);
        doc.getRange().getBookmarks().remove(bookmark);

        // Remove a bookmark by specified name
        doc.getRange().getBookmarks().remove("My_Bookmark2");

        // Remove a bookmark at the specified index
        doc.getRange().getBookmarks().removeAt(0);
        //ExEnd

        // In docx we have additional hidden bookmark "_GoBack"
        // When we check bookmarks count, the result will be 1 instead of 0
        msAssert.areEqual(1, doc.getRange().getBookmarks().getCount());
    }

    @Test
    public void replaceBookmarkUnderscoresWithWhitespaces() throws Exception
    {
        //ExStart
        //ExFor:Bookmark.Name
        //ExSummary:Shows how to replace elements in bookmark name
        // Open a document with 3 bookmarks: "MyBookmark1", "My_Bookmark2", "MyBookmark3"
        Document doc = new Document(getMyDir() + "Bookmarks.docx");
        msAssert.areEqual("MyBookmark3", doc.getRange().getBookmarks().get(2).getName()); //ExSkip

        // MS Word document does not support bookmark names with whitespaces by default
        // If you have document which contains bookmark names with underscores, you can simply replace them to whitespaces
        for (Bookmark bookmark : doc.getRange().getBookmarks()) bookmark.setName(bookmark.getName().replace("_", " "));
        //ExEnd
    }
}
