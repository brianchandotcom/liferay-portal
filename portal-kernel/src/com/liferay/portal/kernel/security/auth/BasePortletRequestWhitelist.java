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

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Set;

/**
 * @author Péter Borkuti
 * @author Tomas Polesovsky
 */
public abstract class BasePortletRequestWhitelist
	implements PortletRequestWhitelist {

	public BasePortletRequestWhitelist() {
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

	public abstract String[] getWhitelistActionsPropsValues();

	public abstract String[] getWhitelistPropsValues();

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

			if (whitelistActions.contains(strutsAction) &&
				isValidStrutsAction(companyId, portletId, strutsAction)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public Set<String> resetPortletInvocationWhitelist() {
		_portletInvocationWhitelist = SetUtil.fromArray(
			getWhitelistPropsValues());

		if (_portletInvocationWhitelist.isEmpty()) {
			_portletInvocationWhitelist = Collections.emptySet();
		}
		else {
			_portletInvocationWhitelist = Collections.unmodifiableSet(
				_portletInvocationWhitelist);
		}

		return _portletInvocationWhitelist;
	}

	@Override
	public Set<String> resetPortletInvocationWhitelistActions() {
		_portletInvocationWhitelistActions = SetUtil.fromArray(
			getWhitelistActionsPropsValues());

		if (_portletInvocationWhitelistActions.isEmpty()) {
			_portletInvocationWhitelistActions = Collections.emptySet();
		}
		else {
			_portletInvocationWhitelistActions = Collections.unmodifiableSet(
				_portletInvocationWhitelistActions);
		}

		return _portletInvocationWhitelistActions;
	}

	protected boolean isValidStrutsAction(
		long companyId, String portletId, String strutsAction) {

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletId);

			if (portlet == null) {
				return false;
			}

			String strutsPath = strutsAction.substring(
				1, strutsAction.lastIndexOf(CharPool.SLASH));

			if (strutsPath.equals(portlet.getStrutsPath()) ||
				strutsPath.equals(portlet.getParentStrutsPath())) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	private Set<String> _portletInvocationWhitelist;
	private Set<String> _portletInvocationWhitelistActions;

}