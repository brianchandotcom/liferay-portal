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

package com.liferay.content.dashboard.web.internal.application.list.test;

import com.liferay.application.list.PanelApp;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class ContentDashboardAdminPanelAppTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetPortlet() {
		Portlet portlet = _panelApp.getPortlet();

		Assert.assertEquals(
			"com_liferay_content_dashboard_web_portlet_" +
				"ContentDashboardAdminPortlet",
			portlet.getPortletName());
	}

	@Test
	public void testGetPortletId() {
		Assert.assertEquals(
			"com_liferay_content_dashboard_web_portlet_" +
				"ContentDashboardAdminPortlet",
			_panelApp.getPortletId());
	}

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.web.internal.application.list.ContentDashboardAdminPanelApp"
	)
	private PanelApp _panelApp;

}