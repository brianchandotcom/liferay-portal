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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 */
public class GetPageAttachmentAction extends PortletAction {

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			resourceResponse);

		try {
			long nodeId = ParamUtil.getLong(resourceRequest, "nodeId");
			String title = ParamUtil.getString(resourceRequest, "title");
			String fileName = ParamUtil.getString(resourceRequest, "fileName");
			int status = ParamUtil.getInteger(
				resourceRequest, "status", WorkflowConstants.STATUS_APPROVED);

			getFile(nodeId, title, fileName, status, request, response);

			setForward(resourceRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}
	}

	@Override
	public ActionForward strutsExecute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			long nodeId = ParamUtil.getLong(request, "nodeId");
			String title = ParamUtil.getString(request, "title");
			String fileName = ParamUtil.getString(request, "fileName");
			int status = ParamUtil.getInteger(
				request, "status", WorkflowConstants.STATUS_APPROVED);

			getFile(nodeId, title, fileName, status, request, response);

			return null;
		}
		catch (Exception e) {
			if ((e instanceof NoSuchPageException) ||
				(e instanceof NoSuchFileException)) {

				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}
			else {
				PortalUtil.sendError(e, request, response);
			}

			return null;
		}
	}

	protected void getFile(
			long nodeId, String title, String fileName, int status,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		WikiPage wikiPage = WikiPageServiceUtil.getPage(nodeId, title);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			wikiPage.getGroupId(), wikiPage.getAttachmentsFolderId(), fileName);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if ((status != WorkflowConstants.STATUS_IN_TRASH) &&
			dlFileEntry.isInTrash()) {

			return;
		}

		if (dlFileEntry.isInTrash()) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		GetPageAttachmentAction.class);

}