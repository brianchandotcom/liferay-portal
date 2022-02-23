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