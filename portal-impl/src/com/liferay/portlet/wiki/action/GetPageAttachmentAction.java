/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

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
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
			String title = ParamUtil.getString(actionRequest, "title");
			String fileName = ParamUtil.getString(actionRequest, "fileName");

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			getFile(nodeId, title, fileName, request, response);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, actionRequest, actionResponse);
		}
	}

	@Override
	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long nodeId = ParamUtil.getLong(request, "nodeId");
			String title = ParamUtil.getString(request, "title");
			String fileName = ParamUtil.getString(request, "fileName");

			getFile(nodeId, title, fileName, request, response);

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
			long nodeId, String title, String fileName,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		int pos = fileName.indexOf(CharPool.SLASH);

		if (pos != -1) {
			title = fileName.substring(0, pos);
			fileName = fileName.substring(pos + 1);
		}

		WikiPage page = WikiPageServiceUtil.getPage(nodeId, title);

		String path = page.getAttachmentsDir() + "/" + fileName;

		InputStream is = DLStoreUtil.getFileAsStream(
			page.getCompanyId(), CompanyConstants.SYSTEM, path);
		long contentLength = DLStoreUtil.getFileSize(
			page.getCompanyId(), CompanyConstants.SYSTEM, path);
		String contentType = MimeTypesUtil.getContentType(fileName);

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentLength, contentType);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(
		GetPageAttachmentAction.class);

}