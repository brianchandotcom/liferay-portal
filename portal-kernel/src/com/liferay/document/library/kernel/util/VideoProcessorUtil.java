/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class VideoProcessorUtil {

	public static void generateVideo(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor != null) {
			videoProcessor.generateVideo(
				sourceFileVersion, destinationFileVersion);
		}
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, String type)
		throws Exception {

		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return null;
		}

		return videoProcessor.getPreviewAsStream(fileVersion, type);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return 0;
		}

		return videoProcessor.getPreviewFileSize(fileVersion, type);
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return null;
		}

		return videoProcessor.getThumbnailAsStream(fileVersion, index);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return 0;
		}

		return videoProcessor.getThumbnailFileSize(fileVersion, index);
	}

	public static Set<String> getVideoMimeTypes() {
		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return null;
		}

		return videoProcessor.getVideoMimeTypes();
	}

	public static VideoProcessor getVideoProcessor() {
		return (VideoProcessor)DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.VIDEO_PROCESSOR);
	}

	public static boolean hasVideo(FileVersion fileVersion) {
		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return false;
		}

		return videoProcessor.hasVideo(fileVersion);
	}

	public static boolean isAvailable() {
		VideoProcessor videoProcessor = getVideoProcessor();

		return videoProcessor.isAvailable();
	}

	public static boolean isSupported(String mimeType) {
		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return false;
		}

		return videoProcessor.isSupported(mimeType);
	}

	public static boolean isVideoSupported(FileVersion fileVersion) {
		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return false;
		}

		return videoProcessor.isVideoSupported(fileVersion);
	}

	public static boolean isVideoSupported(String mimeType) {
		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor == null) {
			return false;
		}

		return videoProcessor.isVideoSupported(mimeType);
	}

	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		VideoProcessor videoProcessor = getVideoProcessor();

		if (videoProcessor != null) {
			videoProcessor.trigger(sourceFileVersion, destinationFileVersion);
		}
	}

}