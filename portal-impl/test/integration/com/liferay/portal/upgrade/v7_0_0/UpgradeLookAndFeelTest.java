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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
public class UpgradeLookAndFeelTest extends UpgradeLookAndFeel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testUpgrade() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);

		setPortletShowBorders("portlet1", true);
		setPortletShowBorders("portlet2", false);

		doUpgrade();

		CacheRegistryUtil.clear();

		_layout = LayoutLocalServiceUtil.getLayout(_layout.getPlid());

		Assert.assertEquals(
			StringPool.BLANK, getPortletDecoratorId("portlet1"));
		Assert.assertEquals("borderless", getPortletDecoratorId("portlet2"));
	}

	protected String getPortletDecoratorId(String portletId) throws Exception {
		PortletPreferences portletPreferences =
			LayoutTestUtil.getPortletPreferences(_layout, portletId);

		return portletPreferences.getValue(
			"portletSetupPortletDecoratorId", StringPool.BLANK);
	}

	protected void setPortletShowBorders(String portletId, boolean showBorders)
		throws Exception {

		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put(
			"portletSetupShowBorders", String.valueOf(showBorders));

		LayoutTestUtil.updateLayoutPortletPreferences(
			_layout, portletId, portletPreferencesMap);
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

}