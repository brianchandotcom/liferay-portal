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

package com.liferay.portal.struts;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutPermissionException;
import com.liferay.portal.kernel.exception.PortletActiveException;
import com.liferay.portal.kernel.exception.UserActiveException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.model.UserTrackerPath;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.InterruptedPortletRequestWhitelistUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.service.persistence.UserTrackerPathUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;
import com.liferay.portal.struts.model.ModuleConfig;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Wesley Gong
 * @author Mika Koivisto
 * @author Neil Griffin
 */
public class PortalRequestProcessor {

	public static final String INCLUDE_PATH_INFO =
		"javax.servlet.include.path_info";

	public static final String INCLUDE_SERVLET_PATH =
		"javax.servlet.include.servlet_path";

	public PortalRequestProcessor(
		ServletContext servletContext, ModuleConfig moduleConfig) {

		_servletContext = servletContext;
		_moduleConfig = moduleConfig;

		_definitions = (Map<String, Definition>)servletContext.getAttribute(
			TilesUtil.DEFINITIONS);

		// auth.forward.last.path.

		_lastPaths = new HashSet<>(
			Arrays.asList(
				PropsUtil.getArray(PropsKeys.AUTH_FORWARD_LAST_PATHS)));

		_lastPaths.add(_PATH_PORTAL_LAYOUT);

		// auth.public.path.

		_publicPaths = new HashSet<>();

		_publicPaths.add(_PATH_C);
		_publicPaths.add(_PATH_PORTAL_API_JSONWS);
		_publicPaths.add(_PATH_PORTAL_FLASH);
		_publicPaths.add(_PATH_PORTAL_J_LOGIN);
		_publicPaths.add(_PATH_PORTAL_LAYOUT);
		_publicPaths.add(_PATH_PORTAL_LICENSE);
		_publicPaths.add(_PATH_PORTAL_LOGIN);
		_publicPaths.add(_PATH_PORTAL_RENDER_PORTLET);
		_publicPaths.add(_PATH_PORTAL_RESILIENCY);
		_publicPaths.add(_PATH_PORTAL_TCK);
		_publicPaths.add(_PATH_PORTAL_UPDATE_LANGUAGE);
		_publicPaths.add(_PATH_PORTAL_UPDATE_PASSWORD);
		_publicPaths.add(_PATH_PORTAL_VERIFY_EMAIL_ADDRESS);
		_publicPaths.add(PropsValues.AUTH_LOGIN_DISABLED_PATH);

		_trackerIgnorePaths = new HashSet<>(
			Arrays.asList(
				PropsUtil.getArray(PropsKeys.SESSION_TRACKER_IGNORE_PATHS)));
	}

	public void process(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		String path = _processPath(httpServletRequest);

		ActionMapping actionMapping = _moduleConfig.getActionMapping(path);

		if (actionMapping == null) {
			String lastPath = _getLastPath(httpServletRequest);

			if (_log.isDebugEnabled()) {
				_log.debug("Last path " + lastPath);
			}

			httpServletResponse.sendRedirect(lastPath);

			return;
		}

		_process(actionMapping, httpServletRequest, httpServletResponse);
	}

	private String _findPath(HttpServletRequest httpServletRequest) {
		String path = (String)httpServletRequest.getAttribute(
			INCLUDE_PATH_INFO);

		if (path == null) {
			path = httpServletRequest.getPathInfo();
		}

		if ((path != null) && (path.length() > 0)) {
			return path;
		}

		path = (String)httpServletRequest.getAttribute(INCLUDE_SERVLET_PATH);

		if (path == null) {
			path = httpServletRequest.getServletPath();
		}

		int periodIndex = path.lastIndexOf(CharPool.PERIOD);
		int slashIndex = path.lastIndexOf(CharPool.SLASH);

		if ((periodIndex >= 0) && (periodIndex > slashIndex)) {
			path = path.substring(0, periodIndex);
		}

		return path;
	}

