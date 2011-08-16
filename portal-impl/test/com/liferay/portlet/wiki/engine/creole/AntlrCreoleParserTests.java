/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.wiki.engine.creole;

import com.liferay.portal.parsers.creole.ast.ASTNode;
import com.liferay.portal.parsers.creole.ast.BaseListNode;
import com.liferay.portal.parsers.creole.ast.BoldTextNode;
import com.liferay.portal.parsers.creole.ast.CollectionNode;
import com.liferay.portal.parsers.creole.ast.ForcedEndOfLineNode;
import com.liferay.portal.parsers.creole.ast.FormattedTextNode;
import com.liferay.portal.parsers.creole.ast.HorizontalNode;
import com.liferay.portal.parsers.creole.ast.ImageNode;
import com.liferay.portal.parsers.creole.ast.ItalicTextNode;
import com.liferay.portal.parsers.creole.ast.ItemNode;
import com.liferay.portal.parsers.creole.ast.LineNode;
import com.liferay.portal.parsers.creole.ast.NoWikiSectionNode;
import com.liferay.portal.parsers.creole.ast.OrderedListItemNode;
import com.liferay.portal.parsers.creole.ast.OrderedListNode;
import com.liferay.portal.parsers.creole.ast.ParagraphNode;
import com.liferay.portal.parsers.creole.ast.ScapedNode;
import com.liferay.portal.parsers.creole.ast.UnformattedTextNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListItemNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.ast.link.LinkNode;
import com.liferay.portal.parsers.creole.ast.table.TableDataNode;
import com.liferay.portal.parsers.creole.ast.table.TableHeaderNode;
import com.liferay.portal.parsers.creole.ast.table.TableNode;

import java.util.List;

import org.junit.Assert;

/**
 * @author Miguel Pastor
 */
public class AntlrCreoleParserTests extends AbstractWikiParserTests {

	public void testParseCorrectlyBoldContentInListItems() {
		UnorderedListNode unorderedListNode =
			(UnorderedListNode) parseListNode(
				LISTS_FILES_PREFIX + "list-6.creole");

		Assert.assertEquals(1, unorderedListNode.getChildASTNodesCount());

		UnorderedListItemNode unorderedItemNode =
			(UnorderedListItemNode) unorderedListNode.getChildASTNode(0);

		Assert.assertNotNull(unorderedItemNode);

		FormattedTextNode boldTextContent =
			(FormattedTextNode) unorderedItemNode.getChildASTNode(1);

		BoldTextNode boldText =
			(BoldTextNode) boldTextContent.getChildASTNode(0);
		CollectionNode child =
			(CollectionNode) boldText.getChildASTNode(0);
		UnformattedTextNode text = (UnformattedTextNode) child.get(0);

		Assert.assertEquals("abcdefg", text.getContent());
	}

	protected BaseListNode parseListNode(String file) {
		WikiPageNode root = parseFile(file);

		BaseListNode listNode =
			(BaseListNode) root.getChildASTNode(0);
		Assert.assertNotNull(listNode);

		return listNode;
	}

	public void testParseCorrectlyItalicContentInListItems() {
		UnorderedListNode unorderedListNode =
			(UnorderedListNode) parseListNode(
				LISTS_FILES_PREFIX + "list-5.creole");

		Assert.assertEquals(1, unorderedListNode.getChildASTNodesCount());

		UnorderedListItemNode unorderedItemNode =
			(UnorderedListItemNode) unorderedListNode.getChildASTNode(0);

		Assert.assertNotNull(unorderedItemNode);
		Assert.assertEquals(2, unorderedItemNode.getChildASTNodesCount());

		FormattedTextNode italicTextContent =
			(FormattedTextNode) unorderedItemNode.getChildASTNode(1);

		ItalicTextNode italicTex =
			(ItalicTextNode) italicTextContent.getChildASTNode(0);
		CollectionNode child =
			(CollectionNode) italicTex.getChildASTNode(0);
		UnformattedTextNode text = (UnformattedTextNode) child.get(0);

		Assert.assertEquals("abcdefg", text.getContent());
	}

