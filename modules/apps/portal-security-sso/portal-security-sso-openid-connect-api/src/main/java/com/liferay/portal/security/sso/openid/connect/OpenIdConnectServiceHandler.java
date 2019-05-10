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

package com.liferay.portal.security.sso.openid.connect;

import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Thuong Dinh
 */
@ProviderType
public interface OpenIdConnectServiceHandler {

	public boolean hasValidOpenIdConnectSession(HttpSession httpSession)
		throws OpenIdConnectServiceException.NoOpenIdConnectSessionException;

	public void processAuthenticationResponse(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException;

	public void requestAuthentication(
			String openIdConnectProviderName,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException;

}