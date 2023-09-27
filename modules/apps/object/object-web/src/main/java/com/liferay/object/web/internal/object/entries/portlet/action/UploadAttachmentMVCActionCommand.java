/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.portlet.action;

import com.liferay.object.web.internal.object.entries.upload.AttachmentUploadFileEntryHandler;
import com.liferay.object.web.internal.object.entries.upload.AttachmentUploadResponseHandler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.upload.UploadHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Carolina Barbosa
 */
public class UploadAttachmentMVCActionCommand extends BaseMVCActionCommand {

	public UploadAttachmentMVCActionCommand(
		AttachmentUploadFileEntryHandler attachmentUploadFileEntryHandler,
		AttachmentUploadResponseHandler attachmentUploadResponseHandler,
		UploadHandler uploadHandler) {

		_attachmentUploadFileEntryHandler = attachmentUploadFileEntryHandler;
		_attachmentUploadResponseHandler = attachmentUploadResponseHandler;
		_uploadHandler = uploadHandler;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_attachmentUploadFileEntryHandler, _attachmentUploadResponseHandler,
			actionRequest, actionResponse);

		hideDefaultSuccessMessage(actionRequest);
	}

	private final AttachmentUploadFileEntryHandler
		_attachmentUploadFileEntryHandler;
	private final AttachmentUploadResponseHandler
		_attachmentUploadResponseHandler;
	private final UploadHandler _uploadHandler;

}