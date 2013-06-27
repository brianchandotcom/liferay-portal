/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class AuthTokenWhitelistImpl implements AuthTokenWhitelist {

	public AuthTokenWhitelistImpl() {
		resetPortletCSRFWhitelist();
		resetPortletCSRFWhitelistActions();
		resetPortletInvocationWhitelist();
		resetPortletInvocationWhitelistActions();
	}

	@Override
	public Set<String> getPortletCSRFWhitelist() {
		return _portletCSRFWhitelist;
	}

	@Override
	public Set<String> getPortletCSRFWhitelistActions() {
		return _portletCSRFWhitelistActions;
	}

	@Override
	public Set<String> getPortletInvocationWhitelist() {
		return _portletInvocationWhitelist;
	}

	@Override
	public Set<String> getPortletInvocationWhitelistActions() {
		return _portletInvocationWhitelistActions;
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction) {

		Set<String> whitelist = getPortletInvocationWhitelist();

		if (whitelist.contains(portletId)) {
			return true;
		}

		if (Validator.isNotNull(strutsAction)) {
			Set<String> whitelistActions =
				getPortletInvocationWhitelistActions();

			if (whitelistActions.contains(strutsAction)) {
				return true;
			}
		}

		return false;
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
	public Set<String> resetPortletCSRFWhitelistActions() {
		_portletCSRFWhitelistActions = SetUtil.fromArray(
			PropsValues.AUTH_TOKEN_IGNORE_ACTIONS);
		_portletCSRFWhitelistActions = Collections.unmodifiableSet(
			_portletCSRFWhitelistActions);

		return _portletCSRFWhitelistActions;
	}

	@Override
	public Set<String> resetPortletInvocationWhitelist() {
		_portletInvocationWhitelist = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);
		_portletInvocationWhitelist = Collections.unmodifiableSet(
			_portletInvocationWhitelist);

		return _portletInvocationWhitelist;
	}

	@Override
	public Set<String> resetPortletInvocationWhitelistActions() {
		_portletInvocationWhitelistActions = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS);
		_portletInvocationWhitelistActions = Collections.unmodifiableSet(
			_portletInvocationWhitelistActions);

		return _portletInvocationWhitelistActions;
	}

	private Set<String> _portletCSRFWhitelist;
	private Set<String> _portletCSRFWhitelistActions;
	private Set<String> _portletInvocationWhitelist;
	private Set<String> _portletInvocationWhitelistActions;

}