/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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