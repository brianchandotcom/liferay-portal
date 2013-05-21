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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerSecurity;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.TempAttributesServletRequest;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.Event;
import javax.portlet.PortletMode;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@DoPrivileged
public class PortletContainerSecurityImpl implements PortletContainer,
	PortletContainerSecurity {

	public PortletContainerSecurityImpl(PortletContainer portletContainer) {

		this._portletContainer = portletContainer;

		// Portlet add default resource check white list

		resetPortletAddDefaultResourceCheckWhitelist();
		resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return _portletAddDefaultResourceCheckWhitelist;
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelistActions() {
		return _portletAddDefaultResourceCheckWhitelistActions;
	}

	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(request, portlet);
	}

	public ActionResult processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			HttpServletRequest ownerLayoutRequest =
				getOwnerLayoutRequestWrapper(request, portlet);

			checkAction(ownerLayoutRequest, portlet);

			return _portletContainer.processAction(request, response, portlet);
		}
		catch (PrincipalException e) {
			return processActionException(request, response, portlet, e);
		}
		catch (PortletContainerException e) {
			throw e;
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		return _portletContainer.processEvent(
			request, response, portlet, layout, event);
	}

	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			checkRender(request, portlet);

			_portletContainer.render(request, response, portlet);
		}
		catch (PrincipalException e) {
			processRenderException(request, response, portlet);
		}
		catch (PortletContainerException e) {
			throw e;
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		_portletAddDefaultResourceCheckWhitelist = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);

		_portletAddDefaultResourceCheckWhitelist = Collections.unmodifiableSet(
			_portletAddDefaultResourceCheckWhitelist);

		return _portletAddDefaultResourceCheckWhitelist;
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelistActions() {
		_portletAddDefaultResourceCheckWhitelistActions = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS);

		_portletAddDefaultResourceCheckWhitelistActions =
			Collections.unmodifiableSet(
				_portletAddDefaultResourceCheckWhitelistActions);

		return _portletAddDefaultResourceCheckWhitelistActions;
	}

	public void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			HttpServletRequest ownerLayoutRequest =
				getOwnerLayoutRequestWrapper(request, portlet);

			checkResource(ownerLayoutRequest, portlet);

			_portletContainer.serveResource(request, response, portlet);
		}
		catch (PrincipalException e) {
			processServeResourceException(request, response, portlet, e);
		}
		catch (PortletContainerException e) {
			throw e;
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	protected void check(HttpServletRequest request, Portlet portlet)
		throws Exception {

		if (portlet == null) {
			return;
		}

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		if (layout.isTypeControlPanel()) {
			isAccessAllowedToControlPanelPortlet(request, portlet);
			return;
		}

		if (isAccessAllowedToLayoutPortlet(request, portlet)) {
			PortalUtil.addPortletDefaultResource(request, portlet);

			if (hasAccessPermission(request, portlet)) {
				return;
			}
		}

		throw new PrincipalException();
	}

	protected void checkAction(HttpServletRequest request, Portlet portlet)
		throws Exception {

		checkCSRFProtection(request, portlet);

		check(request, portlet);
	}

	protected void checkCSRFProtection(
			HttpServletRequest request, Portlet portlet)
		throws PortalException {

		if (!PropsValues.AUTH_TOKEN_CHECK_ENABLED) {
			return;
		}

		Map<String, String> initParams = portlet.getInitParams();

		boolean checkAuthToken = GetterUtil.getBoolean(
			initParams.get("check-auth-token"), true);

		if (checkAuthToken) {
			AuthTokenUtil.check(request);
		}
	}

	protected void checkRender(HttpServletRequest request, Portlet portlet)
		throws Exception {

		check(request, portlet);
	}

	protected void checkResource(HttpServletRequest request, Portlet portlet)
		throws Exception {

		check(request, portlet);
	}

	protected HttpServletRequest getOwnerLayoutRequestWrapper(
			HttpServletRequest request, Portlet portlet)
		throws Exception {

		if (!PropsValues.PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET ||
			PropsValues.PORTLET_CROSS_LAYOUT_INVOCATION_MODE.equals("render")) {

			return request;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

		Layout currentLayout = themeDisplay.getLayout();

		Layout requestLayout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		List<LayoutTypePortlet> layoutTypePortlets =
			PortletContainerUtil.getLayoutTypePortlets(requestLayout);

		Layout ownerLayout = null;
		LayoutTypePortlet ownerLayoutTypePortlet = null;

		for (LayoutTypePortlet layoutTypePortlet : layoutTypePortlets) {
			if (layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
				ownerLayoutTypePortlet = layoutTypePortlet;

				ownerLayout = layoutTypePortlet.getLayout();

				break;
			}
		}

		if ((ownerLayout != null) && !currentLayout.equals(ownerLayout)) {
			ThemeDisplay themeDisplayClone = (ThemeDisplay)themeDisplay.clone();

			themeDisplayClone.setLayout(ownerLayout);
			themeDisplayClone.setLayoutTypePortlet(ownerLayoutTypePortlet);

			TempAttributesServletRequest tempAttributesServletRequest =
				new TempAttributesServletRequest(request);

			tempAttributesServletRequest.setTempAttribute(
				WebKeys.THEME_DISPLAY, themeDisplayClone);
			tempAttributesServletRequest.setTempAttribute(
				WebKeys.LAYOUT, ownerLayout);

			return tempAttributesServletRequest;
		}

		return request;
	}

	protected boolean hasAccessPermission(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		PortletMode portletMode = PortletModeFactory.getPortletMode(
			ParamUtil.getString(request, "p_p_mode"));

		boolean access = PortletPermissionUtil.hasAccessPermission(
			permissionChecker, scopeGroupId, layout, portlet, portletMode);

		return access;
	}

	protected void isAccessAllowedToControlPanelPortlet(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		if (portlet.isSystem()) {
			return;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		if (PortletPermissionUtil.hasControlPanelAccessPermission(
				permissionChecker, scopeGroupId, portlet)) {

			return;
		}

		if (isAccessGrantedByRuntimePortlet(request, portlet)) {
			return;
		}

		if (isAccessGrantedByPPAuthToken(request, portlet)) {
			return;
		}

		throw new PrincipalException();
	}

	protected boolean isAccessAllowedToLayoutPortlet(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		if (isAccessGrantedByRuntimePortlet(request, portlet)) {
			return true;
		}

		if (isAccessGrantedByPortletOnPage(request, portlet)) {
			return true;
		}

		if (isLayoutConfigurationAllowed(request, portlet)) {
			return true;
		}

		if (isAccessGrantedByPPAuthToken(request, portlet)) {
			return true;
		}

		return false;
	}

	protected boolean isAccessGrantedByPortletOnPage(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		String portletId = portlet.getPortletId();

		if (layout.isTypePanel() &&
			isPanelSelectedPortlet(themeDisplay, portletId)) {

			return true;
		}

		if ((layoutTypePortlet != null) &&
			layoutTypePortlet.hasPortletId(portletId)) {

			return true;
		}

		return false;
	}

	protected boolean isAccessGrantedByPPAuthToken(
		HttpServletRequest request, Portlet portlet) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = portlet.getPortletId();

		if (!portlet.isAddDefaultResource()) {
			return false;
		}

		if (!PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED) {
			return true;
		}

		if (_portletAddDefaultResourceCheckWhitelist.contains(portletId)) {
			return true;
		}

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String strutsAction = ParamUtil.getString(
			request, namespace + "struts_action");

		if (Validator.isNull(strutsAction)) {
			strutsAction = ParamUtil.getString(request, "struts_action");
		}

		if (_portletAddDefaultResourceCheckWhitelistActions.contains(
				strutsAction)) {

			return true;
		}

		String requestPortletAuthenticationToken = ParamUtil.getString(
			request, "p_p_auth");

		if (Validator.isNull(requestPortletAuthenticationToken)) {
			HttpServletRequest originalRequest =
				PortalUtil.getOriginalServletRequest(request);

			requestPortletAuthenticationToken = ParamUtil.getString(
				originalRequest, "p_p_auth");
		}

		if (Validator.isNotNull(requestPortletAuthenticationToken)) {
			String actualPortletAuthenticationToken = AuthTokenUtil.getToken(
				request, themeDisplay.getPlid(), portletId);

			if (requestPortletAuthenticationToken.equals(
					actualPortletAuthenticationToken)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isAccessGrantedByRuntimePortlet(
		HttpServletRequest request, Portlet portlet) {

		Boolean renderPortletResource = (Boolean)request.getAttribute(
			WebKeys.RENDER_PORTLET_RESOURCE);

		if (renderPortletResource != null) {
			boolean runtimePortlet = renderPortletResource.booleanValue();

			if (runtimePortlet) {
				return true;
			}
		}

		return false;
	}

	protected boolean isLayoutConfigurationAllowed(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return false;
		}

		if (!portlet.getPortletId().equals(PortletKeys.LAYOUTS_ADMIN)) {
			return false;
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		Layout layout = themeDisplay.getLayout();
		Group group = layout.getGroup();

		if (group.isSite()) {
			if (LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.CUSTOMIZE) ||
				LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.UPDATE)) {

				return true;
			}
		}

		if (group.isCompany()) {
			if (permissionChecker.isCompanyAdmin()) {
				return true;
			}
		}
		else if (group.isLayoutPrototype()) {
			long layoutPrototypeId = group.getClassPK();

			if (LayoutPrototypePermissionUtil.contains(
					permissionChecker, layoutPrototypeId,
				ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isLayoutSetPrototype()) {
			long layoutSetPrototypeId = group.getClassPK();

			if (LayoutSetPrototypePermissionUtil.contains(
					permissionChecker, layoutSetPrototypeId,
				ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			if (OrganizationPermissionUtil.contains(
					permissionChecker, organizationId, ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isUserGroup()) {
			long scopeGroupId = themeDisplay.getScopeGroupId();

			if (GroupPermissionUtil.contains(
					permissionChecker, scopeGroupId, ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isUser()) {
			return true;
		}

		return false;
	}

	protected boolean isPanelSelectedPortlet(
		ThemeDisplay themeDisplay, String portletId) {

		Layout layout = themeDisplay.getLayout();

		String panelSelectedPortlets = layout.getTypeSettingsProperty(
			"panelSelectedPortlets");

		if (Validator.isNotNull(panelSelectedPortlets)) {
			String[] panelSelectedPortletsArray = StringUtil.split(
				panelSelectedPortlets);

			return ArrayUtil.contains(panelSelectedPortletsArray, portletId);
		}

		return false;
	}

	protected ActionResult processActionException(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, PrincipalException e)
		throws PortletContainerException {

		if (_log.isDebugEnabled()) {
			_log.debug(e);
		}

		String url = _getOriginalURL(request);

		_log.warn(
			"Reject processAction for " + url + " on " +
				portlet.getPortletId());

		return ActionResult.EMPTY_ACTION_RESULT;
	}

	protected void processRenderException(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		String portletContent = null;

		if (portlet.isShowPortletAccessDenied()) {
			portletContent = "/html/portal/portlet_access_denied.jsp";
		}

		try {
			if (portletContent != null) {
				RequestDispatcher requestDispatcher =
					request.getRequestDispatcher(portletContent);

				requestDispatcher.include(request, response);
			}
		}
		catch (Exception ex) {
			throw new PortletContainerException(ex);
		}
	}

	protected void processServeResourceException(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, PrincipalException e)
		throws PortletContainerException {

		if (_log.isDebugEnabled()) {
			_log.debug(e);
		}

		String url = _getOriginalURL(request);

		response.setHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);

		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

		_log.warn(
			"Reject serveResource for " + url + " on " +
				portlet.getPortletId());
	}

	private String _getOriginalURL(HttpServletRequest request) {
		LastPath lastPath = (LastPath)request.getAttribute(WebKeys.LAST_PATH);

		if (lastPath != null) {
			StringBundler sb = new StringBundler(3);

			sb.append(PortalUtil.getPortalURL(request));
			sb.append(lastPath.getContextPath());
			sb.append(lastPath.getPath());

			return sb.toString();
		}

		return String.valueOf(request.getRequestURI());
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletContainerSecurityImpl.class);

	private Set<String> _portletAddDefaultResourceCheckWhitelist;
	private Set<String> _portletAddDefaultResourceCheckWhitelistActions;
	private PortletContainer _portletContainer;

}