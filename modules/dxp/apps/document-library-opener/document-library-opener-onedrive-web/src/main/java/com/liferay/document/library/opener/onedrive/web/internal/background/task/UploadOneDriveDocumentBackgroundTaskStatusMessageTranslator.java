/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.background.task;

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
 * @review
 */
public class UploadOneDriveDocumentBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		int status = GetterUtil.getInteger(message.get("status"), -1);

		if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			backgroundTaskStatus.setAttribute("complete", Boolean.TRUE);
		}

		boolean error = false;

		if (status == BackgroundTaskConstants.STATUS_FAILED) {
			error = true;
		}

		backgroundTaskStatus.setAttribute("error", error);
	}

}