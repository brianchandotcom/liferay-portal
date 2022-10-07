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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.upload.DDMUserPersonalFolderUploadFileEntryHandler;
import com.liferay.dynamic.data.mapping.form.web.internal.upload.DDMUserPersonalFolderUploadResponseHandler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.upload.UploadHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"mvc.command.name=/dynamic_data_mapping_form/upload_ddm_user_personal_folder"
	},
	service = MVCActionCommand.class
)
public class UploadDDMUserPersonalFolderMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_ddmUserPersonalFolderUploadFileEntryHandler,
			_ddmUserPersonalFolderUploadResponseHandler, actionRequest,
			actionResponse);
	}

	@Reference
	private DDMUserPersonalFolderUploadFileEntryHandler
		_ddmUserPersonalFolderUploadFileEntryHandler;

	@Reference
	private DDMUserPersonalFolderUploadResponseHandler
		_ddmUserPersonalFolderUploadResponseHandler;

	@Reference
	private UploadHandler _uploadHandler;

}