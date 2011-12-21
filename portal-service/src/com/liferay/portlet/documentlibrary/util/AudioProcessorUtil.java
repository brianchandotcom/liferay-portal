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

import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class AudioProcessorUtil {

	public static void generateAudio(FileVersion fileVersion) throws Exception {
		getAudioProcessor().generateAudio(fileVersion);
	}

	public static Set<String> getAudioMimeTypes() {
		return getAudioProcessor().getAudioMimeTypes();
	}

	public static AudioProcessor getAudioProcessor() {
		return _audioProcessor;
	}

	public static InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return getAudioProcessor().getPreviewAsStream(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return getAudioProcessor().getPreviewFileSize(fileVersion);
	}

	public static boolean hasAudio(FileVersion fileVersion) {
		return getAudioProcessor().hasAudio(fileVersion);
	}

	public static boolean isAudioSupported(FileVersion fileVersion) {
		return getAudioProcessor().isAudioSupported(fileVersion);
	}

	public static boolean isAudioSupported(String mimeType) {
		return getAudioProcessor().isAudioSupported(mimeType);
	}

	public void setAudioProcessor(AudioProcessor audioProcessor) {
		_audioProcessor = audioProcessor;
	}

	public boolean isSupported(String mimeType) {
		return getAudioProcessor().isSupported(mimeType);
	}

	public void trigger(FileVersion fileVersion) {
		getAudioProcessor().trigger(fileVersion);
	}

	private static AudioProcessor _audioProcessor;

}