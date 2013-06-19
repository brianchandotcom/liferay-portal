/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class GetMessageAttachmentAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			long messageId = ParamUtil.getLong(actionRequest, "messageId");
			String fileName = ParamUtil.getString(actionRequest, "attachment");
			int status = ParamUtil.getInteger(
				actionRequest, "status", WorkflowConstants.STATUS_APPROVED);

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			getFile(messageId, fileName, status, request, response);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, actionRequest, actionResponse);
		}
	}

	@Override
	public ActionForward strutsExecute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			long messageId = ParamUtil.getLong(request, "messageId");
			String fileName = ParamUtil.getString(request, "attachment");
			int status = ParamUtil.getInteger(
				request, "status", WorkflowConstants.STATUS_APPROVED);

			getFile(messageId, fileName, status, request, response);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected void getFile(
			long messageId, String fileName, int status,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		MBMessage message = MBMessageServiceUtil.getMessage(messageId);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			message.getGroupId(), message.getAttachmentsFolderId(), fileName);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		if ((status != WorkflowConstants.STATUS_IN_TRASH) &&
			(dlFileVersion.isInTrash() || dlFileEntry.isInTrashContainer())) {

			return;
		}

		if (dlFileVersion.isInTrash()) {
			fileName = TrashUtil.getOriginalTitle(dlFileEntry.getTitle());
		}

		ServletResponseUtil.sendFile(
			request, response, fileName, fileEntry.getContentStream(),
			fileEntry.getSize(), fileEntry.getMimeType());
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}