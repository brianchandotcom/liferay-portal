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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
	public void testDeleteLayouts() throws Exception {
		_testDeleteLayouts(false);
		_testDeleteLayouts(true);
	}

	@Test
	public void testExistingLayoutCanHaveTheSameFriendlyURLAsDeletedOne()
		throws Exception {

		String friendlyURL1 = "/friendly-url-1";

		Layout layout1 = LayoutTestUtil.addLayout(
			_group.getGroupId(), true,
			Collections.singletonMap(LocaleUtil.getDefault(), "name"),
			Collections.singletonMap(LocaleUtil.getDefault(), friendlyURL1));

		String friendlyURL2 = "/friendly-url-2";

		Layout layout2 = LayoutTestUtil.addLayout(
			_group.getGroupId(), true,
			Collections.singletonMap(LocaleUtil.getDefault(), "name"),
			Collections.singletonMap(LocaleUtil.getDefault(), friendlyURL2));

		_layoutLocalService.deleteLayout(layout1);

		layout2 = _layoutLocalService.updateFriendlyURL(
			TestPropsValues.getUserId(), layout2.getPlid(), friendlyURL1,
			_group.getDefaultLanguageId());

		Assert.assertEquals(
			layout2,
			_layoutLocalService.fetchLayoutByFriendlyURL(
				_group.getGroupId(), true, friendlyURL1));
		Assert.assertEquals(
			layout2,
			_layoutLocalService.getFriendlyURLLayout(
				_group.getGroupId(), true, friendlyURL1));
	}

	@Test
	public void testKeepsAHistoryOfOldFriendlyURLs() throws Exception {
		String friendlyURL = "/friendly-url";

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), false,
			Collections.singletonMap(LocaleUtil.getDefault(), "name"),
			Collections.singletonMap(LocaleUtil.getDefault(), friendlyURL));

		for (int i = 0; i < 10; i++) {
			layout = _layoutLocalService.updateFriendlyURL(
				TestPropsValues.getUserId(), layout.getPlid(),
				"/friendly-url-" + i, _group.getDefaultLanguageId());
		}

		for (int i = 0; i < 10; i++) {
			Assert.assertEquals(
				layout,
				_layoutLocalService.fetchLayoutByFriendlyURL(
					_group.getGroupId(), false, "/friendly-url-" + i));
			Assert.assertEquals(
				layout,
				_layoutLocalService.getFriendlyURLLayout(
					_group.getGroupId(), false, "/friendly-url-" + i));
		}
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

	@Test
	public void testNewLayoutCanHaveTheSameFriendlyURLAsDeletedOne()
		throws Exception {

		String friendlyURL = "/friendly-url";

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), true,
			Collections.singletonMap(LocaleUtil.getDefault(), "name"),
			Collections.singletonMap(LocaleUtil.getDefault(), friendlyURL));

		_layoutLocalService.deleteLayout(layout);

		layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), true,
			Collections.singletonMap(LocaleUtil.getDefault(), "name"),
			Collections.singletonMap(LocaleUtil.getDefault(), friendlyURL));

		Assert.assertEquals(
			layout,
			_layoutLocalService.fetchLayoutByFriendlyURL(
				_group.getGroupId(), true, friendlyURL));
		Assert.assertEquals(
			layout,
			_layoutLocalService.getFriendlyURLLayout(
				_group.getGroupId(), true, friendlyURL));
	}

	@Test
	public void testPrivateLayoutCanHaveTheSameFriendlyURLAsPublicOne()
		throws Exception {

		String friendlyURL = "/friendly-url";

		Layout privateLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), true,
			Collections.singletonMap(LocaleUtil.getDefault(), "name"),
			Collections.singletonMap(LocaleUtil.getDefault(), friendlyURL));

		Layout publicLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), false,
			Collections.singletonMap(LocaleUtil.getDefault(), "name"),
			Collections.singletonMap(LocaleUtil.getDefault(), friendlyURL));

		Assert.assertEquals(
			privateLayout,
			_layoutLocalService.fetchLayoutByFriendlyURL(
				_group.getGroupId(), true, friendlyURL));
		Assert.assertEquals(
			privateLayout,
			_layoutLocalService.getFriendlyURLLayout(
				_group.getGroupId(), true, friendlyURL));
		Assert.assertEquals(
			publicLayout,
			_layoutLocalService.fetchLayoutByFriendlyURL(
				_group.getGroupId(), false, friendlyURL));
		Assert.assertEquals(
			publicLayout,
			_layoutLocalService.getFriendlyURLLayout(
				_group.getGroupId(), false, friendlyURL));
	}

	private void _testDeleteLayouts(boolean system) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			LayoutConstants.TYPE_CONTENT, false, system, null, serviceContext);
		_layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			LayoutConstants.TYPE_CONTENT, false, system, null, serviceContext);

		_layoutLocalService.deleteLayouts(
			_group.getGroupId(), true, new ServiceContext());
		_layoutLocalService.deleteLayouts(
			_group.getGroupId(), false, new ServiceContext());

		Assert.assertEquals(
			0, _layoutLocalService.getLayoutsCount(_group.getGroupId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

}