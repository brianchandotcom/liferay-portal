/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class AnalyticsReportsContentDashboardItemActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreation() {
		String url = RandomTestUtil.randomString();

		AnalyticsReportsContentDashboardItemAction
			analyticsReportsContentDashboardItemAction =
				new AnalyticsReportsContentDashboardItemAction(url);

		Assert.assertEquals(
			"viewMetrics",
			analyticsReportsContentDashboardItemAction.getName());
		Assert.assertEquals(
			url, analyticsReportsContentDashboardItemAction.getURL());
		Assert.assertEquals(
			ContentDashboardItemAction.Type.VIEW_IN_PANEL,
			analyticsReportsContentDashboardItemAction.getType());
	}

}