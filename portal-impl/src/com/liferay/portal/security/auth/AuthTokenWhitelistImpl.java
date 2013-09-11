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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;

import java.util.Collections;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class AuthTokenWhitelistImpl implements AuthTokenWhitelist {

	public AuthTokenWhitelistImpl() {
		resetOriginCSRFWhitelist();
		resetPortletCSRFWhitelist();
		resetPortletCSRFWhitelistActions();
		resetPortletInvocationWhitelist();
		resetPortletInvocationWhitelistActions();
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
	public boolean isCSRFOriginWhitelisted(long companyId, String origin) {
		Set<String> whitelist = getOriginCSRFWhitelist();

		for (String whitelistedCSRFOrigins : whitelist) {
			if (origin.startsWith(whitelistedCSRFOrigins)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction) {

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		Set<String> whitelist = getPortletCSRFWhitelist();

		if (whitelist.contains(rootPortletId)) {
			return true;
		}

		if (Validator.isNotNull(strutsAction)) {
			Set<String> whitelistActions = getPortletCSRFWhitelistActions();

			if (whitelistActions.contains(strutsAction) &&
				isValidStrutsAction(companyId, rootPortletId, strutsAction)) {

				return true;
			}
		}

		return false;
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

			if (whitelistActions.contains(strutsAction) &&
				isValidStrutsAction(companyId, portletId, strutsAction)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isValidCSRFSharedSecret(String sharedSecret) {
		if (Validator.isNotNull(sharedSecret)) {
			String authTokenSharedSecret = PropsValues.AUTH_TOKEN_SHARED_SECRET;

			if (Validator.isNotNull(authTokenSharedSecret)) {
				String expectedSharedSecretValue = Encryptor.digest(
					authTokenSharedSecret);

				if (expectedSharedSecretValue.equals(sharedSecret)) {
					return true;
				}
			}
		}

		return false;
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

	private Set<String> _originCSRFWhitelist;
	private Set<String> _portletCSRFWhitelist;
	private Set<String> _portletCSRFWhitelistActions;
	private Set<String> _portletInvocationWhitelist;
	private Set<String> _portletInvocationWhitelistActions;

}