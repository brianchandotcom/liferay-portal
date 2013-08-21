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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.SecurityPortletContainerWrapper;
import com.liferay.util.PwdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Amos Fong
 */
public class SessionAuthToken implements AuthToken {

	@Override
	public void check(HttpServletRequest request) throws PrincipalException {
		checkCSRFToken(
			request, SecurityPortletContainerWrapper.class.getName());
	}

	@Override
	public void checkCSRFToken(HttpServletRequest request, String context)
		throws PrincipalException {

		if (!PropsValues.AUTH_TOKEN_CHECK_ENABLED) {
			return;
		}

		String csrfSharedSecret = ParamUtil.getString(request, "p_auth_secret");

		if (AuthTokenWhitelistUtil.isValidCSRFSharedSecret(csrfSharedSecret)) {
			return;
		}

		long companyId = PortalUtil.getCompanyId(request);

		if (AuthTokenWhitelistUtil.isCSRFContextWhitelisted(
				companyId, context)) {

			return;
		}

		if (context.equals(SecurityPortletContainerWrapper.class.getName())) {
			String ppid = ParamUtil.getString(request, "p_p_id");

			String portletNamespace = PortalUtil.getPortletNamespace(ppid);

			String strutsAction = ParamUtil.getString(
				request, portletNamespace + "struts_action");

			if (AuthTokenWhitelistUtil.isPortletCSRFWhitelisted(
					companyId, ppid, strutsAction)) {

				return;
			}
		}

		String csrfToken = ParamUtil.getString(request, "p_auth");

		String sessionToken = getSessionAuthenticationToken(
			request, _CSRF, false);

		if (!csrfToken.equals(sessionToken)) {
			throw new PrincipalException("Invalid authentication token");
		}
	}

	@Override
	public String getToken(HttpServletRequest request) {
		return getSessionAuthenticationToken(request, _CSRF, true);
	}

	@Override
	public String getToken(
		HttpServletRequest request, long plid, String portletId) {

		return getSessionAuthenticationToken(
			request, PortletPermissionUtil.getPrimaryKey(plid, portletId),
			true);
	}

	@Override
	public boolean isValidPortletInvocationToken(
		HttpServletRequest request, long plid, String portletId,
		String strutsAction, String tokenValue) {

		long companyId = PortalUtil.getCompanyId(request);

		if (AuthTokenWhitelistUtil.isPortletInvocationWhitelisted(
				companyId, portletId, strutsAction)) {

			return true;
		}

		if (Validator.isNotNull(tokenValue)) {
			String key = PortletPermissionUtil.getPrimaryKey(plid, portletId);

			String sessionToken = getSessionAuthenticationToken(
				request, key, false);

			if (Validator.isNotNull(sessionToken) &&
				sessionToken.equals(tokenValue)) {

				return true;
			}
		}

		return false;
	}

	protected String getSessionAuthenticationToken(
		HttpServletRequest request, String key, boolean createToken) {

		HttpSession session = request.getSession();

		String tokenKey = WebKeys.AUTHENTICATION_TOKEN.concat(key);

		String sessionAuthenticationToken = (String)session.getAttribute(
			tokenKey);

		if (createToken && Validator.isNull(sessionAuthenticationToken)) {
			sessionAuthenticationToken = PwdGenerator.getPassword();

			session.setAttribute(tokenKey, sessionAuthenticationToken);
		}

		return sessionAuthenticationToken;
	}

	private static final String _CSRF = "#CSRF";

}