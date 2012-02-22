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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.FileUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Igor Spasic
 * @see    MimeTypesImplTest
 */
public class FileImplExtractTest extends BaseTestCase {

	public void testDoc() {
		String text = extractText("test.doc");

		assertEquals("Extract test.", text);
	}

	public void testDocx() {
		String text = extractText("test-2007.docx");

		assertEquals("Extract test.", text);

		text = extractText("test-2010.docx");

		assertEquals("Extract test.", text);
	}

	public void testHtml() {
		String text = extractText("test.html");

		assertEquals("Extract test.", text);
	}

	public void testJpg() {
		String text = extractText("test.jpg");

		assertEquals("", text);
	}

	public void testOdt() {
		String text = extractText("test.odt");

		assertEquals("Extract test.", text);
	}

	public void testPdf() {
		String text = extractText("test-2010.pdf");

		assertEquals("Extract test.", text);

		// PDFBOX-890

		//text = _extractText("test.pdf");

		//assertEquals("Extract test.", text);
	}

	public void testPpt() {
		String text = extractText("test.ppt");

		assertEquals("Extract test.", text);
	}

	public void testPptx() {
		String text = extractText("test-2010.pptx");

		assertEquals("Extract test.", text);
	}

	public void testRtf() {
		String text = extractText("test.rtf");

		assertEquals("Extract  test.", text);
	}

	public void testTxt() {
		String text = extractText("test.txt");

		assertEquals("Extract test.", text);
	}

	public void testXls() {
		String text = extractText("test.xls");

		assertEquals("Sheet1\n\tExtract test.", text);
	}

	public void testXlsx() {
		String text = extractText("test-2010.xlsx");

		assertEquals("Sheet1\n\tExtract test.", text);
	}

	public void testXml() {
		String text = extractText("test.xml");

		assertEquals("<test>Extract test.</test>", text);
	}

	protected String extractText(String fileName) {
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(
				"portal-impl/test/com/liferay/portal/util/dependencies/" +
					fileName);
		}
		catch (FileNotFoundException fnfe) {
			return null;
		}

		String text = FileUtil.extractText(fileInputStream, fileName);

		return text.trim();
	}

}