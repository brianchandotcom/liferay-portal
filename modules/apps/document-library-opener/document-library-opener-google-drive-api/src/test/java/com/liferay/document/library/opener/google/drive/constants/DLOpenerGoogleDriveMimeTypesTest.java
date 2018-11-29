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

package com.liferay.document.library.opener.google.drive.constants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class DLOpenerGoogleDriveMimeTypesTest {

	@Test
	public void testSupportsDocumentMimeTypes() {
		_assertIsSupportedGoogleDocsDocument("text/html", "html");
		_assertIsSupportedGoogleDocsDocument("application/text", "txt");
		_assertIsSupportedGoogleDocsDocument("text", "txt");
		_assertIsSupportedGoogleDocsDocument("text/plain", "txt");
		_assertIsSupportedGoogleDocsDocument("application/rtf", "rtf");
		_assertIsSupportedGoogleDocsDocument(
			"application/vnd.oasis.opendocument.text", "odt");
		_assertIsSupportedGoogleDocsDocument("application/pdf", "pdf");
		_assertIsSupportedGoogleDocsDocument(
			"application/vnd.openxmlformats-officedocument.wordprocessingml." +
				"document",
			"docx");
		_assertIsSupportedGoogleDocsDocument("application/epub+zip", "epub");
	}

	@Test
	public void testSupportsDrawingsMimeTypes() {
		_assertIsSupportedGoogleDocsDrawing("image/jpeg", "jpeg");
		_assertIsSupportedGoogleDocsDrawing("image/png", "png");
		_assertIsSupportedGoogleDocsDrawing("image/svg+xml", "svg");
	}

	@Test
	public void testSupportsPresentationFiles() {
		_assertIsSupportedGoogleDocsPresentation(
			"application/vnd.openxmlformats-officedocument.presentationml." +
				"presentation",
			"pptx");
		_assertIsSupportedGoogleDocsPresentation(
			"application/vnd.oasis.opendocument.presentation", "odp");
	}

	@Test
	public void testSupportsSpreadsheetMimeTypes() {
		_assertIsSupportedGoogleDocsSpreadsheet(
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			"xslx");
		_assertIsSupportedGoogleDocsSpreadsheet(
			"application/vnd.oasis.opendocument.spreadsheet", "ods");
		_assertIsSupportedGoogleDocsSpreadsheet("text/csv", "csv");
		_assertIsSupportedGoogleDocsSpreadsheet(
			"text/tab-separated-values", "tsv");
	}

	private void _assertIsSupportedGoogleDocsDocument(
		String mimeType, String extension) {

		_assertMimeTypeSupported(
			DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
			mimeType, extension);
	}

	private void _assertIsSupportedGoogleDocsDrawing(
		String mimeType, String extension) {

		_assertMimeTypeSupported(
			DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_GOOGLE_APPS_DRAWING,
			mimeType, extension);
	}

	private void _assertIsSupportedGoogleDocsPresentation(
		String mimeType, String extension) {

		_assertMimeTypeSupported(
			DLOpenerGoogleDriveMimeTypes.
				APPLICATION_VND_GOOGLE_APPS_PRESENTATION,
			mimeType, extension);
	}

	private void _assertIsSupportedGoogleDocsSpreadsheet(
		String mimeType, String extension) {

		_assertMimeTypeSupported(
			DLOpenerGoogleDriveMimeTypes.
				APPLICATION_VND_GOOGLE_APPS_SPREADSHEET,
			mimeType, extension);
	}

	private void _assertMimeTypeSupported(
		String googleDocsMimeType, String mimeType, String extension) {

		Assert.assertEquals(
			googleDocsMimeType,
			DLOpenerGoogleDriveMimeTypes.getGoogleDocsMimeType(mimeType));

		Assert.assertTrue(
			mimeType + " not supported",
			DLOpenerGoogleDriveMimeTypes.isMimeTypeSupported(mimeType));

		Assert.assertEquals(
			"." + extension,
			DLOpenerGoogleDriveMimeTypes.getMimeTypeExtension(mimeType));
	}

}