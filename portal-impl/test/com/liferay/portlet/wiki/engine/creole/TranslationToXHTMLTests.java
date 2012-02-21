/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Miguel Pastor
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class TranslationToXHTMLTests extends AbstractWikiParserTests {

	@Test
	public void escapedEscapedCharacter() {
		Assert.assertEquals(
			"<p>~&#034;~ is escaped&#034; </p>", translate("escape-2.creole"));
	}

	@Test
	public void parseCorrectlyBoldContentInListItems() {
		Assert.assertEquals(
			"<ul><li> <strong>abcdefg</strong></li></ul>",
			translate("list-6.creole"));
	}

	@Test
	public void parseCorrectlyItalicContentInListItems() {
		Assert.assertEquals(
			"<ul><li> <em>abcdefg</em></li></ul>", translate("list-5.creole"));
	}

	@Test
	public void parseCorrectlyMixedHorizontalBlocks() {
		Assert.assertEquals(
			"<h1>Before Horizontal section</h1><hr/><pre>\tNo wiki section " +
				"after Horizontal section</pre>",
			translate("horizontal-3.creole"));
	}

	@Test
	public void parseCorrectlyMultipleHeadingBlocks() {
		Assert.assertEquals(
			"<h1>Level 1</h1><h2>Level 2</h2><h3>Level 3</h3>",
			translate("heading-10.creole"));
	}

	@Test
	public void parseCorrectlyNoClosedFirstHeadingBlock() {
		Assert.assertEquals(
			"<h1>This is a non closed heading</h1>",
			translate("heading-3.creole"));
	}

	@Test
	public void parseCorrectlyNoClosedSecondHeadingBlock() {
		Assert.assertEquals(
			"<h2>This is a non closed heading</h2>",
			translate("heading-6.creole"));
	}

	@Test
	public void parseCorrectlyNoClosedThirdHeadingBlock() {
		Assert.assertEquals("<h3>Level 3</h3>", translate("heading-7.creole"));
	}

	@Test
	public void parseCorrectlyNoWikiBlockWitBraces() {
		Assert.assertEquals(
			"<pre>{\nfoo\n}\n</pre>", translate("nowikiblock-7.creole"));
	}

	@Test
	public void parseCorrectlyNoWikiBlockWitMultipleAndText() {
		Assert.assertEquals(
			"<pre>public interface Foo {\nvoid foo();\n}\n</pre><p>Outside " +
				"preserve </p>",
			translate("nowikiblock-9.creole"));
	}

	@Test
	public void parseCorrectlyNoWikiBlockWitMultipleBraces() {
		Assert.assertEquals(
			"<pre>public interface Foo {\nvoid foo();\n}\n</pre>",
			translate("nowikiblock-8.creole"));
	}

	@Test
	public void parseCorrectlyOneEmptyFirstHeadingBlock() {
		Assert.assertEquals("<h1>  </h1>", translate("heading-2.creole"));
	}

	@Test
	public void parseCorrectlyOneEmptyNoWikiBlock() {
		Assert.assertEquals("<pre></pre>", translate("nowikiblock-3.creole"));
	}

	@Test
	public void parseCorrectlyOneEmptySecondHeadingBlock() {
		Assert.assertEquals("<h2>  </h2>", translate("heading-5.creole"));
	}

	@Test
	public void parseCorrectlyOneEmptyThirdHeadingBlock() {
		Assert.assertEquals("<h3>  </h3>", translate("heading-8.creole"));
	}

	@Test
	public void parseCorrectlyOneHorizontalBlocks() {
		Assert.assertEquals("<hr/>", translate("horizontal-1.creole"));
	}

	@Test
	public void parseCorrectlyOneItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>ABCDEFG</li></ul>", translate("list-1.creole"));
	}

	@Test
	public void parseCorrectlyOneNonEmptyFirstHeadingBlock() {
		Assert.assertEquals(
			"<h1> Level 1 (largest) </h1>", translate("heading-1.creole"));
	}

	@Test
	public void parseCorrectlyOneNonEmptyNoWikiBlock() {
		Assert.assertEquals(
			"<pre>This is a non \\empty\\ block</pre>",
			translate("nowikiblock-4.creole"));
	}

	@Test
	public void parseCorrectlyOneNonEmptyNoWikiBlockWitBraces() {
		Assert.assertEquals(
			"<p>Preserving </p><pre>.lfr-helper{span}</pre>",
			translate("nowikiblock-6.creole"));
	}

	@Test
	public void parseCorrectlyOneNonEmptyNoWikiBlockWitMultipleLines() {
		Assert.assertEquals(
			"<pre>Multiple\nlines</pre>", translate("nowikiblock-5.creole"));
	}

	@Test
	public void parseCorrectlyOneNonEmptySecondHeadingBlock() {
		Assert.assertEquals("<h2>Level 2</h2>", translate("heading-4.creole"));
	}

	@Test
	public void parseCorrectlyOneNonEmptyThirdHeadingBlock() {
		Assert.assertEquals(
			"<h3>This is a non closed heading</h3>",
			translate("heading-9.creole"));
	}

	@Test
	public void parseCorrectlyOneOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>ABCDEFG</li></ol>", translate("list-7.creole"));
	}

	@Test
	public void parseCorrectlyOrderedNestedLevels() {
		Assert.assertEquals(
			"<ol><li>a</li><ol><li>a.1</li></ol><li>b</li><ol><li>b.1</li>" +
				"<li>b.2</li><li>b.3</li></ol><li>c</li></ol>",
			translate("list-10.creole"));
	}

	@Test
	public void parseCorrectlyThreeItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>1</li><li>2</li><li>3</li></ul>",
			translate("list-3.creole"));
	}

	@Test
	public void parseCorrectlyThreeNoWikiBlock() {
		Assert.assertEquals(
			"<pre>1111</pre><pre>2222</pre><pre>3333</pre>",
			translate("nowikiblock-2.creole"));
	}

	@Test
	public void parseCorrectlyThreeOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>1</li><li>2</li><li>3</li></ol>",
			translate("list-9.creole"));
	}

	@Test
	public void parseCorrectlyTwoHorizontalBlocks() {
		Assert.assertEquals("<hr/><hr/>", translate("horizontal-2.creole"));
	}

	@Test
	public void parseCorrectlyTwoItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>1</li><li>2</li></ul>", translate("list-2.creole"));
	}

	@Test
	public void parseCorrectlyTwoOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>1</li><li>2</li></ol>", translate("list-8.creole"));
	}

	@Test
	public void parseEmpyImageTag() {
		Assert.assertEquals(
			"<p><img src=\"\" /> </p>", translate("image-4.creole"));
	}

	@Test
	public void parseImageAndTextInListItem() {
		Assert.assertEquals(
			"<ul><li><img src=\"imageLink\" alt=\"altText\"/> end.</li></ul>",
			translate("list-17.creole"));
	}

	@Test
	public void parseImageInListItem() {
		Assert.assertEquals(
			"<ul><li><img src=\"imageLink\" alt=\"altText\"/></li></ul>",
			translate("list-16.creole"));
	}

	@Test
	public void parseLinkEmpty() {
		Assert.assertEquals("<p> </p>", translate("link-8.creole"));
	}

	@Test
	public void parseLinkEmptyInHeader() {
		Assert.assertEquals("<h2>  </h2>", translate("link-9.creole"));
	}

	@Test
	public void parseLinkInListItem() {
		Assert.assertEquals(
			"<ul><li><a href=\"l\">a</a></li></ul>",
			translate("list-13.creole"));
	}

	@Test
	public void parseLinkInListItemMixedText() {
		Assert.assertEquals(
			"<ul><li>This is an item with a link <a href=\"l\">a</a> inside " +
				"text</li></ul>",
			translate("list-12.creole"));
	}

	@Test
	public void parseLinkInListItemWithPreText() {
		Assert.assertEquals(
			"<ul><li>This is an item with a link <a href=\"l\">a</a></li></ul>",
			translate("list-11.creole"));
	}

	@Test
	public void parseLinkWithNoAlt() {
		Assert.assertEquals(
			"<p><a href=\"Link\">Link</a> </p>", translate("link-7.creole"));
	}

	@Test
	public void parseMultilineTextParagraph() {
		Assert.assertEquals(
			"<p>Simple P0 Simple P1 Simple P2 Simple P3 Simple P4 Simple P5 " +
				"Simple P6 Simple P7 Simple P8 Simple P9 </p>",
			translate("text-2.creole"));
	}

	@Test
	public void parseMultipleImageTags() {
		Assert.assertEquals(
			"<p><img src=\"L1\" alt=\"A1\"/><img src=\"L2\" alt=\"A2\"/><img " +
				"src=\"L3\" alt=\"A3\"/><img src=\"L4\" alt=\"A4\"/><img " +
					"src=\"L5\" alt=\"A5\"/> </p>",
			translate("image-5.creole"));
	}

	@Test
	public void parseMultipleLinkTags() {
		Assert.assertEquals(
			"<p><a href=\"L\">A</a> <a href=\"L\">A</a> <a href=\"L\">A</a> " +
				"</p>",
			translate("link-3.creole"));
	}

	@Test
	public void parseNestedLists() {
		Assert.assertEquals(
			"<ul><li> 1</li><li> 2</li><ul><li> 2.1</li><ul><li> 2.1.1</li>" +
				"<ul><li> 2.1.1.1</li><li> 2.1.1.2</li></ul><li> 2.1.2</li>" +
					"<li> 2.1.3</li></ul><li> 2.2</li><li> 2.3</li></ul><li>3" +
						"</li></ul>",
			translate("list-18.creole"));
	}

	@Test
	public void parseNoWikiAndTextInListItem() {
		Assert.assertEquals(
			"<ul><li><pre>This is nowiki inside a list item</pre> and <em>" +
				"italics</em></li></ul>",
			translate("list-15.creole"));
	}

	@Test
	public void parseNoWikiInListItem() {
		Assert.assertEquals(
			"<ul><li><pre>This is nowiki inside a list item</pre></li></ul>",
			translate("list-14.creole"));
	}

	@Test
	public void parseOnlySpacesContentInImageTag() {
		Assert.assertEquals(
			"<p><img src=\"L1\" alt=\"A1\"/><img src=\"L2\" alt=\"A2\"/>"  +
				"<img src=\"L3\" alt=\"A3\"/><img src=\"L4\" alt=\"A4\"/>" +
					"<img src=\"L5\" alt=\"A5\"/> </p>",
			translate("image-5.creole"));
	}

	@Test
	public void parseSimpleImageTag() {
		Assert.assertEquals(
			"<p><img src=\"link\" alt=\"alternative text\"/> </p>",
			translate("image-1.creole"));
	}

	@Test
	public void parseSimpleImageTagWithNoAlternative() {
		Assert.assertEquals(
			"<p><img src=\"link\" /> </p>", translate("image-2.creole"));
	}

	@Test
	public void parseSimpleLinkTag() {
		Assert.assertEquals(
			"<p><a href=\"link\">alternative text</a> </p>",
			translate("link-1.creole"));
	}

	@Test
	public void parseSimpleLinkTagWithoutDescription() {
		Assert.assertEquals(
			"<p><a href=\"link\">link</a> </p>", translate("link-2.creole"));
	}

	@Test
	public void parseSimpleTextBoldAndItalics() {
		Assert.assertEquals(
			"<p>Text <strong><em>ItalicAndBold</em></strong> </p>",
			translate("text-6.creole"));
	}

	@Test
	public void parseSimpleTextParagraph() {
		Assert.assertEquals(
			"<p>Simple paragraph </p>", translate("text-1.creole"));
	}

	@Test
	public void parseSimpleTextWithBold() {
		Assert.assertEquals(
			"<p>Text with some content in <strong>bold</strong> </p>",
			translate("text-4.creole"));
	}

	@Test
	public void parseSimpleTextWithBoldAndItalics() {
		Assert.assertEquals(
			"<p>Text with some content in <strong>bold</strong> and with " +
				"some content in <em>italic</em> </p>",
			translate("text-5.creole"));
	}

	@Test
	public void parseSimpleTextWithForcedEndline() {
		Assert.assertEquals(
			"<p>Text with <br/>forced line break </p>",
			translate("text-7.creole"));
	}

	@Test
	public void parseSimpleTextWithItalics() {
		Assert.assertEquals(
			"<p>Text with some content in <em>italic</em> </p>",
			translate("text-3.creole"));
	}

	@Test
	public void parseTableMultipleRowsAndCOlumns() {
		Assert.assertEquals(
			"<table><tr><th>H1</th><th>H2</th><th>H3</th><th>H4</th></tr>" +
				"<tr><td>C1</td><td>C2</td><td>C3</td><td>C4</td></tr><tr>" +
					"<td>C5</td><td>C6</td><td>C7</td><td>C8</td></tr><tr>" +
						"<td>C9</td><td>C10</td><td>C11</td><td>C12</td>" +
							"</tr></table>",
			translate("table-2.creole"));
	}

	@Test
	public void parseTableOneRowOneColumn() {
		Assert.assertEquals(
			"<table><tr><th>H1</th></tr><tr><td>C1.1</td></tr></table>",
			translate("table-1.creole"));
	}

	@Test
	public void simpleEscapedCharacter() {
		Assert.assertEquals(
			"<p>ESCAPED1 Esto no está escaped </p>",
			translate("escape-1.creole"));
	}

	@Test
	public void translateOneNoWikiBlock() {
		Assert.assertEquals(
				"<pre>\t//This// does **not** get [[formatted]]</pre>",
				translate("nowikiblock-1.creole"));
	}

	protected String translate(String fileName) {
		return _xhtmlTranslationVisitor.translate(getWikiPageNode(fileName));
	}

	private XhtmlTranslationVisitor _xhtmlTranslationVisitor =
		new XhtmlTranslationVisitor();

}