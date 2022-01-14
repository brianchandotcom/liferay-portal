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

package com.liferay.document.library.opener.google.drive.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.opener.google.drive.constants.DLOpenerGoogleDriveMimeTypes;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveConstants;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/google_drive_background_task_status"
	},
	service = MVCResourceCommand.class
)
public class GoogleDriveBackgroundTaskStatusMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		boolean complete = false;
		boolean error = false;

		long backgroundTaskId = ParamUtil.getLong(
			resourceRequest, "backgroundTaskId");

		BackgroundTaskStatus backgroundTaskStatus =
			_backgroundTaskStatusRegistry.getBackgroundTaskStatus(
				backgroundTaskId);

		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId");

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				fetchDLOpenerFileEntryReference(
					DLOpenerGoogleDriveConstants.GOOGLE_DRIVE_REFERENCE_TYPE,
					fileEntry);

		if (backgroundTaskStatus == null) {
			if (dlOpenerFileEntryReference == null) {
				complete = false;
				error = true;
			}
			else {
				complete = true;
				error = false;
			}
		}
		else {
			complete = GetterUtil.getBoolean(
				backgroundTaskStatus.getAttribute("complete"));
			error = GetterUtil.getBoolean(
				backgroundTaskStatus.getAttribute("error"));
		}

		JSONObject jsonObject = JSONUtil.put(
			"complete", complete
		).put(
			"error", error
		);

		if (complete && (dlOpenerFileEntryReference != null) &&
			Validator.isNotNull(dlOpenerFileEntryReference.getReferenceKey())) {

			jsonObject.put(
				"googleDocsEditURL",
				PortletURLBuilder.createRenderURL(
					_portal.getLiferayPortletResponse(resourceResponse),
					_portal.getPortletId(resourceRequest)
				).setMVCRenderCommandName(
					"/document_library/open_google_docs"
				).setParameter(
					"fileEntryId", fileEntryId
				).setParameter(
					"googleDocsEditURL",
					_getGoogleDocsEditURL(
						dlOpenerFileEntryReference.getReferenceKey(),
						DLOpenerGoogleDriveMimeTypes.getGoogleDocsMimeType(
							fileEntry.getMimeType()))
				).setParameter(
					"googleDocsRedirect",
					ParamUtil.getString(resourceRequest, "googleDocsRedirect")
				).setWindowState(
					LiferayWindowState.EXCLUSIVE
				).buildString());
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	private String _getGoogleDocsEditURL(
		String googleDriveFileId, String mimeType) {

		return StringBundler.concat(
			_paths.get(mimeType),
			FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY,
			googleDriveFileId, "/edit");
	}

	private static final Map<String, String> _paths = MapUtil.fromArray(
		DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_GOOGLE_APPS_DOCUMENT,
		"document",
		DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_GOOGLE_APPS_PRESENTATION,
		"presentation",
		DLOpenerGoogleDriveMimeTypes.APPLICATION_VND_GOOGLE_APPS_SPREADSHEET,
		"spreadsheets");

	@Reference
	private BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	@Reference
	private Portal _portal;

}