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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class LayoutAnalyticsReportsInfoItemTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetAuthorName() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		try {
			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			Assert.assertNull(_analyticsReportsInfoItem.getAuthorName(layout));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetAuthorUserId() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		try {
			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			Assert.assertEquals(
				0L, _analyticsReportsInfoItem.getAuthorUserId(layout));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetAuthorUserIdWithDeletedUser() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_userLocalService.deleteUser(user);

		Assert.assertEquals(
			0L, _analyticsReportsInfoItem.getAuthorUserId(layout));
	}

	@Test
	public void testGetAvailableLocales() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			LocaleUtil.SPAIN);

		Assert.assertEquals(
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			_analyticsReportsInfoItem.getAvailableLocales(layout));
	}

	@Test
	public void testGetCanonicalURL() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.setAttribute(
				WebKeys.CURRENT_COMPLETE_URL, StringPool.BLANK);

			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setCompany(
				_companyLocalService.fetchCompany(
					TestPropsValues.getCompanyId()));

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			themeDisplay.setLayoutSet(layout.getLayoutSet());

			themeDisplay.setRequest(mockHttpServletRequest);
			themeDisplay.setSiteGroupId(_group.getGroupId());

			mockHttpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			serviceContext.setRequest(mockHttpServletRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			String canonicalURL = _analyticsReportsInfoItem.getCanonicalURL(
				layout, LocaleUtil.getSiteDefault());

			Assert.assertTrue(canonicalURL.contains(_group.getFriendlyURL()));
		}
		finally {
			_userLocalService.deleteUser(user);

			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGetCanonicalURLWithoutThemeDisplay() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		try {
			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			Assert.assertEquals(
				StringPool.BLANK,
				_analyticsReportsInfoItem.getCanonicalURL(
					layout, LocaleUtil.getSiteDefault()));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDefaultLocale() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

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
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Assert.assertEquals(
			layout.getPublishDate(),
			_analyticsReportsInfoItem.getPublishDate(layout));
	}

	@Test
	public void testGetTitle() throws Exception {
		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			Assert.assertEquals(
				layout.getTitle(LocaleUtil.SPAIN),
				_analyticsReportsInfoItem.getTitle(layout, LocaleUtil.SPAIN));
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
	}

	@Test
	public void testGetTitleWithEmptyTitle() throws Exception {
		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			Assert.assertEquals(
				layout.getName(LocaleUtil.SPAIN),
				_analyticsReportsInfoItem.getTitle(layout, LocaleUtil.SPAIN));
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
	}

	@Test
	public void testIsShow() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		try {
			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setRequest(mockHttpServletRequest);

			mockHttpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			serviceContext.setRequest(mockHttpServletRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			Assert.assertTrue(_analyticsReportsInfoItem.isShow(layout));
		}
		finally {
			_userLocalService.deleteUser(user);

			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testIsShowWithPortletLayout() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		try {
			Layout layout = LayoutTestUtil.addTypePortletLayout(
				_group.getGroupId());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setRequest(mockHttpServletRequest);

			mockHttpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			serviceContext.setRequest(mockHttpServletRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			Assert.assertTrue(_analyticsReportsInfoItem.isShow(layout));
		}
		finally {
			_userLocalService.deleteUser(user);

			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Inject(filter = "component.name=*.LayoutAnalyticsReportsInfoItem")
	private AnalyticsReportsInfoItem<Layout> _analyticsReportsInfoItem;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserLocalService _userLocalService;

}