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

package com.liferay.portal.servlet.filters.sso.cas;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;

/**
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Tina Tian
 * @author Zsolt Balogh
 */
public class CASFilter extends BasePortalFilter {

	public static void reload(long companyId) {
		_ticketValidators.remove(companyId);
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.CAS_AUTH_ENABLED,
					PropsValues.CAS_AUTH_ENABLED)) {

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

		String serverName = PrefsPropsUtil.getString(
			companyId, PropsKeys.CAS_SERVER_NAME, PropsValues.CAS_SERVER_NAME);
		String serverUrl = PrefsPropsUtil.getString(
			companyId, PropsKeys.CAS_SERVER_URL, PropsValues.CAS_SERVER_URL);
		String loginUrl = PrefsPropsUtil.getString(
			companyId, PropsKeys.CAS_LOGIN_URL, PropsValues.CAS_LOGIN_URL);

		Cas20ProxyTicketValidator cas20ProxyTicketValidator =
			new Cas20ProxyTicketValidator(serverUrl);

		Map<String, String> parameters = new HashMap<String, String>();

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

		String pathInfo = request.getPathInfo();

		Object forceLogout = session.getAttribute(WebKeys.CAS_FORCE_LOGOUT);

		if (forceLogout != null) {
			session.removeAttribute(WebKeys.CAS_FORCE_LOGOUT);

			String logoutUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_LOGOUT_URL,
				PropsValues.CAS_LOGOUT_URL);

			response.sendRedirect(logoutUrl);

			return;
		}

		if (Validator.isNotNull(pathInfo) &&
			pathInfo.contains("/portal/logout")) {

			session.invalidate();

			String logoutUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_LOGOUT_URL,
				PropsValues.CAS_LOGOUT_URL);

			response.sendRedirect(logoutUrl);

			return;
		}
		else {
			String login = (String)session.getAttribute(WebKeys.CAS_LOGIN);

			if (Validator.isNotNull(login)) {
				processFilter(CASFilter.class, request, response, filterChain);

				return;
			}

			String serverName = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVER_NAME,
				PropsValues.CAS_SERVER_NAME);

			String serviceUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_SERVICE_URL,
				PropsValues.CAS_SERVICE_URL);

			if (Validator.isNull(serviceUrl)) {
				serviceUrl = CommonUtils.constructServiceUrl(
					request, response, serviceUrl, serverName, "ticket", false);
			}

			String ticket = ParamUtil.getString(request, "ticket");

			if (Validator.isNull(ticket)) {
				String loginUrl = PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_LOGIN_URL,
					PropsValues.CAS_LOGIN_URL);

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

				session.setAttribute(WebKeys.CAS_LOGIN, login);
			}
		}

		processFilter(CASFilter.class, request, response, filterChain);
	}

	private static final Log _log = LogFactoryUtil.getLog(CASFilter.class);

	private static final Map<Long, TicketValidator> _ticketValidators =
		new ConcurrentHashMap<Long, TicketValidator>();

}