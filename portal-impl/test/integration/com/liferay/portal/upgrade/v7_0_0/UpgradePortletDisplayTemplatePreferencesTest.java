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
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.dynamicdatamapping.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;

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
public class UpgradePortletDisplayTemplatePreferencesTest
	extends UpgradePortletDisplayTemplatePreferences {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testUpgrade() throws Exception {
		_group = GroupTestUtil.addGroup();

		long[] classNameIds = TemplateHandlerRegistryUtil.getClassNameIds();
		long resourceClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.display.template.PortletDisplayTemplate");

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), classNameIds[0], 0, resourceClassNameId);

		_layout = LayoutTestUtil.addLayout(_group);

		setPortletDisplayStyle(
			"portlet1", DISPLAY_STYLE_PREFIX_6_2 + ddmTemplate.getUuid());
		setPortletDisplayStyle("portlet2", "testDisplayStyle");

		upgrade();

		CacheRegistryUtil.clear();

		_layout = LayoutLocalServiceUtil.getLayout(_layout.getPlid());

		Assert.assertEquals(
			PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX +
				ddmTemplate.getTemplateKey(),
			getPortletDisplayStyle("portlet1"));
		Assert.assertEquals(
			"testDisplayStyle", getPortletDisplayStyle("portlet2"));
	}

	protected String getPortletDisplayStyle(String portletId) throws Exception {
		PortletPreferences portletPreferences =
			LayoutTestUtil.getPortletPreferences(_layout, portletId);

		return portletPreferences.getValue("displayStyle", StringPool.BLANK);
	}

	protected void setPortletDisplayStyle(String portletId, String displayStyle)
		throws Exception {

		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put("displayStyle", displayStyle);
		portletPreferencesMap.put(
			"displayStyleGroupId", String.valueOf(_group.getGroupId()));

		LayoutTestUtil.updateLayoutPortletPreferences(
			_layout, portletId, portletPreferencesMap);
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

}