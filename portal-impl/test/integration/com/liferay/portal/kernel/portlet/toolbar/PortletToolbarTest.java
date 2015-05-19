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

package com.liferay.portal.kernel.portlet.toolbar;

import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.security.Principal;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class PortletToolbarTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.portlettoolbar"));

	@Test
	public void testGetPortletTitleMenus() {
		PortletToolbar portletToolbar = new PortletToolbar();

		String portletId = "PortletToolbarTest";

		TestPortletRequest testPortletRequest = new TestPortletRequest();

		List<Menu> portletTitleMenus = portletToolbar.getPortletTitleMenus(
			portletId, testPortletRequest);

		for (Menu menu : portletTitleMenus) {
			String label = menu.getLabel();

			if ((label != null) && label.equals("PortletToolbarTest")) {
				return;
			}
		}

		Assert.fail(
			"Unable to retrieve menu with label = \"PortletToolbarTest\"");
	}

	private class TestPortletRequest implements PortletRequest {

		@Override
		public Object getAttribute(String arg0) {
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
		public String getParameter(String arg0) {
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
		public String[] getParameterValues(String arg0) {
			return null;
		}

		@Override
		public PortalContext getPortalContext() {
			return null;
		}

		@Override
		public PortletMode getPortletMode() {
			return null;
		}

		@Override
		public PortletSession getPortletSession() {
			return null;
		}

		@Override
		public PortletSession getPortletSession(boolean arg0) {
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
		public Enumeration<String> getProperties(String arg0) {
			return null;
		}

		@Override
		public String getProperty(String arg0) {
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
		public boolean isPortletModeAllowed(PortletMode arg0) {
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
		public boolean isUserInRole(String arg0) {
			return false;
		}

		@Override
		public boolean isWindowStateAllowed(WindowState arg0) {
			return false;
		}

		@Override
		public void removeAttribute(String arg0) {
		}

		@Override
		public void setAttribute(String arg0, Object arg1) {
		}

	}

}