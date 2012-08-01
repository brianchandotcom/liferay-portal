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
public class AudioProcessorUtil {

	/**
	 * @deprecated
	 */
	public static void generateAudio(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		getAudioProcessor().generateAudio(
			sourceFileVersion, destinationFileVersion);
	}

	/**
	 * @deprecated
	 */
	public static Set<String> getAudioMimeTypes() {
		return getAudioProcessor().getAudioMimeTypes();
	}

	/**
	 * @deprecated
	 */
	public static AudioProcessor getAudioProcessor() {
		return _audioProcessor;
	}

	/**
	 * @deprecated
	 */
	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, String type)
		throws Exception {

		return getAudioProcessor().getPreviewAsStream(fileVersion, type);
	}

	/**
	 * @deprecated
	 */
	public static long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		return getAudioProcessor().getPreviewFileSize(fileVersion, type);
	}

	/**
	 * @deprecated
	 */
	public static boolean hasAudio(FileVersion fileVersion) {
		return getAudioProcessor().hasAudio(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static boolean isAudioSupported(FileVersion fileVersion) {
		return getAudioProcessor().isAudioSupported(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static boolean isAudioSupported(String mimeType) {
		return getAudioProcessor().isAudioSupported(mimeType);
	}

	/**
	 * @deprecated
	 */
	public static boolean isSupported(String mimeType) {
		return getAudioProcessor().isSupported(mimeType);
	}

	/**
	 * @deprecated
	 */
	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		getAudioProcessor().trigger(sourceFileVersion, destinationFileVersion);
	}

	/**
	 * @deprecated
	 */
	public void setAudioProcessor(AudioProcessor audioProcessor) {
		_audioProcessor = audioProcessor;
	}

	private static AudioProcessor _audioProcessor =
		(AudioProcessor)DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.AUDIO_PROCESSOR);

}