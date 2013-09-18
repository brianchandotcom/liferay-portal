/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.documentlibrary.util.AudioProcessorUtil;

import java.util.Set;

/**
 * @author Juan Gonzalez
 */
public class AudioCommandReceiver extends BaseFileEntryCommandReceiver {

	@Override
	protected String[] getFileEntryMimeTypes() {
		Set<String> audioMimeTypes = AudioProcessorUtil.getAudioMimeTypes();

		if (audioMimeTypes == null) {
			return null;
		}

		return ArrayUtil.toStringArray(audioMimeTypes.toArray());
	}

	@Override
	protected String getUnavaiablePreviewErrorMessage() {
		return _UNAVAIABLE_PREVIEW_ERROR_MESSAGE;
	}

	@Override
	protected int getXugglerDisabledFileUploadReturnValue() {
		return ServletResponseConstants.SC_AUDIO_PREVIEW_DISABLED_EXCEPTION;
	}

	@Override
	protected boolean hasFileEntryPreview(FileVersion fileVersion) {
		return AudioProcessorUtil.hasAudio(fileVersion);
	}

	private static final String _UNAVAIABLE_PREVIEW_ERROR_MESSAGE =
		"the-audio-preview-is-not-yet-ready.-please-try-again-later";

}