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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.auth.AuthToken;
import com.liferay.portal.kernel.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortalUtil;
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
		HttpServletRequest request, LiferayPortletURL liferayPortletURL) {

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

		liferayPortletURL.setParameter("p_auth", getToken(request));
	}

	@Override
	public void addPortletInvocationToken(
		HttpServletRequest request, LiferayPortletURL liferayPortletURL) {

		if (!PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED) {
			return;
		}

		long companyId = PortalUtil.getCompanyId(request);

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
			"p_p_auth", getToken(request, plid, portletId));
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void check(HttpServletRequest request) throws PrincipalException {
		checkCSRFToken(
			request, SecurityPortletContainerWrapper.class.getName());
	}

	@Override
	public void checkCSRFToken(HttpServletRequest request, String origin)
		throws PrincipalException {

		if (!PropsValues.AUTH_TOKEN_CHECK_ENABLED) {
			return;
		}

		String sharedSecret = ParamUtil.getString(request, "p_auth_secret");

		if (AuthTokenWhitelistUtil.isValidSharedSecret(sharedSecret)) {
			return;
		}

		long companyId = PortalUtil.getCompanyId(request);

		if (AuthTokenWhitelistUtil.isOriginCSRFWhitelisted(companyId, origin)) {
			return;
		}

		if (origin.equals(SecurityPortletContainerWrapper.class.getName())) {
			String ppid = ParamUtil.getString(request, "p_p_id");

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, ppid);

			if (AuthTokenWhitelistUtil.isPortletCSRFWhitelisted(
					request, portlet)) {

				return;
			}
		}

		String csrfToken = ParamUtil.getString(request, "p_auth");

		if (Validator.isNull(csrfToken)) {
			csrfToken = GetterUtil.getString(request.getHeader("X-CSRF-Token"));
		}

		String sessionToken = getSessionAuthenticationToken(
			request, _CSRF, false);

		if (!csrfToken.equals(sessionToken)) {
			throw new PrincipalException.MustBeAuthenticated(
				PortalUtil.getUserId(request));
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
		HttpServletRequest request, Layout layout, Portlet portlet) {

		if (AuthTokenWhitelistUtil.isPortletInvocationWhitelisted(
				request, portlet)) {

			return true;
		}

		long plid = layout.getPlid();

		String portletId = portlet.getPortletId();

		String portletToken = ParamUtil.getString(request, "p_p_auth");

		if (Validator.isNull(portletToken)) {
			HttpServletRequest originalRequest =
				PortalUtil.getOriginalServletRequest(request);

			portletToken = ParamUtil.getString(originalRequest, "p_p_auth");
		}

		if (Validator.isNotNull(portletToken)) {
			String key = PortletPermissionUtil.getPrimaryKey(plid, portletId);

			String sessionToken = getSessionAuthenticationToken(
				request, key, false);

			if (Validator.isNotNull(sessionToken) &&
				sessionToken.equals(portletToken)) {

				return true;
			}
		}

		return false;
	}

	@Deprecated
	@Override
	public boolean isValidPortletInvocationToken(
		HttpServletRequest request, long plid, String portletId,
		String strutsAction, String tokenValue) {

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);
			Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

			return isValidPortletInvocationToken(request, layout, portlet);
		}
		catch (PortalException e) {
			ReflectionUtil.throwException(e);
		}

		return false;
	}

	protected String getSessionAuthenticationToken(
		HttpServletRequest request, String key, boolean createToken) {

		String sessionAuthenticationToken = null;

		HttpServletRequest currentRequest = request;
		HttpSession session = null;
		String tokenKey = WebKeys.AUTHENTICATION_TOKEN.concat(key);

		while (currentRequest instanceof HttpServletRequestWrapper) {
			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)currentRequest;

			session = currentRequest.getSession();

			sessionAuthenticationToken = (String)session.getAttribute(tokenKey);

			if (Validator.isNotNull(sessionAuthenticationToken)) {
				break;
			}

			currentRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		if (session == null) {
			session = currentRequest.getSession();

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