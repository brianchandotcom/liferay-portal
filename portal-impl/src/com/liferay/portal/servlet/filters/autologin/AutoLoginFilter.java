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

package com.liferay.portal.servlet.filters.autologin;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 * @author Raymond Augé
 */
public class AutoLoginFilter extends BasePortalFilter {

	protected String getLoginRemoteUser(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession session,
			String[] credentials)
		throws Exception {

		if ((credentials == null) || (credentials.length != 3)) {
			return null;
		}

		String jUsername = credentials[0];
		String jPassword = credentials[1];

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

		if (!PropsValues.AUTH_SIMULTANEOUS_LOGINS) {
			AuthenticatedSessionManagerUtil.signOutSimultaneousLogins(userId);
		}

		if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			session = AuthenticatedSessionManagerUtil.renewSession(
				httpServletRequest, session);
		}

		session.setAttribute("j_username", jUsername);

		// Not having access to the unencrypted password will not allow you to
		// connect to external resources that require it (mail server)

		if (GetterUtil.getBoolean(credentials[2])) {
			session.setAttribute("j_password", jPassword);
		}
		else {
			session.setAttribute(
				"j_password",
				PasswordEncryptorUtil.encrypt(jPassword, user.getPassword()));

			if (PropsValues.SESSION_STORE_PASSWORD) {
				session.setAttribute(WebKeys.USER_PASSWORD, jPassword);
			}
		}

		session.setAttribute("j_remoteuser", jUsername);

		if (PropsValues.PORTAL_JAAS_ENABLE) {
			String mainPath = PortalUtil.getPathMain();

			String redirect = mainPath.concat("/portal/protected");

			if (PropsValues.AUTH_FORWARD_BY_LAST_PATH) {
				redirect = redirect.concat("?redirect=");

				String autoLoginRedirect =
					(String)httpServletRequest.getAttribute(
						AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE);

				if (Validator.isNull(autoLoginRedirect)) {
					autoLoginRedirect = PortalUtil.getCurrentCompleteURL(
						httpServletRequest);
				}

				redirect = redirect.concat(
					URLCodec.encodeURL(autoLoginRedirect));
			}

			httpServletResponse.sendRedirect(redirect);
		}

		return jUsername;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		HttpSession session = httpServletRequest.getSession();

		String host = PortalUtil.getHost(httpServletRequest);

		if (PortalInstances.isAutoLoginIgnoreHost(host)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignore host " + host);
			}

			processFilter(
				AutoLoginFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);

			return;
		}

		String contextPath = PortalUtil.getPathContext();

		String path = StringUtil.toLowerCase(
			httpServletRequest.getRequestURI());

		if (!contextPath.equals(StringPool.SLASH) &&
			path.contains(contextPath)) {

			path = path.substring(contextPath.length());
		}

		if (PortalInstances.isAutoLoginIgnorePath(path)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignore path " + path);
			}

			processFilter(
				AutoLoginFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);

			return;
		}

		String remoteUser = httpServletRequest.getRemoteUser();
		String jUserName = (String)session.getAttribute("j_username");

		if (!PropsValues.AUTH_LOGIN_DISABLED && (remoteUser == null) &&
			(jUserName == null)) {

			for (AutoLogin autoLogin : _autoLogins) {
				try {
					String[] credentials = autoLogin.login(
						httpServletRequest, httpServletResponse);

					String redirect = (String)httpServletRequest.getAttribute(
						AutoLogin.AUTO_LOGIN_REDIRECT);

					if (Validator.isNotNull(redirect)) {
						httpServletResponse.sendRedirect(redirect);

						return;
					}

					String loginRemoteUser = getLoginRemoteUser(
						httpServletRequest, httpServletResponse, session,
						credentials);

					if (loginRemoteUser != null) {
						httpServletRequest = new ProtectedServletRequest(
							httpServletRequest, loginRemoteUser);

						if (PropsValues.PORTAL_JAAS_ENABLE) {
							return;
						}

						if (!PropsValues.AUTH_FORWARD_BY_LAST_PATH) {
							redirect = Portal.PATH_MAIN;
						}
						else {
							redirect = (String)httpServletRequest.getAttribute(
								AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE);
						}

						if (Validator.isNotNull(redirect)) {
							httpServletResponse.sendRedirect(redirect);

							return;
						}
					}
				}
				catch (Exception e) {
					StringBundler sb = new StringBundler(6);

					sb.append("Current URL ");

					String currentURL = PortalUtil.getCurrentURL(
						httpServletRequest);

					sb.append(currentURL);

					sb.append(" generates exception: ");
					sb.append(e.getMessage());

					if (_log.isInfoEnabled()) {
						sb.append(" stack: ");
						sb.append(StackTraceUtil.getStackTrace(e));
					}

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

		processFilter(
			AutoLoginFilter.class.getName(), httpServletRequest,
			httpServletResponse, filterChain);
	}

	private static final String _PATH_CHAT_LATEST = "/-/chat/latest";

	private static final Log _log = LogFactoryUtil.getLog(
		AutoLoginFilter.class);

	private static final ServiceTrackerList<AutoLogin> _autoLogins =
		ServiceTrackerCollections.openList(
			AutoLogin.class, new AutoLoginServiceTrackerCustomizer());

	private static class AutoLoginServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<AutoLogin, AutoLogin> {

		@Override
		public AutoLogin addingService(
			ServiceReference<AutoLogin> serviceReference) {

			if (GetterUtil.getBoolean(
					serviceReference.getProperty("private.auto.login"))) {

				return null;
			}

			Registry registry = RegistryUtil.getRegistry();

			AutoLogin autoLogin = registry.getService(serviceReference);

			if (autoLogin == null) {
				return null;
			}

			return autoLogin;
		}

		@Override
		public void modifiedService(
			ServiceReference<AutoLogin> serviceReference, AutoLogin autoLogin) {
		}

		@Override
		public void removedService(
			ServiceReference<AutoLogin> serviceReference, AutoLogin autoLogin) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_autoLogins.remove(autoLogin);
		}

	}

}