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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.InputStream;

/**
 * @author Sergio González
 */
public class PDFProcessorUtil {

	public static void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		getPDFProcessor().generateImages(
			sourceFileVersion, destinationFileVersion);
	}

	public static PDFProcessor getPDFProcessor() {
		PortalRuntimePermission.checkGetBeanProperty(PDFProcessorUtil.class);

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
			FileVersion fileVersion, int index)
		throws Exception {

		return getPDFProcessor().getThumbnailAsStream(fileVersion, index);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return getPDFProcessor().getThumbnailFileSize(fileVersion, index);
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

	public static boolean isSupported(String mimeType) {
		return getPDFProcessor().isSupported(mimeType);
	}

	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		getPDFProcessor().trigger(sourceFileVersion, destinationFileVersion);
	}

	public void setPDFProcessor(PDFProcessor pdfProcessor) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_pdfProcessor = pdfProcessor;
	}

	private static PDFProcessor _pdfProcessor;

}