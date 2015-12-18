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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.TempAttributesServletRequest;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypeAccessPolicy;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.Map;

import javax.portlet.Event;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
@DoPrivileged
public class SecurityPortletContainerWrapper implements PortletContainer {

	public static PortletContainer createSecurityPortletContainerWrapper(
		PortletContainer portletContainer) {

		if (!SPIUtil.isSPI()) {
			portletContainer = new SecurityPortletContainerWrapper(
				portletContainer);
		}

		return portletContainer;
	}

	public SecurityPortletContainerWrapper(PortletContainer portletContainer) {
		_portletContainer = portletContainer;
	}

	@Override
	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(request, portlet);
	}

	@Override
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
		catch (PrincipalException pe) {
			return processActionException(request, response, portlet, pe);
		}
		catch (PortletContainerException pce) {
			throw pce;
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	@Override
	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		return _portletContainer.processEvent(
			request, response, portlet, layout, event);
	}

	@Override
	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			checkRender(request, portlet);

			_portletContainer.render(request, response, portlet);
		}
		catch (PrincipalException pe) {
			processRenderException(request, response, portlet);
		}
		catch (PortletContainerException pce) {
			throw pce;
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	@Override
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
		catch (PrincipalException pe) {
			processServeResourceException(request, response, portlet, pe);
		}
		catch (PortletContainerException pce) {
			throw pce;
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

		if (!isValidPortletId(portlet.getPortletId())) {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid portlet ID " + portlet.getPortletId());
			}

			throw new PrincipalException(
				"Invalid portlet ID " + portlet.getPortletId());
		}

		if (portlet.isUndeployedPortlet()) {
			return;
		}

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		LayoutType layoutType = layout.getLayoutType();

		LayoutTypeAccessPolicy layoutTypeAccessPolicy =
			layoutType.getLayoutTypeAccessPolicy();

		layoutTypeAccessPolicy.checkAccessAllowedToPortlet(
			request, layout, portlet);
	}

	protected void checkAction(HttpServletRequest request, Portlet portlet)
		throws Exception {

		checkCSRFProtection(request, portlet);

		check(request, portlet);
	}

	protected void checkCSRFProtection(
			HttpServletRequest request, Portlet portlet)
		throws PortalException {

		Map<String, String> initParams = portlet.getInitParams();

		boolean checkAuthToken = GetterUtil.getBoolean(
			initParams.get("check-auth-token"), true);

		if (AuthTokenWhitelistUtil.isPortletCSRFWhitelisted(request, portlet)) {
			checkAuthToken = false;
		}

		if (checkAuthToken) {
			AuthTokenUtil.checkCSRFToken(
				request, SecurityPortletContainerWrapper.class.getName());
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

	protected String getOriginalURL(HttpServletRequest request) {
		LastPath lastPath = (LastPath)request.getAttribute(WebKeys.LAST_PATH);

		if (lastPath == null) {
			return String.valueOf(request.getRequestURI());
		}

		String portalURL = PortalUtil.getPortalURL(request);

		return portalURL.concat(
			lastPath.getContextPath()).concat(lastPath.getPath());
	}

	protected HttpServletRequest getOwnerLayoutRequestWrapper(
			HttpServletRequest request, Portlet portlet)
		throws Exception {

		if (!PropsValues.PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET ||
			PropsValues.PORTLET_CROSS_LAYOUT_INVOCATION_MODE.equals("render")) {

			return request;
		}

		Layout ownerLayout = null;
		LayoutTypePortlet ownerLayoutTypePortlet = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout requestLayout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		List<LayoutTypePortlet> layoutTypePortlets =
			PortletContainerUtil.getLayoutTypePortlets(requestLayout);

		for (LayoutTypePortlet layoutTypePortlet : layoutTypePortlets) {
			if (layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
				ownerLayoutTypePortlet = layoutTypePortlet;

				ownerLayout = layoutTypePortlet.getLayout();

				break;
			}
		}

		if (ownerLayout == null) {
			return request;
		}

		Layout currentLayout = themeDisplay.getLayout();

		if (currentLayout.equals(ownerLayout)) {
			return request;
		}

		ThemeDisplay themeDisplayClone = (ThemeDisplay)themeDisplay.clone();

		themeDisplayClone.setLayout(ownerLayout);
		themeDisplayClone.setLayoutTypePortlet(ownerLayoutTypePortlet);

		TempAttributesServletRequest tempAttributesServletRequest =
			new TempAttributesServletRequest(request);

		tempAttributesServletRequest.setTempAttribute(
			WebKeys.LAYOUT, ownerLayout);
		tempAttributesServletRequest.setTempAttribute(
			WebKeys.THEME_DISPLAY, themeDisplayClone);

		return tempAttributesServletRequest;
	}

	protected boolean isValidPortletId(String portletId) {
		for (int i = 0; i < portletId.length(); i++) {
			char c = portletId.charAt(i);

			if ((c >= CharPool.LOWER_CASE_A) && (c <= CharPool.LOWER_CASE_Z)) {
				continue;
			}

			if ((c >= CharPool.UPPER_CASE_A) && (c <= CharPool.UPPER_CASE_Z)) {
				continue;
			}

			if ((c >= CharPool.NUMBER_0) && (c <= CharPool.NUMBER_9)) {
				continue;
			}

			if ((c == CharPool.POUND) || (c == CharPool.UNDERLINE)) {
				continue;
			}

			return false;
		}

		return true;
	}

	protected ActionResult processActionException(
		HttpServletRequest request, HttpServletResponse response,
		Portlet portlet, PrincipalException pe) {

		if (_log.isDebugEnabled()) {
			_log.debug(pe);
		}

		String url = getOriginalURL(request);

		if (_log.isWarnEnabled()) {
			_log.warn(
				String.format(
					"User %s is not allowed to access URL %s and portlet %s",
					PortalUtil.getUserId(request), url,
					portlet.getPortletId()));
		}

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
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	protected void processServeResourceException(
		HttpServletRequest request, HttpServletResponse response,
		Portlet portlet, PrincipalException pe) {

		if (_log.isDebugEnabled()) {
			_log.debug(pe);
		}

		String url = getOriginalURL(request);

		response.setHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);

		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Reject serveResource for " + url + " on " +
					portlet.getPortletId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SecurityPortletContainerWrapper.class);

	private final PortletContainer _portletContainer;

}