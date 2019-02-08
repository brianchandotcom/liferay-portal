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

import com.liferay.portal.kernel.portlet.toolbar.bundle.portlettoolbar.TestPortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.locator.PortletToolbarContributorLocator;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

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
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.portlettoolbar"));

	@Test
	public void testGetPortletTitleMenus() {
		PortletToolbar portletToolbar = new PortletToolbar();

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.registerDependencies(
			PortletToolbarContributorLocator.class);

		serviceDependencyManager.waitForDependencies(1000);

		PortletRequest portletRequest = ProxyFactory.newDummyInstance(
			PortletRequest.class);

		portletRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			ProxyFactory.newDummyInstance(HttpServletRequest.class));

		List<Menu> menus = portletToolbar.getPortletTitleMenus(
			RandomTestUtil.randomString(), portletRequest,
			ProxyFactory.newDummyInstance(PortletResponse.class));

		Assert.assertTrue(
			"Unable to retrieve menu with label " +
				TestPortletToolbarContributor.LABEL,
			menus.removeIf(
				menu -> TestPortletToolbarContributor.LABEL.equals(
					menu.getLabel())));
	}

}