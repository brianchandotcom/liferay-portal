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

package com.liferay.journal.web.portlet.action;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.web.upload.ImageJournalUploadHandler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.upload.UploadHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/upload_image"
	},
	service = MVCActionCommand.class
)
public class UploadImageMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(actionRequest, actionResponse);
	}

	private final UploadHandler _uploadHandler =
		new ImageJournalUploadHandler();

}