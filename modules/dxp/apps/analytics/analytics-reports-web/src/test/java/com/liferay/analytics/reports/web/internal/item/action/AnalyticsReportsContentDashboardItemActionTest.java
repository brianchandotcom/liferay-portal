/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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