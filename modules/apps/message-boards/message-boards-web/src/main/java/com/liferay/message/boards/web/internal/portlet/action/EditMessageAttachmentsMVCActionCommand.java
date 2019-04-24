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

import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.web.internal.upload.TempAttachmentMBUploadFileEntryHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadHandler;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/edit_message_attachments"
	},
	service = MVCActionCommand.class
)
public class EditMessageAttachmentsMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_tempAttachmentMBUploadFileEntryHandler,
			_multipleUploadResponseHandler, actionRequest, actionResponse);
	}

	protected void deleteAttachment(
			ActionRequest actionRequest, boolean moveToTrash)
		throws PortalException {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		String fileName = ParamUtil.getString(actionRequest, "fileName");

		if (moveToTrash) {
			_mbMessageService.moveMessageAttachmentToTrash(messageId, fileName);
		}
		else {
			_mbMessageService.deleteMessageAttachment(messageId, fileName);
		}
	}

	protected void deleteTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long categoryId = ParamUtil.getLong(uploadPortletRequest, "categoryId");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			_mbMessageService.deleteTempAttachment(
				themeDisplay.getScopeGroupId(), categoryId,
				MBMessageConstants.TEMP_FOLDER_NAME, fileName);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("deleted", Boolean.FALSE);

			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("errorMessage", errorMessage);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD_TEMP)) {
				addTempAttachment(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteAttachment(actionRequest, false);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempAttachment(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.EMPTY_TRASH)) {
				emptyTrash(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteAttachment(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreEntries(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (PrincipalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/message_boards/error.jsp");
		}
	}

	protected void emptyTrash(ActionRequest actionRequest) throws Exception {
		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		_mbMessageService.emptyMessageAttachments(messageId);
	}

	protected void restoreEntries(ActionRequest actionRequest)
		throws Exception {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		String fileName = ParamUtil.getString(actionRequest, "fileName");

		_mbMessageService.restoreMessageAttachmentFromTrash(
			messageId, fileName);
	}

	@Reference
	private MBMessageService _mbMessageService;

	@Reference(target = "(upload.response.handler=multiple)")
	private UploadResponseHandler _multipleUploadResponseHandler;

	@Reference
	private Portal _portal;

	@Reference
	private TempAttachmentMBUploadFileEntryHandler
		_tempAttachmentMBUploadFileEntryHandler;

	@Reference
	private UploadHandler _uploadHandler;

}