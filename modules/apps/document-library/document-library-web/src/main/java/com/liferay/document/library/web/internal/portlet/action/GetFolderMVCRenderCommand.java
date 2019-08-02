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

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
public abstract class GetFolderMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FOLDER,
				ActionUtil.getFolder(renderRequest));

			renderRequest.setAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_TRASH_UTIL, getDLTrashUtil());

			HttpSession session = _getPortalSession(renderRequest);

			String error = GetterUtil.getString(session.getAttribute("error"));

			if (Validator.isNotNull(error)) {
				session.removeAttribute("error");

				SessionErrors.add(renderRequest, error);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/document_library/error.jsp";
			}

			throw new PortletException(e);
		}

		return getPath();
	}

	protected abstract DLTrashUtil getDLTrashUtil();

	protected abstract String getPath();

	@Reference
	protected Portal portal;

	private HttpSession _getPortalSession(PortletRequest portletRequest) {
		HttpServletRequest originalHttpServletRequest =
			portal.getOriginalServletRequest(
				portal.getHttpServletRequest(portletRequest));

		return originalHttpServletRequest.getSession();
	}

}