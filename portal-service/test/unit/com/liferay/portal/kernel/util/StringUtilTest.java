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

	public void testIndexOfAny() throws Exception {
		char[] chars = {CharPool.COLON, CharPool.COMMA};

		assertEquals(-1, StringUtil.indexOfAny(null, chars));
		assertEquals(-1, StringUtil.indexOfAny(null, chars, 1));
		assertEquals(-1, StringUtil.indexOfAny(null, chars, 1, 5));

		assertEquals(4, StringUtil.indexOfAny("test,:test", chars));
		assertEquals(5, StringUtil.indexOfAny("test,:test,:test", chars, 5));
		assertEquals(
			-1, StringUtil.indexOfAny("test,:test,:test", chars, 7, 9));
		assertEquals(
			10, StringUtil.indexOfAny("test,:test,:test", chars, 7, 12));

		String[] strings = {null, "ab", "cd"};

		assertEquals(-1, StringUtil.indexOfAny(null, strings));
		assertEquals(-1, StringUtil.indexOfAny(null, strings, 1));
		assertEquals(-1, StringUtil.indexOfAny(null, strings, 1, 5));

		assertEquals(4, StringUtil.indexOfAny("1234cdab1234", strings));
		assertEquals(6, StringUtil.indexOfAny("1234cdabcd1234", strings, 5));
		assertEquals(
			-1, StringUtil.indexOfAny("1234cdab1234abcd", strings, 7, 9));
		assertEquals(
			12, StringUtil.indexOfAny("1234cdab1234cdab", strings, 7, 15));

		assertEquals(0, StringUtil.indexOfAny("1234", new String[] {""}));
		assertEquals(2, StringUtil.indexOfAny("1234", new String[] {""}, 2));
		assertEquals(2, StringUtil.indexOfAny("1234", new String[] {""}, 2, 4));
	}

	public void testLastIndexOfAny() throws Exception {
		char[] chars = {CharPool.COLON, CharPool.COMMA};

		assertEquals(-1, StringUtil.lastIndexOfAny(null, chars));
		assertEquals(-1, StringUtil.lastIndexOfAny(null, chars, 1));
		assertEquals(-1, StringUtil.lastIndexOfAny(null, chars, 1, 5));

		assertEquals(5, StringUtil.lastIndexOfAny("test,:test", chars));
		assertEquals(
			5, StringUtil.lastIndexOfAny("test,:test,:test", chars, 7));
		assertEquals(
			-1, StringUtil.lastIndexOfAny("test,:test,:test", chars, 7, 9));
		assertEquals(
			11, StringUtil.lastIndexOfAny("test,:test,:test", chars, 7, 12));

		String[] strings = {null, "ab", "cd"};

		assertEquals(-1, StringUtil.lastIndexOfAny(null, strings));
		assertEquals(-1, StringUtil.lastIndexOfAny(null, strings, 1));
		assertEquals(-1, StringUtil.lastIndexOfAny(null, strings, 1, 5));

		assertEquals(6, StringUtil.lastIndexOfAny("1234cdab1234", strings));
		assertEquals(
			4, StringUtil.lastIndexOfAny("1234cdabcd1234", strings, 5));
		assertEquals(
			-1, StringUtil.lastIndexOfAny("1234cdab1234abcd", strings, 7, 9));
		assertEquals(
			12, StringUtil.lastIndexOfAny("1234cdab1234cdab", strings, 7, 14));

		assertEquals(3, StringUtil.lastIndexOfAny("1234", new String[] {""}));
		assertEquals(
			2, StringUtil.lastIndexOfAny("1234", new String[] {""}, 2));
		assertEquals(
			3, StringUtil.lastIndexOfAny("1234", new String[] {""}, 2, 3));
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

}