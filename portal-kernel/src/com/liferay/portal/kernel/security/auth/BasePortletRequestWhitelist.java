/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasePortletRequestWhitelist.class);

	private Set<String> _portletInvocationWhitelist;
	private Set<String> _portletInvocationWhitelistActions;

}