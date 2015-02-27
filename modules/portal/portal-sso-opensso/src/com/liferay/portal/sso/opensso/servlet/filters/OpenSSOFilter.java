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

package com.liferay.portal.sso.opensso.servlet.filters;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.OpenSSO;
import com.liferay.portal.sso.opensso.configuration.OpenSSOConfiguration;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Prashant Dighe
 * @author Hugo Huijser
 */
@Component(
	configurationPid = "com.liferay.portal.sso.opensso.configuration.OpenSSOConfiguration",
	immediate = true,
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SSO Open SSO Filter",
		"url-pattern=/c/portal/login", "url-pattern=/c/portal/logout"
	},
	service = Filter.class
)
public class OpenSSOFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			boolean enabled = PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
				_openSSOConfiguration.enabled());
			String loginUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_LOGIN_URL,
				_openSSOConfiguration.loginURL());
			String logoutUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_LOGOUT_URL,
				_openSSOConfiguration.logoutURL());
			String serviceUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_SERVICE_URL,
				_openSSOConfiguration.serviceURL());

			if (enabled && Validator.isNotNull(loginUrl) &&
				Validator.isNotNull(logoutUrl) &&
				Validator.isNotNull(serviceUrl)) {

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_openSSOConfiguration = Configurable.createConfigurable(
			OpenSSOConfiguration.class, properties);
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		String loginUrl = PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_LOGIN_URL,
			_openSSOConfiguration.loginURL());
		String logoutUrl = PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_LOGOUT_URL,
			_openSSOConfiguration.logoutURL());
		String serviceUrl = PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_SERVICE_URL,
			_openSSOConfiguration.serviceURL());

		String requestURI = GetterUtil.getString(request.getRequestURI());

		if (requestURI.endsWith("/portal/logout")) {
			HttpSession session = request.getSession();

			session.invalidate();

			response.sendRedirect(logoutUrl);

			return;
		}

		boolean authenticated = false;

		try {

			// LEP-5943

			authenticated = _openSSO.isAuthenticated(request, serviceUrl);
		}
		catch (Exception e) {
			_log.error(e, e);

			processFilter(OpenSSOFilter.class, request, response, filterChain);

			return;
		}

		HttpSession session = request.getSession();

		if (authenticated) {

			// LEP-5943

			String newSubjectId = _openSSO.getSubjectId(request, serviceUrl);

			String oldSubjectId = (String)session.getAttribute(_SUBJECT_ID_KEY);

			if (oldSubjectId == null) {
				session.setAttribute(_SUBJECT_ID_KEY, newSubjectId);
			}
			else if (!newSubjectId.equals(oldSubjectId)) {
				session.invalidate();

				session = request.getSession();

				session.setAttribute(_SUBJECT_ID_KEY, newSubjectId);
			}

			processFilter(OpenSSOFilter.class, request, response, filterChain);

			return;
		}
		else if (PortalUtil.getUserId(request) > 0) {
			session.invalidate();
		}

		if (!PropsValues.AUTH_FORWARD_BY_LAST_PATH ||
			!loginUrl.contains("/portal/login")) {

			response.sendRedirect(loginUrl);

			return;
		}

		String currentURL = PortalUtil.getCurrentURL(request);

		String redirect = currentURL;

		if (currentURL.contains("/portal/login")) {
			redirect = ParamUtil.getString(request, "redirect");

			if (Validator.isNull(redirect)) {
				redirect = PortalUtil.getPathMain();
			}
		}

		redirect =
			loginUrl +
				HttpUtil.encodeURL("?redirect=" + HttpUtil.encodeURL(redirect));

		response.sendRedirect(redirect);
	}

	@Reference
	protected void setOpenSSO(OpenSSO openSSO) {
		_openSSO = openSSO;
	}

	private static final String _SUBJECT_ID_KEY = "open.sso.subject.id";

	private static final Log _log = LogFactoryUtil.getLog(OpenSSOFilter.class);

	private OpenSSO _openSSO;
	private OpenSSOConfiguration _openSSOConfiguration;

}