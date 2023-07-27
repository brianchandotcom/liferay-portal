/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class ImageProcessorUtil {

	public static void cleanUp(FileEntry fileEntry) {
		if (_imageProcessor != null) {
			_imageProcessor.cleanUp(fileEntry);
		}
	}

	public static void cleanUp(FileVersion fileVersion) {
		if (_imageProcessor != null) {
			_imageProcessor.cleanUp(fileVersion);
		}
	}

	public static void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		if (_imageProcessor != null) {
			_imageProcessor.generateImages(
				sourceFileVersion, destinationFileVersion);
		}
	}

	public static Set<String> getImageMimeTypes() {
		if (_imageProcessor == null) {
			return null;
		}

		return _imageProcessor.getImageMimeTypes();
	}

	public static ImageProcessor getImageProcessor() {
		return _imageProcessor;
	}

	public static InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		if (_imageProcessor == null) {
			return null;
		}

		return _imageProcessor.getPreviewAsStream(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		if (_imageProcessor == null) {
			return 0;
		}

		return _imageProcessor.getPreviewFileSize(fileVersion);
	}

	public static String getPreviewType(FileVersion fileVersion) {
		if (_imageProcessor == null) {
			return null;
		}

		return _imageProcessor.getPreviewType(fileVersion);
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		if (_imageProcessor == null) {
			return null;
		}

		return _imageProcessor.getThumbnailAsStream(fileVersion, index);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		if (_imageProcessor == null) {
			return 0;
		}

		return _imageProcessor.getThumbnailFileSize(fileVersion, index);
	}

	public static String getThumbnailType(FileVersion fileVersion) {
		if (_imageProcessor == null) {
			return null;
		}

		return _imageProcessor.getThumbnailType(fileVersion);
	}

	public static boolean hasImages(FileVersion fileVersion) {
		if (_imageProcessor == null) {
			return false;
		}

		return _imageProcessor.hasImages(fileVersion);
	}

	public static boolean isImageSupported(FileVersion fileVersion) {
		if (_imageProcessor == null) {
			return false;
		}

		return _imageProcessor.isImageSupported(fileVersion);
	}

	public static boolean isImageSupported(String mimeType) {
		if (_imageProcessor == null) {
			return false;
		}

		return _imageProcessor.isImageSupported(mimeType);
	}

	public static boolean isSupported(String mimeType) {
		if (_imageProcessor == null) {
			return false;
		}

		return _imageProcessor.isSupported(mimeType);
	}

	public static void storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId, InputStream inputStream,
			String type)
		throws Exception {

		if (_imageProcessor != null) {
			_imageProcessor.storeThumbnail(
				companyId, groupId, fileEntryId, fileVersionId, custom1ImageId,
				custom2ImageId, inputStream, type);
		}
	}

	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		if (_imageProcessor != null) {
			_imageProcessor.trigger(sourceFileVersion, destinationFileVersion);
		}
	}

	private static volatile ImageProcessor _imageProcessor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ImageProcessor.class, ImageProcessorUtil.class, "_imageProcessor",
			false);

}