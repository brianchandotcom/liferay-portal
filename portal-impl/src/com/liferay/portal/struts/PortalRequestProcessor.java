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

import com.liferay.portal.LayoutPermissionException;
import com.liferay.portal.PortletActiveException;
import com.liferay.portal.UserActiveException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.security.auth.InterruptedPortletRequestWhitelistUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.UserTrackerPathUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.portlet.RenderResponseImpl;

import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.util.MessageResources;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Wesley Gong
 * @author Mika Koivisto
 */
public class PortalRequestProcessor extends TilesRequestProcessor {

	public PortalRequestProcessor() {

		// auth.forward.last.path.

		_lastPaths = new HashSet<>();

		_lastPaths.add(_PATH_PORTAL_LAYOUT);

		addPaths(_lastPaths, PropsKeys.AUTH_FORWARD_LAST_PATHS);

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

		_trackerIgnorePaths = new HashSet<>();

		addPaths(_trackerIgnorePaths, PropsKeys.SESSION_TRACKER_IGNORE_PATHS);
	}

	@Override
	public void process(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String path = super.processPath(request, response);

		ActionMapping actionMapping =
			(ActionMapping)moduleConfig.findActionConfig(path);

		Action action = StrutsActionRegistryUtil.getAction(path);

		if ((actionMapping == null) && (action == null)) {
			String lastPath = getLastPath(request);

			if (_log.isDebugEnabled()) {
				_log.debug("Last path " + lastPath);
			}

			response.sendRedirect(lastPath);

			return;
		}

		super.process(request, response);

		try {
			if (isPortletPath(path)) {
				cleanUp(request);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void addPaths(Set<String> paths, String propsKey) {
		String[] pathsArray = PropsUtil.getArray(propsKey);

		for (String path : pathsArray) {
			paths.add(path);
		}
	}

	protected void callParentDoForward(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		super.doForward(uri, request, response);
	}

	protected HttpServletRequest callParentProcessMultipart(
		HttpServletRequest request) {

		return super.processMultipart(request);
	}

	protected String callParentProcessPath(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		return super.processPath(request, response);
	}

	protected boolean callParentProcessRoles(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping)
		throws IOException, ServletException {

		return super.processRoles(request, response, actionMapping);
	}

	protected void cleanUp(HttpServletRequest request) throws Exception {

		// Clean up portlet objects that may have been created by defineObjects
		// for portlets that are called directly from a Struts path

		RenderRequestImpl renderRequestImpl =
			(RenderRequestImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (renderRequestImpl != null) {
			renderRequestImpl.cleanUp();
		}
	}

	protected void defineObjects(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		String portletId = portlet.getPortletId();

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portletId);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getStrictPreferences(
				portletPreferencesIds);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		RenderRequestImpl renderRequestImpl = RenderRequestFactory.create(
			request, portlet, invokerPortlet, portletContext,
			WindowState.MAXIMIZED, PortletMode.VIEW, portletPreferences);

		RenderResponseImpl renderResponseImpl = RenderResponseFactory.create(
			renderRequestImpl, response, portletId, portlet.getCompanyId());

		renderRequestImpl.defineObjects(portletConfig, renderResponseImpl);

		request.setAttribute(WebKeys.PORTLET_STRUTS_EXECUTE, Boolean.TRUE);
	}

	@Override
	protected void doForward(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws ServletException {

		StrutsUtil.forward(uri, getServletContext(), request, response);
	}

	@Override
	protected void doInclude(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws ServletException {

		StrutsUtil.include(uri, getServletContext(), request, response);
	}

	protected String getFriendlyTrackerPath(
			String path, ThemeDisplay themeDisplay, HttpServletRequest request)
		throws Exception {

		if (!path.equals(_PATH_PORTAL_LAYOUT)) {
			return null;
		}

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid == 0) {
			return null;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		String layoutFriendlyURL = PortalUtil.getLayoutFriendlyURL(
			layout, themeDisplay);

		String portletId = ParamUtil.getString(request, "p_p_id");

		if (Validator.isNull(portletId)) {
			return layoutFriendlyURL;
		}

		long companyId = PortalUtil.getCompanyId(request);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		if (portlet == null) {
			String strutsPath = path.substring(
				1, path.lastIndexOf(CharPool.SLASH));

			portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
				companyId, strutsPath);
		}

		if ((portlet == null) || !portlet.isActive()) {
			return layoutFriendlyURL.concat(StringPool.QUESTION).concat(
				request.getQueryString());
		}

		String namespace = PortalUtil.getPortletNamespace(portletId);

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		if (friendlyURLMapper == null) {
			return layoutFriendlyURL.concat(StringPool.QUESTION).concat(
				request.getQueryString());
		}

		PortletURLImpl portletURL = new PortletURLImpl(
			request, portletId, plid, PortletRequest.RENDER_PHASE);

		Map<String, String[]> parameterMap = request.getParameterMap();

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
		else {
			return layoutFriendlyURL.concat(StringPool.QUESTION).concat(
				request.getQueryString());
		}
	}

	protected String getLastPath(HttpServletRequest request) {
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean httpsInitial = (Boolean)session.getAttribute(
			WebKeys.HTTPS_INITIAL);

		String portalURL = null;

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!PropsValues.SESSION_ENABLE_PHISHING_PROTECTION &&
			(httpsInitial != null) && !httpsInitial.booleanValue()) {

			portalURL = PortalUtil.getPortalURL(request, false);
		}
		else {
			portalURL = PortalUtil.getPortalURL(request);
		}

		StringBundler sb = new StringBundler(7);

		sb.append(portalURL);
		sb.append(themeDisplay.getPathMain());
		sb.append(_PATH_PORTAL_LAYOUT);

		if (!PropsValues.AUTH_FORWARD_BY_LAST_PATH) {
			if (request.getRemoteUser() != null) {

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

		if (lastPath.getContextPath().equals(themeDisplay.getPathMain())) {
			ActionMapping actionMapping =
				(ActionMapping)moduleConfig.findActionConfig(
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

	protected boolean isPortletPath(String path) {
		if ((path != null) && !path.equals(_PATH_C) &&
			!path.startsWith(_PATH_COMMON) &&
			!path.contains(_PATH_J_SECURITY_CHECK) &&
			!path.startsWith(_PATH_PORTAL)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isPublicPath(String path) {
		if ((path != null) &&
			(_publicPaths.contains(path) || path.startsWith(_PATH_COMMON) ||
			 AuthPublicPathRegistry.contains(path))) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected Action processActionCreate(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping)
		throws IOException {

		ActionAdapter actionAdapter =
			(ActionAdapter)StrutsActionRegistryUtil.getAction(
				actionMapping.getPath());

		if (actionAdapter != null) {
			ActionConfig actionConfig = moduleConfig.findActionConfig(
				actionMapping.getPath());

			if (actionConfig != null) {
				Action originalAction = super.processActionCreate(
					request, response, actionMapping);

				actionAdapter.setOriginalAction(originalAction);
			}

			return actionAdapter;
		}

		return super.processActionCreate(request, response, actionMapping);
	}

	@Override
	protected ActionMapping processMapping(
			HttpServletRequest request, HttpServletResponse response,
			String path)
		throws IOException {

		if (path == null) {
			return null;
		}

		Action action = StrutsActionRegistryUtil.getAction(path);

		if (action != null) {
			ActionMapping actionMapping =
				(ActionMapping)moduleConfig.findActionConfig(path);

			if (actionMapping == null) {
				actionMapping = new ActionMapping();

				actionMapping.setModuleConfig(moduleConfig);
				actionMapping.setPath(path);

				request.setAttribute(Globals.MAPPING_KEY, actionMapping);
			}

			return actionMapping;
		}

		ActionMapping actionMapping = super.processMapping(
			request, response, path);

		if (actionMapping == null) {
			MessageResources messageResources = getInternal();

			String msg = messageResources.getMessage("processInvalid");

			_log.error("User ID " + request.getRemoteUser());
			_log.error("Current URL " + PortalUtil.getCurrentURL(request));
			_log.error("Referer " + request.getHeader("Referer"));
			_log.error("Remote address " + request.getRemoteAddr());

			_log.error(msg + " " + path);
		}

		return actionMapping;
	}

	@Override
	protected HttpServletRequest processMultipart(HttpServletRequest request) {

		// Disable Struts from automatically wrapping a multipart request

		return request;
	}

	@Override
	protected String processPath(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String path = GetterUtil.getString(
			super.processPath(request, response));

		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
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
					fullPath = getFriendlyTrackerPath(
						path, themeDisplay, request);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			String fullPathWithoutQueryString = fullPath;

			if (Validator.isNull(fullPath)) {
				String queryString = request.getQueryString();

				fullPathWithoutQueryString = path;

				if (Validator.isNotNull(queryString)) {
					fullPath = path.concat(StringPool.QUESTION).concat(
						queryString);
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

		String remoteUser = request.getRemoteUser();

		User user = null;

		try {
			user = PortalUtil.getUser(request);
		}
		catch (Exception e) {
		}

		// Last path

		if (_lastPaths.contains(path) && !_trackerIgnorePaths.contains(path)) {
			boolean saveLastPath = ParamUtil.getBoolean(
				request, "saveLastPath", true);

			if (themeDisplay.isLifecycleResource() ||
				themeDisplay.isStateExclusive() ||
				themeDisplay.isStatePopUp() ||
				!StringUtil.equalsIgnoreCase(
					request.getMethod(), HttpMethods.GET)) {

				saveLastPath = false;
			}

			// Save last path

			if (saveLastPath) {

				// Was a last path set by another servlet that dispatched to the
				// MainServlet? If so, use that last path instead.

				LastPath lastPath = (LastPath)request.getAttribute(
					WebKeys.LAST_PATH);

				if (lastPath == null) {
					lastPath = new LastPath(
						themeDisplay.getPathMain(), path,
						HttpUtil.parameterMapToString(
							request.getParameterMap()));
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

		// Authenticated users can always log out

		if (((remoteUser != null) || (user != null)) &&
			path.equals(_PATH_PORTAL_LOGOUT)) {

			return path;
		}

		// Authenticated users can always extend or confirm their session

		if (((remoteUser != null) || (user != null)) &&
			(path.equals(_PATH_PORTAL_EXPIRE_SESSION) ||
			 path.equals(_PATH_PORTAL_EXTEND_SESSION))) {

			return path;
		}

		// Authenticated users can always agree to terms of use

		if (((remoteUser != null) || (user != null)) &&
			path.equals(_PATH_PORTAL_UPDATE_TERMS_OF_USE)) {

			return path;
		}

		// Authenticated users must still exist in the system

		if ((remoteUser != null) && (user == null)) {
			return _PATH_PORTAL_LOGOUT;
		}

		// Authenticated users must be active

		if ((user != null) && !user.isActive()) {
			SessionErrors.add(request, UserActiveException.class.getName());

			return _PATH_PORTAL_ERROR;
		}

		long companyId = PortalUtil.getCompanyId(request);
		String portletId = ParamUtil.getString(request, "p_p_id");

		if (!path.equals(_PATH_PORTAL_JSON_SERVICE) &&
			!path.equals(_PATH_PORTAL_RENDER_PORTLET) &&
			!ParamUtil.getBoolean(request, "wsrp") &&
			!themeDisplay.isImpersonated() &&
			!InterruptedPortletRequestWhitelistUtil.
				isPortletInvocationWhitelisted(
					companyId, portletId,
					PortalUtil.getStrutsAction(request))) {

			// Authenticated users should agree to Terms of Use

			if ((user != null) && !user.isTermsOfUseComplete()) {
				return _PATH_PORTAL_TERMS_OF_USE;
			}

			// Authenticated users should have a verified email address

			if ((user != null) && !user.isEmailAddressVerificationComplete()) {
				if (path.equals(_PATH_PORTAL_UPDATE_EMAIL_ADDRESS)) {
					return _PATH_PORTAL_UPDATE_EMAIL_ADDRESS;
				}

				return _PATH_PORTAL_VERIFY_EMAIL_ADDRESS;
			}

			// Authenticated users must have a current password

			if ((user != null) && user.isPasswordReset()) {
				try {
					PasswordPolicy passwordPolicy = user.getPasswordPolicy();

					if ((passwordPolicy == null) ||
						passwordPolicy.isChangeRequired()) {

						return _PATH_PORTAL_UPDATE_PASSWORD;
					}
				}
				catch (Exception e) {
					_log.error(e, e);

					return _PATH_PORTAL_UPDATE_PASSWORD;
				}
			}
			else if ((user != null) && !user.isPasswordReset() &&
					 path.equals(_PATH_PORTAL_UPDATE_PASSWORD)) {

				return null;
			}

			// Authenticated users must have an email address

			if ((user != null) && !user.isEmailAddressComplete()) {
				return _PATH_PORTAL_UPDATE_EMAIL_ADDRESS;
			}

			// Authenticated users should have a reminder query

			if ((user != null) && !user.isDefaultUser() &&
				!user.isReminderQueryComplete()) {

				return _PATH_PORTAL_UPDATE_REMINDER_QUERY;
			}
		}

		// Users must sign in

		if (!isPublicPath(path)) {
			if (user == null) {
				SessionErrors.add(request, PrincipalException.class.getName());

				return _PATH_PORTAL_LOGIN;
			}
		}

		ActionMapping actionMapping =
			(ActionMapping)moduleConfig.findActionConfig(path);

		if (actionMapping == null) {
			Action strutsAction = StrutsActionRegistryUtil.getAction(path);

			if (strutsAction == null) {
				return null;
			}
		}
		else {
			path = actionMapping.getPath();
		}

		// Define the portlet objects

		if (isPortletPath(path)) {
			try {
				Portlet portlet = null;

				if (Validator.isNotNull(portletId)) {
					portlet = PortletLocalServiceUtil.getPortletById(
						companyId, portletId);
				}

				if (portlet == null) {
					String strutsPath = path.substring(
						1, path.lastIndexOf(CharPool.SLASH));

					portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
						companyId, strutsPath);
				}

				if ((portlet != null) && portlet.isActive()) {
					defineObjects(request, response, portlet);
				}
			}
			catch (Exception e) {
				request.setAttribute(PageContext.EXCEPTION, e);

				path = _PATH_COMMON_ERROR;
			}
		}

		// Authenticated users must have access to at least one layout

		if (SessionErrors.contains(
				request, LayoutPermissionException.class.getName())) {

			return _PATH_PORTAL_ERROR;
		}

		return path;
	}

	@Override
	protected void processPopulate(
			HttpServletRequest request, HttpServletResponse response,
			ActionForm actionForm, ActionMapping actionMapping)
		throws ServletException {

		if (actionForm == null) {
			return;
		}

		boolean hasIgnoredParameter = false;

		Map<String, String[]> oldParameterMap = request.getParameterMap();

		Map<String, String[]> newParameterMap = new LinkedHashMap<>(
			oldParameterMap.size());

		for (Map.Entry<String, String[]> entry : oldParameterMap.entrySet()) {
			String name = entry.getKey();

			Matcher matcher = _strutsPortletIgnoredParamtersPattern.matcher(
				name);

			if (matcher.matches()) {
				hasIgnoredParameter = true;
			}
			else {
				newParameterMap.put(name, entry.getValue());
			}
		}

		if (hasIgnoredParameter) {
			request = new DynamicServletRequest(
				request, newParameterMap, false);
		}

		super.processPopulate(request, response, actionForm, actionMapping);
	}

	@Override
	protected boolean processRoles(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping)
		throws IOException, ServletException {

		String path = actionMapping.getPath();

		if (isPublicPath(path)) {
			return true;
		}

		boolean authorized = true;

		User user = null;

		try {
			user = PortalUtil.getUser(request);
		}
		catch (Exception e) {
		}

		if ((user != null) && isPortletPath(path)) {
			try {

				// Authenticated users can always log out

				if (path.equals(_PATH_PORTAL_LOGOUT)) {
					return true;
				}

				Portlet portlet = null;

				String portletId = ParamUtil.getString(request, "p_p_id");

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
						(ThemeDisplay)request.getAttribute(
							WebKeys.THEME_DISPLAY);

					Layout layout = themeDisplay.getLayout();
					PermissionChecker permissionChecker =
						themeDisplay.getPermissionChecker();

					if (!PortletPermissionUtil.contains(
							permissionChecker, layout, portlet,
							ActionKeys.VIEW)) {

						throw new PrincipalException.MustHavePermission(
							permissionChecker, Portlet.class.getName(),
							portlet.getPortletId(), ActionKeys.VIEW);
					}
				}
				else if ((portlet != null) && !portlet.isActive()) {
					SessionErrors.add(
						request, PortletActiveException.class.getName());

					authorized = false;
				}
			}
			catch (Exception e) {
				SessionErrors.add(request, PrincipalException.class.getName());

				authorized = false;
			}
		}

		if (!authorized) {
			ForwardConfig forwardConfig = actionMapping.findForward(
				_PATH_PORTAL_ERROR);

			processForwardConfig(request, response, forwardConfig);

			return false;
		}
		else {
			return true;
		}
	}

	private static final String _PATH_C = "/c";

	private static final String _PATH_COMMON = "/common";

	private static final String _PATH_COMMON_ERROR = "/common/error";

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

	private static final Pattern _strutsPortletIgnoredParamtersPattern =
		Pattern.compile(PropsValues.STRUTS_PORTLET_IGNORED_PARAMETERS_REGEXP);

	private final Set<String> _lastPaths;
	private final Set<String> _publicPaths;
	private final Set<String> _trackerIgnorePaths;

}