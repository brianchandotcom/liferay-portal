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

package com.liferay.layout.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.sites.kernel.util.Sites;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class LayoutServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpdateLayoutFriendlyURLMap() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		long userId = layout.getUserId();

		layout.setUserId(-1);

		LayoutLocalServiceUtil.updateLayout(layout);

		Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

		friendlyURLMap.put(
			LocaleUtil.GERMANY,
			StringPool.SLASH + RandomTestUtil.randomString());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		LayoutLocalServiceUtil.updateLayout(
			_group.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getParentLayoutId(), layout.getNameMap(),
			layout.getTitleMap(), layout.getDescriptionMap(),
			layout.getKeywordsMap(), layout.getRobotsMap(), layout.getType(),
			layout.isHidden(), friendlyURLMap, layout.getIconImage(), null,
			serviceContext);
	}

	@Test
	public void testUpdateLayoutLookAndFeel() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		layout = LayoutLocalServiceUtil.updateLookAndFeel(
			_group.getGroupId(), false, layout.getLayoutId(),
			"test_WAR_testtheme", "01", StringPool.BLANK);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			layout.getUserId(), "1_column", false);

		LayoutLocalServiceUtil.updateLayout(layout);
	}

	@Test
	public void testUpdateLayoutPrototypeVerifyTypeSettings() throws Exception {
		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			RandomTestUtil.randomString());

		Layout layout = layoutPrototype.getLayout();

		LayoutLocalServiceUtil.updateLayout(layout);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(layout.getUserId());

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getParentLayoutId(), layout.getNameMap(),
			layout.getTitleMap(), layout.getDescriptionMap(),
			layout.getKeywordsMap(), layout.getRobotsMap(), layout.getType(),
			layout.isHidden(), layout.getFriendlyURLMap(),
			layout.getIconImage(), null, serviceContext);

		Layout updatedLayout = LayoutLocalServiceUtil.getLayout(
			layout.getPlid());

		UnicodeProperties typeSettingsProperties =
			updatedLayout.getTypeSettingsProperties();

		Assert.assertFalse(
			"Updating layout prototype should not add " +
				Sites.LAYOUT_UPDATEABLE + " property",
			typeSettingsProperties.containsKey(Sites.LAYOUT_UPDATEABLE));
	}

	@DeleteAfterTestRun
	private Group _group;

}