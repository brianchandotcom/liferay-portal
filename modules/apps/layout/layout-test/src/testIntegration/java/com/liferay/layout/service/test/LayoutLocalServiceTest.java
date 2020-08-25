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
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class LayoutLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		FriendlyURLEntryLocalServiceUtil.deleteGroupFriendlyURLEntries(
			_group.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(User.class));
		FriendlyURLEntryLocalServiceUtil.deleteGroupFriendlyURLEntries(
			_group.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(User.class));
	}

	@Test
	public void testLayoutsAreFoundBasedOnDoubleHttpEncodedLegacyFriendlyURL()
		throws Exception {

		String name = "café";

		String friendlyURL = HttpUtil.decodeURL(StringPool.SLASH + name);

		friendlyURL = HttpUtil.decodeURL(friendlyURL);

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), false,
			Collections.singletonMap(LocaleUtil.getDefault(), name),
			Collections.singletonMap(LocaleUtil.getDefault(), friendlyURL));

		Assert.assertEquals(
			layout,
			_layoutLocalService.getFriendlyURLLayout(
				_group.getGroupId(), false, friendlyURL));
	}

	@Test
	public void testLayoutsAreFoundBasedOnHttpEncodedFriendlyURL()
		throws Exception {

		String name = "café";

		String friendlyURL = HttpUtil.decodeURL(StringPool.SLASH + name);

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), false,
			Collections.singletonMap(LocaleUtil.getDefault(), name),
			Collections.singletonMap(LocaleUtil.getDefault(), null));

		Assert.assertEquals(
			layout,
			_layoutLocalService.getFriendlyURLLayout(
				_group.getGroupId(), false, friendlyURL));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

}