/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.document.library.opener.google.drive.web.internal.background.task;

import com.google.api.client.googleapis.media.MediaHttpUploader;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * Translates message bus messages and updates the background task status
 * accordingly. This class understands a payload with two fields:
 *
 * <ul>
 * <li>
 * {@code uploadState}: An instance of {@code
 * MediaHttpUploader.UploadState} used to
 * get the status of a pending upload. This is mapped to the {@code complete}
 * attribute in the background task state.
 * </li>
 * <li>
 * {@code status}: Detects error conditions. If different than {@code
 * BackgroundTaskConstants.STATUS_FAILED},
 * the upload is considered successful.
 * </li>
 * </ul>
 *
 * @author Sergio González
 */
public class UploadGoogleDriveDocumentBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		boolean complete = false;

		MediaHttpUploader.UploadState uploadState =
			(MediaHttpUploader.UploadState)message.get("uploadState");

		if (uploadState == MediaHttpUploader.UploadState.MEDIA_COMPLETE) {
			complete = true;
		}

		backgroundTaskStatus.setAttribute("complete", complete);

		int status = GetterUtil.getInteger(message.get("status"), -1);

		boolean error = false;

		if (status == BackgroundTaskConstants.STATUS_FAILED) {
			error = true;
		}

		backgroundTaskStatus.setAttribute("error", error);
	}

}