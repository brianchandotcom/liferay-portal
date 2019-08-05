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

package com.liferay.portal.kernel.servlet.util;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;

import java.security.Principal;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderParameters;
import javax.portlet.WindowState;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class MockLiferayPortletRequest implements LiferayPortletRequest {

	public MockLiferayPortletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	@Override
	public void cleanUp() {
	}

	@Override
	public Map<String, String[]> clearRenderParameters() {
		return null;
	}

	@Override
	public void defineObjects(
		PortletConfig portletConfig, PortletResponse portletResponse) {
	}

	@Override
	public Object getAttribute(String s) {
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}

	@Override
	public String getAuthType() {
		return null;
	}

	@Override
	public String getContextPath() {
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		return new Cookie[0];
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	@Override
	public String getLifecycle() {
		return null;
	}

	@Override
	public Locale getLocale() {
		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return null;
	}

	@Override
	public HttpServletRequest getOriginalHttpServletRequest() {
		return httpServletRequest;
	}

	@Override
	public String getParameter(String s) {
		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return null;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return null;
	}

	@Override
	public String[] getParameterValues(String s) {
		return new String[0];
	}

	@Override
	public long getPlid() {
		return 0;
	}

	@Override
	public PortalContext getPortalContext() {
		return null;
	}

	@Override
	public Portlet getPortlet() {
		return null;
	}

	@Override
	public PortletContext getPortletContext() {
		return null;
	}

	@Override
	public PortletMode getPortletMode() {
		return null;
	}

	@Override
	public String getPortletName() {
		return null;
	}

	@Override
	public HttpServletRequest getPortletRequestDispatcherRequest() {
		return null;
	}

	@Override
	public PortletSession getPortletSession() {
		return null;
	}

	@Override
	public PortletSession getPortletSession(boolean b) {
		return null;
	}

	@Override
	public PortletPreferences getPreferences() {
		return null;
	}

	@Override
	public Map<String, String[]> getPrivateParameterMap() {
		return null;
	}

	@Override
	public Enumeration<String> getProperties(String s) {
		return null;
	}

	@Override
	public String getProperty(String s) {
		return null;
	}

	@Override
	public Enumeration<String> getPropertyNames() {
		return null;
	}

	@Override
	public Map<String, String[]> getPublicParameterMap() {
		return null;
	}

	@Override
	public String getRemoteUser() {
		return null;
	}

	@Override
	public RenderParameters getRenderParameters() {
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		return null;
	}

	@Override
	public String getResponseContentType() {
		return null;
	}

	@Override
	public Enumeration<String> getResponseContentTypes() {
		return null;
	}

	@Override
	public String getScheme() {
		return null;
	}

	@Override
	public String getServerName() {
		return null;
	}

	@Override
	public int getServerPort() {
		return 0;
	}

	@Override
	public String getUserAgent() {
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		return null;
	}

	@Override
	public String getWindowID() {
		return null;
	}

	@Override
	public WindowState getWindowState() {
		return null;
	}

	@Override
	public void invalidateSession() {
	}

	@Override
	public boolean isPortletModeAllowed(PortletMode portletMode) {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return false;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public boolean isUserInRole(String s) {
		return false;
	}

	@Override
	public boolean isWindowStateAllowed(WindowState windowState) {
		return false;
	}

	@Override
	public void removeAttribute(String s) {
	}

	@Override
	public void setAttribute(String s, Object o) {
	}

	@Override
	public void setPortletRequestDispatcherRequest(
		HttpServletRequest httpServletRequest) {
	}

	protected HttpServletRequest httpServletRequest;

}