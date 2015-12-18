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
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.util.List;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

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

		List<Menu> menus = portletToolbar.getPortletTitleMenus(
			RandomTestUtil.randomString(), Mockito.mock(PortletRequest.class));

		for (Menu menu : menus) {
			String label = menu.getLabel();

			if ((label != null) &&
				label.equals(TestPortletToolbarContributor.LABEL)) {

				return;
			}
		}

		Assert.fail(
			"Unable to retrieve menu with label " +
				TestPortletToolbarContributor.LABEL);
	}

}