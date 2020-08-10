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

package com.liferay.commerce.media.internal.servlet;

import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
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
 * @author Alec Sloan
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/" + CommerceMediaConstants.SERVLET_PATH,
		"osgi.http.whiteboard.servlet.name=com.liferay.commerce.media.servlet.CommerceMediaServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + CommerceMediaConstants.SERVLET_PATH + "/*"
	},
	service = Servlet.class
)
public class CommerceMediaServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		if (PortalSessionThreadLocal.getHttpSession() == null) {
			PortalSessionThreadLocal.setHttpSession(
				httpServletRequest.getSession());
		}

		try {
			User user = _portal.getUser(httpServletRequest);

			if (user == null) {
				user = _userLocalService.getDefaultUser(
					_portal.getCompanyId(httpServletRequest));
			}

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			PrincipalThreadLocal.setName(user.getUserId());
		}
		catch (Exception e) {
			_log.error(e, e);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String contentDisposition = HttpHeaders.CONTENT_DISPOSITION_INLINE;

		boolean download = ParamUtil.getBoolean(httpServletRequest, "download");

		if (download) {
			contentDisposition = HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT;
		}

		_commerceMediaResolver.sendMediaBytes(
			httpServletRequest, httpServletResponse, contentDisposition);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMediaServlet.class);

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}