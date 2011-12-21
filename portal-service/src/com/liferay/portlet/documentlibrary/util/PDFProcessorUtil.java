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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

/**
 * @author Sergio González
 */
public class PDFProcessorUtil {

	public static String getGlobalSearchPath() throws Exception {
		return getPDFProcessor().getGlobalSearchPath();
	}

	public static void generateImages(FileVersion fileVersion)
		throws Exception {

		getPDFProcessor().generateImages(fileVersion);
	}

	public static PDFProcessor getPDFProcessor() {
		return _pdfProcessor;
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		return getPDFProcessor().getPreviewAsStream(fileVersion, index);
	}

	public static int getPreviewFileCount(FileVersion fileVersion) {
		return getPDFProcessor().getPreviewFileCount(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return getPDFProcessor().getPreviewFileSize(fileVersion, index);
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception {

		return getPDFProcessor().getThumbnailAsStream(
			fileVersion, thumbnailIndex);
	}

	public static long getThumbnailFileSize(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception {

		return getPDFProcessor().getThumbnailFileSize(
			fileVersion, thumbnailIndex);
	}

	public static boolean hasImages(FileVersion fileVersion) {
		return getPDFProcessor().hasImages(fileVersion);
	}

	public static boolean isDocumentSupported(FileVersion fileVersion) {
		return getPDFProcessor().isDocumentSupported(fileVersion);
	}

	public static boolean isDocumentSupported(String mimeType) {
		return getPDFProcessor().isDocumentSupported(mimeType);
	}

	public static boolean isImageMagickEnabled() throws Exception {
		return getPDFProcessor().isImageMagickEnabled();
	}

	public static void reset() throws Exception {
		getPDFProcessor().reset();
	}

	public void setPDFProcessor(PDFProcessor pdfProcessor) {
		_pdfProcessor = pdfProcessor;
	}

	public boolean isSupported(String mimeType) {
		return getPDFProcessor().isSupported(mimeType);
	}

	public void trigger(FileVersion fileVersion) {
		getPDFProcessor().trigger(fileVersion);
	}

	private static PDFProcessor _pdfProcessor;

}