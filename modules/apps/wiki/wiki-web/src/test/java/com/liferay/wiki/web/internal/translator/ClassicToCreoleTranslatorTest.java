/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.wiki.web.internal.translator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jorge Ferrer
 */
public class ClassicToCreoleTranslatorTest {

	public ClassicToCreoleTranslatorTest() {
		_classicToCreoleTranslator = new ClassicToCreoleTranslator();
	}

	@Test
	public void testBold() throws Exception {
		String content = "This is '''bold'''.";

		String expected = "This is **bold**.";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testBoldItalics() throws Exception {
		String content = "This is '''''bold and italics'''''.";

		String expected = "This is **//bold and italics//**.";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCamelCase() throws Exception {
		String content = "text CamelCase text";

		String expected = "text [[CamelCase]] text";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testHeader1() throws Exception {
		String content = "= Header 1 =";

		String expected = content;
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testHorizontalRule() throws Exception {
		String content = "\n----";

		String expected = content;
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIndentedParagraph() throws Exception {
		String content = "\t:\tparagraph";

		String expected = "paragraph";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testItalics() throws Exception {
		String content = "This is ''italics''.";

		String expected = "This is //italics//.";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLink() throws Exception {
		String content = "[Link]";

		String expected = "[[Link]]";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLinkWithLabel() throws Exception {
		String content = "[Link This is the label]";

		String expected = "[[Link|This is the label]]";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testListItem() throws Exception {
		String content = "\t* item";

		String expected = "* item";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testListSubitem() throws Exception {
		String content = "\t\t* subitem";

		String expected = "** subitem";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testListSubsubitem() throws Exception {
		String content = "\t\t\t* subsubitem";

		String expected = "*** subsubitem";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testListSubsubsubitem() throws Exception {
		String content = "\t\t\t\t* subsubitem";

		String expected = "**** subsubitem";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testMonospace() throws Exception {
		String content = "previous line\n monospace\nnext line";

		String expected = "previous line\n{{{\n monospace\n}}}\nnext line";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testMultilinePre() throws Exception {
		String content = "previous line\n monospace\n second line\nnext line";

		String expected =
			"previous line\n{{{\n monospace\n second line\n}}}\nnext line";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testMultilinePreWithBraces() throws Exception {
		String content = "previous line\n monospace\n {second line}\nnext line";

		String expected =
			"previous line\n{{{\n monospace\n {second line}\n}}}\nnext line";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testNotListItem() throws Exception {
		String content = "\t*item";

		String expected = content;
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testOrderedListItem() throws Exception {
		String content = "\t1 item";

		String expected = "# item";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testOrderedListSubitem() throws Exception {
		String content = "\t\t1 subitem";

		String expected = "## subitem";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testOrderedListSubsubitem() throws Exception {
		String content = "\t\t\t1 subsubitem";

		String expected = "### subsubitem";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testOrderedListSubsubsubitem() throws Exception {
		String content = "\t\t\t\t1 subsubitem";

		String expected = "#### subsubitem";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testQuoted() throws Exception {
		String content = "This is 'quoted'.";

		String expected = content;
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testTermDefinition() throws Exception {
		String content = "\tterm:\tdefinition";

		String expected = "**term**:\ndefinition";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testURL() throws Exception {
		String content = "text[http://www.liferay.com]text";

		String expected = "text[[http://www.liferay.com]]text";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testURLWithLabel() throws Exception {
		String content = "[http://www.liferay.com This is the label]";

		String expected = "[[http://www.liferay.com|This is the label]]";
		String actual = _translate(content);

		Assert.assertEquals(expected, actual);
	}

	private String _translate(String content) {
		return _classicToCreoleTranslator.translate(content);
	}

	private final ClassicToCreoleTranslator _classicToCreoleTranslator;

}