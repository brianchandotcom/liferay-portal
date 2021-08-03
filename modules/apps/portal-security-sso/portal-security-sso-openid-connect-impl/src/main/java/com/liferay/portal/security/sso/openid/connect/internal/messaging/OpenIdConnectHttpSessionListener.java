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

package com.liferay.portal.security.sso.openid.connect.internal.messaging;

import com.liferay.portal.kernel.servlet.PortletSessionListenerManager;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.internal.auto.login.OpenIdConnectAutoLogin;
import com.liferay.portal.security.sso.openid.connect.internal.session.manager.OfflineOpenIdConnectSessionManager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(immediate = true, service = {})
public class OpenIdConnectHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		if (!_offlineOpenIdConnectSessionManager.isOpenIdConnectSession(
				httpSession) ||
			(httpSession.getAttribute(OpenIdConnectAutoLogin.USER_ID) !=
				null)) {

			return;
		}

		Long openIdConnectSessionId = (Long)httpSession.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION_ID);

		_offlineOpenIdConnectSessionManager.endOpenIdConnectSession(
			openIdConnectSessionId);
	}

	@Activate
	protected void activate() {
		PortletSessionListenerManager.addHttpSessionListener(this);
	}

	@Deactivate
	protected void deactivate() {
		PortletSessionListenerManager.removeHttpSessionListener(this);
	}

	@Reference
	private OfflineOpenIdConnectSessionManager
		_offlineOpenIdConnectSessionManager;

}