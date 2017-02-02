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

package com.liferay.portal.servlet.filters.strip;

import com.liferay.portal.cache.key.HashCodeCacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.minifier.GoogleJavaScriptMinifier;
import com.liferay.portal.minifier.MinifierUtil;

import java.io.StringWriter;

import java.nio.CharBuffer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Miguel Pastor
 */
public class StripFilterTest {

	@BeforeClass
	public static void setUpClass() {
		CacheKeyGeneratorUtil cacheKeyGeneratorUtil =
			new CacheKeyGeneratorUtil();

		cacheKeyGeneratorUtil.setDefaultCacheKeyGenerator(
			new HashCodeCacheKeyGenerator());
	}

	@Test
	public void testHasMarker() {
		StripFilter stripFilter = new StripFilter();

		// Marker is longer than buffer's remaining

		CharBuffer charBuffer = CharBuffer.wrap("abcdef");

		charBuffer.position(2);
		charBuffer.limit(4);

		char[] marker = "cdef".toCharArray();

		Assert.assertFalse(stripFilter.hasMarker(charBuffer, marker));

		Assert.assertEquals(2, charBuffer.position());

		// No match

		charBuffer = CharBuffer.wrap("abcdef");
		marker = "abce".toCharArray();

		Assert.assertFalse(stripFilter.hasMarker(charBuffer, marker));

		Assert.assertEquals(0, charBuffer.position());

		// Exact match

		charBuffer = CharBuffer.wrap("abcdef");
		marker = "abcd".toCharArray();

		Assert.assertTrue(stripFilter.hasMarker(charBuffer, marker));

		Assert.assertEquals(0, charBuffer.position());

		// Match ignore case

		charBuffer = CharBuffer.wrap("aBcDef");
		marker = "abcd".toCharArray();

		Assert.assertTrue(stripFilter.hasMarker(charBuffer, marker));

		Assert.assertEquals(0, charBuffer.position());
	}

