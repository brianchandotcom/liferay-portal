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

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class AuthTokenWhitelistImpl extends BaseAuthTokenWhitelist {

	public AuthTokenWhitelistImpl() {
		resetOriginCSRFWhitelist();
		resetPortletCSRFWhitelist();
		resetPortletInvocationWhitelist();
	}

	@Override
	public Set<String> getOriginCSRFWhitelist() {
		return _originCSRFWhitelist;
	}

	@Override
	public Set<String> getPortletCSRFWhitelist() {
		return _portletCSRFWhitelist;
	}

	@Override
	public Set<String> getPortletInvocationWhitelist() {
		return _portletInvocationWhitelist;
	}

	@Override
	public boolean isOriginCSRFWhitelisted(long companyId, String origin) {
		Set<String> whitelist = getOriginCSRFWhitelist();

		for (String whitelistedOrigins : whitelist) {
			if (origin.startsWith(whitelistedOrigins)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		String rootPortletId = portlet.getRootPortletId();

		Set<String> whitelist = getPortletCSRFWhitelist();

		if (whitelist.contains(rootPortletId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		Set<String> whitelist = getPortletInvocationWhitelist();

		if (whitelist.contains(portlet.getPortletId())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String portletId = liferayPortletURL.getPortletId();

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		Set<String> whitelist = getPortletCSRFWhitelist();

		if (whitelist.contains(rootPortletId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String portletId = liferayPortletURL.getPortletId();

		Set<String> whitelist = getPortletInvocationWhitelist();

		if (whitelist.contains(portletId)) {
			return true;
		}

		return false;
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

	@Override
	public Set<String> resetOriginCSRFWhitelist() {
		_originCSRFWhitelist = SetUtil.fromArray(
			PropsValues.AUTH_TOKEN_IGNORE_ORIGINS);
		_originCSRFWhitelist = Collections.unmodifiableSet(
			_originCSRFWhitelist);

		return _originCSRFWhitelist;
	}

	@Override
	public Set<String> resetPortletCSRFWhitelist() {
		_portletCSRFWhitelist = SetUtil.fromArray(
			PropsValues.AUTH_TOKEN_IGNORE_PORTLETS);
		_portletCSRFWhitelist = Collections.unmodifiableSet(
			_portletCSRFWhitelist);

		return _portletCSRFWhitelist;
	}

	@Override
	public Set<String> resetPortletInvocationWhitelist() {
		_portletInvocationWhitelist = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);
		_portletInvocationWhitelist = Collections.unmodifiableSet(
			_portletInvocationWhitelist);

		return _portletInvocationWhitelist;
	}

	private Set<String> _originCSRFWhitelist;
	private Set<String> _portletCSRFWhitelist;
	private Set<String> _portletInvocationWhitelist;

}