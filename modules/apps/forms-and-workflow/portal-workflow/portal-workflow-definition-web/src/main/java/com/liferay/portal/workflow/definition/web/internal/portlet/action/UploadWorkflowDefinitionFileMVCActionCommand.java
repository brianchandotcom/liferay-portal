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

package com.liferay.portal.workflow.definition.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.InputStream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	property = {
		"javax.portlet.name=" + PortletKeys.WORKFLOW_DEFINITION,
		"mvc.command.name=uploadWorkflowDefinitionFile"
	},
	service = MVCResourceCommand.class
)
public class UploadWorkflowDefinitionFileMVCActionCommand
	extends BaseMVCResourceCommand {

	public static final String TEMP_FOLDER_NAME =
		UploadWorkflowDefinitionFileMVCActionCommand.class.getName();

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(resourceRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String sourceFileName = uploadPortletRequest.getFileName("file");

		InputStream inputStream = null;

		try {
			String fileName = TempFileEntryUtil.getTempFileName(sourceFileName);

			inputStream = uploadPortletRequest.getFileAsStream("file");

			String mimeType = uploadPortletRequest.getContentType("file");

			FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				TEMP_FOLDER_NAME, fileName, inputStream, mimeType);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", fileEntry.getGroupId());
			jsonObject.put("name", fileEntry.getTitle());
			jsonObject.put("title", sourceFileName);
			jsonObject.put("uuid", fileEntry.getUuid());

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UploadWorkflowDefinitionFileMVCActionCommand.class);

}