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

package com.liferay.portal.security.sso.cas.servlet.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.cas.constants.CASConstants;
import com.liferay.portal.security.sso.cas.constants.CASWebKeys;
import com.liferay.portal.security.sso.cas.module.configuration.CASConfiguration;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
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
 * This class checks if the HTTP session attribute CAS_FORCE_LOGOUT is set by
 * CASAutoLogin and, if so, redirects the browser to the configured CAS Logout
 * URL.
 * </p>
 *
 * <p>
 * Next, if the session attribute CAS_LOGIN has not already been set and no
 * ticket parameter is received via the HttpServletRequest, the CAS server login
 * URL is constructed based on the configuration of the Login URL, the Server
 * name, and the Service URL and the browser is redirected to this URL. If a
 * ticket parameter was received, it will be validated.
 * </p>
 *
 * <p>
 * Validation includes sending a SAML request containing the ticket to the CAS
 * server, and in return receiving an assertion of user attributes. However,
 * only the principal attribute is used and it is set as the session attribute
 * CAS_LOGIN as mentioned earlier. It is important that the CAS server issues a
 * principal of the same type that the portal instance is configured to use
 * (e.g., screen name vs. email address).
 * </p>
 *
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Tina Tian
 * @author Zsolt Balogh
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.cas.module.configuration.CASConfiguration",
	immediate = true,
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
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
		HttpServletRequest request, HttpServletResponse response) {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			CASConfiguration casConfiguration =
				_configurationFactory.getConfiguration(
					CASConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, CASConstants.SERVICE_NAME));

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
			_configurationFactory.getConfiguration(
				CASConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, CASConstants.SERVICE_NAME));

		String serverName = casConfiguration.serverName();
		String serverUrl = casConfiguration.serverURL();
		String loginUrl = casConfiguration.loginURL();

		Cas20ProxyTicketValidator cas20ProxyTicketValidator =
			new Cas20ProxyTicketValidator(serverUrl);

		Map<String, String> parameters = new HashMap<>();

		parameters.put("serverName", serverName);
		parameters.put("casServerUrlPrefix", serverUrl);
		parameters.put("casServerLoginUrl", loginUrl);
		parameters.put("redirectAfterValidation", "false");

		cas20ProxyTicketValidator.setCustomParameters(parameters);

		_ticketValidators.put(companyId, cas20ProxyTicketValidator);

		return cas20ProxyTicketValidator;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		HttpSession session = request.getSession();

		long companyId = PortalUtil.getCompanyId(request);

		CASConfiguration casConfiguration =
			_configurationFactory.getConfiguration(
				CASConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, CASConstants.SERVICE_NAME));

		String pathInfo = request.getPathInfo();

		Object forceLogout = session.getAttribute(CASWebKeys.CAS_FORCE_LOGOUT);

		if (forceLogout != null) {
			session.removeAttribute(CASWebKeys.CAS_FORCE_LOGOUT);

			String logoutUrl = casConfiguration.logoutURL();

			response.sendRedirect(logoutUrl);

			return;
		}

		if (Validator.isNotNull(pathInfo) &&
			pathInfo.contains("/portal/logout")) {

			session.invalidate();

			String logoutUrl = casConfiguration.logoutURL();

			response.sendRedirect(logoutUrl);

			return;
		}
		else {
			String login = (String)session.getAttribute(CASWebKeys.CAS_LOGIN);

			if (Validator.isNotNull(login)) {
				processFilter(
					CASFilter.class.getName(), request, response, filterChain);

				return;
			}

			String serverName = casConfiguration.serverName();

			String serviceUrl = casConfiguration.serviceURL();

			if (Validator.isNull(serviceUrl)) {
				serviceUrl = CommonUtils.constructServiceUrl(
					request, response, serviceUrl, serverName, "ticket", false);
			}

			String ticket = ParamUtil.getString(request, "ticket");

			if (Validator.isNull(ticket)) {
				String loginUrl = casConfiguration.loginURL();

				loginUrl = HttpUtil.addParameter(
					loginUrl, "service", serviceUrl);

				response.sendRedirect(loginUrl);

				return;
			}

			TicketValidator ticketValidator = getTicketValidator(companyId);

			Assertion assertion = ticketValidator.validate(ticket, serviceUrl);

			if (assertion != null) {
				AttributePrincipal attributePrincipal =
					assertion.getPrincipal();

				login = attributePrincipal.getName();

				session.setAttribute(CASWebKeys.CAS_LOGIN, login);
			}
		}

		processFilter(
			CASFilter.class.getName(), request, response, filterChain);
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(CASFilter.class);

	private static final Map<Long, TicketValidator> _ticketValidators =
		new ConcurrentHashMap<>();

	private volatile ConfigurationFactory _configurationFactory;

}