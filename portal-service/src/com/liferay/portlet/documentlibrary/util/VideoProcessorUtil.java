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
import com.liferay.portlet.documentlibrary.model.DLProcessorConstants;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class VideoProcessorUtil {

	/**
	 * @deprecated
	 */
	public static void generateVideo(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		getVideoProcessor().generateVideo(
			sourceFileVersion, destinationFileVersion);
	}

	/**
	 * @deprecated
	 */
	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, String type)
		throws Exception {

		return getVideoProcessor().getPreviewAsStream(fileVersion, type);
	}

	/**
	 * @deprecated
	 */
	public static long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		return getVideoProcessor().getPreviewFileSize(fileVersion, type);
	}

	/**
	 * @deprecated
	 */
	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		return getVideoProcessor().getThumbnailAsStream(fileVersion, index);
	}

	/**
	 * @deprecated
	 */
	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return getVideoProcessor().getThumbnailFileSize(fileVersion, index);
	}

	/**
	 * @deprecated
	 */
	public static Set<String> getVideoMimeTypes() {
		return getVideoProcessor().getVideoMimeTypes();
	}

	/**
	 * @deprecated
	 */
	public static VideoProcessor getVideoProcessor() {
		return _videoProcessor;
	}

	/**
	 * @deprecated
	 */
	public static boolean hasVideo(FileVersion fileVersion) {
		return getVideoProcessor().hasVideo(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static boolean isSupported(String mimeType) {
		return getVideoProcessor().isSupported(mimeType);
	}

	/**
	 * @deprecated
	 */
	public static boolean isVideoSupported(FileVersion fileVersion) {
		return getVideoProcessor().isVideoSupported(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static boolean isVideoSupported(String mimeType) {
		return getVideoProcessor().isVideoSupported(mimeType);
	}

	/**
	 * @deprecated
	 */
	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		getVideoProcessor().trigger(sourceFileVersion, destinationFileVersion);
	}

	/**
	 * @deprecated
	 */
	public void setVideoProcessor(VideoProcessor videoProcessor) {
		_videoProcessor = videoProcessor;
	}

	private static VideoProcessor _videoProcessor =
		(VideoProcessor)DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.VIDEO_PROCESSOR);

}