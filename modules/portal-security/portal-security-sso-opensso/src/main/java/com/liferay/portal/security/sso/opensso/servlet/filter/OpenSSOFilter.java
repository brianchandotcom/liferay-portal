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

package com.liferay.portal.security.sso.opensso.servlet.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.security.sso.OpenSSO;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConstants;
import com.liferay.portal.security.sso.opensso.module.configuration.OpenSSOConfiguration;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Prashant Dighe
 * @author Hugo Huijser
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.opensso.module.configuration.OpenSSOConfiguration",
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

			OpenSSOConfiguration openSSOConfiguration = getOpenSSOConfiguration(
				companyId);

			if (openSSOConfiguration.enabled() &&
				Validator.isNotNull(openSSOConfiguration.loginURL()) &&
				Validator.isNotNull(openSSOConfiguration.logoutURL()) &&
				Validator.isNotNull(openSSOConfiguration.serviceURL())) {

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	protected OpenSSOConfiguration getOpenSSOConfiguration(long companyId)
		throws Exception {

		return _configurationFactory.getConfiguration(
			OpenSSOConfiguration.class,
			new CompanyServiceSettingsLocator(
				companyId, OpenSSOConstants.SERVICE_NAME));
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		OpenSSOConfiguration openSSOConfiguration = getOpenSSOConfiguration(
			companyId);

		String requestURI = GetterUtil.getString(request.getRequestURI());

		if (requestURI.endsWith("/portal/logout")) {
			HttpSession session = request.getSession();

			session.invalidate();

			response.sendRedirect(openSSOConfiguration.logoutURL());

			return;
		}

		boolean authenticated = false;

		try {

			// LEP-5943

			authenticated = _openSSO.isAuthenticated(
				request, openSSOConfiguration.serviceURL());
		}
		catch (Exception e) {
			_log.error(e, e);

			processFilter(
				OpenSSOFilter.class.getName(), request, response, filterChain);

			return;
		}

		HttpSession session = request.getSession();

		if (authenticated) {

			// LEP-5943

			String newSubjectId = _openSSO.getSubjectId(
				request, openSSOConfiguration.serviceURL());

			String oldSubjectId = (String)session.getAttribute(_SUBJECT_ID_KEY);

			if (oldSubjectId == null) {
				session.setAttribute(_SUBJECT_ID_KEY, newSubjectId);
			}
			else if (!newSubjectId.equals(oldSubjectId)) {
				session.invalidate();

				session = request.getSession();

				session.setAttribute(_SUBJECT_ID_KEY, newSubjectId);
			}

			processFilter(
				OpenSSOFilter.class.getName(), request, response, filterChain);

			return;
		}
		else if (PortalUtil.getUserId(request) > 0) {
			session.invalidate();
		}

		if (!PropsValues.AUTH_FORWARD_BY_LAST_PATH ||
			!openSSOConfiguration.loginURL().contains("/portal/login")) {

			response.sendRedirect(openSSOConfiguration.loginURL());

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
			openSSOConfiguration.loginURL() +
				HttpUtil.encodeURL("?redirect=" + HttpUtil.encodeURL(redirect));

		response.sendRedirect(redirect);
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	@Reference(unbind = "-")
	protected void setOpenSSO(OpenSSO openSSO) {
		_openSSO = openSSO;
	}

	private static final String _SUBJECT_ID_KEY = "open.sso.subject.id";

	private static final Log _log = LogFactoryUtil.getLog(OpenSSOFilter.class);

	private volatile ConfigurationFactory _configurationFactory;
	private volatile OpenSSO _openSSO;

}