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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Alexander Chow
 * @author Shuyang Zhou
 * @author Hugo Huijser
 */
public class StringUtilTest extends TestCase {

	public void testAppendParentheticalSuffixInteger() throws Exception {
		String original = "Hello World";
		String expected = "Hello World (2)";

		String actual = StringUtil.appendParentheticalSuffix(original, 2);

		assertEquals(expected, actual);

		original = "Hello (World)";
		expected = "Hello (World) (2)";

		actual = StringUtil.appendParentheticalSuffix(original, 2);

		assertEquals(expected, actual);

		original = "Hello World (2)";
		expected = "Hello World (3)";

		actual = StringUtil.appendParentheticalSuffix(original, 3);

		assertEquals(expected, actual);

		original = "Hello World (2)";
		expected = "Hello World (2) (4)";

		actual = StringUtil.appendParentheticalSuffix(original, 4);

		assertEquals(expected, actual);
	}

	public void testAppendParentheticalSuffixString() throws Exception {
		String original = "Hello";
		String expected = "Hello (World)";

		String actual = StringUtil.appendParentheticalSuffix(original, "World");

		assertEquals(expected, actual);

		original = "Hello (World)";
		expected = "Hello (World) (Liferay)";

		actual = StringUtil.appendParentheticalSuffix(original, "Liferay");

		assertEquals(expected, actual);
	}

	public void testHighlight() throws Exception {
		String original = "Hello World Liferay";
		String expected =
			"<span class=\"highlight\">Hello</span> World " +
				"<span class=\"highlight\">Liferay</span>";

		String actual = StringUtil.highlight(
			original, new String[] {"Hello","Liferay"});

		assertEquals(expected, actual);
	}

	public void testReplaceChar() throws Exception {
		String original = "127.0.0.1";
		String expected = "127_0_0_1";

		String actual = StringUtil.replace(original, '.', '_');

		assertEquals(expected, actual);
	}

	public void testReplaceEmptyString() throws Exception {
		String original = "Hello World HELLO WORLD Hello World";
		String expected = "Hello World HELLO WORLD Hello World";

		String actual = StringUtil.replace(original, "", "Aloha");

		assertEquals(expected, actual);
	}

	public void testReplaceFirstChar() throws Exception {
		String original = "127.0.0.1";
		String expected = "127_0.0.1";

		String actual = StringUtil.replaceFirst(original, '.', '_');

		assertEquals(expected, actual);
	}

	public void testReplaceFirstString() throws Exception {
		String original = "Hello World HELLO WORLD Hello World";
		String expected = "Aloha World HELLO WORLD Hello World";

		String actual = StringUtil.replaceFirst(original, "Hello", "Aloha");

		assertEquals(expected, actual);
	}

	public void testReplaceFirstStringArray() throws Exception {
		String original = "Hello World HELLO WORLD Hello World HELLO WORLD";
		String expected = "Aloha World ALOHA WORLD Hello World HELLO WORLD";

		String actual = StringUtil.replaceFirst(
			original, new String[] {"Hello", "HELLO"},
			new String[] {"Aloha", "ALOHA"});

		assertEquals(expected, actual);
	}

	public void testReplaceLastChar() throws Exception {
		String original = "127.0.0.1";
		String expected = "127.0.0_1";

		String actual = StringUtil.replaceLast(original, '.', '_');

		assertEquals(expected, actual);
	}

	public void testReplaceLastString() throws Exception {
		String original = "Hello World HELLO WORLD Hello World";
		String expected = "Hello World HELLO WORLD Aloha World";

		String actual = StringUtil.replaceLast(original, "Hello", "Aloha");

		assertEquals(expected, actual);
	}

	public void testReplaceLastStringArray() throws Exception {
		String original = "Hello World HELLO WORLD Hello World HELLO WORLD";
		String expected = "Hello World HELLO WORLD Aloha World ALOHA WORLD";

		String actual = StringUtil.replaceLast(
			original, new String[] {"Hello", "HELLO"},
			new String[] {"Aloha", "ALOHA"});

		assertEquals(expected, actual);
	}

	public void testReplaceSpaceString() throws Exception {
		String original = "Hello World HELLO WORLD Hello World";
		String expected = "HelloWorldHELLOWORLDHelloWorld";

		String actual = StringUtil.replace(original, " ", StringPool.BLANK);

		assertEquals(expected, actual);
	}

	public void testReplaceString() throws Exception {
		String original = "Hello World HELLO WORLD Hello World";
		String expected = "Aloha World HELLO WORLD Aloha World";

		String actual = StringUtil.replace(original, "Hello", "Aloha");

		assertEquals(expected, actual);
	}

	public void testReplaceStringArray() throws Exception {
		String original = "Hello World HELLO WORLD Hello World";
		String expected = "Aloha World ALOHA WORLD Aloha World";

		String actual = StringUtil.replace(
			original, new String[] {"Hello", "HELLO"},
			new String[] {"Aloha", "ALOHA"});

		assertEquals(expected, actual);
	}

	public void testSplitLines() {
		String singleLine = "abcdefg";

		String[] lines = StringUtil.splitLines(singleLine);

		assertEquals(1, lines.length);
		assertEquals(singleLine, lines[0]);

		String splitByReturn = "abcd\refg\rhijk\rlmn\r";

		lines = StringUtil.splitLines(splitByReturn);

		assertEquals(4, lines.length);
		assertEquals("abcd", lines[0]);
		assertEquals("efg", lines[1]);
		assertEquals("hijk", lines[2]);
		assertEquals("lmn", lines[3]);

		String splitByNewLine = "abcd\nefg\nhijk\nlmn\n";

		lines = StringUtil.splitLines(splitByNewLine);

		assertEquals(4, lines.length);
		assertEquals("abcd", lines[0]);
		assertEquals("efg", lines[1]);
		assertEquals("hijk", lines[2]);
		assertEquals("lmn", lines[3]);

		String splitByBoth = "abcd\r\nefg\r\nhijk\r\nlmn\r\n";

		lines = StringUtil.splitLines(splitByBoth);

		assertEquals(4, lines.length);
		assertEquals("abcd", lines[0]);
		assertEquals("efg", lines[1]);
		assertEquals("hijk", lines[2]);
		assertEquals("lmn", lines[3]);

		String splitByMix = "abcd\refg\nhijk\n\rlmn\r\n";

		lines = StringUtil.splitLines(splitByMix);

		assertEquals(5, lines.length);
		assertEquals("abcd", lines[0]);
		assertEquals("efg", lines[1]);
		assertEquals("hijk", lines[2]);
		assertEquals("", lines[3]);
		assertEquals("lmn", lines[4]);
	}

	public void testStripChar() {
		String original = " a b  c   d";
		String expected = "abcd";

		String actual = StringUtil.strip(original, ' ');

		assertEquals(expected, actual);
	}

}