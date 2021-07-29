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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectAuthenticationHandler;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.internal.session.manager.OfflineOpenIdConnectSessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 */
@Component(immediate = true, service = OpenIdConnectServiceHandler.class)
public class OpenIdConnectServiceHandlerImpl
	implements OpenIdConnectServiceHandler {

	@Override
	public boolean hasValidOpenIdConnectSession(HttpSession httpSession)
		throws OpenIdConnectServiceException.NoOpenIdConnectSessionException {

		if (_offlineOpenIdConnectSessionManager.isOpenIdConnectSession(
				httpSession) &&
			!_offlineOpenIdConnectSessionManager.isOpenIdConnectSessionExpired(
				httpSession)) {

			return true;
		}

		return false;
	}

	@Override
	public void processAuthenticationResponse(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		_openIdConnectAuthenticationHandler.processAuthenticationResponse(
			httpServletRequest, httpServletResponse);
	}

	@Override
	public void requestAuthentication(
			String openIdConnectProviderName,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		_openIdConnectAuthenticationHandler.requestAuthentication(
			openIdConnectProviderName, httpServletRequest, httpServletResponse);
	}

	@Reference
	private OfflineOpenIdConnectSessionManager
		_offlineOpenIdConnectSessionManager;

	@Reference
	private OpenIdConnectAuthenticationHandler
		_openIdConnectAuthenticationHandler;

}