	public void testParseCorrectlyNestedLevels() {
		UnorderedListNode unorderedListNode =
			(UnorderedListNode) parseListNode(
				LISTS_FILES_PREFIX + "list-4.creole");

		Assert.assertEquals(7, unorderedListNode.getChildASTNodesCount());

		int sumOfItemsInLevel1 = 0, sumOfItemsInLevel2 = 0,
			expectedSumOfItemsInLevel1 = 3 * 1,
			expectedSumOfItemsInLevel2 = 4 * 2;
		;

		for (Object node : unorderedListNode.getChildASTNodes()) {
			UnorderedListItemNode listItem =
				(UnorderedListItemNode) node;
			int currentLevel = listItem.getLevel();

			if (currentLevel == 1) {
				sumOfItemsInLevel1 += currentLevel;
			}
			else if (currentLevel == 2) {
				sumOfItemsInLevel2 += currentLevel;
			}
			else {
				Assert.fail("Parsed has not been achieved correctly");
			}
		}

		Assert.assertEquals(sumOfItemsInLevel1, expectedSumOfItemsInLevel1);
		Assert.assertEquals(sumOfItemsInLevel2, expectedSumOfItemsInLevel2);
	}

	public void testParseCorrectlyOneItemFirstLevel() {
		execFirstLevelItemListTests(LISTS_FILES_PREFIX + "list-1.creole", 1);
	}

	protected void execFirstLevelItemListTests(String file, int numOfItems) {
		BaseListNode listNode = parseListNode(file);

		Assert.assertEquals(numOfItems, listNode.getChildASTNodesCount());

		for (Object node : listNode.getChildASTNodes()) {
			ItemNode listItem = (ItemNode) node;
			Assert.assertNotNull(listItem);
			Assert.assertEquals(1, listItem.getLevel());
		}
	}

	public void testParseCorrectlyOneOrderedItemFirstLevel() {
		execFirstLevelItemListTests(LISTS_FILES_PREFIX + "list-7.creole", 1);
	}

	public void testParseCorrectlyOrderedNestedLevels() {
		OrderedListNode orderedListNode = (OrderedListNode) parseListNode(
			LISTS_FILES_PREFIX + "list-10.creole");

		Assert.assertEquals(7, orderedListNode.getChildASTNodesCount());

		int sumOfItemsInLevel1 = 0, sumOfItemsInLevel2 = 0,
			expectedSumOfItemsInLevel1 = 3 * 1,
			expectedSumOfItemsInLevel2 = 4 * 2;

		for (Object node : orderedListNode.getChildASTNodes()) {
			OrderedListItemNode listItem =
				(OrderedListItemNode) node;
			int currentLevel = listItem.getLevel();

			if (currentLevel == 1) {
				sumOfItemsInLevel1 += currentLevel;
			}
			else if (currentLevel == 2) {
				sumOfItemsInLevel2 += currentLevel;
			}
			else {
				Assert.fail("Parsed has not been achieved correctly");
			}
		}

		Assert.assertEquals(sumOfItemsInLevel1, expectedSumOfItemsInLevel1);
		Assert.assertEquals(sumOfItemsInLevel2, expectedSumOfItemsInLevel2);
	}

	public void testParseCorrectlyThreeItemFirstLevel() {
		execFirstLevelItemListTests(LISTS_FILES_PREFIX + "list-3.creole", 3);
	}

	public void testParseCorrectlyThreeOrderedItemFirstLevel() {
		execFirstLevelItemListTests(LISTS_FILES_PREFIX + "list-9.creole", 3);
	}

	public void testParseCorrectlyTwoItemFirstLevel() {
		execFirstLevelItemListTests(LISTS_FILES_PREFIX + "list-2.creole", 2);
	}

	public void testParseCorrectlyTwoOrderedItemFirstLevel() {
		execFirstLevelItemListTests(LISTS_FILES_PREFIX + "list-8.creole", 2);
	}

	public void testParseEmpyImageTag() {
		WikiPageNode root = parseFile(IMAGE_FILES_PREFIX + "image-4.creole");
		Assert.assertNotNull(root);

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);
		LineNode line = (LineNode) p.getChildASTNode(0);
		Assert.assertEquals(1, line.getChildASTNodesCount());

