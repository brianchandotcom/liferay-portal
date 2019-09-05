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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.TempAttributesServletRequest;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.LayoutTypeAccessPolicyTracker;
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
	public void preparePortlet(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(httpServletRequest, portlet);
	}

	@Override
	public ActionResult processAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		try {
			HttpServletRequest ownerLayoutHttpServletRequest =
				getOwnerLayoutRequestWrapper(httpServletRequest, portlet);

			checkAction(ownerLayoutHttpServletRequest, portlet);

			return _portletContainer.processAction(
				httpServletRequest, httpServletResponse, portlet);
		}
		catch (PrincipalException pe) {
			return processActionException(
				httpServletRequest, httpServletResponse, portlet, pe);
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet,
			Layout layout, Event event)
		throws PortletContainerException {

		return _portletContainer.processEvent(
			httpServletRequest, httpServletResponse, portlet, layout, event);
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout) {

		_portletContainer.processPublicRenderParameters(
			httpServletRequest, layout);
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {

		_portletContainer.processPublicRenderParameters(
			httpServletRequest, layout, portlet);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		try {
			checkRender(httpServletRequest, portlet);

			_portletContainer.render(
				httpServletRequest, httpServletResponse, portlet);
		}
		catch (PrincipalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			processRenderException(
				httpServletRequest, httpServletResponse, portlet);
		}
		catch (PortletContainerException pce) {
			throw pce;
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	@Override
	public void renderHeaders(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		try {
			checkRender(httpServletRequest, portlet);

			_portletContainer.renderHeaders(
				httpServletRequest, httpServletResponse, portlet);
		}
		catch (PrincipalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			processRenderException(
				httpServletRequest, httpServletResponse, portlet);
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		try {
			HttpServletRequest ownerLayoutHttpServletRequest =
				getOwnerLayoutRequestWrapper(httpServletRequest, portlet);

			checkResource(ownerLayoutHttpServletRequest, portlet);

			_portletContainer.serveResource(
				httpServletRequest, httpServletResponse, portlet);
		}
		catch (PrincipalException pe) {
			processServeResourceException(
				httpServletRequest, httpServletResponse, portlet, pe);
		}
		catch (PortletContainerException pce) {
			throw pce;
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	protected void check(HttpServletRequest httpServletRequest, Portlet portlet)
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

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

		LayoutTypeAccessPolicy layoutTypeAccessPolicy =
			LayoutTypeAccessPolicyTracker.getLayoutTypeAccessPolicy(layout);

		layoutTypeAccessPolicy.checkAccessAllowedToPortlet(
			httpServletRequest, layout, portlet);
	}

	protected void checkAction(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws Exception {

		checkCSRFProtection(httpServletRequest, portlet);

		check(httpServletRequest, portlet);
	}

	protected void checkCSRFProtection(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws PortalException {

		Map<String, String> initParams = portlet.getInitParams();

		boolean checkAuthToken = GetterUtil.getBoolean(
			initParams.get("check-auth-token"), true);

		if (AuthTokenWhitelistUtil.isPortletCSRFWhitelisted(
				httpServletRequest, portlet)) {

			checkAuthToken = false;
		}

		if (checkAuthToken) {
			AuthTokenUtil.checkCSRFToken(
				httpServletRequest,
				SecurityPortletContainerWrapper.class.getName());
		}
	}

	protected void checkRender(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws Exception {

		check(httpServletRequest, portlet);
	}

	protected void checkResource(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws Exception {

		check(httpServletRequest, portlet);
	}

	protected String getOriginalURL(HttpServletRequest httpServletRequest) {
		LastPath lastPath = (LastPath)httpServletRequest.getAttribute(
			WebKeys.LAST_PATH);

		if (lastPath == null) {
			return String.valueOf(httpServletRequest.getRequestURI());
		}

		String portalURL = PortalUtil.getPortalURL(httpServletRequest);

		return portalURL.concat(
			lastPath.getContextPath()
		).concat(
			lastPath.getPath()
		);
	}

	protected HttpServletRequest getOwnerLayoutRequestWrapper(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws Exception {

		if (!PropsValues.PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET ||
			PropsValues.PORTLET_CROSS_LAYOUT_INVOCATION_MODE.equals("render")) {

			return httpServletRequest;
		}

		Layout ownerLayout = null;
		LayoutTypePortlet ownerLayoutTypePortlet = null;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout requestLayout = (Layout)httpServletRequest.getAttribute(
			WebKeys.LAYOUT);

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
			return httpServletRequest;
		}

		Layout currentLayout = themeDisplay.getLayout();

		if (currentLayout.equals(ownerLayout)) {
			return httpServletRequest;
		}

		ThemeDisplay themeDisplayClone = (ThemeDisplay)themeDisplay.clone();

		themeDisplayClone.setLayout(ownerLayout);
		themeDisplayClone.setLayoutTypePortlet(ownerLayoutTypePortlet);

		TempAttributesServletRequest tempAttributesServletRequest =
			new TempAttributesServletRequest(httpServletRequest);

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

			if ((c == CharPool.DOLLAR) || (c == CharPool.POUND) ||
				(c == CharPool.UNDERLINE)) {

				continue;
			}

			return false;
		}

		return true;
	}

	protected ActionResult processActionException(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Portlet portlet,
		PrincipalException pe) {

		if (_log.isDebugEnabled()) {
			_log.debug(pe, pe);
		}

		if (_log.isWarnEnabled()) {
			String url = getOriginalURL(httpServletRequest);

			_log.warn(
				String.format(
					"User %s is not allowed to access URL %s and portlet %s: " +
						"%s",
					PortalUtil.getUserId(httpServletRequest), url,
					portlet.getPortletId(), pe.getMessage()));
		}

		httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

		return ActionResult.EMPTY_ACTION_RESULT;
	}

	protected void processRenderException(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Portlet portlet)
		throws PortletContainerException {

		String portletContent = null;

		if (portlet.isShowPortletAccessDenied()) {
			portletContent = "/html/portal/portlet_access_denied.jsp";
		}

		try {
			if (portletContent != null) {
				HttpServletRequest originalHttpServletRequest =
					PortalUtil.getOriginalServletRequest(httpServletRequest);

				RequestDispatcher requestDispatcher =
					originalHttpServletRequest.getRequestDispatcher(
						portletContent);

				requestDispatcher.include(
					httpServletRequest, httpServletResponse);
			}
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	protected void processServeResourceException(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Portlet portlet,
		PrincipalException pe) {

		if (_log.isDebugEnabled()) {
			_log.debug(pe, pe);
		}

		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);

		httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

		if (_log.isWarnEnabled()) {
			String url = getOriginalURL(httpServletRequest);

			_log.warn(
				String.format(
					"User %s is not allowed to serve resource for %s on %s: %s",
					PortalUtil.getUserId(httpServletRequest), url,
					portlet.getPortletId(), pe.getMessage()));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SecurityPortletContainerWrapper.class);

	private final PortletContainer _portletContainer;

}