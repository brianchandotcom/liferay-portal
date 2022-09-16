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

package com.liferay.document.library.taglib.internal.servlet;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.document.library.taglib.internal.servlet.RepositoryBrowserServlet",
		"osgi.http.whiteboard.servlet.pattern=/repository_browser",
		"servlet.init.httpMethods=DELETE"
	},
	service = Servlet.class
)
public class RepositoryBrowserServlet extends HttpServlet {

	@Override
	protected void doDelete(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			User user = _portal.getUser(httpServletRequest);

			if ((user == null) || user.isDefaultUser()) {
				throw new PrincipalException.MustBeAuthenticated(
					StringPool.BLANK);
			}

			PrincipalThreadLocal.setName(user.getUserId());

			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			long fileEntryId = ParamUtil.getLong(
				httpServletRequest, "fileEntryId");

			if (fileEntryId != 0) {
				_dlAppService.deleteFileEntry(fileEntryId);
			}

			long fileShortcutId = ParamUtil.getLong(
				httpServletRequest, "fileShortcutId");

			if (fileShortcutId != 0) {
				_dlAppService.deleteFileShortcut(fileShortcutId);
			}

			long folderId = ParamUtil.getLong(httpServletRequest, "folderId");

			if (folderId != 0) {
				_dlAppService.deleteFolder(folderId);
			}

			SessionMessages.add(httpServletRequest, "requestProcessed");

			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"success", true
				).toString());
		}
		catch (PortalException portalException) {
			throw new ServletException(portalException);
		}
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

}