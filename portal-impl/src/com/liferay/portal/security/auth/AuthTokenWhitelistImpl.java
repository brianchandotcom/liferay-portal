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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.BaseAuthTokenWhitelist;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class AuthTokenWhitelistImpl extends BaseAuthTokenWhitelist {

	public AuthTokenWhitelistImpl() {
		trackWhitelistServices(
			PropsKeys.AUTH_TOKEN_IGNORE_ORIGINS, _originCSRFWhitelist);

		registerPortalProperty(PropsKeys.AUTH_TOKEN_IGNORE_ORIGINS);

		trackWhitelistServices(
			PropsKeys.AUTH_TOKEN_IGNORE_PORTLETS, _portletCSRFWhitelist);

		registerPortalProperty(PropsKeys.AUTH_TOKEN_IGNORE_PORTLETS);

		trackWhitelistServices(
			PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST,
			_portletInvocationWhitelist);

		registerPortalProperty(
			PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public Set<String> getOriginCSRFWhitelist() {
		return _originCSRFWhitelist;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public Set<String> getPortletCSRFWhitelist() {
		return _portletCSRFWhitelist;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public Set<String> getPortletInvocationWhitelist() {
		return _portletInvocationWhitelist;
	}

	@Override
	public boolean isOriginCSRFWhitelisted(long companyId, String origin) {
		for (String whitelistedOrigin : _originCSRFWhitelist) {
			if (origin.startsWith(whitelistedOrigin)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		return _portletCSRFWhitelist.contains(portlet.getRootPortletId());
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		return _portletInvocationWhitelist.contains(portlet.getPortletId());
	}

	@Override
	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String rootPortletId = PortletIdCodec.decodePortletName(
			liferayPortletURL.getPortletId());

		return _portletCSRFWhitelist.contains(rootPortletId);
	}

	@Override
	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		return _portletInvocationWhitelist.contains(
			liferayPortletURL.getPortletId());
	}

	@Override
	public boolean isValidSharedSecret(String sharedSecret) {
		if (Validator.isNull(sharedSecret)) {
			return false;
		}

		if (Validator.isNull(PropsValues.AUTH_TOKEN_SHARED_SECRET)) {
			return false;
		}

		return sharedSecret.equals(
			Encryptor.digest(PropsValues.AUTH_TOKEN_SHARED_SECRET));
	}

	private final Set<String> _originCSRFWhitelist = new ConcurrentHashSet<>();
	private final Set<String> _portletCSRFWhitelist = new ConcurrentHashSet<>();
	private final Set<String> _portletInvocationWhitelist =
		new ConcurrentHashSet<>();

}