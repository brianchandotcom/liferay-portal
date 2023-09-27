/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.product.navigation.control.menu.test;

import com.liferay.analytics.reports.constants.AnalyticsReportsWebKeys;
import com.liferay.analytics.reports.test.MockObject;
import com.liferay.analytics.reports.test.analytics.reports.info.item.MockObjectAnalyticsReportsInfoItem;
import com.liferay.analytics.reports.test.util.MockContextUtil;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.PortalPreferencesImpl;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;

import java.util.Dictionary;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class AnalyticsReportsProductNavigationControlMenuEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group.getGroupId());
	}

	@Test
	public void testIsShow() throws Exception {
		MockContextUtil.testWithMockContext(
			new MockContextUtil.MockContext.Builder(
			).mockObjectAnalyticsReportsInfoItem(
				MockObjectAnalyticsReportsInfoItem.builder(
				).show(
					true
				).build()
			).build(),
			() -> Assert.assertTrue(
				_productNavigationControlMenuEntry.isShow(
					_getHttpServletRequest())));
	}

	@Test
	public void testIsShowWithIsNotShowAnalyticsReportsInfoItem()
		throws Exception {

		MockContextUtil.testWithMockContext(
			new MockContextUtil.MockContext.Builder(
			).mockObjectAnalyticsReportsInfoItem(
				MockObjectAnalyticsReportsInfoItem.builder(
				).show(
					false
				).build()
			).build(),
			() -> Assert.assertFalse(
				_productNavigationControlMenuEntry.isShow(
					_getHttpServletRequest())));
	}

	@Test
	public void testIsShowWithIsShowAnalyticsReportsInfoItem()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"liferayAnalyticsDataSourceId",
							RandomTestUtil.nextLong()
						).put(
							"liferayAnalyticsEnableAllGroupIds", true
						).put(
							"liferayAnalyticsFaroBackendSecuritySignature",
							RandomTestUtil.randomString()
						).put(
							"liferayAnalyticsFaroBackendURL",
							RandomTestUtil.randomString()
						).build())) {

			MockContextUtil.testWithMockContext(
				new MockContextUtil.MockContext.Builder(
				).mockObjectAnalyticsReportsInfoItem(
					MockObjectAnalyticsReportsInfoItem.builder(
					).show(
						true
					).build()
				).build(),
				() -> Assert.assertTrue(
					_productNavigationControlMenuEntry.isShow(
						_getHttpServletRequest())));
		}
	}

	@Test
	public void testIsShowWithIsShowAnalyticsReportsInfoItemWithNullLiferayAnalyticsDataSourceId()
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary();

		dictionary.put("liferayAnalyticsDataSourceId", null);

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(), dictionary)) {

			MockContextUtil.testWithMockContext(
				new MockContextUtil.MockContext.Builder(
				).mockObjectAnalyticsReportsInfoItem(
					MockObjectAnalyticsReportsInfoItem.builder(
					).show(
						true
					).build()
				).build(),
				() -> Assert.assertTrue(
					_productNavigationControlMenuEntry.isShow(
						_getHttpServletRequest())));
		}
	}

	@Test
	public void testIsShowWithIsShowAnalyticsReportsInfoItemWithNullLiferayAnalyticsDataSourceIdAndHidePanel()
		throws Exception {

		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(
			WebKeys.PORTAL_PREFERENCES,
			new HidePanelPortalPreferencesWrapper());

		Dictionary<String, Object> dictionary = new HashMapDictionary();

		dictionary.put("liferayAnalyticsDataSourceId", null);

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(), dictionary)) {

			MockContextUtil.testWithMockContext(
				new MockContextUtil.MockContext.Builder(
				).mockObjectAnalyticsReportsInfoItem(
					MockObjectAnalyticsReportsInfoItem.builder(
					).show(
						true
					).build()
				).build(),
				() -> Assert.assertFalse(
					_productNavigationControlMenuEntry.isShow(
						httpServletRequest)));
		}
	}

	private HttpServletRequest _getHttpServletRequest() throws PortalException {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			AnalyticsReportsWebKeys.ANALYTICS_INFO_ITEM_REFERENCE,
			new InfoItemReference(MockObject.class.getName(), 0));
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "component.name=com.liferay.analytics.reports.web.internal.product.navigation.control.menu.AnalyticsReportsProductNavigationControlMenuEntry"
	)
	private ProductNavigationControlMenuEntry
		_productNavigationControlMenuEntry;

	private class HidePanelPortalPreferencesWrapper
		extends PortalPreferencesImpl {

		@Override
		public String getValue(String namespace, String key) {
			if (Objects.equals(key, "hide-panel")) {
				return String.valueOf(Boolean.TRUE);
			}

			return null;
		}

	}

}