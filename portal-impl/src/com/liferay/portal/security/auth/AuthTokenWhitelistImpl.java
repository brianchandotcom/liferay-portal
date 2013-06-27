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
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.Set;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class AuthTokenWhitelistImpl implements AuthTokenWhitelist {

	public AuthTokenWhitelistImpl() {
		resetPortletInvocationWhitelist();
		resetPortletInvocationWhitelistActions();
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

	private Set<String> _portletInvocationWhitelist;
	private Set<String> _portletInvocationWhitelistActions;

}