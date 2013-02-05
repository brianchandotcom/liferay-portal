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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Alexander Chow
 * @author Shuyang Zhou
 * @author Hugo Huijser
 */
public class StringUtilTest extends TestCase {

	public void testAppendParentheticalSuffixInteger() throws Exception {
		assertEquals(
			"Hello World (2)",
			StringUtil.appendParentheticalSuffix("Hello World", 2));
		assertEquals(
			"Hello (World) (2)",
			StringUtil.appendParentheticalSuffix("Hello (World)", 2));
		assertEquals(
			"Hello World (3)",
			StringUtil.appendParentheticalSuffix("Hello World (2)", 3));
		assertEquals(
			"Hello World (2) (4)",
			StringUtil.appendParentheticalSuffix("Hello World (2)", 4));
	}

	public void testAppendParentheticalSuffixString() throws Exception {
		assertEquals(
			"Hello (World)",
			StringUtil.appendParentheticalSuffix("Hello", "World"));
		assertEquals(
			"Hello (World) (Liferay)",
			StringUtil.appendParentheticalSuffix("Hello (World)", "Liferay"));
	}

	public void testHighlight() throws Exception {
		assertEquals(
			"<span class=\"highlight\">Hello</span> World <span " +
				"class=\"highlight\">Liferay</span>",
			StringUtil.highlight(
				"Hello World Liferay", new String[] {"Hello","Liferay"}));
	}

	public void testReplaceChar() throws Exception {
		assertEquals("127_0_0_1", StringUtil.replace("127.0.0.1", '.', '_'));
	}

	public void testReplaceEmptyString() throws Exception {
		assertEquals(
			"Hello World HELLO WORLD Hello World",
			StringUtil.replace(
				"Hello World HELLO WORLD Hello World", "", "Aloha"));
	}

	public void testReplaceFirstChar() throws Exception {
		assertEquals(
			"127_0.0.1", StringUtil.replaceFirst("127.0.0.1", '.', '_'));
	}

	public void testReplaceFirstString() throws Exception {
		assertEquals(
			"Aloha World HELLO WORLD Hello World",
			StringUtil.replaceFirst(
				"Hello World HELLO WORLD Hello World", "Hello", "Aloha"));
	}

	public void testReplaceFirstStringArray() throws Exception {
		assertEquals(
			"Aloha World ALOHA WORLD Hello World HELLO WORLD",
			StringUtil.replaceFirst(
				"Hello World HELLO WORLD Hello World HELLO WORLD",
				new String[] {"Hello", "HELLO"},
				new String[] {"Aloha", "ALOHA"}));
	}

	public void testReplaceLastChar() throws Exception {
		assertEquals(
			"127.0.0_1", StringUtil.replaceLast("127.0.0.1", '.', '_'));
	}

	public void testReplaceLastString() throws Exception {
		assertEquals(
			"Hello World HELLO WORLD Aloha World",
			StringUtil.replaceLast(
				"Hello World HELLO WORLD Hello World", "Hello", "Aloha"));
	}

	public void testReplaceLastStringArray() throws Exception {
		assertEquals(
			"Hello World HELLO WORLD Aloha World ALOHA WORLD",
			StringUtil.replaceLast(
				"Hello World HELLO WORLD Hello World HELLO WORLD",
				new String[] {"Hello", "HELLO"},
				new String[] {"Aloha", "ALOHA"}));
	}

	public void testReplaceSpaceString() throws Exception {
		assertEquals(
			"HelloWorldHELLOWORLDHelloWorld",
			StringUtil.replace(
				"Hello World HELLO WORLD Hello World", " ", StringPool.BLANK));
	}

	public void testReplaceString() throws Exception {
		assertEquals(
			"Aloha World HELLO WORLD Aloha World",
			StringUtil.replace(
				"Hello World HELLO WORLD Hello World", "Hello", "Aloha"));
	}

	public void testReplaceStringArray() throws Exception {
		assertEquals(
			"Aloha World ALOHA WORLD Aloha World",
			StringUtil.replace(
				"Hello World HELLO WORLD Hello World",
				new String[] {"Hello", "HELLO"},
				new String[] {"Aloha", "ALOHA"}));
	}

	public void testShortenString() {
		assertEquals(
			"Hello World HELLO...",
			StringUtil.shorten("Hello World HELLO WORLD Hello World"));
		assertEquals("Hi Hello", StringUtil.shorten("Hi Hello", 8));
		assertEquals("Hello...", StringUtil.shorten("Hello World", 8));
		assertEquals("Hi...", StringUtil.shorten("Hi Hello World", 8));
		assertEquals("...", StringUtil.shorten(" Hello World", 8));
		assertEquals(
			"HelloWorldHe... etc.",
			StringUtil.shorten(
				"HelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHello", 20,
				"... etc."));
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
		assertEquals("abcd", StringUtil.strip(" a b  c   d", ' '));
	}

