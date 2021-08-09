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

package com.liferay.portal.security.sso.openid.connect.internal.provider;

import com.liferay.portal.security.sso.openid.connect.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.provider.OpenIdConnectSessionProvider;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Istvan Sajtos
 */
@Component(
	service = {
		OpenIdConnectSessionProvider.class,
		OpenIdConnectSessionProviderImpl.class
	}
)
public class OpenIdConnectSessionProviderImpl
	implements OpenIdConnectSessionProvider {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public static void setOpenIdConnectSession(
		HttpSession httpSession, OpenIdConnectSession openIdConnectSession) {

		httpSession.setAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION, openIdConnectSession);
	}

	public OpenIdConnectSession getOpenIdConnectSession(
		HttpSession httpSession) {

		return (OpenIdConnectSession)httpSession.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);
	}

}