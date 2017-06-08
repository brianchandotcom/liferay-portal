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

import com.liferay.portal.parsers.creole.visitor.impl.XhtmlTranslationVisitor;

import org.junit.Assert;

/**
 * @author Miguel Pastor
 */
public class TranslationToXHTMLTests extends AbstractWikiParserTests {

	public XhtmlTranslationVisitor getTranslator() {
		return _translator;
	}

	public void testEscapedEscapedCharacter() {
		Assert.assertEquals(
			"<p>~&#034;~ is escaped&#034; </p>",
			translate(ESCAPE_FILES_PREFIX + "escape-2.creole"));
	}

	public void testParseCorrectlyBoldContentInListItems() {
		Assert.assertEquals(
			"<ul><li> <strong>abcdefg</strong></li></ul>",
			translate(LISTS_FILES_PREFIX + "list-6.creole"));
	}

	public void testParseCorrectlyItalicContentInListItems() {
		Assert.assertEquals(
			"<ul><li> <em>abcdefg</em></li></ul>",
			translate(LISTS_FILES_PREFIX + "list-5.creole"));
	}

	public void testParseCorrectlyMixedHorizontalBlocks() {
		Assert.assertEquals(
			"<h1>Before Horizontal section</h1><hr/>" +
			"<pre>\tNo wiki section after Horizontal section</pre>",
			translate(HORIZONTAL_FILES_PREFIX + "horizontal-3.creole"));
	}

	public void testParseCorrectlyMultipleHeadingBlocks() {
		Assert.assertEquals(
			"<h1>Level 1</h1><h2>Level 2</h2><h3>Level 3</h3>",
			translate(HEADING_FILES_PREFIX + "heading-10.creole"));
	}

	public void testParseCorrectlyNoClosedFirstHeadingBlock() {
		Assert.assertEquals(
			"<h1>This is a non closed heading</h1>",
			translate(HEADING_FILES_PREFIX + "heading-3.creole"));
	}

	public void testParseCorrectlyNoClosedSecondHeadingBlock() {
		Assert.assertEquals(
			"<h2>This is a non closed heading</h2>",
			translate(HEADING_FILES_PREFIX + "heading-6.creole"));
	}

	public void testParseCorrectlyNoClosedThirdHeadingBlock() {
		Assert.assertEquals(
			"<h3>Level 3</h3>",
			translate(HEADING_FILES_PREFIX + "heading-7.creole"));
	}

	public void testParseCorrectlyOneEmptyFirstHeadingBlock() {
		Assert.assertEquals(
			"<h1>  </h1>",
			translate(HEADING_FILES_PREFIX + "heading-2.creole"));
	}

	public void testParseCorrectlyOneEmptyNoWikiBlock() {
		Assert.assertEquals(
			"<pre></pre>",
			translate(NOWIKI_FILES_PREFIX + "nowikiblock-3.creole"));
	}

	public void testParseCorrectlyOneEmptySecondHeadingBlock() {
		Assert.assertEquals(
			"<h2>  </h2>",
			translate(HEADING_FILES_PREFIX + "heading-5.creole"));
	}

	public void testParseCorrectlyOneEmptyThirdHeadingBlock() {
		Assert.assertEquals(
			"<h3>  </h3>",
			translate(HEADING_FILES_PREFIX + "heading-8.creole"));
	}

	public void testParseCorrectlyOneHorizontalBlocks() {
		Assert.assertEquals(
			"<hr/>",
			translate(HORIZONTAL_FILES_PREFIX + "horizontal-1.creole"));
	}

