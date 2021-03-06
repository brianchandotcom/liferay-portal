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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

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
		String label = RandomTestUtil.randomString();
		String url = RandomTestUtil.randomString();

		ResourceBundle resourceBundle = new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(
					Collections.singletonList("view-metrics"));
			}

			@Override
			protected Object handleGetObject(String key) {
				return label;
			}

		};

		AnalyticsReportsContentDashboardItemAction
			analyticsReportsContentDashboardItemAction =
				new AnalyticsReportsContentDashboardItemAction(
					locale -> resourceBundle, url);

		Assert.assertEquals(
			label,
			analyticsReportsContentDashboardItemAction.getLabel(LocaleUtil.US));
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