	public void testTrim() {

		// 1) Null String

		assertNull(StringUtil.trim(null));

		// 2) Blank String

		assertSame(StringPool.BLANK, StringUtil.trim(StringPool.BLANK));

		// 3) Spaces String

		String spacesString = " \t\r\n";

		assertSame(StringPool.BLANK, StringUtil.trim(spacesString));

		// 4) Not trimable

		String testString = "a";

		assertSame(testString, StringUtil.trim(testString));

		testString = "ab";

		assertSame(testString, StringUtil.trim(testString));

		// 5) Leading spaces

		String leadingSpacesString = " \t\r\n" + testString;

		assertEquals(testString, StringUtil.trim(leadingSpacesString));

		// 6) Trailing spaces

		String trailingSpacesString = testString + " \t\r\n";

		assertEquals(testString, StringUtil.trim(trailingSpacesString));

		// 7) Surrounding spaces

		String surroundingSpacesString = " \t\r\n" + testString + " \t\r\n";

		assertEquals(testString, StringUtil.trim(surroundingSpacesString));
	}

	public void testTrimLeading() {

		// 1) Null String

		assertNull(StringUtil.trimLeading(null));

		// 2) Blank String

		assertSame(StringPool.BLANK, StringUtil.trimLeading(StringPool.BLANK));

		// 3) Spaces String

		String spacesString = " \t\r\n";

		assertSame(StringPool.BLANK, StringUtil.trimLeading(spacesString));

		// 4) Not trimable

		String testString = "a";

		assertSame(testString, StringUtil.trimLeading(testString));

		testString = "ab";

		assertSame(testString, StringUtil.trimLeading(testString));

		// 5) Leading spaces

		String leadingSpacesString = " \t\r\n" + testString;

		assertEquals(testString, StringUtil.trimLeading(leadingSpacesString));

		// 6) Trailing spaces

		String trailingSpacesString = testString + " \t\r\n";

		assertSame(
			trailingSpacesString, StringUtil.trimLeading(trailingSpacesString));

		// 7) Surrounding spaces

		String surroundingSpacesString = " \t\r\n" + testString + " \t\r\n";

		assertEquals(
			testString + " \t\r\n",
			StringUtil.trimLeading(surroundingSpacesString));
	}

	public void testTrimLeadingWithExceptions() {

		// 1) Null String

		assertNull(StringUtil.trimLeading(null, null));

		// 2) Null exceptions

		assertSame(StringPool.BLANK, StringUtil.trimLeading(" ", null));

		// 3) Zero exceptions

		assertSame(StringPool.BLANK, StringUtil.trimLeading(" ", new char[0]));

		char[] exceptions = {'\t', '\r'};

		// 4) Blank String

		assertSame(
			StringPool.BLANK,
			StringUtil.trimLeading(StringPool.BLANK, exceptions));

		// 5) Spaces String

		String spacesString = " \t\r\n";

		assertEquals(
			"\t\r\n", StringUtil.trimLeading(spacesString, exceptions));

		// 6) Not trimable

		String testString = "\t";

		assertSame(testString, StringUtil.trimLeading(testString, exceptions));

		testString = "\t\r";

		assertSame(testString, StringUtil.trimLeading(testString, exceptions));

		// 7) All trimable

		assertSame(StringPool.BLANK, StringUtil.trimLeading(" \n", exceptions));

		// 8) Leading spaces

		String leadingSpacesString = " \t\r\n" + testString;

		assertEquals(
			"\t\r\n" + testString,
			StringUtil.trimLeading(leadingSpacesString, exceptions));

		// 9) Trailing spaces

		String trailingSpacesString = testString + " \t\r\n";

		assertSame(
			trailingSpacesString,
			StringUtil.trimLeading(trailingSpacesString, exceptions));

		// 10) Surrounding spaces

		String surroundingSpacesString = " \t\r\n" + testString + " \t\r\n";

		assertEquals(
			"\t\r\n" + testString + " \t\r\n",
			StringUtil.trimLeading(surroundingSpacesString, exceptions));
	}

