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
public class AudioProcessorUtil {

	public static void generateAudio(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		if (_audioProcessor != null) {
			_audioProcessor.generateAudio(
				sourceFileVersion, destinationFileVersion);
		}
	}

	public static Set<String> getAudioMimeTypes() {
		if (_audioProcessor == null) {
			return null;
		}

		return _audioProcessor.getAudioMimeTypes();
	}

	public static AudioProcessor getAudioProcessor() {
		return _audioProcessor;
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, String type)
		throws Exception {

		if (_audioProcessor == null) {
			return null;
		}

		return _audioProcessor.getPreviewAsStream(fileVersion, type);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		if (_audioProcessor == null) {
			return 0;
		}

		return _audioProcessor.getPreviewFileSize(fileVersion, type);
	}

	public static boolean hasAudio(FileVersion fileVersion) {
		if (_audioProcessor == null) {
			return false;
		}

		return _audioProcessor.hasAudio(fileVersion);
	}

	public static boolean isAudioSupported(FileVersion fileVersion) {
		if (_audioProcessor == null) {
			return false;
		}

		return _audioProcessor.isAudioSupported(fileVersion);
	}

	public static boolean isAudioSupported(String mimeType) {
		if (_audioProcessor == null) {
			return false;
		}

		return _audioProcessor.isAudioSupported(mimeType);
	}

	public static boolean isEnabled() {
		if (_audioProcessor == null) {
			return false;
		}

		return _audioProcessor.isEnabled();
	}

	public static boolean isSupported(String mimeType) {
		if (_audioProcessor == null) {
			return false;
		}

		return _audioProcessor.isSupported(mimeType);
	}

	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		if (_audioProcessor == null) {
			return;
		}

		_audioProcessor.trigger(sourceFileVersion, destinationFileVersion);
	}

	private static volatile AudioProcessor _audioProcessor =
		ServiceProxyFactory.newServiceTrackedInstance(
			AudioProcessor.class, AudioProcessorUtil.class, "_audioProcessor",
			false);

}