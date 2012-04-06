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

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class VideoProcessorUtil {

	public static void generateVideo(FileVersion fileVersion) throws Exception {
		getVideoProcessor().generateVideo(fileVersion);
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, String type)
		throws Exception {

		return getVideoProcessor().getPreviewAsStream(fileVersion, type);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		return getVideoProcessor().getPreviewFileSize(fileVersion, type);
	}

	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		return getVideoProcessor().getThumbnailAsStream(fileVersion, index);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return getVideoProcessor().getThumbnailFileSize(fileVersion, index);
	}

	public static Set<String> getVideoMimeTypes() {

		return getVideoProcessor().getVideoMimeTypes();
	}

	public static VideoProcessor getVideoProcessor() {
		return _videoProcessor;
	}

	public static boolean hasVideo(FileVersion fileVersion) {
		return getVideoProcessor().hasVideo(fileVersion);
	}

	public static boolean isSupported(String mimeType) {
		return getVideoProcessor().isSupported(mimeType);
	}

	public static boolean isVideoSupported(FileVersion fileVersion) {
		return getVideoProcessor().isVideoSupported(fileVersion);
	}

	public static boolean isVideoSupported(String mimeType) {
		return getVideoProcessor().isVideoSupported(mimeType);
	}

	public static void trigger(FileVersion fileVersion) {
		getVideoProcessor().trigger(fileVersion);
	}

	public void setVideoProcessor(VideoProcessor videoProcessor) {
		_videoProcessor = videoProcessor;
	}

	private static VideoProcessor _videoProcessor;

}