	public void testTrimTrailing() {

		// 1) Null String

		assertNull(StringUtil.trimTrailing(null));

		// 2) Blank String

		assertSame(StringPool.BLANK, StringUtil.trimTrailing(StringPool.BLANK));

		// 3) Spaces String

		String spacesString = " \t\r\n";

		assertSame(StringPool.BLANK, StringUtil.trimTrailing(spacesString));

		// 4) Not trimable

		String testString = "a";

		assertSame(testString, StringUtil.trimTrailing(testString));

		testString = "ab";

		assertSame(testString, StringUtil.trimTrailing(testString));

		// 5) Leading spaces

		String leadingSpacesString = " \t\r\n" + testString;

		assertSame(
			leadingSpacesString, StringUtil.trimTrailing(leadingSpacesString));

		// 6) Trailing spaces

		String trailingSpacesString = testString + " \t\r\n";

		assertEquals(testString, StringUtil.trimTrailing(trailingSpacesString));

		// 7) Surrounding spaces

		String surroundingSpacesString = " \t\r\n" + testString + " \t\r\n";

		assertEquals(
			" \t\r\n" + testString,
			StringUtil.trimTrailing(surroundingSpacesString));
	}

	public void testTrimTrailingWithExceptions() {

		// 1) Null String

		assertNull(StringUtil.trimTrailing(null, null));

		// 2) Null exceptions

		assertSame(StringPool.BLANK, StringUtil.trimTrailing(" ", null));

		// 3) Zero exceptions

		assertSame(StringPool.BLANK, StringUtil.trimTrailing(" ", new char[0]));

		char[] exceptions = {'\t', '\r'};

		// 4) Blank String

		assertSame(
			StringPool.BLANK,
			StringUtil.trimTrailing(StringPool.BLANK, exceptions));

		// 5) Spaces String

		String spacesString = " \t\r\n";

		assertEquals(
			" \t\r", StringUtil.trimTrailing(spacesString, exceptions));

		// 6) Not trimable

		String testString = "\t";

		assertSame(testString, StringUtil.trimTrailing(testString, exceptions));

		testString = "\t\r";

		assertSame(testString, StringUtil.trimTrailing(testString, exceptions));

		// 7) All trimable

		assertSame(
			StringPool.BLANK, StringUtil.trimTrailing(" \n", exceptions));

		// 8) Leading spaces

		String leadingSpacesString = " \t\r\n" + testString;

		assertSame(
			leadingSpacesString,
			StringUtil.trimTrailing(leadingSpacesString, exceptions));

		// 9) Trailing spaces

		String trailingSpacesString = testString + " \t\r\n";

		assertEquals(
			testString + " \t\r",
			StringUtil.trimTrailing(trailingSpacesString, exceptions));

		// 10) Surrounding spaces

		String surroundingSpacesString = " \t\r\n" + testString + " \t\r\n";

		assertEquals(
			" \t\r\n" + testString + " \t\r",
			StringUtil.trimTrailing(surroundingSpacesString, exceptions));
	}

	public void testTrimWithExceptions() {

		// 1) Null String

		assertNull(StringUtil.trim(null, null));

		// 2) Null exceptions

		assertSame(StringPool.BLANK, StringUtil.trim(" ", null));

		// 3) Zero exceptions

		assertSame(StringPool.BLANK, StringUtil.trim(" ", new char[0]));

		char[] exceptions = {'\t', '\r'};

		// 4) Blank String

		assertSame(
			StringPool.BLANK, StringUtil.trim(StringPool.BLANK, exceptions));

		// 5) Spaces String

		String spacesString = " \t\r\n";

		assertEquals("\t\r", StringUtil.trim(spacesString, exceptions));

		// 6) Not trimable

		String testString = "\t";

		assertSame(testString, StringUtil.trim(testString, exceptions));

		testString = "\t\r";

		assertSame(testString, StringUtil.trim(testString, exceptions));

		// 7) All trimable

		assertSame(StringPool.BLANK, StringUtil.trim(" \n", exceptions));

		// 8) Leading spaces

		String leadingSpacesString = " \t\r\n" + testString;

		assertEquals(
			"\t\r\n" + testString,
			StringUtil.trim(leadingSpacesString, exceptions));

		// 9) Trailing spaces

		String trailingSpacesString = testString + " \t\r\n";

		assertEquals(
			testString + " \t\r",
			StringUtil.trim(trailingSpacesString, exceptions));

		// 10) Surrounding spaces

		String surroundingSpacesString = " \t\r\n" + testString + " \t\r\n";

		assertEquals(
			"\t\r\n" + testString + " \t\r",
			StringUtil.trim(surroundingSpacesString, exceptions));
	}

}