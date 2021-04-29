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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.web.internal.upload.ImageJournalUploadFileEntryHandler;
import com.liferay.journal.web.internal.upload.ImageJournalUploadResponseHandler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.upload.UploadHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/image_editor",
		"mvc.command.name=/journal/upload_image"
	},
	service = MVCActionCommand.class
)
public class UploadImageMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_imageJournalUploadFileEntryHandler,
			_imageJournalUploadResponseHandler, actionRequest, actionResponse);
	}

	@Reference
	private ImageJournalUploadFileEntryHandler
		_imageJournalUploadFileEntryHandler;

	@Reference
	private ImageJournalUploadResponseHandler
		_imageJournalUploadResponseHandler;

	@Reference
	private UploadHandler _uploadHandler;

}