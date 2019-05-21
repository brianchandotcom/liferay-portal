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

package com.liferay.message.boards.web.internal.portlet.action;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/get_attachments"
	},
	service = MVCResourceCommand.class
)
public class GetAttachmentsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		MBMessage message = ActionUtil.getMessage(resourceRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"active",
				_getAttachmentsJSONArray(
					message, message.getAttachmentsFileEntries(),
					resourceRequest, resourceResponse)
			).put(
				"deleted",
				_getAttachmentsJSONArray(
					message, message.getDeletedAttachmentsFileEntries(),
					resourceRequest, resourceResponse)
			));
	}

	private JSONArray _getAttachmentsJSONArray(
			MBMessage message, List<FileEntry> attachmentsFileEntries,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (FileEntry fileEntry : attachmentsFileEntries) {
			JSONObject jsonObject = JSONUtil.put(
				"id", fileEntry.getFileEntryId()
			).put(
				"title", fileEntry.getTitle()
			).put(
				"size",
				TextFormatter.formatStorageSize(
					fileEntry.getSize(), resourceRequest.getLocale())
			).put(
				"deleteURL",
				_getDeleteURL(
					message, resourceRequest, resourceResponse, fileEntry)
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private String _getDeleteCommand(ResourceRequest resourceRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_trashHelper.isTrashEnabled(themeDisplay.getScopeGroupId())) {
			return Constants.MOVE_TO_TRASH;
		}

		return Constants.DELETE;
	}

	private PortletURL _getDeleteURL(
			MBMessage message, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, FileEntry fileEntry)
		throws PortalException {

		PortletURL deleteURL = resourceResponse.createActionURL();

		deleteURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/message_boards/edit_message_attachments");
		deleteURL.setParameter(
			Constants.CMD, _getDeleteCommand(resourceRequest));
		deleteURL.setParameter(
			"fileName", HtmlUtil.unescape(fileEntry.getTitle()));
		deleteURL.setParameter(
			"messageId", String.valueOf(message.getMessageId()));

		return deleteURL;
	}

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

}