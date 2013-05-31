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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.PortletSecurity;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.Set;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class PortletSecurityImpl implements PortletSecurity {

	public PortletSecurityImpl() {
		resetWhitelist();
		resetWhitelistActions();
	}

	@Override
	public Set<String> getWhitelist() {
		return _whitelist;
	}

	@Override
	public Set<String> getWhitelistActions() {
		return _whitelistActions;
	}

	@Override
	public Set<String> resetWhitelist() {
		_whitelist = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);
		_whitelist = Collections.unmodifiableSet(_whitelist);

		return _whitelist;
	}

	@Override
	public Set<String> resetWhitelistActions() {
		_whitelistActions = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS);
		_whitelistActions = Collections.unmodifiableSet(_whitelistActions);

		return _whitelistActions;
	}

	private Set<String> _whitelist;
	private Set<String> _whitelistActions;

}