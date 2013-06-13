/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.autologin;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class AutoLoginFilter extends BasePortalFilter {

	public static void registerAutoLogin(AutoLogin autoLogin) {
		_autoLogins.add(autoLogin);
	}

	public static void unregisterAutoLogin(AutoLogin autoLogin) {
		_autoLogins.remove(autoLogin);
	}

	public AutoLoginFilter() {
		for (String autoLoginClassName : PropsValues.AUTO_LOGIN_HOOKS) {
			AutoLogin autoLogin = (AutoLogin)InstancePool.get(
				autoLoginClassName);

			_autoLogins.add(autoLogin);
		}
	}

	protected String getLoginRemoteUser(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, String[] credentials)
		throws Exception {

		if ((credentials == null) || (credentials.length != 3)) {
			return null;
		}

		String jUsername = credentials[0];
		String jPassword = credentials[1];
		boolean encPassword = GetterUtil.getBoolean(credentials[2]);

		if (Validator.isNull(jUsername) || Validator.isNull(jPassword)) {
			return null;
		}

		long userId = GetterUtil.getLong(jUsername);

		if (userId <= 0) {
			return null;
		}

		User user = UserLocalServiceUtil.fetchUserById(userId);

		if ((user == null) || user.isLockout()) {
			return null;
		}

		session.setAttribute("j_username", jUsername);

		// Not having access to the unencrypted password will not allow you to
		// connect to external resources that require it (mail server)

		if (encPassword) {
			session.setAttribute("j_password", jPassword);
		}
		else {
			session.setAttribute(
				"j_password", PasswordEncryptorUtil.encrypt(jPassword));

			if (PropsValues.SESSION_STORE_PASSWORD) {
				session.setAttribute(WebKeys.USER_PASSWORD, jPassword);
			}
		}

		session.setAttribute("j_remoteuser", jUsername);

		if (PropsValues.PORTAL_JAAS_ENABLE) {
			response.sendRedirect(
				PortalUtil.getPathMain() + "/portal/touch_protected");
		}

		return jUsername;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		HttpSession session = request.getSession();

		String host = PortalUtil.getHost(request);

		if (PortalInstances.isAutoLoginIgnoreHost(host)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignore host " + host);
			}

			processFilter(
				AutoLoginFilter.class, request, response, filterChain);

			return;
		}

		String contextPath = PortalUtil.getPathContext();

		String path = request.getRequestURI().toLowerCase();

		if (!contextPath.equals(StringPool.SLASH) &&
			path.contains(contextPath)) {

			path = path.substring(contextPath.length());
		}

		if (PortalInstances.isAutoLoginIgnorePath(path)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignore path " + path);
			}

			processFilter(
				AutoLoginFilter.class, request, response, filterChain);

			return;
		}

		String remoteUser = request.getRemoteUser();
		String jUserName = (String)session.getAttribute("j_username");

		// PLACEHOLDER 01
		// PLACEHOLDER 02
		// PLACEHOLDER 03
		// PLACEHOLDER 04
		// PLACEHOLDER 05

		if (!PropsValues.AUTH_LOGIN_DISABLED &&
			(remoteUser == null) && (jUserName == null)) {

			for (AutoLogin autoLogin : _autoLogins) {
				try {
					String[] credentials = autoLogin.login(request, response);

					String redirect = (String)request.getAttribute(
						AutoLogin.AUTO_LOGIN_REDIRECT);

					if (Validator.isNotNull(redirect)) {
						response.sendRedirect(redirect);

						return;
					}

					String loginRemoteUser = getLoginRemoteUser(
						request, response, session, credentials);

					if (loginRemoteUser != null) {
						request = new ProtectedServletRequest(
							request, loginRemoteUser);

						if (PropsValues.PORTAL_JAAS_ENABLE) {
							return;
						}

						if (!PropsValues.AUTH_FORWARD_BY_LAST_PATH) {
							redirect = Portal.PATH_MAIN;
						}
						else {
							redirect = (String)request.getAttribute(
								AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE);
						}

						if (Validator.isNotNull(redirect)) {
							response.sendRedirect(redirect);

							return;
						}
					}
				}
				catch (Exception e) {
					StringBundler sb = new StringBundler(4);

					sb.append("Current URL ");

					String currentURL = PortalUtil.getCurrentURL(request);

					sb.append(currentURL);

					sb.append(" generates exception: ");
					sb.append(e.getMessage());

					if (currentURL.endsWith(_PATH_CHAT_LATEST)) {
						if (_log.isWarnEnabled()) {
							_log.warn(sb.toString());
						}
					}
					else {
						_log.error(sb.toString());
					}
				}
			}
		}

		processFilter(AutoLoginFilter.class, request, response, filterChain);
	}

	private static final String _PATH_CHAT_LATEST = "/-/chat/latest";

	private static Log _log = LogFactoryUtil.getLog(AutoLoginFilter.class);

	private static List<AutoLogin> _autoLogins =
		new CopyOnWriteArrayList<AutoLogin>();

}