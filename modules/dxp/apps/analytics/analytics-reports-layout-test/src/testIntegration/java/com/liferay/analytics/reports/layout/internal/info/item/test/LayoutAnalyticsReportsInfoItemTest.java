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

package com.liferay.analytics.reports.layout.internal.info.item.test;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class LayoutAnalyticsReportsInfoItemTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetAuthorName() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		try {
			Layout layout = _layoutLocalService.addLayout(
				user.getUserId(), _group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			Assert.assertEquals(
				user.getFullName(),
				_analyticsReportsInfoItem.getAuthorName(layout));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetAuthorNameWithDeletedUser() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		Layout layout = _layoutLocalService.addLayout(
			user.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_userLocalService.deleteUser(user);

		Assert.assertEquals(
			StringPool.BLANK, _analyticsReportsInfoItem.getAuthorName(layout));
	}

	@Test
	public void testGetAuthorUserId() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		try {
			Layout layout = _layoutLocalService.addLayout(
				user.getUserId(), _group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			Assert.assertEquals(
				user.getUserId(),
				_analyticsReportsInfoItem.getAuthorUserId(layout));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetAuthorUserIdWithDeletedUser() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		Layout layout = _layoutLocalService.addLayout(
			user.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_userLocalService.deleteUser(user);

		Assert.assertEquals(
			0L, _analyticsReportsInfoItem.getAuthorUserId(layout));
	}

	@Test
	public void testGetAvailableLocales() throws Exception {
		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			LocaleUtil.SPAIN);

		Assert.assertEquals(
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			_analyticsReportsInfoItem.getAvailableLocales(layout));
	}

	@Test
	public void testGetDefaultLocale() throws Exception {
		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			LocaleUtil.SPAIN);

		Assert.assertEquals(
			LocaleUtil.SPAIN,
			_analyticsReportsInfoItem.getDefaultLocale(layout));
	}

	@Test
	public void testGetPublishDate() throws Exception {
		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			layout.getPublishDate(),
			_analyticsReportsInfoItem.getPublishDate(layout));
	}

	@Test
	public void testGetTitle() throws Exception {
		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

			Layout layout = _layoutLocalService.addLayout(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			Assert.assertEquals(
				layout.getTitle(LocaleUtil.SPAIN),
				_analyticsReportsInfoItem.getTitle(layout, LocaleUtil.SPAIN));
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
	}

	@Inject(filter = "component.name=*.LayoutAnalyticsReportsInfoItem")
	private AnalyticsReportsInfoItem<Layout> _analyticsReportsInfoItem;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserLocalService _userLocalService;

}