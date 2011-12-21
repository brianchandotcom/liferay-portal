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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class ImageProcessorUtil {

	public static void cleanUp(FileEntry fileEntry) {
		getImageProcessor().cleanUp(fileEntry);
	}

	public static void cleanUp(FileVersion fileVersion) {
		getImageProcessor().cleanUp(fileVersion);
	}

	public static void generateImages(FileVersion fileVersion) {
		getImageProcessor().generateImages(fileVersion);
	}

	public static Set<String> getImageMimeTypes() {
		return getImageProcessor().getImageMimeTypes();
	}

	public static ImageProcessor getImageProcessor() {
		return _imageProcessor;
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception {

		return getImageProcessor().getThumbnailAsStream(
			fileVersion, thumbnailIndex);
	}

	public static long getThumbnailFileSize(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception {

		return getImageProcessor().getThumbnailFileSize(
			fileVersion, thumbnailIndex);
	}

	public static boolean hasImages(FileVersion fileVersion) {
		return getImageProcessor().hasImages(fileVersion);
	}

	public static boolean isImageSupported(FileVersion fileVersion) {
		return getImageProcessor().isImageSupported(fileVersion);
	}

	public static boolean isImageSupported(String mimeType) {
		return getImageProcessor().isImageSupported(mimeType);
	}

	public static void storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId,
			InputStream is, String type)
		throws Exception {

		getImageProcessor().storeThumbnail(
			companyId, groupId, fileEntryId, fileVersionId, custom1ImageId,
			custom2ImageId, is, type);
	}

	public void setImageProcessor(ImageProcessor imageProcessor) {
		_imageProcessor = imageProcessor;
	}

	public static boolean isSupported(String mimeType) {
		return getImageProcessor().isSupported(mimeType);
	}

	public static void trigger(FileVersion fileVersion) {
		getImageProcessor().trigger(fileVersion);
	}

	private static ImageProcessor _imageProcessor;

}