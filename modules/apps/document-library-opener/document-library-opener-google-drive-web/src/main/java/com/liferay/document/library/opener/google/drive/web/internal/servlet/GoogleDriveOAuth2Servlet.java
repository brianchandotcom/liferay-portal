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

package com.liferay.document.library.opener.google.drive.web.internal.servlet;

import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.OAuth2StateUtil;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveWebConstants;
import com.liferay.document.library.opener.google.drive.web.internal.util.OAuth2Helper;
import com.liferay.document.library.opener.model.OAuth2State;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.document.library.opener.google.drive.web.internal.servlet.GoogleDriveOAuth2Servlet",
		"osgi.http.whiteboard.servlet.pattern=" + DLOpenerGoogleDriveWebConstants.GOOGLE_DRIVE_SERVLET_PATH,
		"servlet.init.httpMethods=GET,POST"
	},
	service = Servlet.class
)
public class GoogleDriveOAuth2Servlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		OAuth2State oAuth2State = OAuth2StateUtil.get(
			_portal.getOriginalServletRequest(httpServletRequest));

		if (oAuth2State == null) {
			throw new IllegalStateException(
				"Authorization oAuth2State not initialized");
		}
		else if (!OAuth2StateUtil.isValid(oAuth2State, httpServletRequest)) {
			OAuth2StateUtil.cleanUp(httpServletRequest);
			httpServletResponse.sendRedirect(oAuth2State.getFailureURL());
		}
		else {
			_requestAuthorizationToken(
				httpServletRequest, httpServletResponse, oAuth2State);
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		doGet(httpServletRequest, httpServletResponse);
	}

	private void _requestAuthorizationToken(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, OAuth2State oAuth2State)
		throws IOException {

		String code = ParamUtil.getString(httpServletRequest, "code");

		if (Validator.isNull(code)) {
			OAuth2StateUtil.cleanUp(httpServletRequest);
			httpServletResponse.sendRedirect(oAuth2State.getFailureURL());
		}
		else {
			try {
				_dlOpenerGoogleDriveManager.requestAuthorizationToken(
					_portal.getCompanyId(httpServletRequest),
					oAuth2State.getUserId(), code,
					_oAuth2Helper.getRedirectURI(httpServletRequest));
			}
			catch (PortalException pe) {
				throw new IOException(pe);
			}

			OAuth2StateUtil.cleanUp(httpServletRequest);
			httpServletResponse.sendRedirect(oAuth2State.getSuccessURL());
		}
	}

	private static final long serialVersionUID = 7759897747401129852L;

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	@Reference
	private OAuth2Helper _oAuth2Helper;

	@Reference
	private Portal _portal;

}