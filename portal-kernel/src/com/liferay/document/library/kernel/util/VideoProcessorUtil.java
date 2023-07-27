/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class VideoProcessorUtil {

	public static void generateVideo(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		if (_videoProcessor != null) {
			_videoProcessor.generateVideo(
				sourceFileVersion, destinationFileVersion);
		}
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, String type)
		throws Exception {

		if (_videoProcessor == null) {
			return null;
		}

		return _videoProcessor.getPreviewAsStream(fileVersion, type);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		if (_videoProcessor == null) {
			return 0;
		}

		return _videoProcessor.getPreviewFileSize(fileVersion, type);
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		if (_videoProcessor == null) {
			return null;
		}

		return _videoProcessor.getThumbnailAsStream(fileVersion, index);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		if (_videoProcessor == null) {
			return 0;
		}

		return _videoProcessor.getThumbnailFileSize(fileVersion, index);
	}

	public static Set<String> getVideoMimeTypes() {
		if (_videoProcessor == null) {
			return null;
		}

		return _videoProcessor.getVideoMimeTypes();
	}

	public static VideoProcessor getVideoProcessor() {
		return _videoProcessor;
	}

	public static boolean hasVideo(FileVersion fileVersion) {
		if (_videoProcessor == null) {
			return false;
		}

		return _videoProcessor.hasVideo(fileVersion);
	}

	public static boolean isAvailable() {
		return _videoProcessor.isAvailable();
	}

	public static boolean isSupported(String mimeType) {
		if (_videoProcessor == null) {
			return false;
		}

		return _videoProcessor.isSupported(mimeType);
	}

	public static boolean isVideoSupported(FileVersion fileVersion) {
		if (_videoProcessor == null) {
			return false;
		}

		return _videoProcessor.isVideoSupported(fileVersion);
	}

	public static boolean isVideoSupported(String mimeType) {
		if (_videoProcessor == null) {
			return false;
		}

		return _videoProcessor.isVideoSupported(mimeType);
	}

	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		if (_videoProcessor != null) {
			_videoProcessor.trigger(sourceFileVersion, destinationFileVersion);
		}
	}

	private static volatile VideoProcessor _videoProcessor =
		ServiceProxyFactory.newServiceTrackedInstance(
			VideoProcessor.class, VideoProcessorUtil.class, "_videoProcessor",
			false);

}