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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/copy_folder"
	},
	service = MVCActionCommand.class
)
public class CopyFolderMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFolder.class.getName(), actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			_copyFolder(
				actionRequest, actionResponse, serviceContext, themeDisplay);
		}
		catch (IOException ioException) {
			_log.error(ioException);

			throw new PortalException(ioException);
		}
	}

	private void _copyFolder(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ServiceContext serviceContext, ThemeDisplay themeDisplay)
		throws IOException {

		long sourceRepositoryId = ParamUtil.getLong(
			actionRequest, "sourceRepositoryId");
		long sourceFolderId = ParamUtil.getLong(
			actionRequest, "sourceFolderId");
		long destinationRepositoryId = ParamUtil.getLong(
			actionRequest, "destinationRepositoryId");
		long destinationParentFolderId = ParamUtil.getLong(
			actionRequest, "destinationParentFolderId");

		try {
			_dlAppService.copyFolder(
				sourceRepositoryId, sourceFolderId, destinationRepositoryId,
				destinationParentFolderId, serviceContext);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, _jsonFactory.createJSONObject());
		}
		catch (PortalException portalException) {
			String errorMessage = themeDisplay.translate(
				portalException.getMessage());

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("errorMessage", errorMessage));

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyFolderMVCActionCommand.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private JSONFactory _jsonFactory;

}