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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
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
import com.liferay.portlet.login.util.LoginUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

	public AutoLoginFilter() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			AutoLogin.class, new AutoLoginServiceTrackerCustomizer());

		_serviceTracker.open();
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

		if (!PropsValues.AUTH_SIMULTANEOUS_LOGINS) {
			LoginUtil.signOutSimultaneousLogins(userId);
		}

		if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			session = LoginUtil.renewSession(request, session);
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
			String redirect = PortalUtil.getPathMain().concat(
				"/portal/protected");

			if (PropsValues.AUTH_FORWARD_BY_LAST_PATH) {
				String autoLoginRedirect = (String)request.getAttribute(
					AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE);

				redirect = redirect.concat("?redirect=");

				if (Validator.isNotNull(autoLoginRedirect)) {
					redirect = redirect.concat(autoLoginRedirect);
				}
				else {
					redirect = redirect.concat(
						PortalUtil.getCurrentCompleteURL(request));
				}
			}

			response.sendRedirect(redirect);
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

		String path = StringUtil.toLowerCase(request.getRequestURI());

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

	private static final Log _log = LogFactoryUtil.getLog(
		AutoLoginFilter.class);

	private static final List<AutoLogin> _autoLogins =
		new CopyOnWriteArrayList<AutoLogin>();

	private final ServiceTracker<?, AutoLogin> _serviceTracker;

	private class AutoLoginServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<AutoLogin, AutoLogin> {

		@Override
		public AutoLogin addingService(
			ServiceReference<AutoLogin> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			AutoLogin autoLogin = registry.getService(serviceReference);

			_autoLogins.add(autoLogin);

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