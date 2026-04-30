/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.display.context.test;

import com.liferay.announcements.kernel.exception.EntryDisplayDateException;
import com.liferay.announcements.kernel.exception.EntryExpirationDateException;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.RenderRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Constructor;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Akhash Ramprakash
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AnnouncementsAdminViewDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			AnnouncementsAdminViewDisplayContextTest.class);

		bundle = BundleUtil.getBundle(
			bundle.getBundleContext(), "com.liferay.announcements.web");

		Class<?> clazz = bundle.loadClass(
			"com.liferay.announcements.web.internal.display.context." +
				"AnnouncementsAdminViewDisplayContext");

		_constructor = clazz.getConstructor(
			HttpServletRequest.class, LiferayPortletRequest.class,
			LiferayPortletResponse.class, RenderRequest.class);
	}

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.getAdminUser(TestPropsValues.getCompanyId());
	}

	@Test
	@TestInfo("LPD-86099")
	public void testGetSearchContainer() throws Exception {
		AnnouncementsEntry announcementsEntry1 = _addEntry("Beta");
		AnnouncementsEntry announcementsEntry2 = _addEntry("Alpha");
		AnnouncementsEntry announcementsEntry3 = _addEntry("Charlie");

		_assertSearchContainer(
			Arrays.asList(
				announcementsEntry2, announcementsEntry1, announcementsEntry3),
			_getSearchContainer(_getMockHttpServletRequestWithOrderBy("asc")));
		_assertSearchContainer(
			Arrays.asList(
				announcementsEntry3, announcementsEntry1, announcementsEntry2),
			_getSearchContainer(_getMockHttpServletRequestWithOrderBy("desc")));
	}

	private AnnouncementsEntry _addEntry(String title) throws Exception {
		return _announcementsEntryLocalService.addEntry(
			_user.getUserId(), 0, 0, title, RandomTestUtil.randomString(),
			"http://localhost", "general",
			_portal.getDate(
				1, 1, 1990, 1, 1, _user.getTimeZone(),
				EntryDisplayDateException.class),
			_portal.getDate(
				1, 1, 3000, 1, 1, _user.getTimeZone(),
				EntryExpirationDateException.class),
			1, false);
	}

	private void _assertSearchContainer(
		List<AnnouncementsEntry> expectedAnnouncementsEntries,
		SearchContainer<AnnouncementsEntry> searchContainer) {

		List<AnnouncementsEntry> announcementsEntries =
			searchContainer.getResults();

		Assert.assertEquals(
			announcementsEntries.toString(),
			expectedAnnouncementsEntries.size(), announcementsEntries.size());

		for (int i = 0; i < expectedAnnouncementsEntries.size(); i++) {
			Assert.assertEquals(
				expectedAnnouncementsEntries.get(
					i
				).getTitle(),
				announcementsEntries.get(
					i
				).getTitle());
		}
	}

	private MockHttpServletRequest _getMockHttpServletRequestWithOrderBy(
			String orderByType)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());
		mockHttpServletRequest.setParameter(
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "title");
		mockHttpServletRequest.setParameter(
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, orderByType);

		return mockHttpServletRequest;
	}

	private SearchContainer<AnnouncementsEntry> _getSearchContainer(
			MockHttpServletRequest mockHttpServletRequest)
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest(mockHttpServletRequest);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			mockHttpServletRequest.getAttribute(WebKeys.THEME_DISPLAY));
		mockLiferayPortletRenderRequest.setAttribute(
			StringBundler.concat(
				mockLiferayPortletRenderRequest.getPortletName(), "-",
				WebKeys.CURRENT_PORTLET_URL),
			new MockLiferayPortletURL());

		Object announcementsAdminViewDisplayContext = _constructor.newInstance(
			mockHttpServletRequest, mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse(),
			mockLiferayPortletRenderRequest);

		return ReflectionTestUtil.invoke(
			announcementsAdminViewDisplayContext, "getSearchContainer",
			new Class<?>[0]);
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_user.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setUser(_user);

		return themeDisplay;
	}

	private static Constructor<?> _constructor;

	@Inject
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private Portal _portal;

	private User _user;

}