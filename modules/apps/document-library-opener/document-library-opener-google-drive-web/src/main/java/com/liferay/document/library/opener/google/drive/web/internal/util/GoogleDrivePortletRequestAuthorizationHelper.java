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

package com.liferay.document.library.opener.google.drive.web.internal.util;

import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.oauth.OAuth2StateUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = GoogleDrivePortletRequestAuthorizationHelper.class)
public class GoogleDrivePortletRequestAuthorizationHelper {

	public void performAuthorizationFlow(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String state = PwdGenerator.getPassword(5);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(portletRequest));

		OAuth2StateUtil.save(
			originalHttpServletRequest, themeDisplay.getUserId(),
			_getSuccessURL(portletRequest), _getFailureURL(portletRequest),
			state);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(portletResponse);

		String authorizationURL =
			_dlOpenerGoogleDriveManager.getAuthorizationURL(
				themeDisplay.getCompanyId(), state,
				OAuth2StateUtil.getRedirectURI(
					_portal.getPortalURL(portletRequest)));

		if (!_dlOpenerGoogleDriveManager.hasValidCredential(
				themeDisplay.getCompanyId(), themeDisplay.getUserId())) {

			authorizationURL = _httpUtil.setParameter(
				authorizationURL, "prompt", "select_account");
		}

		httpServletResponse.sendRedirect(authorizationURL);
	}

	private String _getFailureURL(PortletRequest portletRequest)
		throws PortalException {

		LiferayPortletURL liferayPortletURL = _portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			_portal.getControlPanelPlid(portletRequest),
			PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	private String _getSuccessURL(PortletRequest portletRequest) {
		return _portal.getCurrentURL(
			_portal.getHttpServletRequest(portletRequest));
	}

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	@Reference
	private HttpUtil _httpUtil;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}