	public void testParseCorrectlyOneItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>ABCDEFG</li></ul>",
			translate(LISTS_FILES_PREFIX + "list-1.creole"));
	}

	public void testParseCorrectlyOneNonEmptyFirstHeadingBlock() {
		Assert.assertEquals(
			"<h1> Level 1 (largest) </h1>",
			translate(HEADING_FILES_PREFIX + "heading-1.creole"));
	}

	public void testParseCorrectlyOneNonEmptyNoWikiBlock() {
		Assert.assertEquals(
			"<pre>This is a non \\empty\\ block</pre>",
			translate(NOWIKI_FILES_PREFIX + "nowikiblock-4.creole"));
	}

	public void testParseCorrectlyOneNonEmptyNoWikiBlockWitMultipleLines() {
		Assert.assertEquals(
			"<pre>Multiple\nlines</pre>",
			translate(NOWIKI_FILES_PREFIX + "nowikiblock-5.creole"));
	}

	public void testParseCorrectlyOneNonEmptySecondHeadingBlock() {
		Assert.assertEquals(
			"<h2>Level 2</h2>",
			translate(HEADING_FILES_PREFIX + "heading-4.creole"));
	}

	public void testParseCorrectlyOneNonEmptyThirdHeadingBlock() {
		Assert.assertEquals(
			"<h3>This is a non closed heading</h3>",
			translate(HEADING_FILES_PREFIX + "heading-9.creole"));
	}

	public void testParseCorrectlyOneOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>ABCDEFG</li></ol>",
			translate(LISTS_FILES_PREFIX + "list-7.creole"));
	}

	public void testParseCorrectlyOrderedNestedLevels() {
		Assert.assertEquals(
			"<ol><li>a</li><ol><li>a.1</li></ol><li>b</li><ol><li>b.1</li>" +
			"<li>b.2</li><li>b.3</li></ol><li>c</li></ol>",
			translate(LISTS_FILES_PREFIX + "list-10.creole"));
	}

	public void testParseCorrectlyThreeItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>1</li><li>2</li><li>3</li></ul>",
			translate(LISTS_FILES_PREFIX + "list-3.creole"));
	}

	public void testParseCorrectlyThreeNoWikiBlock() {
		Assert.assertEquals(
			"<pre>1111</pre><pre>2222</pre><pre>3333</pre>",
			translate(NOWIKI_FILES_PREFIX + "nowikiblock-2.creole"));
	}

	public void testParseCorrectlyThreeOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>1</li><li>2</li><li>3</li></ol>",
			translate(LISTS_FILES_PREFIX + "list-9.creole"));
	}

	public void testParseCorrectlyTwoHorizontalBlocks() {
		Assert.assertEquals(
			"<hr/><hr/>",
			translate(HORIZONTAL_FILES_PREFIX + "horizontal-2.creole"));
	}

	public void testParseCorrectlyTwoItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>1</li><li>2</li></ul>",
			translate(LISTS_FILES_PREFIX + "list-2.creole"));
	}

	public void testParseCorrectlyTwoOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>1</li><li>2</li></ol>",
			translate(LISTS_FILES_PREFIX + "list-8.creole"));
	}

	public void testParseEmpyImageTag() {
		Assert.assertEquals(
			"<p><img src=\"\" /> </p>",
			translate(IMAGE_FILES_PREFIX + "image-4.creole"));
	}

	public void testParseImageAndTextInListItem() {
		Assert.assertEquals(
			"<ul><li><img src=\"imageLink\" alt=\"altText\"/> end.</li></ul>",
			translate(LISTS_FILES_PREFIX + "list-17.creole"));
	}

	public void testParseImageInListItem() {
		Assert.assertEquals(
			"<ul><li><img src=\"imageLink\" alt=\"altText\"/></li></ul>",
			translate(LISTS_FILES_PREFIX + "list-16.creole"));
	}

	public void testParseLinkInListItem() {
		Assert.assertEquals(
			"<ul><li><a href=\"l\">a</a></li></ul>",
			translate(LISTS_FILES_PREFIX + "list-13.creole"));
	}

	public void testParseLinkInListItemMixedText() {
		Assert.assertEquals(
			"<ul><li>This is an item with a link <a href=\"l\">a</a> " + "" +
			"inside text</li></ul>",
			translate(LISTS_FILES_PREFIX + "list-12.creole"));
	}

	public void testParseLinkInListItemWithPreText() {
		Assert.assertEquals(
			"<ul><li>This is an item with a link <a href=\"l\">a</a></li></ul>",
			translate(LISTS_FILES_PREFIX + "list-11.creole"));
	}

	public void testParseLinkWithNoAlt() {
		Assert.assertEquals(
			"<p><a href=\"Link\">Link</a> </p>",
			translate(LINK_FILES_PREFIX + "link-7.creole"));
	}

	public void testParseMultilineTextParagraph() {
		Assert.assertEquals(
			"<p>Simple P0 Simple P1 Simple P2 Simple P3 Simple P4 " +
			"Simple P5 Simple P6 Simple P7 Simple P8 Simple P9 </p>",
			translate(TEXT_FILES_PREFIX + "text-2.creole"));
	}

	public void testParseMultipleImageTags() {
		Assert.assertEquals(
			"<p><img src=\"L1\" alt=\"A1\"/><img src=\"L2\" alt=\"A2\"/>" +
			"<img src=\"L3\" alt=\"A3\"/><img src=\"L4\" alt=\"A4\"/>" +
			"<img src=\"L5\" alt=\"A5\"/> </p>",
			translate(IMAGE_FILES_PREFIX + "image-5.creole"));
	}

	public void testParseMultipleLinkTags() {
		Assert.assertEquals(
			"<p><a href=\"L\">A</a> <a href=\"L\">A</a>" +
			" <a href=\"L\">A</a> </p>",
			translate(LINK_FILES_PREFIX + "link-3.creole"));
	}

	public void testParseNestedLists() {
		Assert.assertEquals(
			"<ul><li> 1</li><li> 2</li><ul><li> 2.1</li><ul><li> 2.1.1</li>" +
			"<ul><li> 2.1.1.1</li><li> 2.1.1.2</li></ul><li> 2.1.2</li><li>" +
			" 2.1.3</li></ul><li> 2.2</li><li> 2.3</li></ul><li>3</li></ul>",
			translate(LISTS_FILES_PREFIX + "list-18.creole"));
	}

	public void testParseNoWikiAndTextInListItem() {
		Assert.assertEquals(
			"<ul><li><pre>This is nowiki inside a list item</pre> and " +
			"<em>italics</em></li></ul>",
			translate(LISTS_FILES_PREFIX + "list-15.creole"));
	}

	public void testParseNoWikiInListItem() {
		Assert.assertEquals(
			"<ul><li><pre>This is nowiki inside a list item</pre></li></ul>",
			translate(LISTS_FILES_PREFIX + "list-14.creole"));
	}

	public void testParseOnlySpacesContentInImageTag() {
		Assert.assertEquals(
			"<p><img src=\"L1\" alt=\"A1\"/><img src=\"L2\" alt=\"A2\"/>"  +
			"<img src=\"L3\" alt=\"A3\"/><img src=\"L4\" alt=\"A4\"/>" + "" +
			"<img src=\"L5\" alt=\"A5\"/> </p>",
			translate(IMAGE_FILES_PREFIX + "image-5.creole"));
	}

	public void testParseSimpleImageTag() {
		Assert.assertEquals(
			"<p><img src=\"link\" alt=\"alternative text\"/> </p>",
			translate(IMAGE_FILES_PREFIX + "image-1.creole"));
	}

	public void testParseSimpleImageTagWithNoAlternative() {
		Assert.assertEquals(
			"<p><img src=\"link\" /> </p>",
			translate(IMAGE_FILES_PREFIX + "image-2.creole"));
	}

	public void testParseSimpleLinkTag() {
		Assert.assertEquals(
			"<p><a href=\"link\">alternative text</a> </p>",
			translate(LINK_FILES_PREFIX + "link-1.creole"));
	}

	public void testParseSimpleLinkTagWithoutDescription() {
		Assert.assertEquals(
			"<p><a href=\"link\">link</a> </p>",
			translate(LINK_FILES_PREFIX + "link-2.creole"));
	}

	public void testParseSimpleTextBoldAndItalics() {
		Assert.assertEquals(
			"<p>Text <strong><em>ItalicAndBold</em></strong> </p>",
			translate(TEXT_FILES_PREFIX + "text-6.creole"));
	}

	public void testParseSimpleTextParagraph() {
		Assert.assertEquals(
			"<p>Simple paragraph </p>",
			translate(TEXT_FILES_PREFIX + "text-1.creole"));
	}

	public void testParseSimpleTextWithBold() {
		Assert.assertEquals(
			"<p>Text with some contents in <strong>bold</strong> </p>",
			translate(TEXT_FILES_PREFIX + "text-4.creole"));
	}

	public void testParseSimpleTextWithBoldAndItalics() {
		Assert.assertEquals(
			"<p>Text with some contents in <strong>bold</strong> and with" +
			" some contents in <em>italic</em> </p>",
			translate(TEXT_FILES_PREFIX + "text-5.creole"));
	}

	public void testParseSimpleTextWithForcedEndline() {
		Assert.assertEquals(
			"<p>Text with <br/>forced line break </p>",
			translate(TEXT_FILES_PREFIX + "text-7.creole"));
	}

	public void testParseSimpleTextWithItalics() {
		Assert.assertEquals(
			"<p>Text with some contents in <em>italic</em> </p>",
			translate(TEXT_FILES_PREFIX + "text-3.creole"));
	}

	public void testParseTableMultipleRowsAndCOlumns() {
		Assert.assertEquals(
			"<table><tr><th>H1</th><th>H2</th><th>H3</th><th>H4</th>" +
			"</tr><tr><td>C1</td><td>C2</td><td>C3</td><td>C4</td></tr>" +
			"<tr><td>C5</td><td>C6</td><td>C7</td><td>C8</td></tr>" +
			"<tr><td>C9</td><td>C10</td><td>C11</td><td>C12</td></tr>" +
			"</table>", translate(TABLE_FILES_PREFIX + "table-2.creole"));
	}

	public void testParseTableOneRowOneColumn() {
		Assert.assertEquals(
			"<table><tr><th>H1</th></tr><tr><td>C1.1</td></tr></table>",
			translate(TABLE_FILES_PREFIX + "table-1.creole"));
	}

	public void testSimpleEscapedCharacter() {
		Assert.assertEquals(
			"<p>ESCAPED1 Esto no está escaped </p>",
			translate(ESCAPE_FILES_PREFIX + "escape-1.creole"));
	}

	public void testTranslateOneNoWikiBlock() {
		Assert.assertEquals(
			"<pre>\t//This// does **not** get [[formatted]]</pre>",
			translate(NOWIKI_FILES_PREFIX + "nowikiblock-1.creole"));
	}

	protected String translate(String file) {
		return getTranslator().translate(parseFile(file));
	}

	// the translator to test
	private XhtmlTranslationVisitor _translator = new XhtmlTranslationVisitor();

}