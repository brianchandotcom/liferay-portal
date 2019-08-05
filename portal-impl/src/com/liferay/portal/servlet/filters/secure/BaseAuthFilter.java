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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsUtil;

import java.util.Arrays;
import java.util.Collections;
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
public abstract class BaseAuthFilter extends BasePortalFilter {

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

		if (hostsAllowed.length == 0) {
			_hostsAllowed = Collections.emptySet();
		}
		else {
			_hostsAllowed = new HashSet<>(Arrays.asList(hostsAllowed));
		}

		_usePermissionChecker = GetterUtil.getBoolean(
			filterConfig.getInitParameter("use_permission_checker"));
	}

	protected HttpServletRequest basicAuth(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		HttpSession session = httpServletRequest.getSession();

		User user = (User)session.getAttribute(WebKeys.USER);

		if (user == null) {
			long userId = 0;

			try {
				userId = HttpAuthManagerUtil.getBasicUserId(httpServletRequest);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			if (userId > 0) {
				httpServletRequest = setCredentials(
					httpServletRequest, session,
					UserLocalServiceUtil.getUser(userId),
					HttpServletRequest.BASIC_AUTH);
			}
			else {
				HttpAuthorizationHeader httpAuthorizationHeader =
					new HttpAuthorizationHeader(
						HttpAuthorizationHeader.SCHEME_BASIC);

				HttpAuthManagerUtil.generateChallenge(
					httpServletRequest, httpServletResponse,
					httpAuthorizationHeader);

				return null;
			}
		}
		else {
			httpServletRequest = new ProtectedServletRequest(
				httpServletRequest, String.valueOf(user.getUserId()),
				HttpServletRequest.BASIC_AUTH);

			PrincipalThreadLocal.setPassword(
				PortalUtil.getUserPassword(httpServletRequest));
		}

		return httpServletRequest;
	}

	protected HttpServletRequest digestAuth(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		HttpSession session = httpServletRequest.getSession();

		User user = (User)session.getAttribute(WebKeys.USER);

		if (user == null) {
			long userId = 0;

			try {
				userId = HttpAuthManagerUtil.getDigestUserId(
					httpServletRequest);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			if (userId > 0) {
				httpServletRequest = setCredentials(
					httpServletRequest, session,
					UserLocalServiceUtil.getUser(userId),
					HttpServletRequest.DIGEST_AUTH);
			}
			else {
				HttpAuthorizationHeader httpAuthorizationHeader =
					new HttpAuthorizationHeader(
						HttpAuthorizationHeader.SCHEME_DIGEST);

				HttpAuthManagerUtil.generateChallenge(
					httpServletRequest, httpServletResponse,
					httpAuthorizationHeader);

				return null;
			}
		}
		else {
			httpServletRequest = new ProtectedServletRequest(
				httpServletRequest, String.valueOf(user.getUserId()),
				HttpServletRequest.DIGEST_AUTH);

			PrincipalThreadLocal.setPassword(
				PortalUtil.getUserPassword(httpServletRequest));
		}

		return httpServletRequest;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected void initThreadLocals(HttpServletRequest httpServletRequest)
		throws Exception {

		HttpSession session = httpServletRequest.getSession();

		User user = (User)session.getAttribute(WebKeys.USER);

		initThreadLocals(user);

		PrincipalThreadLocal.setPassword(
			PortalUtil.getUserPassword(httpServletRequest));
	}

	protected void initThreadLocals(User user) throws Exception {
		CompanyThreadLocal.setCompanyId(user.getCompanyId());

		long userId = user.getUserId();

		PrincipalThreadLocal.setName(userId);

		if (!_usePermissionChecker) {
			return;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker != null) &&
			(permissionChecker.getUserId() == userId)) {

			return;
		}

		permissionChecker = PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		if (AccessControlUtil.isAccessAllowed(
				httpServletRequest, _hostsAllowed)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Access allowed for " + httpServletRequest.getRemoteAddr());
			}
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Access denied for " + httpServletRequest.getRemoteAddr());
			}

			httpServletResponse.sendError(
				HttpServletResponse.SC_FORBIDDEN,
				"Access denied for " + httpServletRequest.getRemoteAddr());

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

		if (_httpsRequired && !PortalUtil.isSecure(httpServletRequest)) {
			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(
					httpServletRequest);

				_log.debug("Securing " + completeURL);
			}

			StringBundler sb = new StringBundler(5);

			sb.append(PortalUtil.getPortalURL(httpServletRequest, true));
			sb.append(PortalUtil.getPathContext(httpServletRequest));
			sb.append(httpServletRequest.getRequestURI());

			if (Validator.isNotNull(httpServletRequest.getQueryString())) {
				sb.append(StringPool.QUESTION);
				sb.append(httpServletRequest.getQueryString());
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Redirect to " + sb.toString());
			}

			httpServletResponse.sendRedirect(sb.toString());
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not securing " +
						HttpUtil.getCompleteURL(httpServletRequest));
			}

			User user = null;

			try {
				user = PortalUtil.initUser(httpServletRequest);
			}
			catch (NoSuchUserException nsue) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsue, nsue);
				}

				httpServletResponse.sendRedirect(
					HttpUtil.getCompleteURL(httpServletRequest));

				return;
			}

			initThreadLocals(user);

			if (!user.isDefaultUser()) {
				String authType = ParamUtil.getString(
					httpServletRequest, "authType");

				if (authType == null) {
					Company company = PortalUtil.getCompany(httpServletRequest);

					authType = company.getAuthType();
				}

				httpServletRequest = setCredentials(
					httpServletRequest, httpServletRequest.getSession(), user,
					authType);
			}
			else {
				if (_digestAuthEnabled) {
					httpServletRequest = digestAuth(
						httpServletRequest, httpServletResponse);
				}
				else if (_basicAuthEnabled) {
					httpServletRequest = basicAuth(
						httpServletRequest, httpServletResponse);
				}
			}

			if (httpServletRequest != null) {
				Class<?> clazz = getClass();

				processFilter(
					clazz.getName(), httpServletRequest, httpServletResponse,
					filterChain);
			}
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #setCredentials(HttpServletRequest, HttpSession, User,
	 *             String)}
	 */
	@Deprecated
	protected HttpServletRequest setCredentials(
			HttpServletRequest httpServletRequest, HttpSession session,
			long userId, String authType)
		throws Exception {

		return setCredentials(
			httpServletRequest, session, UserLocalServiceUtil.getUser(userId),
			authType);
	}

	protected HttpServletRequest setCredentials(
			HttpServletRequest httpServletRequest, HttpSession session,
			User user, String authType)
		throws Exception {

		httpServletRequest = new ProtectedServletRequest(
			httpServletRequest, String.valueOf(user.getUserId()), authType);

		session.setAttribute(WebKeys.USER, user);

		PrincipalThreadLocal.setPassword(
			PortalUtil.getUserPassword(httpServletRequest));

		return httpServletRequest;
	}

	protected void setUsePermissionChecker(boolean usePermissionChecker) {
		_usePermissionChecker = usePermissionChecker;
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseAuthFilter.class);

	private boolean _basicAuthEnabled;
	private boolean _digestAuthEnabled;
	private Set<String> _hostsAllowed;
	private boolean _httpsRequired;
	private boolean _usePermissionChecker;

}