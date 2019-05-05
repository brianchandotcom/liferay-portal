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

import com.liferay.portal.kernel.portlet.toolbar.contributor.locator.PortletToolbarContributorLocator;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Leon Chi
 */
public class PortletToolbarTest {

	@Test
	public void testGetPortletTitleMenus() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PropsUtil.setProps(new PropsImpl());

		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		Menu testMenu = new Menu();

		ServiceRegistration<PortletToolbarContributorLocator>
			serviceRegistration = registry.registerService(
				PortletToolbarContributorLocator.class,
				(portletId, portletRequest) -> Collections.singletonList(
					(portletRequest1, portletResponse) ->
						Collections.singletonList(testMenu)));

		PortletToolbar portletToolbar = new PortletToolbar();

		PortletRequest portletRequest = new MockPortletRequest();

		portletRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			new MockHttpServletRequest());

		List<Menu> menus = portletToolbar.getPortletTitleMenus(
			RandomTestUtil.randomString(), portletRequest,
			ProxyFactory.newDummyInstance(PortletResponse.class));

		Assert.assertTrue(
			"Unable to find " + testMenu,
			menus.removeIf(menu -> testMenu == menu));

		serviceRegistration.unregister();
	}

}