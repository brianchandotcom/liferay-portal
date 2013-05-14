/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.parser.Creole10Lexer;
import com.liferay.portal.parsers.creole.parser.Creole10Parser;
import com.liferay.portal.parsers.creole.visitor.impl.XhtmlTranslationVisitor;
import com.liferay.portal.test.mockito.ReturnArgumentCalledAnswer;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Miguel Pastor
 * @author Manuel de la Peña
 */

/**
 * @author Miguel Pastor
 * @author Manuel de la Peña
 */
@PrepareForTest(HtmlUtil.class)
@RunWith(PowerMockRunner.class)
public class TranslationToXHTMLTest extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(HtmlUtil.class);

		when(
			HtmlUtil.escape(Mockito.anyString())
		).then(
			new ReturnArgumentCalledAnswer<String>(0)
		);
	}

	@After
	public void tearDown() {
		verifyStatic();
	}

	@Test
	public void testEscapedEscapedCharacter() {
		Assert.assertEquals(
			"<p>~\"~ is escaped\" </p>", translate("escape-2.creole"));
	}

	@Test
	public void testParseCorrectlyBoldContentInListItems() {
		Assert.assertEquals(
			"<ul><li> <strong>abcdefg</strong></li></ul>",
			translate("list-6.creole"));
	}

	@Test
	public void testParseCorrectlyComplexNestedList() {
		Assert.assertEquals(
			"<ul><li>a<ul><li>a.1</li></ul></li><li>b<ul><li>b.1</li>" +
				"<li>b.2</li><li>b.3</li></ul></li><li>c</li></ul>",
			translate("list-4.creole"));
	}

	@Test
	public void testParseCorrectlyItalicContentInListItems() {
		Assert.assertEquals(
			"<ul><li> <em>abcdefg</em></li></ul>", translate("list-5.creole"));
	}

	@Test
	public void testParseCorrectlyMixedHorizontalBlocks() {
		Assert.assertEquals(
			"<h1>Before Horizontal section</h1><hr/><pre>\tNo wiki section " +
				"after Horizontal section</pre>",
			translate("horizontal-3.creole"));
	}

	@Test
	public void testParseCorrectlyMultipleHeadingBlocks() {
		Assert.assertEquals(
			"<h1>Level 1</h1><h2>Level 2</h2><h3>Level 3</h3>",
			translate("heading-10.creole"));
	}

	@Test
	public void testParseCorrectlyNoClosedFirstHeadingBlock() {
		Assert.assertEquals(
			"<h1>This is a non closed heading</h1>",
			translate("heading-3.creole"));
	}

	@Test
	public void testParseCorrectlyNoClosedSecondHeadingBlock() {
		Assert.assertEquals(
			"<h2>This is a non closed heading</h2>",
			translate("heading-6.creole"));
	}

	@Test
	public void testParseCorrectlyNoClosedThirdHeadingBlock() {
		Assert.assertEquals("<h3>Level 3</h3>", translate("heading-7.creole"));
	}

	@Test
	public void testParseCorrectlyNoWikiBlockInline() {
		Assert.assertEquals(
			"<p><pre> Inline </pre></p>", translate("nowikiblock-10.creole"));
	}

	@Test
	public void testParseCorrectlyNoWikiBlockWithBraces() {
		Assert.assertEquals(
			"<pre>{" + _NEW_LINE + "foo" + _NEW_LINE + "}" + _NEW_LINE +
				"</pre>",
			toUnix(translate("nowikiblock-7.creole")));
	}

	@Test
	public void testParseCorrectlyNoWikiBlockWithMultipleAndText() {
		Assert.assertEquals(
			"<pre>public interface Foo {" + _NEW_LINE + "void foo();" +
				_NEW_LINE + "}" + _NEW_LINE + "</pre><p>Outside preserve </p>",
			toUnix(translate("nowikiblock-9.creole")));
	}

	@Test
	public void testParseCorrectlyNoWikiBlockWithMultipleBraces() {
		Assert.assertEquals(
			"<pre>public interface Foo {" + _NEW_LINE + "void foo();" +
				_NEW_LINE + "}" + _NEW_LINE + "</pre>",
			toUnix(translate("nowikiblock-8.creole")));
	}

	@Test
	public void testParseCorrectlyOneEmptyFirstHeadingBlock() {
		Assert.assertEquals("<h1>  </h1>", translate("heading-2.creole"));
	}

	@Test
	public void testParseCorrectlyOneEmptyNoWikiBlock() {
		Assert.assertEquals("<pre></pre>", translate("nowikiblock-3.creole"));
	}

	@Test
	public void testParseCorrectlyOneEmptySecondHeadingBlock() {
		Assert.assertEquals("<h2>  </h2>", translate("heading-5.creole"));
	}

	@Test
	public void testParseCorrectlyOneEmptyThirdHeadingBlock() {
		Assert.assertEquals("<h3>  </h3>", translate("heading-8.creole"));
	}

	@Test
	public void testParseCorrectlyOneHorizontalBlocks() {
		Assert.assertEquals("<hr/>", translate("horizontal-1.creole"));
	}

	@Test
	public void testParseCorrectlyOneItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>ABCDEFG</li></ul>", translate("list-1.creole"));
	}

	@Test
	public void testParseCorrectlyOneNonEmptyFirstHeadingBlock() {
		Assert.assertEquals(
			"<h1> Level 1 (largest) </h1>", translate("heading-1.creole"));
	}

	@Test
	public void testParseCorrectlyOneNonEmptyNoWikiBlock() {
		Assert.assertEquals(
			"<pre>This is a non \\empty\\ block</pre>",
			translate("nowikiblock-4.creole"));
	}

	@Test
	public void testParseCorrectlyOneNonEmptyNoWikiBlockWithBraces() {
		Assert.assertEquals(
			"<p>Preserving </p><pre>.lfr-helper{span}</pre>",
			translate("nowikiblock-6.creole"));
	}

	@Test
	public void testParseCorrectlyOneNonEmptyNoWikiBlockWithMultipleLines() {
		Assert.assertEquals(
			"<pre>Multiple" + _NEW_LINE + "lines</pre>",
			toUnix(translate("nowikiblock-5.creole")));
	}

	@Test
	public void testParseCorrectlyOneNonEmptySecondHeadingBlock() {
		Assert.assertEquals("<h2>Level 2</h2>", translate("heading-4.creole"));
	}

	@Test
	public void testParseCorrectlyOneNonEmptyThirdHeadingBlock() {
		Assert.assertEquals(
			"<h3>This is a non closed heading</h3>",
			translate("heading-9.creole"));
	}

	@Test
	public void testParseCorrectlyOneOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>ABCDEFG</li></ol>", translate("list-7.creole"));
	}

	@Test
	public void testParseCorrectlyOrderedNestedLevels() {
		Assert.assertEquals(
			"<ol><li>a<ol><li>a.1</li></ol></li><li>b<ol><li>b.1</li>" +
				"<li>b.2</li><li>b.3</li></ol></li><li>c</li></ol>",
			translate("list-10.creole"));
	}

	@Test
	public void testParseCorrectlyThreeItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>1</li><li>2</li><li>3</li></ul>",
			translate("list-3.creole"));
	}

	@Test
	public void testParseCorrectlyThreeNoWikiBlock() {
		Assert.assertEquals(
			"<pre>1111</pre><pre>2222</pre><pre>3333</pre>",
			translate("nowikiblock-2.creole"));
	}

	@Test
	public void testParseCorrectlyThreeOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>1</li><li>2</li><li>3</li></ol>",
			translate("list-9.creole"));
	}

	@Test
	public void testParseCorrectlyTwoHorizontalBlocks() {
		Assert.assertEquals("<hr/><hr/>", translate("horizontal-2.creole"));
	}

	@Test
	public void testParseCorrectlyTwoItemFirstLevel() {
		Assert.assertEquals(
			"<ul><li>1</li><li>2</li></ul>", translate("list-2.creole"));
	}

	@Test
	public void testParseCorrectlyTwoOrderedItemFirstLevel() {
		Assert.assertEquals(
			"<ol><li>1</li><li>2</li></ol>", translate("list-8.creole"));
	}

	@Test
	public void testParseEmpyImageTag() {
		Assert.assertEquals(
			"<p><img src=\"\" /> </p>", translate("image-4.creole"));
	}

	@Test
	public void testParseImageAndTextInListItem() {
		Assert.assertEquals(
			"<ul><li><img src=\"imageLink\" alt=\"altText\"/> end.</li></ul>",
			translate("list-17.creole"));
	}

	@Test
	public void testParseImageInListItem() {
		Assert.assertEquals(
			"<ul><li><img src=\"imageLink\" alt=\"altText\"/></li></ul>",
			translate("list-16.creole"));
	}

	@Test
	public void testParseLinkEmpty() {
		Assert.assertEquals("<p> </p>", translate("link-8.creole"));
	}

	@Test
	public void testParseLinkEmptyInHeader() {
		Assert.assertEquals("<h2>  </h2>", translate("link-9.creole"));
	}

	@Test
	public void testParseLinkInListItem() {
		Assert.assertEquals(
			"<ul><li><a href=\"l\">a</a></li></ul>",
			translate("list-13.creole"));
	}

	@Test
	public void testParseLinkInListItemMixedText() {
		Assert.assertEquals(
			"<ul><li>This is an item with a link <a href=\"l\">a</a> inside " +
				"text</li></ul>",
			translate("list-12.creole"));
	}

	@Test
	public void testParseLinkInListItemWithPreText() {
		Assert.assertEquals(
			"<ul><li>This is an item with a link <a href=\"l\">a</a></li></ul>",
			translate("list-11.creole"));
	}

	@Test
	public void testParseLinkWithNoAlt() {
		Assert.assertEquals(
			"<p><a href=\"Link\">Link</a> </p>", translate("link-7.creole"));
	}

	@Test
	public void testParseMixedList1() {
		Assert.assertEquals(
			"<ul><li> U1</li></ul><ol><li> O1</li></ol>",
			translate("mixed-list-1.creole"));
	}

	@Test
	public void testParseMixedList2() {
		Assert.assertEquals(
			"<ol><li> 1<ol><li> 1.1</li><li> 1.2</li><li> 1.3</li></ol></li>" +
				"</ol><ul><li> A<ul><li> A.A</li><li> A.B</li></ul></li></ul>",
			translate("mixed-list-2.creole"));
	}

	@Test
	public void testParseMixedList3() {
		Assert.assertEquals(
			"<ol><li> T1<ol><li> T1.1</li></ol></li><li> T2</li><li> T3" +
				"</li></ol><ul><li> Divider 1<ul><li> Divider 2a</li>" +
					"<li> Divider 2b<ul><li> Divider 3</li></ul></li>" +
						"</ul></li></ul><ol><li> T3.2</li>" +
							"<li> T3.3</li></ol>",
		translate("mixed-list-3.creole"));
	}

	@Test
	public void testParseMultilineTextParagraph() {
		Assert.assertEquals(
			"<p>Simple P0 Simple P1 Simple P2 Simple P3 Simple P4 Simple P5 " +
				"Simple P6 Simple P7 Simple P8 Simple P9 </p>",
			translate("text-2.creole"));
	}

	@Test
	public void testParseMultipleImageTags() {
		Assert.assertEquals(
			"<p><img src=\"L1\" alt=\"A1\"/><img src=\"L2\" alt=\"A2\"/><img " +
				"src=\"L3\" alt=\"A3\"/><img src=\"L4\" alt=\"A4\"/><img " +
					"src=\"L5\" alt=\"A5\"/> </p>",
			translate("image-5.creole"));
	}

	@Test
	public void testParseMultipleLinkTags() {
		Assert.assertEquals(
			"<p><a href=\"L\">A</a> <a href=\"L\">A</a> <a href=\"L\">A</a> " +
				"</p>",
			translate("link-3.creole"));
	}

	@Test
	public void testParseNestedLists() {
		Assert.assertEquals(
			"<ul><li> 1</li><li> 2<ul><li> 2.1<ul><li> 2.1.1<ul>" +
				"<li> 2.1.1.1</li><li> 2.1.1.2</li></ul></li><li> 2.1.2</li>" +
					"<li> 2.1.3</li></ul></li><li> 2.2</li><li> 2.3</li></ul>" +
						"</li><li>3</li></ul>",
			translate("list-18.creole"));
	}

	@Test
	public void testParseNoWikiAndTextInListItem() {
		Assert.assertEquals(
			"<ul><li><pre>This is nowiki inside a list item</pre> and <em>" +
				"italics</em></li></ul>",
			translate("list-15.creole"));
	}

	@Test
	public void testParseNoWikiInListItem() {
		Assert.assertEquals(
			"<ul><li><pre>This is nowiki inside a list item</pre></li></ul>",
			translate("list-14.creole"));
	}

	@Test
	public void testParseOnlySpacesContentInImageTag() {
		Assert.assertEquals(
			"<p><img src=\"L1\" alt=\"A1\"/><img src=\"L2\" alt=\"A2\"/>"  +
				"<img src=\"L3\" alt=\"A3\"/><img src=\"L4\" alt=\"A4\"/>" +
					"<img src=\"L5\" alt=\"A5\"/> </p>",
			translate("image-5.creole"));
	}

	@Test
	public void testParseSimpleImageTag() {
		Assert.assertEquals(
			"<p><img src=\"link\" alt=\"alternative text\"/> </p>",
			translate("image-1.creole"));
	}

	@Test
	public void testParseSimpleImageTagWithNoAlternative() {
		Assert.assertEquals(
			"<p><img src=\"link\" /> </p>", translate("image-2.creole"));
	}

	@Test
	public void testParseSimpleLinkTag() {
		Assert.assertEquals(
			"<p><a href=\"link\">alternative text</a> </p>",
			translate("link-1.creole"));
	}

	@Test
	public void testParseSimpleLinkTagWithoutDescription() {
		Assert.assertEquals(
			"<p><a href=\"link\">link</a> </p>", translate("link-2.creole"));
	}

	@Test
	public void testParseSimpleTextBoldAndItalics() {
		Assert.assertEquals(
			"<p>Text <strong><em>ItalicAndBold</em></strong> </p>",
			translate("text-6.creole"));
	}

	@Test
	public void testParseSimpleTextParagraph() {
		Assert.assertEquals(
			"<p>Simple paragraph </p>", translate("text-1.creole"));
	}

	@Test
	public void testParseSimpleTextWithBold() {
		Assert.assertEquals(
			"<p>Text with some content in <strong>bold</strong> </p>",
			translate("text-4.creole"));
	}

	@Test
	public void testParseSimpleTextWithBoldAndItalics() {
		Assert.assertEquals(
			"<p>Text with some content in <strong>bold</strong> and with " +
				"some content in <em>italic</em> </p>",
			translate("text-5.creole"));
	}

	@Test
	public void testParseSimpleTextWithForcedEndline() {
		Assert.assertEquals(
			"<p>Text with <br/>forced line break </p>",
			translate("text-7.creole"));
	}

	@Test
	public void testParseSimpleTextWithItalics() {
		Assert.assertEquals(
			"<p>Text with some content in <em>italic</em> </p>",
			translate("text-3.creole"));
	}

	@Test
	public void testParseTableMultipleRowsAndColumns() {
		Assert.assertEquals(
			"<table><tr><th>H1</th><th>H2</th><th>H3</th><th>H4</th></tr>" +
				"<tr><td>C1</td><td>C2</td><td>C3</td><td>C4</td></tr><tr>" +
					"<td>C5</td><td>C6</td><td>C7</td><td>C8</td></tr><tr>" +
						"<td>C9</td><td>C10</td><td>C11</td><td>C12</td>" +
							"</tr></table>",
			translate("table-2.creole"));
	}

	@Test
	public void testParseTableOfContents() {
		Assert.assertEquals(
			"<h2> Level 1  </h2><h2> Level 2 </h2>",
			translate("tableofcontents-1.creole"));
	}

	@Test
	public void testParseTableOfContentsWithTitle() {
		Assert.assertEquals(
			"<h2> Level 1 (largest) </h2><p><strong>L1 text</strong> </p>" +
				"<h2> Level 2 </h2><h3> Level 3 </h3>",
			translate("tableofcontents-2.creole"));
	}

	@Test
	public void testParseTableOneRowOneColumn() {
		Assert.assertEquals(
			"<table><tr><th>H1</th></tr><tr><td>C1.1</td></tr></table>",
			translate("table-1.creole"));
	}

	@Test
	public void testSimpleEscapedCharacter() {
		Assert.assertEquals(
			"<p>ESCAPED1 This is not escaped </p>",
			translate("escape-1.creole"));
	}

	@Test
	public void testTranslateOneNoWikiBlock() {
		Assert.assertEquals(
			"<pre>\t//This// does **not** get [[formatted]]</pre>",
			translate("nowikiblock-1.creole"));
	}

	protected Creole10Parser getCreole10Parser(String fileName)
		throws IOException {

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);

		Creole10Lexer creole10Lexer = new Creole10Lexer(antlrInputStream);

		CommonTokenStream commonTokenStream = new CommonTokenStream(
			creole10Lexer);

		return new Creole10Parser(commonTokenStream);
	}

	protected WikiPageNode getWikiPageNode(String fileName) {
		try {
			_creole10parser = getCreole10Parser(fileName);

			_creole10parser.wikipage();
		}
		catch (IOException ioe) {
			Assert.fail("File does not exist");
		}
		catch (RecognitionException re) {
			Assert.fail("File could not be parsed");
		}

		return _creole10parser.getWikiPageNode();
	}

	protected String toUnix(String text) {
		return StringUtil.replace(
			text, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	protected String translate(String fileName) {
		return _xhtmlTranslationVisitor.translate(getWikiPageNode(fileName));
	}

	private static final String _NEW_LINE = StringPool.NEW_LINE;

	private Creole10Parser _creole10parser;
	private XhtmlTranslationVisitor _xhtmlTranslationVisitor =
		new XhtmlTranslationVisitor();

}