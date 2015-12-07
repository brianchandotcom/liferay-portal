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

package com.liferay.portal.servlet.filters.secure;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Alexander Chow
 */
public class SecureFilter extends BasePortalFilter {

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_basicAuthEnabled = GetterUtil.getBoolean(
			filterConfig.getInitParameter("basic_auth"));
		_digestAuthEnabled = GetterUtil.getBoolean(
			filterConfig.getInitParameter("digest_auth"));

		String propertyPrefix = filterConfig.getInitParameter(
			"portal_property_prefix");

		String[] hostsAllowed = null;

		if (Validator.isNull(propertyPrefix)) {
			hostsAllowed = StringUtil.split(
				filterConfig.getInitParameter("hosts.allowed"));
			_httpsRequired = GetterUtil.getBoolean(
				filterConfig.getInitParameter("https.required"));
		}
		else {
			hostsAllowed = PropsUtil.getArray(propertyPrefix + "hosts.allowed");
			_httpsRequired = GetterUtil.getBoolean(
				PropsUtil.get(propertyPrefix + "https.required"));
		}

		for (String hostAllowed : hostsAllowed) {
			_hostsAllowed.add(hostAllowed);
		}

		_usePermissionChecker = GetterUtil.getBoolean(
			filterConfig.getInitParameter("use_permission_checker"));
	}

	protected HttpServletRequest basicAuth(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		long userId = GetterUtil.getLong(
			(String)session.getAttribute(_AUTHENTICATED_USER));

		if (userId > 0) {
			request = new ProtectedServletRequest(
				request, String.valueOf(userId), HttpServletRequest.BASIC_AUTH);

			initThreadLocals(request);
		}
		else {
			try {
				userId = HttpAuthManagerUtil.getBasicUserId(request);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			if (userId > 0) {
				request = setCredentials(
					request, session, userId, HttpServletRequest.BASIC_AUTH);
			}
			else {
				HttpAuthorizationHeader httpAuthorizationHeader =
					new HttpAuthorizationHeader(
						HttpAuthorizationHeader.SCHEME_BASIC);

				HttpAuthManagerUtil.generateChallenge(
					request, response, httpAuthorizationHeader);

				return null;
			}
		}

		return request;
	}

	protected HttpServletRequest digestAuth(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		long userId = GetterUtil.getLong(
			(String)session.getAttribute(_AUTHENTICATED_USER));

		if (userId > 0) {
			request = new ProtectedServletRequest(
				request, String.valueOf(userId),
				HttpServletRequest.DIGEST_AUTH);

			initThreadLocals(request);
		}
		else {
			try {
				userId = HttpAuthManagerUtil.getDigestUserId(request);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			if (userId > 0) {
				request = setCredentials(
					request, session, userId, HttpServletRequest.DIGEST_AUTH);
			}
			else {
				HttpAuthorizationHeader httpAuthorizationHeader =
					new HttpAuthorizationHeader(
						HttpAuthorizationHeader.SCHEME_DIGEST);

				HttpAuthManagerUtil.generateChallenge(
					request, response, httpAuthorizationHeader);

				return null;
			}
		}

		return request;
	}

	protected void initThreadLocals(HttpServletRequest request)
		throws Exception {

		HttpSession session = request.getSession();

		User user = (User)session.getAttribute(WebKeys.USER);

		initThreadLocals(user);

		PrincipalThreadLocal.setPassword(PortalUtil.getUserPassword(request));
	}

	protected void initThreadLocals(User user) throws Exception {
		CompanyThreadLocal.setCompanyId(user.getCompanyId());

		PrincipalThreadLocal.setName(user.getUserId());

		if (!_usePermissionChecker) {
			return;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			return;
		}

		permissionChecker = PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		String remoteAddr = request.getRemoteAddr();

		if (AccessControlUtil.isAccessAllowed(request, _hostsAllowed)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Access allowed for " + remoteAddr);
			}
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Access denied for " + remoteAddr);
			}

			response.sendError(
				HttpServletResponse.SC_FORBIDDEN,
				"Access denied for " + remoteAddr);

			return;
		}

		if (_log.isDebugEnabled()) {
			if (_httpsRequired) {
				_log.debug("https is required");
			}
			else {
				_log.debug("https is not required");
			}
		}

		if (_httpsRequired && !request.isSecure()) {
			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(request);

				_log.debug("Securing " + completeURL);
			}

			StringBundler redirectURL = new StringBundler(5);

			redirectURL.append(Http.HTTPS_WITH_SLASH);
			redirectURL.append(request.getServerName());
			redirectURL.append(request.getServletPath());

			String queryString = request.getQueryString();

			if (Validator.isNotNull(queryString)) {
				redirectURL.append(StringPool.QUESTION);
				redirectURL.append(request.getQueryString());
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Redirect to " + redirectURL);
			}

			response.sendRedirect(redirectURL.toString());
		}
		else {
			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(request);

				_log.debug("Not securing " + completeURL);
			}

			User user = PortalUtil.getUser(request);

			if (user == null) {
				user = PortalUtil.initUser(request);
			}

			initThreadLocals(user);

			if (!user.isDefaultUser()) {
				request = setCredentials(
					request, request.getSession(), user.getUserId(), null);
			}
			else {
				if (_digestAuthEnabled) {
					request = digestAuth(request, response);
				}
				else if (_basicAuthEnabled) {
					request = basicAuth(request, response);
				}
			}

			if (request != null) {
				Class<?> clazz = getClass();

				processFilter(clazz.getName(), request, response, filterChain);
			}
		}
	}

	protected HttpServletRequest setCredentials(
			HttpServletRequest request, HttpSession session, long userId,
			String authType)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(userId);

		String userIdString = String.valueOf(userId);

		request = new ProtectedServletRequest(request, userIdString, authType);

		session.setAttribute(WebKeys.USER, user);
		session.setAttribute(_AUTHENTICATED_USER, userIdString);

		initThreadLocals(request);

		return request;
	}

	protected void setUsePermissionChecker(boolean usePermissionChecker) {
		_usePermissionChecker = usePermissionChecker;
	}

	private static final String _AUTHENTICATED_USER =
		SecureFilter.class + "_AUTHENTICATED_USER";

	private static final Log _log = LogFactoryUtil.getLog(SecureFilter.class);

	private boolean _basicAuthEnabled;
	private boolean _digestAuthEnabled;
	private final Set<String> _hostsAllowed = new HashSet<>();
	private boolean _httpsRequired;
	private boolean _usePermissionChecker;

}