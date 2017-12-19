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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.File;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.CompositeParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.ocr.TesseractOCRParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Igor Spasic
 * @see    MimeTypesImplTest
 */
public class FileImplExtractTest {

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = Class.forName(
			"com.liferay.portal.util.FileImpl$TikaConfigHolder");

		TikaConfig tikaConfig = ReflectionTestUtil.getFieldValue(
			clazz, "_tikaConfig");

		CompositeParser compositeParser =
			(CompositeParser)tikaConfig.getParser();

		Map<MediaType, Parser> parsers = compositeParser.getParsers();

		Set<Map.Entry<MediaType, Parser>> set = parsers.entrySet();

		Iterator<Map.Entry<MediaType, Parser>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<MediaType, Parser> entry = iterator.next();

			Parser parser = entry.getValue();

			if (parser instanceof TesseractOCRParser) {
				TesseractOCRParser tesseractOCRParser =
					(TesseractOCRParser)parser;

				if (tesseractOCRParser.hasTesseract(new TesseractOCRConfig())) {
					iterator.remove();
				}
			}
		}

		compositeParser.setParsers(parsers);
	}

	@Test
	public void testDoc() {
		String text = extractText("test.doc");

		Assert.assertEquals("Extract test.", text);
	}

	@Test
	public void testDocx() {
		String text = extractText("test-2007.docx");

		Assert.assertTrue(text, text.contains("Extract test."));

		text = extractText("test-2010.docx");

		Assert.assertTrue(text, text.contains("Extract test."));
	}

	@Test
	public void testHtml() {
		String text = extractText("test.html");

		Assert.assertEquals("Extract test.", text);
	}

	@Test
	public void testJpg() {
		String text = extractText("test.jpg");

		Assert.assertEquals("", text);
	}

	@Test
	public void testOdt() {
		String text = extractText("test.odt");

		Assert.assertEquals("Extract test.", text);
	}

	@Test
	public void testPdf() {
		String text = extractText("test-2010.pdf");

		Assert.assertEquals("Extract test.", text);

		text = extractText("test.pdf");

		Assert.assertEquals("Extract test.", text);
	}

	@Test
	public void testPpt() {
		String text = extractText("test.ppt");

		Assert.assertEquals("Extract test.", text);
	}

	@Test
	public void testPptx() {
		String text = extractText("test-2010.pptx");

		Assert.assertTrue(text, text.contains("Extract test."));
	}

	@Test
	public void testRtf() {
		String text = extractText("test.rtf");

		Assert.assertEquals("Extract test.", text);
	}

	@Test
	public void testTxt() {
		String text = extractText("test.txt");

		Assert.assertEquals("Extract test.", text);
	}

	@Test
	public void testTxtEncodedWithShift_JIS() throws IOException {
		String expectedText = new String(
			_file.getBytes(
				FileImplExtractTest.class.getResourceAsStream(
					"dependencies/test-encoding-Shift_JIS.txt")),
			Charset.forName("Shift_JIS"));

		Assert.assertEquals(
			expectedText.trim(), extractText("test-encoding-Shift_JIS.txt"));
	}

	@Test
	public void testXls() {
		String text = extractText("test.xls");

		Assert.assertEquals("Sheet1\n\tExtract test.", text);
	}

	@Test
	public void testXlsx() {
		String text = extractText("test-2010.xlsx");

		Assert.assertTrue(text, text.contains("Extract test."));
	}

	@Test
	public void testXml() {
		String text = extractText("test.xml");

		Assert.assertEquals("<test>Extract test.</test>", text);
	}

	protected String extractText(String fileName) {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		String text = _file.extractText(inputStream, fileName);

		return text.trim();
	}

	private final File _file = FileImpl.getInstance();

}