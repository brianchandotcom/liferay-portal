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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.upload.UploadHandler;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.web.internal.upload.PageAttachmentWikiUploadFileEntryHandler;
import com.liferay.wiki.web.internal.upload.PageAttachmentWikiUploadResponseHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"mvc.command.name=/wiki/image_editor",
		"mvc.command.name=/wiki/upload_page_attachment"
	},
	service = MVCActionCommand.class
)
public class UploadPageAttachmentMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_pageAttachmentWikiUploadFileEntryHandler,
			_pageAttachmentWikiUploadResponseHandler, actionRequest,
			actionResponse);
	}

	@Reference
	private PageAttachmentWikiUploadFileEntryHandler
		_pageAttachmentWikiUploadFileEntryHandler;

	@Reference
	private PageAttachmentWikiUploadResponseHandler
		_pageAttachmentWikiUploadResponseHandler;

	@Reference
	private UploadHandler _uploadHandler;

}