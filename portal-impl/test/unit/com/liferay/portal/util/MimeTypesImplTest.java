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

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Alexander Chow
 * @see    FileImplExtractTest
 */
public class MimeTypesImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MimeTypesImpl mimeTypesImpl = new MimeTypesImpl();

		mimeTypesImpl.afterPropertiesSet();

		_mimeTypes = mimeTypesImpl;
	}

	@Test
	public void testDoc() {
		Assert.assertEquals(
			ContentTypes.APPLICATION_MSWORD, _getContentType("test.doc", true));
		Assert.assertEquals(
			ContentTypes.APPLICATION_MSWORD,
			_getContentType("test.doc", false));
	}

	@Test
	public void testDocx() {
		String validContentType =
			"application" +
				"/vnd.openxmlformats-officedocument.wordprocessingml.document";

		Assert.assertEquals(
			validContentType, _getContentType("test-2007.docx", true));
		Assert.assertEquals(
			validContentType, _getContentType("test-2007.docx", false));

		Assert.assertEquals(
			validContentType, _getContentType("test-2010.docx", true));
		Assert.assertEquals(
			validContentType, _getContentType("test-2010.docx", false));
	}

	@Test
	public void testHtml() {
		Assert.assertEquals(
			ContentTypes.TEXT_HTML, _getContentType("test.html", true));
		Assert.assertEquals(
			ContentTypes.TEXT_HTML, _getContentType("test.html", false));
	}

	@Test
	public void testJpg() {
		Assert.assertEquals(
			ContentTypes.IMAGE_JPEG, _getContentType("test.jpg", true));
		Assert.assertEquals(
			ContentTypes.IMAGE_JPEG, _getContentType("test.jpg", false));
	}

	@Test
	public void testLar() {
		Assert.assertEquals(
			ContentTypes.APPLICATION_ZIP, _getContentType("test.lar", true));
		Assert.assertEquals(
			ContentTypes.APPLICATION_ZIP, _getContentType("test.lar", false));
	}

	@Test
	public void testOdt() {
		String validContentType = "application/vnd.oasis.opendocument.text";

		Assert.assertEquals(
			validContentType, _getContentType("test.odt", true));
		Assert.assertEquals(
			validContentType, _getContentType("test.odt", false));
	}

	@Test
	public void testPdf() {
		Assert.assertEquals(
			ContentTypes.APPLICATION_PDF, _getContentType("test.pdf", true));
		Assert.assertEquals(
			ContentTypes.APPLICATION_PDF, _getContentType("test.pdf", false));

		Assert.assertEquals(
			ContentTypes.APPLICATION_PDF,
			_getContentType("test-2010.pdf", true));
		Assert.assertEquals(
			ContentTypes.APPLICATION_PDF,
			_getContentType("test-2010.pdf", false));
	}

	@Test
	public void testPpt() {
		Assert.assertEquals(
			ContentTypes.APPLICATION_VND_MS_POWERPOINT,
			_getContentType("test.ppt", true));
		Assert.assertEquals(
			ContentTypes.APPLICATION_VND_MS_POWERPOINT,
			_getContentType("test.ppt", false));
	}

	@Test
	public void testPptx() {
		String validContentType =
			"application/vnd.openxmlformats-officedocument.presentationml." +
				"presentation";

		Assert.assertEquals(
			validContentType, _getContentType("test-2010.pptx", true));
		Assert.assertEquals(
			validContentType, _getContentType("test-2010.pptx", false));
	}

	@Test
	public void testRtf() {
		String validContentType = "application/rtf";

		Assert.assertEquals(
			validContentType, _getContentType("test.rtf", true));
		Assert.assertEquals(
			validContentType, _getContentType("test.rtf", false));
	}

	@Test
	public void testSvg() {
		Assert.assertEquals(
			ContentTypes.IMAGE_SVG_XML, _getContentType("test_#.svg", true));
		Assert.assertEquals(
			ContentTypes.IMAGE_SVG_XML, _getContentType("test_#.svg", false));
	}

	@Test
	public void testTxt() {
		Assert.assertEquals(
			ContentTypes.TEXT_PLAIN, _getContentType("test.txt", true));
		Assert.assertEquals(
			ContentTypes.TEXT_PLAIN, _getContentType("test.txt", false));
	}

	@Test
	public void testXls() {
		Assert.assertEquals(
			ContentTypes.APPLICATION_VND_MS_EXCEL,
			_getContentType("test.xls", true));
		Assert.assertEquals(
			ContentTypes.APPLICATION_VND_MS_EXCEL,
			_getContentType("test.xls", false));
	}

	@Test
	public void testXlsx() {
		String validContentType =
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

		Assert.assertEquals(
			validContentType, _getContentType("test-2010.xlsx", true));
		Assert.assertEquals(
			validContentType, _getContentType("test-2010.xlsx", false));
	}

	@Test
	public void testXml() {
		String validContentType = "application/xml";

		Assert.assertEquals(
			validContentType, _getContentType("test.xml", true));
		Assert.assertEquals(
			validContentType, _getContentType("test.xml", false));
	}

	private String _getContentType(String fileName, boolean checkStream) {
		if (checkStream) {
			return _mimeTypes.getContentType(
				_getInputStream(fileName), fileName);
		}

		return _mimeTypes.getContentType(fileName);
	}

	private InputStream _getInputStream(String fileName) {
		Class<?> clazz = getClass();

		return clazz.getResourceAsStream("dependencies/" + fileName);
	}

	private MimeTypes _mimeTypes;

}