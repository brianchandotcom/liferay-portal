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

import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.documentlibrary.util.VideoProcessorUtil;

import java.util.Set;

/**
 * @author Juan Gonzalez
 * @author Roberto Díaz
 */
public class VideoCommandReceiver extends BaseFileEntryCommandReceiver {

	@Override
	protected String[] getFileEntryMimeTypes() {
		Set<String> videoMimeTypes = VideoProcessorUtil.getVideoMimeTypes();

		if (videoMimeTypes == null) {
			return null;
		}

		return ArrayUtil.toStringArray(videoMimeTypes.toArray());
	}

	@Override
	protected String getUnavaiablePreviewErrorMessage() {
		return _UNAVAIABLE_PREVIEW_ERROR_MESSAGE;
	}

	@Override
	protected int getXugglerDisabledFileUploadReturnValue() {
		return ServletResponseConstants.SC_VIDEO_PREVIEW_DISABLED_EXCEPTION;
	}

	private static final String _UNAVAIABLE_PREVIEW_ERROR_MESSAGE =
		"the-video-preview-is-not-yet-ready.-please-try-again-later";

}