		ImageNode image = (ImageNode) line.getChildASTNode(0);
		Assert.assertEquals("", image.getLink());

		CollectionNode alternativeElements = image.getAltNode();
		Assert.assertNull(alternativeElements);
	}

	public void testParseHeadingBlocksMultiple() {
		WikiPageNode root = parseFile(
			HEADING_FILES_PREFIX + "heading-10.creole");

		Assert.assertEquals(3, root.getChildASTNodesCount());
	}

	// Horizontal section related tests

	public void testParseHorizontalBlock() {
		WikiPageNode root = parseFile(
			HORIZONTAL_FILES_PREFIX + "horizontal-1.creole");

		Assert.assertEquals(1, root.getChildASTNodesCount());
		Assert.assertTrue(root.getChildASTNode(0) instanceof HorizontalNode);
	}

	public void testParseHorizontalMixedBlocks() {
		WikiPageNode root = parseFile(
			HORIZONTAL_FILES_PREFIX + "horizontal-3.creole");

		Assert.assertEquals(3, root.getChildASTNodesCount());
		Assert.assertTrue(root.getChildASTNode(1) instanceof HorizontalNode);
	}

	public void testParseHorizontalTwoBlocks() {
		WikiPageNode root = parseFile(
			HORIZONTAL_FILES_PREFIX + "horizontal-2.creole");

		Assert.assertEquals(2, root.getChildASTNodesCount());
		Assert.assertTrue(root.getChildASTNode(0) instanceof HorizontalNode);
		Assert.assertTrue(root.getChildASTNode(1) instanceof HorizontalNode);
	}

	public void testParseMultilineTextParagraph() {
		WikiPageNode root = parseFile(TEXT_FILES_PREFIX + "text-2.creole");

		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getChildASTNodesCount());

		ParagraphNode paragraph = (ParagraphNode) root.getChildASTNode(0);
		List<ASTNode> lines = paragraph.getChildASTNodes();

		Assert.assertEquals(10, lines.size());

		int counter = 0;
		for (ASTNode l : lines) {
			LineNode line = (LineNode) l;

			Assert.assertEquals(1, line.getChildASTNodesCount());

			UnformattedTextNode textNode =
				(UnformattedTextNode) line.getChildASTNode(
					0);
			UnformattedTextNode text =
				(UnformattedTextNode) textNode.getChildASTNode(
					0);

			Assert.assertEquals("Simple P" + counter++, text.getContent());
		}
	}

	public void testParseMultipleImageTags() {
		WikiPageNode root = parseFile(IMAGE_FILES_PREFIX + "image-5.creole");

		Assert.assertNotNull(root);

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);
		LineNode line = (LineNode) p.getChildASTNode(0);

		Assert.assertEquals(5, line.getChildASTNodesCount());

		for (int i = 0; i < line.getChildASTNodes().size(); ) {
			ImageNode image = (ImageNode) line.getChildASTNode(i);
			Assert.assertEquals("L" + ++i, image.getLink());
		}
	}

	// No wiki block related tests

	public void testParseNoWikiBlock() {
		WikiPageNode root = parseFile(
			NOWIKI_FILES_PREFIX + "nowikiblock-1.creole");

		Assert.assertEquals(1, root.getChildASTNodesCount());
	}

	public void testParseNoWikiBlockEmpty() {
		WikiPageNode root = parseFile(
			NOWIKI_FILES_PREFIX + "nowikiblock-3.creole");

		NoWikiSectionNode child = (NoWikiSectionNode) root.getChildASTNode(0);

		Assert.assertEquals("", child.getContent());
	}

	public void testParseNoWikiBlockMultiple() {
		WikiPageNode root = parseFile(
			NOWIKI_FILES_PREFIX + "nowikiblock-2.creole");

		Assert.assertEquals(3, root.getChildASTNodesCount());
	}

	 public void testParseNoWikiBlockNonEmpty() {
		WikiPageNode root = parseFile(
			NOWIKI_FILES_PREFIX + "nowikiblock-4.creole");

		NoWikiSectionNode child = (NoWikiSectionNode) root.getChildASTNode(0);

		Assert.assertEquals(
			"This is a non \\empty\\ block", child.getContent());
	}

	public void testParseOnlySpacesContentInImageTag() {
		WikiPageNode root = parseFile(IMAGE_FILES_PREFIX + "image-3.creole");

		Assert.assertNotNull(root);

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);
		LineNode line = (LineNode) p.getChildASTNode(0);

		Assert.assertEquals(1, line.getChildASTNodesCount());

		ImageNode image = (ImageNode) line.getChildASTNode(0);

		Assert.assertEquals("  ", image.getLink());

		CollectionNode alternativeElements = image.getAltNode();

		Assert.assertNull(alternativeElements);
	}

	public void testParseSimpleImageTag() {
		WikiPageNode root = parseFile(IMAGE_FILES_PREFIX + "image-1.creole");

		Assert.assertNotNull(root);

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);
		LineNode line = (LineNode) p.getChildASTNode(0);

		Assert.assertEquals(1, line.getChildASTNodesCount());

		ImageNode image = (ImageNode) line.getChildASTNode(0);

		Assert.assertEquals("link", image.getLink());

		CollectionNode alternativeElements = image.getAltNode();

		Assert.assertNotNull(alternativeElements);
		Assert.assertEquals(1, alternativeElements.size());

		UnformattedTextNode textNode =
			(UnformattedTextNode) alternativeElements.getASTNodes().get(0);
		UnformattedTextNode text =
			(UnformattedTextNode) textNode.getChildASTNode(
				0);

		Assert.assertEquals("alternative text", text.getContent());
	}

	public void testParseSimpleImageTagWithNoAlternative() {
		WikiPageNode root = parseFile(IMAGE_FILES_PREFIX + "image-2.creole");

		Assert.assertNotNull(root);

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);
		LineNode line = (LineNode) p.getChildASTNode(0);

		Assert.assertEquals(1, line.getChildASTNodesCount());

		ImageNode image = (ImageNode) line.getChildASTNode(0);

		Assert.assertEquals("link", image.getLink());

		CollectionNode alternativeElements = image.getAltNode();

		Assert.assertNull(alternativeElements);
	}

	public void testParseSimpleLinkTag() {
		WikiPageNode root = parseFile(LINK_FILES_PREFIX + "link-1.creole");

		Assert.assertNotNull(root);

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);
		LineNode line = (LineNode) p.getChildASTNode(0);

		Assert.assertEquals(1, line.getChildASTNodesCount());

		LinkNode link = (LinkNode) line.getChildASTNode(0);

		Assert.assertEquals("link", link.getLink());

		CollectionNode alternativeElements = link.getAltCollectionNode();

		Assert.assertNotNull(alternativeElements);
		Assert.assertEquals(1, alternativeElements.size());

		UnformattedTextNode textNode =
			(UnformattedTextNode) alternativeElements.getASTNodes().get(0);
		CollectionNode textItems = (CollectionNode) textNode.getChildASTNode(0);
		UnformattedTextNode text = (UnformattedTextNode) textItems.get(0);

		Assert.assertEquals("alternative text", text.getContent());
	}

	public void testParseSimpleLinkTagWithoutDescription() {
		WikiPageNode root = parseFile(LINK_FILES_PREFIX + "link-2.creole");

		Assert.assertNotNull(root);

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);
		LineNode line = (LineNode) p.getChildASTNode(0);

		Assert.assertEquals(1, line.getChildASTNodesCount());

		LinkNode link = (LinkNode) line.getChildASTNode(0);

		Assert.assertEquals("link", link.getLink());
		Assert.assertNull(link.getAltCollectionNode());
	}

	public void testParseSimpleLinkTagWithoutDescription2() {
		WikiPageNode root = parseFile(LINK_FILES_PREFIX + "link-3.creole");

		Assert.assertNotNull(root);

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);
		LineNode line = (LineNode) p.getChildASTNode(0);

		Assert.assertEquals(5, line.getChildASTNodesCount());

		List<ASTNode> items = line.getChildASTNodes();

		for (ASTNode n : items) {
			if (n instanceof LinkNode) {
				LinkNode link = (LinkNode) n;
				Assert.assertEquals("L", link.getLink());
				CollectionNode alternativeElements =
					link.getAltCollectionNode();
				Assert.assertNotNull(alternativeElements);
				Assert.assertEquals(1, alternativeElements.size());
				UnformattedTextNode textNode =
					(UnformattedTextNode) alternativeElements.getASTNodes().
						get(0);
				CollectionNode textItems =
					(CollectionNode) textNode.getChildASTNode(
						0);
				UnformattedTextNode text =
					(UnformattedTextNode) textItems.get(0);
				Assert.assertEquals("A", text.getContent());
			}
		}
	}

	public void testParseSimpleTextBoldAndItalics() {
		WikiPageNode root = parseFile(TEXT_FILES_PREFIX + "text-6.creole");

		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getChildASTNodesCount());

		ParagraphNode paragraph = (ParagraphNode) root.getChildASTNode(0);

		Assert.assertEquals(1, paragraph.getChildASTNodesCount());

		LineNode line = (LineNode) paragraph.getChildASTNode(0);

		Assert.assertEquals(2, line.getChildASTNodesCount());

		UnformattedTextNode text =
			(UnformattedTextNode) line.getChildASTNode(0);
		UnformattedTextNode t = (UnformattedTextNode) text.getChildASTNode(0);

		Assert.assertEquals("Text ", t.getContent());

		BoldTextNode boldText = (BoldTextNode) line.getChildASTNode(1);

		Assert.assertEquals(1, boldText.getChildASTNodesCount());

		ItalicTextNode it = (ItalicTextNode) boldText.getChildASTNode(0);
		CollectionNode n = (CollectionNode) it.getChildASTNode(0);
		UnformattedTextNode node = (UnformattedTextNode) n.get(0);

		Assert.assertEquals("ItalicAndBold", node.getContent());
	}

	public void testParseSimpleTextParagraph() {
		WikiPageNode root = parseFile(TEXT_FILES_PREFIX + "text-1.creole");

		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getChildASTNodesCount());

		ParagraphNode paragraph = (ParagraphNode) root.getChildASTNode(0);
		List<ASTNode> lines = paragraph.getChildASTNodes();

		Assert.assertEquals(1, lines.size());

		LineNode line = (LineNode) paragraph.getChildASTNode(0);

		Assert.assertEquals(1, line.getChildASTNodesCount());

		UnformattedTextNode textNode =
			(UnformattedTextNode) line.getChildASTNode(
				0);
		UnformattedTextNode text =
			(UnformattedTextNode) textNode.getChildASTNode(
				0);

		Assert.assertEquals("Simple paragraph", text.getContent());
	}

	public void testParseSimpleTextWithBold() {
		WikiPageNode root = parseFile(TEXT_FILES_PREFIX + "text-4.creole");

		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getChildASTNodesCount());

		ParagraphNode paragraph = (ParagraphNode) root.getChildASTNode(0);

		Assert.assertEquals(1, paragraph.getChildASTNodesCount());

		LineNode line = (LineNode) paragraph.getChildASTNode(0);

		Assert.assertEquals(2, line.getChildASTNodesCount());

		UnformattedTextNode text =
			(UnformattedTextNode) line.getChildASTNode(0);
		UnformattedTextNode t = (UnformattedTextNode) text.getChildASTNode(0);

		Assert.assertEquals("Text with some contents in ", t.getContent());

		BoldTextNode boldTextContent =
			(BoldTextNode) line.getChildASTNode(1);

		FormattedTextNode boldText =
			(FormattedTextNode) boldTextContent.getChildASTNode(0);
		CollectionNode child =
			(CollectionNode) boldText.getChildASTNode(0);

		UnformattedTextNode unformattedTex = (UnformattedTextNode) child.get(0);
		Assert.assertEquals("bold", unformattedTex.getContent());
	}

	public void testParseSimpleTextWithBoldAndItalics() {
		WikiPageNode root = parseFile(TEXT_FILES_PREFIX + "text-5.creole");

		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getChildASTNodesCount());

		ParagraphNode paragraph = (ParagraphNode) root.getChildASTNode(0);

		Assert.assertEquals(1, paragraph.getChildASTNodesCount());

		LineNode line = (LineNode) paragraph.getChildASTNode(0);

		Assert.assertEquals(4, line.getChildASTNodesCount());

		UnformattedTextNode text =
			(UnformattedTextNode) line.getChildASTNode(0);
		UnformattedTextNode t = (UnformattedTextNode) text.getChildASTNode(0);

		Assert.assertEquals("Text with some contents in ", t.getContent());

		BoldTextNode boldTextNode = (BoldTextNode) line.getChildASTNode(1);

		Assert.assertEquals(1, boldTextNode.getChildASTNodesCount());

		FormattedTextNode textInBold =
			(FormattedTextNode) boldTextNode.getChildASTNode(0);
		CollectionNode n = (CollectionNode) textInBold.getChildASTNode(0);
		UnformattedTextNode node =
			(UnformattedTextNode) n.get(0);

		Assert.assertEquals("bold", node.getContent());
	}

	public void testParseSimpleTextWithForcedEndline() {
		WikiPageNode root = parseFile(TEXT_FILES_PREFIX + "text-7.creole");

		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getChildASTNodesCount());

		ParagraphNode paragraph = (ParagraphNode) root.getChildASTNode(0);

		Assert.assertEquals(1, paragraph.getChildASTNodesCount());

		LineNode line = (LineNode) paragraph.getChildASTNode(0);

		Assert.assertEquals(3, line.getChildASTNodesCount());

		UnformattedTextNode text =
			(UnformattedTextNode) line.getChildASTNode(0);
		UnformattedTextNode ut = (UnformattedTextNode) text.getChildASTNode(0);

		Assert.assertEquals("Text with ", ut.getContent());

		CollectionNode newLineText = (CollectionNode) line.getChildASTNode(1);

		Assert.assertEquals(1, newLineText.size());
		Assert.assertTrue(newLineText.get(0) instanceof ForcedEndOfLineNode);

		CollectionNode t = (CollectionNode) line.getChildASTNode(2);

		Assert.assertEquals(
			"forced line break",
			((UnformattedTextNode) t.get(0)).getContent());
	}

	public void testParseSimpleTextWithItalics() {
		WikiPageNode root = parseFile(TEXT_FILES_PREFIX + "text-3.creole");

		Assert.assertNotNull(root);

		List<ASTNode> sections = root.getChildASTNodes();

		Assert.assertEquals(1, sections.size());

		ParagraphNode paragraph = (ParagraphNode) sections.get(0);
		List<ASTNode> lines = paragraph.getChildASTNodes();

		Assert.assertEquals(1, lines.size());

		LineNode line = (LineNode) paragraph.getChildASTNode(0);

		Assert.assertEquals(2, line.getChildASTNodesCount());

		UnformattedTextNode text =
			(UnformattedTextNode) line.getChildASTNode(0);
		UnformattedTextNode t = (UnformattedTextNode) text.getChildASTNode(0);

		Assert.assertEquals("Text with some contents in ", t.getContent());

		ItalicTextNode italicTextContent =
			(ItalicTextNode) line.getChildASTNode(1);

		FormattedTextNode italicTex =
			(FormattedTextNode) italicTextContent.getChildASTNode(0);
		CollectionNode child =
			(CollectionNode) italicTex.getChildASTNode(0);
		UnformattedTextNode unformattedTex = (UnformattedTextNode) child.get(0);

		Assert.assertEquals("italic", unformattedTex.getContent());
	}

	public void testParseSimpleTextWithItalicTextInMultipleLines() {
		WikiPageNode root = parseFile(TEXT_FILES_PREFIX + "text-8.creole");

		Assert.assertNotNull(root);
	}

	public void testParseTableMultipleRowsAndCOlumns() {
		WikiPageNode root = parseFile(TABLE_FILES_PREFIX + "table-2.creole");

		Assert.assertNotNull(root);

		TableNode table = (TableNode) root.getChildASTNode(0);

		Assert.assertNotNull(table);
		Assert.assertEquals(4, table.getChildASTNodesCount());

		CollectionNode row = (CollectionNode) table.getChildASTNode(0);

		Assert.assertEquals(4, row.size());

		// test the header
		for (int i = 0; i < 4; ++i) {
			TableHeaderNode header = (TableHeaderNode) row.get(i);

			Assert.assertNotNull(header);

			UnformattedTextNode textNode =
				(UnformattedTextNode) header.getChildASTNode(0);

			Assert.assertNotNull(textNode);
			Assert.assertEquals(1, textNode.getChildASTNodesCount());

			UnformattedTextNode text =
				(UnformattedTextNode) textNode.getChildASTNode(
					0);

			Assert.assertEquals("H" + (i + 1), text.getContent());
		}

		// test the content rows
		int content = 1;

		for (int rowIndex = 1; rowIndex < 4; ++rowIndex) {
			row = (CollectionNode) table.getChildASTNode(rowIndex);
			Assert.assertEquals(4, row.size());
			for (int column = 0; column < 4; ++column) {
				TableDataNode cell = (TableDataNode) row.get((column));

				Assert.assertNotNull(cell);

				UnformattedTextNode textNode =
					(UnformattedTextNode) cell.getChildASTNode(0);

				Assert.assertNotNull(textNode);
				Assert.assertEquals(1, textNode.getChildASTNodesCount());

				UnformattedTextNode text =
					(UnformattedTextNode) textNode.getChildASTNode(0);

				Assert.assertEquals("C" + content++, text.getContent());
			}
		}
	}

	public void testParseTableOneRowOneColumn() {
		WikiPageNode root = parseFile(TABLE_FILES_PREFIX + "table-1.creole");

		Assert.assertNotNull(root);

		TableNode table = (TableNode) root.getChildASTNode(0);

		Assert.assertNotNull(table);
		Assert.assertEquals(2, table.getChildASTNodesCount());

		CollectionNode firstRow = (CollectionNode) table.getChildASTNode(0);

		Assert.assertEquals(1, firstRow.size());

		TableHeaderNode header = (TableHeaderNode) firstRow.get(0);

		Assert.assertNotNull(header);

		UnformattedTextNode textNode =
			(UnformattedTextNode) header.getChildASTNode(
				0);

		Assert.assertNotNull(textNode);
		Assert.assertEquals(1, textNode.getChildASTNodesCount());

		UnformattedTextNode text =
			(UnformattedTextNode) textNode.getChildASTNode(
				0);

		Assert.assertEquals("H1", text.getContent());

		CollectionNode secondRow =
			(CollectionNode) table.getChildASTNodes().get(1);

		Assert.assertEquals(1, secondRow.size());

		TableDataNode cell = (TableDataNode) secondRow.get(0);

		Assert.assertNotNull(cell);

		textNode = (UnformattedTextNode) cell.getChildASTNode(0);

		Assert.assertNotNull(textNode);
		Assert.assertEquals(1, textNode.getChildASTNodesCount());

		text = (UnformattedTextNode) textNode.getChildASTNode(0);

		Assert.assertEquals("C1.1", text.getContent());
	}

	public void testSimpleEscapedCharacter() {
		WikiPageNode root = parseFile(ESCAPE_FILES_PREFIX + "escape-1.creole");

		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getChildASTNodesCount());

		ParagraphNode p = (ParagraphNode) root.getChildASTNode(0);

		Assert.assertEquals(2, p.getChildASTNodesCount());

		LineNode line = (LineNode) p.getChildASTNode(0);

		Assert.assertEquals(2, line.getChildASTNodesCount());

		UnformattedTextNode firstline =
			(UnformattedTextNode) line.getChildASTNode(
				0);
		ScapedNode scaped = (ScapedNode) firstline.getChildASTNode(0);

		Assert.assertEquals("E", scaped.getContent());
		CollectionNode notScaped =
			(CollectionNode) line.getChildASTNode(1);

		UnformattedTextNode t = (UnformattedTextNode) notScaped.get(0);
		Assert.assertEquals("SCAPED1", t.getContent());
	}

}