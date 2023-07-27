/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.InputStream;

/**
 * @author Sergio González
 */
public class PDFProcessorUtil {

	public static void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		if (_pdfProcessor != null) {
			_pdfProcessor.generateImages(
				sourceFileVersion, destinationFileVersion);
		}
	}

	public static PDFProcessor getPDFProcessor() {
		return _pdfProcessor;
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		if (_pdfProcessor == null) {
			return null;
		}

		return _pdfProcessor.getPreviewAsStream(fileVersion, index);
	}

	public static int getPreviewFileCount(FileVersion fileVersion) {
		if (_pdfProcessor == null) {
			return 0;
		}

		return _pdfProcessor.getPreviewFileCount(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		if (_pdfProcessor == null) {
			return 0;
		}

		return _pdfProcessor.getPreviewFileSize(fileVersion, index);
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		if (_pdfProcessor == null) {
			return null;
		}

		return _pdfProcessor.getThumbnailAsStream(fileVersion, index);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		if (_pdfProcessor == null) {
			return 0;
		}

		return _pdfProcessor.getThumbnailFileSize(fileVersion, index);
	}

	public static boolean hasImages(FileVersion fileVersion) {
		if (_pdfProcessor == null) {
			return false;
		}

		return _pdfProcessor.hasImages(fileVersion);
	}

	public static boolean isDocumentSupported(FileVersion fileVersion) {
		if (_pdfProcessor == null) {
			return false;
		}

		return _pdfProcessor.isDocumentSupported(fileVersion);
	}

	public static boolean isDocumentSupported(String mimeType) {
		if (_pdfProcessor == null) {
			return false;
		}

		return _pdfProcessor.isDocumentSupported(mimeType);
	}

	public static boolean isSupported(String mimeType) {
		if (_pdfProcessor == null) {
			return false;
		}

		return _pdfProcessor.isSupported(mimeType);
	}

	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		if (_pdfProcessor != null) {
			_pdfProcessor.trigger(sourceFileVersion, destinationFileVersion);
		}
	}

	private static volatile PDFProcessor _pdfProcessor =
		ServiceProxyFactory.newServiceTrackedInstance(
			PDFProcessor.class, PDFProcessorUtil.class, "_pdfProcessor", false);

}