	@Test
	public void testProcessCSS() throws Exception {
		StripFilter stripFilter = new StripFilter();

		// Missing close tag

		CharBuffer charBuffer = CharBuffer.wrap("style type=\"text/css\">abc");

		StringWriter stringWriter = new StringWriter();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					StripFilter.class.getName(), Level.WARNING)) {

			stripFilter.processCSS(null, null, charBuffer, stringWriter);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals("Missing </style>", logRecord.getMessage());
		}

		Assert.assertEquals(
			"style type=\"text/css\">", stringWriter.toString());
		Assert.assertEquals(22, charBuffer.position());

		// Empty tag

		charBuffer = CharBuffer.wrap("style type=\"text/css\"></style>");
		stringWriter = new StringWriter();

		stripFilter.processCSS(null, null, charBuffer, stringWriter);

		Assert.assertEquals(
			"style type=\"text/css\"></style>", stringWriter.toString());

		Assert.assertEquals(30, charBuffer.position());

		// Minifier spaces

		charBuffer = CharBuffer.wrap("style type=\"text/css\"> \r\t\n</style>");
		stringWriter = new StringWriter();

		stripFilter.processCSS(null, null, charBuffer, stringWriter);

		Assert.assertEquals(
			"style type=\"text/css\"></style>", stringWriter.toString());

		Assert.assertEquals(34, charBuffer.position());

		// Minifier code

		String code =
			".a{ position: relative; outline: none; overflow: hidden; " +
				"text-align: left /* Force default alignment */ }";

		String minifiedCode = MinifierUtil.minifyCss(code);

		charBuffer = CharBuffer.wrap(
			"style type=\"text/css\">" + code + "</style>");

		stringWriter = new StringWriter();

		stripFilter.processCSS(null, null, charBuffer, stringWriter);

		Assert.assertEquals(
			"style type=\"text/css\">" + minifiedCode + "</style>",
			stringWriter.toString());

		Assert.assertEquals(code.length() + 30, charBuffer.position());

		// Minifier code with trailing spaces

		charBuffer = CharBuffer.wrap(
			"style type=\"text/css\">" + code + "</style> \r\t\n");
		stringWriter = new StringWriter();

		stripFilter.processCSS(null, null, charBuffer, stringWriter);

		Assert.assertEquals(
			"style type=\"text/css\">" + minifiedCode + "</style> ",
			stringWriter.toString());

		Assert.assertEquals(code.length() + 34, charBuffer.position());
	}

	@Test
	public void testProcessJavaScript() throws Exception {
		StripFilter stripFilter = new StripFilter();

		// Missing close tag

		CharBuffer charBuffer = CharBuffer.wrap("script>abc");

		StringWriter stringWriter = new StringWriter();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					StripFilter.class.getName(), Level.WARNING)) {

			stripFilter.processJavaScript(
				"test.js", charBuffer, stringWriter, "script".toCharArray());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals("Missing </script>", logRecord.getMessage());

			Assert.assertEquals("script>", stringWriter.toString());
		}

		Assert.assertEquals(7, charBuffer.position());

		// Empty tag

		charBuffer = CharBuffer.wrap("script></script>");
		stringWriter = new StringWriter();

		stripFilter.processJavaScript(
			"test.js", charBuffer, stringWriter, "script".toCharArray());

		Assert.assertEquals("script></script>", stringWriter.toString());

		Assert.assertEquals(16, charBuffer.position());

		// Minifier spaces

		charBuffer = CharBuffer.wrap("script> \t\r\n</script>");
		stringWriter = new StringWriter();

		stripFilter.processJavaScript(
			"test.js", charBuffer, stringWriter, "script".toCharArray());

		Assert.assertEquals("script></script>", stringWriter.toString());

		Assert.assertEquals(20, charBuffer.position());

		// Minifier code

		String code = "function(){ var abcd; var efgh; }";

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					GoogleJavaScriptMinifier.class.getName(), Level.SEVERE)) {

			String minifiedCode = MinifierUtil.minifyJavaScript(
				"test.js", code);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"(test.js:1): Parse error. unnamed function statement",
				logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"{0} error(s), {1} warning(s)", logRecord.getMessage());

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			charBuffer = CharBuffer.wrap("script>" + code + "</script>");

			stringWriter = new StringWriter();

			stripFilter.processJavaScript(
				"test.js", charBuffer, stringWriter, "script".toCharArray());

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			logRecord = logRecords.get(0);

			Assert.assertEquals(
				"(test.js:1): Parse error. unnamed function statement",
				logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"{0} error(s), {1} warning(s)", logRecord.getMessage());

			Assert.assertEquals(
				"script>" + minifiedCode + "</script>",
				stringWriter.toString());
			Assert.assertEquals(code.length() + 16, charBuffer.position());

			// Minifier code with trailing spaces

			charBuffer = CharBuffer.wrap("script>" + code + "</script> \t\r\n");

			stringWriter = new StringWriter();

			stripFilter.processJavaScript(
				"test.js", charBuffer, stringWriter, "script".toCharArray());

			Assert.assertEquals(
				"script>" + minifiedCode + "</script> ",
				stringWriter.toString());
		}

		Assert.assertEquals(code.length() + 20, charBuffer.position());
	}

	@Test
	public void testProcessPre() throws Exception {
		StripFilter stripFilter = new StripFilter();

		// Missing close tag

		CharBuffer charBuffer = CharBuffer.wrap("pre>abcde");

		StringWriter stringWriter = new StringWriter();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					StripFilter.class.getName(), Level.WARNING)) {

			stripFilter.processPre(charBuffer, stringWriter);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals("Missing </pre>", logRecord.getMessage());

			Assert.assertEquals("pre", stringWriter.toString());
			Assert.assertEquals(3, charBuffer.position());
		}

		// Without trailing spaces

		charBuffer = CharBuffer.wrap("pre>a b </pre>");

		stringWriter = new StringWriter();

		stripFilter.processPre(charBuffer, stringWriter);

		Assert.assertEquals("pre>a b </pre>", stringWriter.toString());

		Assert.assertEquals(14, charBuffer.position());

		// With trailing spaces

		charBuffer = CharBuffer.wrap("pre>a b </pre> \r\n\tc");

		stringWriter = new StringWriter();

		stripFilter.processPre(charBuffer, stringWriter);

		Assert.assertEquals("pre>a b </pre> ", stringWriter.toString());

		Assert.assertEquals(18, charBuffer.position());
	}

	@Test
	public void testProcessTextArea() throws Exception {
		StripFilter stripFilter = new StripFilter();

		// Missing close tag

		CharBuffer charBuffer = CharBuffer.wrap("textarea >abcde");

		StringWriter stringWriter = new StringWriter();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					StripFilter.class.getName(), Level.WARNING)) {

			stripFilter.processTextArea(charBuffer, stringWriter);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals("Missing </textArea>", logRecord.getMessage());

			Assert.assertEquals("textarea ", stringWriter.toString());
			Assert.assertEquals(9, charBuffer.position());
		}

		// Without trailing spaces

		charBuffer = CharBuffer.wrap("textarea >a b </textarea>");

		stringWriter = new StringWriter();

		stripFilter.processTextArea(charBuffer, stringWriter);

		Assert.assertEquals(
			"textarea >a b </textarea>", stringWriter.toString());

		Assert.assertEquals(25, charBuffer.position());

		// With trailing spaces

		charBuffer = CharBuffer.wrap("textarea >a b </textarea> \r\n\tc");

		stringWriter = new StringWriter();

		stripFilter.processTextArea(charBuffer, stringWriter);

		Assert.assertEquals(
			"textarea >a b </textarea> ", stringWriter.toString());

		Assert.assertEquals(29, charBuffer.position());
	}

	@Test
	public void testSkipWhiteSpace() throws Exception {
		StripFilter stripFilter = new StripFilter();

		// Empty buffer

		CharBuffer charBuffer = CharBuffer.allocate(0);

		StringWriter stringWriter = new StringWriter();

		stripFilter.skipWhiteSpace(charBuffer, stringWriter, true);

		Assert.assertEquals("", stringWriter.toString());

		Assert.assertEquals(0, charBuffer.position());

		// No leading space

		charBuffer = CharBuffer.wrap("abc \t\r\n");
		stringWriter = new StringWriter();

		stripFilter.skipWhiteSpace(charBuffer, stringWriter, true);

		Assert.assertEquals("", stringWriter.toString());

		Assert.assertEquals(0, charBuffer.position());

		// Single leading space

		charBuffer = CharBuffer.wrap(" ");
		stringWriter = new StringWriter();

		stripFilter.skipWhiteSpace(charBuffer, stringWriter, true);

		Assert.assertEquals(" ", stringWriter.toString());

		Assert.assertEquals(1, charBuffer.position());

		charBuffer = CharBuffer.wrap("\t");
		stringWriter = new StringWriter();

		stripFilter.skipWhiteSpace(charBuffer, stringWriter, true);

		Assert.assertEquals(" ", stringWriter.toString());

		Assert.assertEquals(1, charBuffer.position());

		charBuffer = CharBuffer.wrap("\r");
		stringWriter = new StringWriter();

		stripFilter.skipWhiteSpace(charBuffer, stringWriter, true);

		Assert.assertEquals(" ", stringWriter.toString());

		Assert.assertEquals(1, charBuffer.position());

		charBuffer = CharBuffer.wrap("\n");
		stringWriter = new StringWriter();

		stripFilter.skipWhiteSpace(charBuffer, stringWriter, true);

		Assert.assertEquals(" ", stringWriter.toString());

		Assert.assertEquals(1, charBuffer.position());

		// Multiple leading spaces

		charBuffer = CharBuffer.wrap(" \t\r\n");
		stringWriter = new StringWriter();

		stripFilter.skipWhiteSpace(charBuffer, stringWriter, true);

		Assert.assertEquals(" ", stringWriter.toString());

		Assert.assertEquals(4, charBuffer.position());
	}

}