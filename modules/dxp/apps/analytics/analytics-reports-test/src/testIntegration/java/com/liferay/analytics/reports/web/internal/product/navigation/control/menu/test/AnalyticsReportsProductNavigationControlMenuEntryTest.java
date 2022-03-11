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

package com.liferay.analytics.reports.web.internal.product.navigation.control.menu.test;

import com.liferay.analytics.reports.test.MockObject;
import com.liferay.analytics.reports.test.analytics.reports.info.item.MockObjectAnalyticsReportsInfoItem;
import com.liferay.analytics.reports.test.util.MockContextUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PrefsPropsImpl;
import com.liferay.portlet.PortalPreferencesImpl;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;

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
			new MockContextUtil.MockContext.Builder().
				mockObjectAnalyticsReportsInfoItem(
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
			new MockContextUtil.MockContext.Builder().
				mockObjectAnalyticsReportsInfoItem(
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
	public void testIsShowWithIsShowAnalyticsReportsInfoItemWithAnalyticsConnected()
		throws Exception {

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		ValidPrefsPropsWrapper validPrefsPropsWrapper =
			new ValidPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", validPrefsPropsWrapper);

		try {
			MockContextUtil.testWithMockContext(
				new MockContextUtil.MockContext.Builder().
					mockObjectAnalyticsReportsInfoItem(
						MockObjectAnalyticsReportsInfoItem.builder(
						).show(
							true
						).build()
					).build(),
				() -> Assert.assertTrue(
					_productNavigationControlMenuEntry.isShow(
						_getHttpServletRequest())));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testIsShowWithIsShowAnalyticsReportsInfoItemWithoutAnalyticsConnected()
		throws Exception {

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		InvalidPropsWrapper invalidPropsWrapper = new InvalidPropsWrapper(
			prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", invalidPropsWrapper);

		try {
			MockContextUtil.testWithMockContext(
				new MockContextUtil.MockContext.Builder().
					mockObjectAnalyticsReportsInfoItem(
						MockObjectAnalyticsReportsInfoItem.builder(
						).show(
							true
						).build()
					).build(),
				() -> Assert.assertTrue(
					_productNavigationControlMenuEntry.isShow(
						_getHttpServletRequest())));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testIsShowWithIsShowAnalyticsReportsInfoItemWithoutAnalyticsConnectedAndHidePanel()
		throws Exception {

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		InvalidPropsWrapper invalidPropsWrapper = new InvalidPropsWrapper(
			prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", invalidPropsWrapper);

		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(
			WebKeys.PORTAL_PREFERENCES,
			new HidePanelPortalPreferencesWrapper());

		try {
			MockContextUtil.testWithMockContext(
				new MockContextUtil.MockContext.Builder().
					mockObjectAnalyticsReportsInfoItem(
						MockObjectAnalyticsReportsInfoItem.builder(
						).show(
							true
						).build()
					).build(),
				() -> Assert.assertFalse(
					_productNavigationControlMenuEntry.isShow(
						httpServletRequest)));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	private HttpServletRequest _getHttpServletRequest() throws PortalException {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockHttpServletRequest.setAttribute(
			"INFO_ITEM_REFERENCE",
			new InfoItemReference(MockObject.class.getName(), 0));

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

	@Inject
	private UserLocalService _userLocalService;

	private class HidePanelPortalPreferencesWrapper
		extends PortalPreferencesImpl {

		@Override
		public String getValue(String namespace, String key) {
			if (Objects.equals("hide-panel", key)) {
				return String.valueOf(Boolean.TRUE);
			}

			return null;
		}

	}

	private class InvalidPropsWrapper extends PrefsPropsImpl {

		public InvalidPropsWrapper(PrefsProps prefsProps) {
			_prefsProps = prefsProps;
		}

		@Override
		public String getString(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsDataSourceId", name) ||
				Objects.equals(
					name, "liferayAnalyticsFaroBackendSecuritySignature") ||
				Objects.equals("liferayAnalyticsFaroBackendURL", name)) {

				return null;
			}

			return _prefsProps.getString(companyId, name);
		}

		private final PrefsProps _prefsProps;

	}

	private class ValidPrefsPropsWrapper extends PrefsPropsImpl {

		public ValidPrefsPropsWrapper(PrefsProps prefsProps) {
			_prefsProps = prefsProps;
		}

		@Override
		public String getString(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsDataSourceId", name) ||
				Objects.equals(
					name, "liferayAnalyticsFaroBackendSecuritySignature") ||
				Objects.equals("liferayAnalyticsFaroBackendURL", name)) {

				return "test";
			}

			return _prefsProps.getString(companyId, name);
		}

		private final PrefsProps _prefsProps;

	}

}