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

package com.liferay.document.library.kernel.util;

import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

/**
 * @author Sergio González
 */
public class PDFProcessorUtil {

	public static void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor != null) {
			pdfProcessor.generateImages(
				sourceFileVersion, destinationFileVersion);
		}
	}

	public static PDFProcessor getPDFProcessor() {
		return (PDFProcessor)DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.PDF_PROCESSOR);
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return null;
		}

		return pdfProcessor.getPreviewAsStream(fileVersion, index);
	}

	public static int getPreviewFileCount(FileVersion fileVersion) {
		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return 0;
		}

		return pdfProcessor.getPreviewFileCount(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return 0;
		}

		return pdfProcessor.getPreviewFileSize(fileVersion, index);
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return null;
		}

		return pdfProcessor.getThumbnailAsStream(fileVersion, index);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return 0;
		}

		return pdfProcessor.getThumbnailFileSize(fileVersion, index);
	}

	public static boolean hasImages(FileVersion fileVersion) {
		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return false;
		}

		return pdfProcessor.hasImages(fileVersion);
	}

	public static boolean isDocumentSupported(FileVersion fileVersion) {
		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return false;
		}

		return pdfProcessor.isDocumentSupported(fileVersion);
	}

	public static boolean isDocumentSupported(String mimeType) {
		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return false;
		}

		return pdfProcessor.isDocumentSupported(mimeType);
	}

	public static boolean isSupported(String mimeType) {
		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor == null) {
			return false;
		}

		return pdfProcessor.isSupported(mimeType);
	}

	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		PDFProcessor pdfProcessor = getPDFProcessor();

		if (pdfProcessor != null) {
			pdfProcessor.trigger(sourceFileVersion, destinationFileVersion);
		}
	}

}