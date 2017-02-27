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

package com.liferay.portal.security.auth.session;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.AuthenticatedUserUUIDStoreUtil;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class AuthenticatedSessionManagerImpl
	implements AuthenticatedSessionManager {

	@Override
	public long getAuthenticatedUserId(
			HttpServletRequest request, String login, String password,
			String authType)
		throws PortalException {

		User user = _getAuthenticatedUser(request, login, password, authType);

		return user.getUserId();
	}

	@Override
	public void login(
			HttpServletRequest request, HttpServletResponse response,
			String login, String password, boolean rememberMe, String authType)
		throws Exception {

		request = PortalUtil.getOriginalServletRequest(request);

		CookieKeys.validateSupportCookie(request);

		HttpSession session = request.getSession();

		Company company = PortalUtil.getCompany(request);

		User user = _getAuthenticatedUser(request, login, password, authType);

		if (!PropsValues.AUTH_SIMULTANEOUS_LOGINS) {
			signOutSimultaneousLogins(user.getUserId());
		}

		if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			session = renewSession(request, session);
		}

		// Set cookies

		String domain = CookieKeys.getDomain(request);

		if (Validator.isNull(domain)) {
			domain = null;
		}

		String userIdString = String.valueOf(user.getUserId());

		session.setAttribute("j_username", userIdString);

		if (PropsValues.PORTAL_JAAS_PLAIN_PASSWORD) {
			session.setAttribute("j_password", password);
		}
		else {
			session.setAttribute("j_password", user.getPassword());
		}

		session.setAttribute("j_remoteuser", userIdString);

		if (PropsValues.SESSION_STORE_PASSWORD) {
			session.setAttribute(WebKeys.USER_PASSWORD, password);
		}

		Cookie companyIdCookie = new Cookie(
			CookieKeys.COMPANY_ID, String.valueOf(company.getCompanyId()));

		if (domain != null) {
			companyIdCookie.setDomain(domain);
		}

		companyIdCookie.setPath(StringPool.SLASH);

		Cookie idCookie = new Cookie(
			CookieKeys.ID,
			Encryptor.encrypt(company.getKeyObj(), userIdString));

		if (domain != null) {
			idCookie.setDomain(domain);
		}

		idCookie.setPath(StringPool.SLASH);

		int loginMaxAge = PropsValues.COMPANY_SECURITY_AUTO_LOGIN_MAX_AGE;

		if (PropsValues.SESSION_DISABLED) {
			rememberMe = true;
		}

		if (rememberMe) {
			companyIdCookie.setMaxAge(loginMaxAge);
			idCookie.setMaxAge(loginMaxAge);
		}
		else {

			// This was explicitly changed from 0 to -1 so that the cookie lasts
			// as long as the browser. This allows an external servlet wrapped
			// in AutoLoginFilter to work throughout the client connection. The
			// cookies ARE removed on an actual logout, so there is no security
			// issue. See LEP-4678 and LEP-5177.

			companyIdCookie.setMaxAge(-1);
			idCookie.setMaxAge(-1);
		}

		boolean secure = request.isSecure();

		if (secure && !PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!StringUtil.equalsIgnoreCase(
				Http.HTTPS, PropsValues.WEB_SERVER_PROTOCOL)) {

			Boolean httpsInitial = (Boolean)session.getAttribute(
				WebKeys.HTTPS_INITIAL);

			if ((httpsInitial == null) || !httpsInitial.booleanValue()) {
				secure = false;
			}
		}

		CookieKeys.addCookie(request, response, companyIdCookie, secure);
		CookieKeys.addCookie(request, response, idCookie, secure);

		if (rememberMe) {
			Cookie loginCookie = new Cookie(CookieKeys.LOGIN, login);

			if (domain != null) {
				loginCookie.setDomain(domain);
			}

			loginCookie.setMaxAge(loginMaxAge);
			loginCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, loginCookie, secure);

			Cookie passwordCookie = new Cookie(
				CookieKeys.PASSWORD,
				Encryptor.encrypt(company.getKeyObj(), password));

			if (domain != null) {
				passwordCookie.setDomain(domain);
			}

			passwordCookie.setMaxAge(loginMaxAge);
			passwordCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, passwordCookie, secure);

			Cookie rememberMeCookie = new Cookie(
				CookieKeys.REMEMBER_ME, Boolean.TRUE.toString());

			if (domain != null) {
				rememberMeCookie.setDomain(domain);
			}

			rememberMeCookie.setMaxAge(loginMaxAge);
			rememberMeCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, rememberMeCookie, secure);

			Cookie screenNameCookie = new Cookie(
				CookieKeys.SCREEN_NAME,
				Encryptor.encrypt(company.getKeyObj(), user.getScreenName()));

			if (domain != null) {
				screenNameCookie.setDomain(domain);
			}

			screenNameCookie.setMaxAge(loginMaxAge);
			screenNameCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, screenNameCookie, secure);
		}

		if (PropsValues.AUTH_USER_UUID_STORE_ENABLED) {
			String userUUID = userIdString.concat(StringPool.PERIOD).concat(
				String.valueOf(System.nanoTime()));

			Cookie userUUIDCookie = new Cookie(
				CookieKeys.USER_UUID,
				Encryptor.encrypt(company.getKeyObj(), userUUID));

			userUUIDCookie.setPath(StringPool.SLASH);

			session.setAttribute(WebKeys.USER_UUID, userUUID);

			if (rememberMe) {
				userUUIDCookie.setMaxAge(loginMaxAge);
			}
			else {
				userUUIDCookie.setMaxAge(-1);
			}

			CookieKeys.addCookie(request, response, userUUIDCookie, secure);

			AuthenticatedUserUUIDStoreUtil.register(userUUID);
		}
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		EventsProcessorUtil.process(
			PropsKeys.LOGOUT_EVENTS_PRE, PropsValues.LOGOUT_EVENTS_PRE, request,
			response);

		String domain = CookieKeys.getDomain(request);

		if (Validator.isNull(domain)) {
			domain = null;
		}

		deleteCookie(request, response, CookieKeys.COMPANY_ID, domain);
		deleteCookie(request, response, CookieKeys.GUEST_LANGUAGE_ID, domain);
		deleteCookie(request, response, CookieKeys.ID, domain);
		deleteCookie(request, response, CookieKeys.PASSWORD, domain);

		boolean rememberMe = GetterUtil.getBoolean(
			CookieKeys.getCookie(request, CookieKeys.REMEMBER_ME));

		if (!rememberMe) {
			deleteCookie(request, response, CookieKeys.LOGIN, domain);
		}

		deleteCookie(request, response, CookieKeys.REMEMBER_ME, domain);

		try {
			session.invalidate();
		}
		catch (Exception e) {
		}

		EventsProcessorUtil.process(
			PropsKeys.LOGOUT_EVENTS_POST, PropsValues.LOGOUT_EVENTS_POST,
			request, response);
	}

	@Override
	public HttpSession renewSession(
			HttpServletRequest request, HttpSession session)
		throws Exception {

		// Invalidate the previous session to prevent session fixation attacks

		String[] protectedAttributeNames =
			PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES;

		Map<String, Object> protectedAttributes = new HashMap<>();

		for (String protectedAttributeName : protectedAttributeNames) {
			Object protectedAttributeValue = session.getAttribute(
				protectedAttributeName);

			if (protectedAttributeValue == null) {
				continue;
			}

			protectedAttributes.put(
				protectedAttributeName, protectedAttributeValue);
		}

		session.invalidate();

		session = request.getSession(true);

		for (String protectedAttributeName : protectedAttributeNames) {
			Object protectedAttributeValue = protectedAttributes.get(
				protectedAttributeName);

			if (protectedAttributeValue == null) {
				continue;
			}

			session.setAttribute(
				protectedAttributeName, protectedAttributeValue);
		}

		return session;
	}

	@Override
	public void signOutSimultaneousLogins(long userId) throws Exception {
		long companyId = CompanyLocalServiceUtil.getCompanyIdByUserId(userId);

		Map<String, UserTracker> sessionUsers = LiveUsers.getSessionUsers(
			companyId);

		List<UserTracker> userTrackers = new ArrayList<>(sessionUsers.values());

		for (UserTracker userTracker : userTrackers) {
			if (userId != userTracker.getUserId()) {
				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			ClusterNode clusterNode = ClusterExecutorUtil.getLocalClusterNode();

			if (clusterNode != null) {
				jsonObject.put("clusterNodeId", clusterNode.getClusterNodeId());
			}

			jsonObject.put("command", "signOut");
			jsonObject.put("companyId", companyId);
			jsonObject.put("sessionId", userTracker.getSessionId());
			jsonObject.put("userId", userId);

			MessageBusUtil.sendMessage(
				DestinationNames.LIVE_USERS, jsonObject.toString());
		}
	}

	protected void deleteCookie(
		HttpServletRequest request, HttpServletResponse response,
		String cookieName, String domain) {

		Cookie cookie = new Cookie(cookieName, StringPool.BLANK);

		if (domain != null) {
			cookie.setDomain(domain);
		}

		cookie.setMaxAge(0);
		cookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(request, response, cookie);
	}

	private User _getAuthenticatedUser(
			HttpServletRequest request, String login, String password,
			String authType)
		throws PortalException {

		long userId = GetterUtil.getLong(login);

		Company company = PortalUtil.getCompany(request);

		String requestURI = request.getRequestURI();

		String contextPath = PortalUtil.getPathContext();

		if (requestURI.startsWith(contextPath.concat("/api/liferay"))) {
			throw new AuthException();
		}
		else {
			Map<String, String[]> headerMap = new HashMap<>();

			Enumeration<String> enu1 = request.getHeaderNames();

			while (enu1.hasMoreElements()) {
				String name = enu1.nextElement();

				Enumeration<String> enu2 = request.getHeaders(name);

				List<String> headers = new ArrayList<>();

				while (enu2.hasMoreElements()) {
					String value = enu2.nextElement();

					headers.add(value);
				}

				headerMap.put(
					name, headers.toArray(new String[headers.size()]));
			}

			Map<String, String[]> parameterMap = request.getParameterMap();
			Map<String, Object> resultsMap = new HashMap<>();

			if (Validator.isNull(authType)) {
				authType = company.getAuthType();
			}

			int authResult = Authenticator.FAILURE;

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				authResult = UserLocalServiceUtil.authenticateByEmailAddress(
					company.getCompanyId(), login, password, headerMap,
					parameterMap, resultsMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				authResult = UserLocalServiceUtil.authenticateByScreenName(
					company.getCompanyId(), login, password, headerMap,
					parameterMap, resultsMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				authResult = UserLocalServiceUtil.authenticateByUserId(
					company.getCompanyId(), userId, password, headerMap,
					parameterMap, resultsMap);
			}

			User user = (User)resultsMap.get("user");

			if (authResult != Authenticator.SUCCESS) {
				if (user != null) {
					user = UserLocalServiceUtil.fetchUser(user.getUserId());
				}

				if (user != null) {
					UserLocalServiceUtil.checkLockout(user);
				}

				throw new AuthException();
			}

			return user;
		}
	}

}