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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.auth.AuthToken;
import com.liferay.portal.kernel.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.SecurityPortletContainerWrapper;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author Amos Fong
 */
public class SessionAuthToken implements AuthToken {

	@Override
	public void addCSRFToken(
		HttpServletRequest httpServletRequest,
		LiferayPortletURL liferayPortletURL) {

		if (!PropsValues.AUTH_TOKEN_CHECK_ENABLED) {
			return;
		}

		String lifecycle = liferayPortletURL.getLifecycle();

		if (!lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			return;
		}

		if (AuthTokenWhitelistUtil.isPortletURLCSRFWhitelisted(
				liferayPortletURL)) {

			return;
		}

		liferayPortletURL.setParameter("p_auth", getToken(httpServletRequest));
	}

	@Override
	public void addPortletInvocationToken(
		HttpServletRequest httpServletRequest,
		LiferayPortletURL liferayPortletURL) {

		if (!PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED) {
			return;
		}

		long companyId = PortalUtil.getCompanyId(httpServletRequest);

		String portletId = liferayPortletURL.getPortletId();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		if (portlet == null) {
			return;
		}

		if (!portlet.isAddDefaultResource()) {
			return;
		}

		if (AuthTokenWhitelistUtil.isPortletURLPortletInvocationWhitelisted(
				liferayPortletURL)) {

			return;
		}

		long plid = liferayPortletURL.getPlid();

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (layoutTypePortlet.hasPortletId(portletId)) {
				return;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage(), e);
			}
		}

		liferayPortletURL.setParameter(
			"p_p_auth", getToken(httpServletRequest, plid, portletId));
	}

	@Override
	public void checkCSRFToken(
			HttpServletRequest httpServletRequest, String origin)
		throws PrincipalException {

		if (!PropsValues.AUTH_TOKEN_CHECK_ENABLED) {
			return;
		}

		String sharedSecret = ParamUtil.getString(
			httpServletRequest, "p_auth_secret");

		if (AuthTokenWhitelistUtil.isValidSharedSecret(sharedSecret)) {
			return;
		}

		long companyId = PortalUtil.getCompanyId(httpServletRequest);

		if (AuthTokenWhitelistUtil.isOriginCSRFWhitelisted(companyId, origin)) {
			return;
		}

		if (origin.equals(SecurityPortletContainerWrapper.class.getName())) {
			String ppid = ParamUtil.getString(httpServletRequest, "p_p_id");

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, ppid);

			if (AuthTokenWhitelistUtil.isPortletCSRFWhitelisted(
					httpServletRequest, portlet)) {

				return;
			}
		}

		String csrfToken = ParamUtil.getString(httpServletRequest, "p_auth");

		if (Validator.isNull(csrfToken)) {
			csrfToken = GetterUtil.getString(
				httpServletRequest.getHeader("X-CSRF-Token"));
		}

		String sessionToken = getSessionAuthenticationToken(
			httpServletRequest, _CSRF, false);

		if (!csrfToken.equals(sessionToken)) {
			throw new PrincipalException.MustHaveValidCSRFToken(
				PortalUtil.getUserId(httpServletRequest), origin);
		}
	}

	@Override
	public String getToken(HttpServletRequest httpServletRequest) {
		return getSessionAuthenticationToken(httpServletRequest, _CSRF, true);
	}

	@Override
	public String getToken(
		HttpServletRequest httpServletRequest, long plid, String portletId) {

		return getSessionAuthenticationToken(
			httpServletRequest,
			PortletPermissionUtil.getPrimaryKey(plid, portletId), true);
	}

	@Override
	public boolean isValidPortletInvocationToken(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {

		if (AuthTokenWhitelistUtil.isPortletInvocationWhitelisted(
				httpServletRequest, portlet)) {

			return true;
		}

		String portletToken = ParamUtil.getString(
			httpServletRequest, "p_p_auth");

		if (Validator.isNull(portletToken)) {
			HttpServletRequest originalHttpServletRequest =
				PortalUtil.getOriginalServletRequest(httpServletRequest);

			portletToken = ParamUtil.getString(
				originalHttpServletRequest, "p_p_auth");
		}

		if (Validator.isNotNull(portletToken)) {
			String key = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portlet.getPortletId());

			String sessionToken = getSessionAuthenticationToken(
				httpServletRequest, key, false);

			if (Validator.isNotNull(sessionToken) &&
				sessionToken.equals(portletToken)) {

				return true;
			}
		}

		return false;
	}

	protected String getSessionAuthenticationToken(
		HttpServletRequest httpServletRequest, String key,
		boolean createToken) {

		String sessionAuthenticationToken = null;

		HttpServletRequest currentHttpServletRequest = httpServletRequest;
		HttpSession session = null;
		String tokenKey = WebKeys.AUTHENTICATION_TOKEN.concat(key);

		while (currentHttpServletRequest instanceof HttpServletRequestWrapper) {
			session = currentHttpServletRequest.getSession();

			sessionAuthenticationToken = (String)session.getAttribute(tokenKey);

			if (Validator.isNotNull(sessionAuthenticationToken)) {
				break;
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)currentHttpServletRequest;

			currentHttpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		if (session == null) {
			session = currentHttpServletRequest.getSession();

			sessionAuthenticationToken = (String)session.getAttribute(tokenKey);
		}

		if (createToken && Validator.isNull(sessionAuthenticationToken)) {
			sessionAuthenticationToken = PwdGenerator.getPassword(
				PropsValues.AUTH_TOKEN_LENGTH);

			session.setAttribute(tokenKey, sessionAuthenticationToken);
		}

		return sessionAuthenticationToken;
	}

	private static final String _CSRF = "#CSRF";

	private static final Log _log = LogFactoryUtil.getLog(
		SessionAuthToken.class);

}