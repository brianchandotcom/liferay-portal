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

package com.liferay.portal.security.sso.cas.internal.servlet.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.cas.configuration.CASConfiguration;
import com.liferay.portal.security.sso.cas.constants.CASConstants;
import com.liferay.portal.security.sso.cas.internal.constants.CASWebKeys;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Participates in every login and logout that triggers an HTTP request to
 * Liferay Portal.
 *
 * <p>
 * This class checks if the HTTP session attribute <code>CAS_FORCE_LOGOUT</code>
 * is set by CASAutoLogin and, if so, redirects the browser to the configured
 * CAS Logout URL.
 * </p>
 *
 * <p>
 * Next, if the session attribute <code>CAS_LOGIN</code> has not already been
 * set and no ticket parameter is received via the HTTP servlet request, the CAS
 * server login URL is constructed based on the configuration of the Login URL,
 * the Server name, and the Service URL and the browser is redirected to this
 * URL. If a ticket parameter was received, it will be validated.
 * </p>
 *
 * <p>
 * Validation includes sending a SAML request containing the ticket to the CAS
 * server, and in return receiving an assertion of user attributes. However,
 * only the principal attribute is used and it is set as the session attribute
 * <code>CAS_LOGIN</code> as mentioned earlier. It is important that the CAS
 * server issues a principal of the same type that the portal instance is
 * configured to use (e.g., screen name versus email address).
 * </p>
 *
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Tina Tian
 * @author Zsolt Balogh
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.cas.configuration.CASConfiguration",
	immediate = true,
	property = {
		"before-filter=Auto Login Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SSO CAS Filter", "url-pattern=/c/portal/login",
		"url-pattern=/c/portal/logout"
	},
	service = Filter.class
)
public class CASFilter extends BaseFilter {

	public static void reload(long companyId) {
		_ticketValidators.remove(companyId);
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			CASConfiguration casConfiguration =
				_configurationProvider.getConfiguration(
					CASConfiguration.class,
					new CompanyServiceSettingsLocator(
						_portal.getCompanyId(httpServletRequest),
						CASConstants.SERVICE_NAME));

			if (casConfiguration.enabled()) {
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

	protected TicketValidator getTicketValidator(long companyId)
		throws Exception {

		TicketValidator ticketValidator = _ticketValidators.get(companyId);

		if (ticketValidator != null) {
			return ticketValidator;
		}

		CASConfiguration casConfiguration =
			_configurationProvider.getConfiguration(
				CASConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, CASConstants.SERVICE_NAME));

		String serverUrl = casConfiguration.serverURL();

		Cas20ProxyTicketValidator cas20ProxyTicketValidator =
			new Cas20ProxyTicketValidator(serverUrl);

		Map<String, String> parameters = HashMapBuilder.put(
			"casServerLoginUrl", casConfiguration.loginURL()
		).put(
			"casServerUrlPrefix", serverUrl
		).put(
			"redirectAfterValidation", "false"
		).put(
			"serverName", casConfiguration.serverName()
		).build();

		cas20ProxyTicketValidator.setCustomParameters(parameters);

		_ticketValidators.put(companyId, cas20ProxyTicketValidator);

		return cas20ProxyTicketValidator;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		HttpSession session = httpServletRequest.getSession();

		long companyId = _portal.getCompanyId(httpServletRequest);

		CASConfiguration casConfiguration =
			_configurationProvider.getConfiguration(
				CASConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, CASConstants.SERVICE_NAME));

		Object forceLogout = session.getAttribute(CASWebKeys.CAS_FORCE_LOGOUT);

		if (forceLogout != null) {
			session.removeAttribute(CASWebKeys.CAS_FORCE_LOGOUT);

			String logoutUrl = casConfiguration.logoutURL();

			httpServletResponse.sendRedirect(logoutUrl);

			return;
		}

		String pathInfo = httpServletRequest.getPathInfo();

		if (Validator.isNotNull(pathInfo) &&
			pathInfo.contains("/portal/logout")) {

			session.invalidate();

			String logoutUrl = casConfiguration.logoutURL();

			httpServletResponse.sendRedirect(logoutUrl);

			return;
		}

		String login = (String)session.getAttribute(CASWebKeys.CAS_LOGIN);

		if (Validator.isNotNull(login)) {
			processFilter(
				CASFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);

			return;
		}

		String serverName = casConfiguration.serverName();

		String serviceURL = casConfiguration.serviceURL();

		if (Validator.isNull(serviceURL)) {
			serviceURL = CommonUtils.constructServiceUrl(
				httpServletRequest, httpServletResponse, serviceURL, serverName,
				"ticket", false);
		}

		String ticket = ParamUtil.getString(httpServletRequest, "ticket");

		if (Validator.isNull(ticket)) {
			String loginUrl = casConfiguration.loginURL();

			loginUrl = _http.addParameter(loginUrl, "service", serviceURL);

			httpServletResponse.sendRedirect(loginUrl);

			return;
		}

		TicketValidator ticketValidator = getTicketValidator(companyId);

		Assertion assertion = ticketValidator.validate(ticket, serviceURL);

		if (assertion != null) {
			AttributePrincipal attributePrincipal = assertion.getPrincipal();

			login = attributePrincipal.getName();

			session.setAttribute(CASWebKeys.CAS_LOGIN, login);
		}

		processFilter(
			CASFilter.class.getName(), httpServletRequest, httpServletResponse,
			filterChain);
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(CASFilter.class);

	private static final Map<Long, TicketValidator> _ticketValidators =
		new ConcurrentHashMap<>();

	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}