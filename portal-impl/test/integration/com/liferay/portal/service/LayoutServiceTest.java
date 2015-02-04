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

package com.liferay.portal.service;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Akos Thurzo
 */
public class LayoutServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

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

		try {
			LayoutLocalServiceUtil.updateLayout(
				_group.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getParentLayoutId(),
				layout.getNameMap(), layout.getTitleMap(),
				layout.getDescriptionMap(), layout.getKeywordsMap(),
				layout.getRobotsMap(), layout.getType(), layout.isHidden(),
				friendlyURLMap, layout.getIconImage(), null, serviceContext);
		}
		catch (NoSuchUserException nsue) {
			Assert.fail(ExceptionUtils.getStackTrace(nsue));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}