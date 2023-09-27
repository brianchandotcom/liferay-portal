/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal.background.task;

import com.google.api.client.googleapis.media.MediaHttpUploader;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
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