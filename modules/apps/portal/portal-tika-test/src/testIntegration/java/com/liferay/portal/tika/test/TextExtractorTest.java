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

package com.liferay.portal.tika.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.TextExtractor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class TextExtractorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class<?> clazz = _textExtractor.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Class<?> tesseractOCRParserClass = classLoader.loadClass(
			"org.apache.tika.parser.ocr.TesseractOCRParser");

		Map<String, Boolean> map = ReflectionTestUtil.getAndSetFieldValue(
			tesseractOCRParserClass, "TESSERACT_PRESENT",
			new HashMap<String, Boolean>() {

				@Override
				public boolean containsKey(Object key) {
					return true;
				}

				@Override
				public Boolean get(Object key) {
					return Boolean.FALSE;
				}

			});

		_resetTikaConfigCloseable = () -> ReflectionTestUtil.setFieldValue(
			tesseractOCRParserClass, "TESSERACT_PRESENT", map);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		_resetTikaConfigCloseable.close();
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
	public void testEmpty() {
		Assert.assertEquals(
			StringPool.BLANK,
			_textExtractor.extractText(
				new UnsyncByteArrayInputStream(new byte[0]), -1));
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
			StreamUtil.toByteArray(
				TextExtractorTest.class.getResourceAsStream(
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

		String text = _textExtractor.extractText(inputStream, -1);

		return text.trim();
	}

	private static Closeable _resetTikaConfigCloseable;

	@Inject
	private static TextExtractor _textExtractor;

}