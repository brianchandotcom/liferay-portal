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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Spasic
 * @see    MimeTypesImplTest
 */
public class FileImplExtractTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testDoc() {
		String text = extractText("test.doc");

		Assert.assertEquals("Extract test.", text);
	}

	@Test
	public void testDocx() {
		String text = extractText("test-2007.docx");

		Assert.assertTrue(text.contains("Extract test."));

		text = extractText("test-2010.docx");

		Assert.assertTrue(text.contains("Extract test."));
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

		Assert.assertTrue(text.contains("Extract test."));
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
	public void testXls() {
		String text = extractText("test.xls");

		Assert.assertEquals("Sheet1\n\tExtract test.", text);
	}

	@Test
	public void testXlsx() {
		String text = extractText("test-2010.xlsx");

		Assert.assertTrue(text.contains("Extract test."));
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

		String text = FileUtil.extractText(inputStream, fileName);

		return text.trim();
	}

}