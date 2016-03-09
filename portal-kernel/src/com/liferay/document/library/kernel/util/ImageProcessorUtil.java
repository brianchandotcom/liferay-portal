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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class ImageProcessorUtil {

	public static void cleanUp(FileEntry fileEntry) {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor != null) {
			imageProcessor.cleanUp(fileEntry);
		}
	}

	public static void cleanUp(FileVersion fileVersion) {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor != null) {
			imageProcessor.cleanUp(fileVersion);
		}
	}

	public static void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor != null) {
			imageProcessor.generateImages(
				sourceFileVersion, destinationFileVersion);
		}
	}

	public static Set<String> getImageMimeTypes() {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return null;
		}

		return imageProcessor.getImageMimeTypes();
	}

	public static ImageProcessor getImageProcessor() {
		return (ImageProcessor)DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.IMAGE_PROCESSOR);
	}

	public static InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return null;
		}

		return imageProcessor.getPreviewAsStream(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return 0;
		}

		return imageProcessor.getPreviewFileSize(fileVersion);
	}

	public static String getPreviewType(FileVersion fileVersion) {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return null;
		}

		return imageProcessor.getPreviewType(fileVersion);
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return null;
		}

		return imageProcessor.getThumbnailAsStream(fileVersion, index);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return 0;
		}

		return imageProcessor.getThumbnailFileSize(fileVersion, index);
	}

	public static String getThumbnailType(FileVersion fileVersion) {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return null;
		}

		return imageProcessor.getThumbnailType(fileVersion);
	}

	public static boolean hasImages(FileVersion fileVersion) {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return false;
		}

		return imageProcessor.hasImages(fileVersion);
	}

	public static boolean isImageSupported(FileVersion fileVersion) {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return false;
		}

		return imageProcessor.isImageSupported(fileVersion);
	}

	public static boolean isImageSupported(String mimeType) {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return false;
		}

		return imageProcessor.isImageSupported(mimeType);
	}

	public static boolean isSupported(String mimeType) {
		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor == null) {
			return false;
		}

		return imageProcessor.isSupported(mimeType);
	}

	public static void storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId, InputStream is,
			String type)
		throws Exception {

		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor != null) {
			imageProcessor.storeThumbnail(
				companyId, groupId, fileEntryId, fileVersionId, custom1ImageId,
				custom2ImageId, is, type);
		}
	}

	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		ImageProcessor imageProcessor = getImageProcessor();

		if (imageProcessor != null) {
			imageProcessor.trigger(sourceFileVersion, destinationFileVersion);
		}
	}

}