	private String _getFriendlyTrackerPath(
			String path, ThemeDisplay themeDisplay,
			HttpServletRequest httpServletRequest)
		throws Exception {

		if (!path.equals(_PATH_PORTAL_LAYOUT)) {
			return null;
		}

		long plid = ParamUtil.getLong(httpServletRequest, "p_l_id");

		if (plid == 0) {
			return null;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		String layoutFriendlyURL = PortalUtil.getLayoutFriendlyURL(
			layout, themeDisplay);

		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		if (Validator.isNull(portletId)) {
			return layoutFriendlyURL;
		}

		long companyId = PortalUtil.getCompanyId(httpServletRequest);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		if (portlet == null) {
			String strutsPath = path.substring(
				1, path.lastIndexOf(CharPool.SLASH));

			portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
				companyId, strutsPath);
		}

		if ((portlet == null) || !portlet.isActive()) {
			return layoutFriendlyURL.concat(
				StringPool.QUESTION
			).concat(
				httpServletRequest.getQueryString()
			);
		}

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		if (friendlyURLMapper == null) {
			return layoutFriendlyURL.concat(
				StringPool.QUESTION
			).concat(
				httpServletRequest.getQueryString()
			);
		}

		String namespace = PortalUtil.getPortletNamespace(portletId);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, portlet, layout, PortletRequest.RENDER_PHASE);

		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith(namespace)) {
				key = key.substring(namespace.length());

				portletURL.setParameter(key, entry.getValue());
			}
		}

		String portletFriendlyURL = friendlyURLMapper.buildPath(portletURL);

		if (portletFriendlyURL != null) {
			return layoutFriendlyURL.concat(portletFriendlyURL);
		}

		return layoutFriendlyURL.concat(
			StringPool.QUESTION
		).concat(
			httpServletRequest.getQueryString()
		);
	}

	private String _getLastPath(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Boolean httpsInitial = (Boolean)session.getAttribute(
			WebKeys.HTTPS_INITIAL);

		String portalURL = null;

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!PropsValues.SESSION_ENABLE_PHISHING_PROTECTION &&
			(httpsInitial != null) && !httpsInitial.booleanValue()) {

			portalURL = PortalUtil.getPortalURL(httpServletRequest, false);
		}
		else {
			portalURL = PortalUtil.getPortalURL(httpServletRequest);
		}

		StringBundler sb = new StringBundler(7);

		sb.append(portalURL);
		sb.append(themeDisplay.getPathMain());
		sb.append(_PATH_PORTAL_LAYOUT);

		if (!PropsValues.AUTH_FORWARD_BY_LAST_PATH) {
			if (httpServletRequest.getRemoteUser() != null) {

				// If we do not forward by last path and the user is logged in,
				// forward to the user's default layout to prevent a lagging
				// loop

				sb.append(StringPool.QUESTION);
				sb.append("p_l_id");
				sb.append(StringPool.EQUAL);
				sb.append(LayoutConstants.DEFAULT_PLID);
			}

			return sb.toString();
		}

		LastPath lastPath = (LastPath)session.getAttribute(WebKeys.LAST_PATH);

		if (lastPath == null) {
			return sb.toString();
		}

		String parameters = lastPath.getParameters();

		// Only test for existing mappings for last paths that were set when the
		// user accessed a layout directly instead of through its friendly URL

		String contextPath = lastPath.getContextPath();

		if (contextPath.equals(themeDisplay.getPathMain())) {
			ActionMapping actionMapping = _moduleConfig.getActionMapping(
				lastPath.getPath());

			if ((actionMapping == null) || parameters.isEmpty()) {
				return sb.toString();
			}
		}

		StringBundler lastPathSB = new StringBundler(4);

		lastPathSB.append(portalURL);
		lastPathSB.append(lastPath.getContextPath());
		lastPathSB.append(lastPath.getPath());
		lastPathSB.append(parameters);

		return lastPathSB.toString();
	}

	private void _internalModuleRelativeForward(
			String uri, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		Definition definition = _definitions.get(uri);

		if (definition != null) {
			httpServletRequest.setAttribute(TilesUtil.DEFINITION, definition);

			uri = definition.getPath();
		}

		StrutsUtil.forward(
			uri, _servletContext, httpServletRequest, httpServletResponse);
	}

	private boolean _isPortletPath(String path) {
		if ((path != null) && !path.equals(_PATH_C) &&
			!path.startsWith(_PATH_COMMON) &&
			!path.contains(_PATH_J_SECURITY_CHECK) &&
			!path.startsWith(_PATH_PORTAL)) {

			return true;
		}

		return false;
	}

	private boolean _isPublicPath(String path) {
		if ((path != null) &&
			(_publicPaths.contains(path) || path.startsWith(_PATH_COMMON) ||
			 AuthPublicPathRegistry.contains(path))) {

			return true;
		}

		return false;
	}

	private void _process(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		_processLocale(httpServletRequest);

		httpServletResponse.setContentType("text/html; charset=UTF-8");

		if (!_processRoles(
				httpServletRequest, httpServletResponse, actionMapping)) {

			return;
		}

		if (!_processForward(
				httpServletRequest, httpServletResponse, actionMapping)) {

			return;
		}

		Action action = actionMapping.getAction();

		try {
			ActionForward actionForward = action.execute(
				actionMapping, httpServletRequest, httpServletResponse);

			if (actionForward != null) {
				_internalModuleRelativeForward(
					actionForward.getPath(), httpServletRequest,
					httpServletResponse);
			}
		}
		catch (IOException | ServletException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private boolean _processForward(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			ActionMapping actionMapping)
		throws IOException, ServletException {

		String forward = actionMapping.getForward();

		if (forward == null) {
			return true;
		}

		_internalModuleRelativeForward(
			forward, httpServletRequest, httpServletResponse);

		return false;
	}

	private void _processLocale(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();

		if (session.getAttribute(WebKeys.LOCALE) != null) {
			return;
		}

		Locale locale = httpServletRequest.getLocale();

		if (locale != null) {
			session.setAttribute(WebKeys.LOCALE, locale);
		}
	}

	private String _processPath(HttpServletRequest httpServletRequest) {
		String path = _findPath(httpServletRequest);

		HttpSession session = httpServletRequest.getSession();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		// Current users

		UserTracker userTracker = LiveUsers.getUserTracker(
			themeDisplay.getCompanyId(), session.getId());

		if ((userTracker != null) && !path.equals(_PATH_C) &&
			!path.contains(_PATH_J_SECURITY_CHECK) &&
			!path.contains(_PATH_PORTAL_PROTECTED) &&
			!_trackerIgnorePaths.contains(path)) {

			String fullPath = null;

			try {
				if (PropsValues.SESSION_TRACKER_FRIENDLY_PATHS_ENABLED) {
					fullPath = _getFriendlyTrackerPath(
						path, themeDisplay, httpServletRequest);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			String fullPathWithoutQueryString = fullPath;

			if (Validator.isNull(fullPath)) {
				String queryString = httpServletRequest.getQueryString();

				fullPathWithoutQueryString = path;

				if (Validator.isNotNull(queryString)) {
					fullPath = path.concat(
						StringPool.QUESTION
					).concat(
						queryString
					);
				}
				else {
					fullPath = path;
				}
			}

			int pos = fullPathWithoutQueryString.indexOf(StringPool.QUESTION);

			if (pos != -1) {
				fullPathWithoutQueryString =
					fullPathWithoutQueryString.substring(0, pos);
			}

			if (!_trackerIgnorePaths.contains(fullPathWithoutQueryString)) {
				UserTrackerPath userTrackerPath = UserTrackerPathUtil.create(0);

				userTrackerPath.setUserTrackerId(
					userTracker.getUserTrackerId());
				userTrackerPath.setPath(fullPath);
				userTrackerPath.setPathDate(new Date());

				userTracker.addPath(userTrackerPath);
			}
		}

		String remoteUser = httpServletRequest.getRemoteUser();

		User user = null;

		try {
			user = PortalUtil.getUser(httpServletRequest);
		}
		catch (Exception e) {
		}

		// Last path

		if (_lastPaths.contains(path) && !_trackerIgnorePaths.contains(path)) {
			boolean saveLastPath = ParamUtil.getBoolean(
				httpServletRequest, "saveLastPath", true);

			if (themeDisplay.isLifecycleResource() ||
				themeDisplay.isStateExclusive() ||
				themeDisplay.isStatePopUp() ||
				!StringUtil.equalsIgnoreCase(
					httpServletRequest.getMethod(), HttpMethods.GET)) {

				saveLastPath = false;
			}

			// Save last path

			if (saveLastPath) {

				// Was a last path set by another servlet that dispatched to the
				// MainServlet? If so, use that last path instead.

				LastPath lastPath = (LastPath)httpServletRequest.getAttribute(
					WebKeys.LAST_PATH);

				if (lastPath == null) {
					lastPath = new LastPath(
						themeDisplay.getPathMain(), path,
						HttpUtil.parameterMapToString(
							httpServletRequest.getParameterMap()));
				}

				session.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
		}

		// Setup wizard

		if (PropsValues.SETUP_WIZARD_ENABLED) {
			if (!path.equals(_PATH_PORTAL_LICENSE) &&
				!path.equals(_PATH_PORTAL_STATUS)) {

				return _PATH_PORTAL_SETUP_WIZARD;
			}
		}
		else if (path.equals(_PATH_PORTAL_SETUP_WIZARD)) {
			return _PATH_PORTAL_LAYOUT;
		}

		if ((remoteUser != null) || (user != null)) {

			// Authenticated users can always log out

			if (path.equals(_PATH_PORTAL_LOGOUT)) {
				return path;
			}

			// Authenticated users can always extend or confirm their session

			if (path.equals(_PATH_PORTAL_EXPIRE_SESSION) ||
				path.equals(_PATH_PORTAL_EXTEND_SESSION)) {

				return path;
			}

			// Authenticated users can always update their language

			if (path.equals(_PATH_PORTAL_UPDATE_LANGUAGE)) {
				return path;
			}

			// Authenticated users can always agree to terms of use

			if (path.equals(_PATH_PORTAL_UPDATE_TERMS_OF_USE)) {
				return path;
			}
		}

		// Authenticated users must still exist in the system

		if ((remoteUser != null) && (user == null)) {
			return _PATH_PORTAL_LOGOUT;
		}

		long companyId = PortalUtil.getCompanyId(httpServletRequest);
		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		// Authenticated users must be active

		if (user != null) {
			if (!user.isActive()) {
				SessionErrors.add(session, UserActiveException.class.getName());

				return _PATH_PORTAL_ERROR;
			}

			if (!path.equals(_PATH_PORTAL_JSON_SERVICE) &&
				!path.equals(_PATH_PORTAL_RENDER_PORTLET) &&
				!themeDisplay.isImpersonated() &&
				!InterruptedPortletRequestWhitelistUtil.
					isPortletInvocationWhitelisted(
						companyId, portletId,
						PortalUtil.getStrutsAction(httpServletRequest))) {

				// Authenticated users should agree to Terms of Use

				if (!user.isTermsOfUseComplete()) {
					return _PATH_PORTAL_TERMS_OF_USE;
				}

				// Authenticated users should have a verified email address

				if (!user.isEmailAddressVerificationComplete()) {
					if (path.equals(_PATH_PORTAL_UPDATE_EMAIL_ADDRESS)) {
						return _PATH_PORTAL_UPDATE_EMAIL_ADDRESS;
					}

					return _PATH_PORTAL_VERIFY_EMAIL_ADDRESS;
				}

				// Authenticated users must have a current password

				if (user.isPasswordReset()) {
					try {
						PasswordPolicy passwordPolicy =
							user.getPasswordPolicy();

						if ((passwordPolicy == null) ||
							passwordPolicy.isChangeable()) {

							return _PATH_PORTAL_UPDATE_PASSWORD;
						}
					}
					catch (Exception e) {
						_log.error(e, e);

						return _PATH_PORTAL_UPDATE_PASSWORD;
					}
				}
				else if (path.equals(_PATH_PORTAL_UPDATE_PASSWORD)) {
					return _PATH_PORTAL_LAYOUT;
				}

				// Authenticated users must have an email address

				if (!user.isEmailAddressComplete()) {
					return _PATH_PORTAL_UPDATE_EMAIL_ADDRESS;
				}

				// Authenticated users should have a reminder query

				if (!user.isDefaultUser() && !user.isReminderQueryComplete()) {
					return _PATH_PORTAL_UPDATE_REMINDER_QUERY;
				}
			}
		}
		else if (!_isPublicPath(path)) {

			// Users must sign in

			SessionErrors.add(session, PrincipalException.class.getName());

			return _PATH_PORTAL_LOGIN;
		}

		// Authenticated users must have access to at least one layout

		if (SessionErrors.contains(
				session, LayoutPermissionException.class.getName())) {

			return _PATH_PORTAL_ERROR;
		}

		return path;
	}

	private boolean _processRoles(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			ActionMapping actionMapping)
		throws IOException, ServletException {

		String path = actionMapping.getPath();

		if (_isPublicPath(path)) {
			return true;
		}

		boolean authorized = true;

		User user = null;

		try {
			user = PortalUtil.getUser(httpServletRequest);
		}
		catch (Exception e) {
		}

		if ((user != null) && _isPortletPath(path)) {
			try {

				// Authenticated users can always log out

				if (path.equals(_PATH_PORTAL_LOGOUT)) {
					return true;
				}

				Portlet portlet = null;

				String portletId = ParamUtil.getString(
					httpServletRequest, "p_p_id");

				if (Validator.isNotNull(portletId)) {
					portlet = PortletLocalServiceUtil.getPortletById(
						user.getCompanyId(), portletId);
				}

				String strutsPath = path.substring(
					1, path.lastIndexOf(CharPool.SLASH));

				if (portlet != null) {
					if (!strutsPath.equals(portlet.getStrutsPath())) {
						throw new PrincipalException.MustBePortletStrutsPath(
							strutsPath, portletId);
					}
				}
				else {
					portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
						user.getCompanyId(), strutsPath);
				}

				if ((portlet != null) && portlet.isActive() &&
					!portlet.isSystem()) {

					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					PermissionChecker permissionChecker =
						themeDisplay.getPermissionChecker();

					if (!PortletPermissionUtil.contains(
							permissionChecker, themeDisplay.getLayout(),
							portlet, ActionKeys.VIEW)) {

						throw new PrincipalException.MustHavePermission(
							permissionChecker, Portlet.class.getName(),
							portlet.getPortletId(), ActionKeys.VIEW);
					}
				}
				else if ((portlet != null) && !portlet.isActive()) {
					SessionErrors.add(
						httpServletRequest,
						PortletActiveException.class.getName());

					authorized = false;
				}
			}
			catch (Exception e) {
				SessionErrors.add(
					httpServletRequest, PrincipalException.class.getName());

				authorized = false;
			}
		}

		if (!authorized) {
			ActionForward actionForward = actionMapping.getActionForward(
				_PATH_PORTAL_ERROR);

			if (actionForward != null) {
				_internalModuleRelativeForward(
					actionForward.getPath(), httpServletRequest,
					httpServletResponse);
			}

			return false;
		}

		return true;
	}

	private static final String _PATH_C = "/c";

	private static final String _PATH_COMMON = "/common";

	private static final String _PATH_J_SECURITY_CHECK = "/j_security_check";

	private static final String _PATH_PORTAL = "/portal";

	private static final String _PATH_PORTAL_API_JSONWS = "/portal/api/jsonws";

	private static final String _PATH_PORTAL_ERROR = "/portal/error";

	private static final String _PATH_PORTAL_EXPIRE_SESSION =
		"/portal/expire_session";

	private static final String _PATH_PORTAL_EXTEND_SESSION =
		"/portal/extend_session";

	private static final String _PATH_PORTAL_FLASH = "/portal/flash";

	private static final String _PATH_PORTAL_J_LOGIN = "/portal/j_login";

	private static final String _PATH_PORTAL_JSON_SERVICE =
		"/portal/json_service";

	private static final String _PATH_PORTAL_LAYOUT = "/portal/layout";

	private static final String _PATH_PORTAL_LICENSE = "/portal/license";

	private static final String _PATH_PORTAL_LOGIN = "/portal/login";

	private static final String _PATH_PORTAL_LOGOUT = "/portal/logout";

	private static final String _PATH_PORTAL_PROTECTED = "/portal/protected";

	private static final String _PATH_PORTAL_RENDER_PORTLET =
		"/portal/render_portlet";

	private static final String _PATH_PORTAL_RESILIENCY = "/portal/resiliency";

	private static final String _PATH_PORTAL_SETUP_WIZARD =
		"/portal/setup_wizard";

	private static final String _PATH_PORTAL_STATUS = "/portal/status";

	private static final String _PATH_PORTAL_TCK = "/portal/tck";

	private static final String _PATH_PORTAL_TERMS_OF_USE =
		"/portal/terms_of_use";

	private static final String _PATH_PORTAL_UPDATE_EMAIL_ADDRESS =
		"/portal/update_email_address";

	private static final String _PATH_PORTAL_UPDATE_LANGUAGE =
		"/portal/update_language";

	private static final String _PATH_PORTAL_UPDATE_PASSWORD =
		"/portal/update_password";

	private static final String _PATH_PORTAL_UPDATE_REMINDER_QUERY =
		"/portal/update_reminder_query";

	private static final String _PATH_PORTAL_UPDATE_TERMS_OF_USE =
		"/portal/update_terms_of_use";

	private static final String _PATH_PORTAL_VERIFY_EMAIL_ADDRESS =
		"/portal/verify_email_address";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalRequestProcessor.class);

	private final Map<String, Definition> _definitions;
	private final Set<String> _lastPaths;
	private final ModuleConfig _moduleConfig;
	private final Set<String> _publicPaths;
	private final ServletContext _servletContext;
	private final Set<String> _trackerIgnorePaths;

}