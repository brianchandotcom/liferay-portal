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
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.AuthenticatedUserUUIDStoreUtil;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
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

				userId = MapUtil.getLong(resultsMap, "userId", userId);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				authResult = UserLocalServiceUtil.authenticateByScreenName(
					company.getCompanyId(), login, password, headerMap,
					parameterMap, resultsMap);

				userId = MapUtil.getLong(resultsMap, "userId", userId);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				authResult = UserLocalServiceUtil.authenticateByUserId(
					company.getCompanyId(), userId, password, headerMap,
					parameterMap, resultsMap);
			}

			if (authResult != Authenticator.SUCCESS) {
				User user = UserLocalServiceUtil.fetchUser(userId);

				if (user != null) {
					UserLocalServiceUtil.checkLockout(user);
				}

				throw new AuthException();
			}
		}

		return userId;
	}

	@Override
	public void login(
			HttpServletRequest request, HttpServletResponse response,
			String login, String password, boolean rememberMe, String authType)
		throws Exception {

		CookieKeys.validateSupportCookie(request);

		HttpSession session = request.getSession();

		Company company = PortalUtil.getCompany(request);

		long userId = getAuthenticatedUserId(
			request, login, password, authType);

		if (!PropsValues.AUTH_SIMULTANEOUS_LOGINS) {
			signOutSimultaneousLogins(userId);
		}

		if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			session = renewSession(request, session);
		}

		// Set cookies

		String domain = CookieKeys.getDomain(request);

		User user = UserLocalServiceUtil.getUserById(userId);

		String userIdString = String.valueOf(userId);

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

		if (Validator.isNotNull(domain)) {
			companyIdCookie.setDomain(domain);
		}

		companyIdCookie.setPath(StringPool.SLASH);

		Cookie idCookie = new Cookie(
			CookieKeys.ID,
			Encryptor.encrypt(company.getKeyObj(), userIdString));

		if (Validator.isNotNull(domain)) {
			idCookie.setDomain(domain);
		}

		idCookie.setPath(StringPool.SLASH);

		Cookie passwordCookie = new Cookie(
			CookieKeys.PASSWORD,
			Encryptor.encrypt(company.getKeyObj(), password));

		if (Validator.isNotNull(domain)) {
			passwordCookie.setDomain(domain);
		}

		passwordCookie.setPath(StringPool.SLASH);

		Cookie rememberMeCookie = new Cookie(
			CookieKeys.REMEMBER_ME, Boolean.TRUE.toString());

		if (Validator.isNotNull(domain)) {
			rememberMeCookie.setDomain(domain);
		}

		rememberMeCookie.setPath(StringPool.SLASH);

		int loginMaxAge = PropsValues.COMPANY_SECURITY_AUTO_LOGIN_MAX_AGE;

		String userUUID = userIdString.concat(StringPool.PERIOD).concat(
			String.valueOf(System.nanoTime()));

		Cookie userUUIDCookie = new Cookie(
			CookieKeys.USER_UUID,
			Encryptor.encrypt(company.getKeyObj(), userUUID));

		userUUIDCookie.setPath(StringPool.SLASH);

		session.setAttribute(WebKeys.USER_UUID, userUUID);

		if (PropsValues.SESSION_DISABLED) {
			rememberMe = true;
		}

		if (rememberMe) {
			companyIdCookie.setMaxAge(loginMaxAge);
			idCookie.setMaxAge(loginMaxAge);
			passwordCookie.setMaxAge(loginMaxAge);
			rememberMeCookie.setMaxAge(loginMaxAge);
			userUUIDCookie.setMaxAge(loginMaxAge);
		}
		else {

			// This was explicitly changed from 0 to -1 so that the cookie lasts
			// as long as the browser. This allows an external servlet wrapped
			// in AutoLoginFilter to work throughout the client connection. The
			// cookies ARE removed on an actual logout, so there is no security
			// issue. See LEP-4678 and LEP-5177.

			companyIdCookie.setMaxAge(-1);
			idCookie.setMaxAge(-1);
			passwordCookie.setMaxAge(-1);
			rememberMeCookie.setMaxAge(0);
			userUUIDCookie.setMaxAge(-1);
		}

		Cookie loginCookie = new Cookie(CookieKeys.LOGIN, login);

		if (Validator.isNotNull(domain)) {
			loginCookie.setDomain(domain);
		}

		loginCookie.setMaxAge(loginMaxAge);
		loginCookie.setPath(StringPool.SLASH);

		Cookie screenNameCookie = new Cookie(
			CookieKeys.SCREEN_NAME,
			Encryptor.encrypt(company.getKeyObj(), user.getScreenName()));

		if (Validator.isNotNull(domain)) {
			screenNameCookie.setDomain(domain);
		}

		screenNameCookie.setMaxAge(loginMaxAge);
		screenNameCookie.setPath(StringPool.SLASH);

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
		CookieKeys.addCookie(request, response, userUUIDCookie, secure);

		if (rememberMe) {
			CookieKeys.addCookie(request, response, loginCookie, secure);
			CookieKeys.addCookie(request, response, passwordCookie, secure);
			CookieKeys.addCookie(request, response, rememberMeCookie, secure);
			CookieKeys.addCookie(request, response, screenNameCookie, secure);
		}

		AuthenticatedUserUUIDStoreUtil.register(userUUID);
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		EventsProcessorUtil.process(
			PropsKeys.LOGOUT_EVENTS_PRE, PropsValues.LOGOUT_EVENTS_PRE, request,
			response);

		String domain = CookieKeys.getDomain(request);

		Cookie companyIdCookie = new Cookie(
			CookieKeys.COMPANY_ID, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			companyIdCookie.setDomain(domain);
		}

		companyIdCookie.setMaxAge(0);
		companyIdCookie.setPath(StringPool.SLASH);

		Cookie idCookie = new Cookie(CookieKeys.ID, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			idCookie.setDomain(domain);
		}

		idCookie.setMaxAge(0);
		idCookie.setPath(StringPool.SLASH);

		Cookie passwordCookie = new Cookie(
			CookieKeys.PASSWORD, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			passwordCookie.setDomain(domain);
		}

		passwordCookie.setMaxAge(0);
		passwordCookie.setPath(StringPool.SLASH);

		boolean rememberMe = GetterUtil.getBoolean(
			CookieKeys.getCookie(request, CookieKeys.REMEMBER_ME));

		if (!rememberMe) {
			Cookie loginCookie = new Cookie(CookieKeys.LOGIN, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				loginCookie.setDomain(domain);
			}

			loginCookie.setMaxAge(0);
			loginCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, loginCookie);
		}

		Cookie rememberMeCookie = new Cookie(
			CookieKeys.REMEMBER_ME, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			rememberMeCookie.setDomain(domain);
		}

		rememberMeCookie.setMaxAge(0);
		rememberMeCookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(request, response, companyIdCookie);
		CookieKeys.addCookie(request, response, idCookie);
		CookieKeys.addCookie(request, response, passwordCookie);
		CookieKeys.addCookie(request, response, rememberMeCookie);

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

}