/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.seo;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Amos Fong
 */
public class BasePortletLayoutSetSEORobotsContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		PropsUtil.set("feature.flag.LPD-71164", "true");

		_layoutLocalService = Mockito.mock(LayoutLocalService.class);
		_portletPreferencesLocalService = Mockito.mock(
			PortletPreferencesLocalService.class);

		_basePortletLayoutSetSEORobotsContributor = Mockito.mock(
			BasePortletLayoutSetSEORobotsContributor.class,
			Mockito.CALLS_REAL_METHODS);

		ReflectionTestUtil.setFieldValue(
			_basePortletLayoutSetSEORobotsContributor, "layoutLocalService",
			_layoutLocalService);
		ReflectionTestUtil.setFieldValue(
			_basePortletLayoutSetSEORobotsContributor,
			"portletPreferencesLocalService", _portletPreferencesLocalService);

		Mockito.doReturn(
			"PORTLET_ID"
		).when(
			_basePortletLayoutSetSEORobotsContributor
		).getPortletId();
	}

	@After
	public void tearDown() {
		PropsUtil.set("feature.flag.LPD-71164", "false");
	}

	@Test
	public void testContributeDisallowURLEntriesDraftLayout() {
		LayoutSet layoutSet = _createLayoutSet();

		_mockPortletPreferences(layoutSet, 1);

		_mockLayout(true, "/search-page", 1, true, false);

		_mockSEOPortletPreferences("categoryId", false);

		Set<String> disallowURLEntries =
			_basePortletLayoutSetSEORobotsContributor.
				contributeDisallowURLEntries(layoutSet);

		Assert.assertTrue(
			disallowURLEntries.toString(), disallowURLEntries.isEmpty());
	}

	@Test
	public void testContributeDisallowURLEntriesFeatureFlagDisabled() {
		PropsUtil.set("feature.flag.LPD-71164", "false");

		LayoutSet layoutSet = _createLayoutSet();

		_mockPortletPreferences(layoutSet, 1);

		_mockLayout(false, "/search-page", 1, true, false);

		_mockSEOPortletPreferences("categoryId", false);

		Set<String> disallowURLEntries =
			_basePortletLayoutSetSEORobotsContributor.
				contributeDisallowURLEntries(layoutSet);

		Assert.assertTrue(
			disallowURLEntries.toString(), disallowURLEntries.isEmpty());
	}

	@Test
	public void testContributeDisallowURLEntriesIndexingDisabled() {
		LayoutSet layoutSet = _createLayoutSet();

		_mockPortletPreferences(layoutSet, 1);

		_mockLayout(false, "/search-page", 1, true, false);

		_mockSEOPortletPreferences("categoryId", false);

		Set<String> disallowURLEntries =
			_basePortletLayoutSetSEORobotsContributor.
				contributeDisallowURLEntries(layoutSet);

		Assert.assertEquals(
			disallowURLEntries.toString(), 2, disallowURLEntries.size());
		Assert.assertTrue(
			disallowURLEntries.contains("/search-page*&categoryId="));
		Assert.assertTrue(
			disallowURLEntries.contains("/search-page*?categoryId="));
	}

	@Test
	public void testContributeDisallowURLEntriesIndexingEnabled() {
		LayoutSet layoutSet = _createLayoutSet();

		_mockPortletPreferences(layoutSet, 1);

		_mockSEOPortletPreferences("categoryId", true);

		Set<String> disallowURLEntries =
			_basePortletLayoutSetSEORobotsContributor.
				contributeDisallowURLEntries(layoutSet);

		Assert.assertTrue(
			disallowURLEntries.toString(), disallowURLEntries.isEmpty());
	}

	@Test
	public void testContributeDisallowURLEntriesNullLayout() {
		LayoutSet layoutSet = _createLayoutSet();

		_mockPortletPreferences(layoutSet, 1);

		_mockSEOPortletPreferences("categoryId", false);

		Set<String> disallowURLEntries =
			_basePortletLayoutSetSEORobotsContributor.
				contributeDisallowURLEntries(layoutSet);

		Assert.assertTrue(
			disallowURLEntries.toString(), disallowURLEntries.isEmpty());
	}

	@Test
	public void testContributeDisallowURLEntriesNullParameterName() {
		LayoutSet layoutSet = _createLayoutSet();

		_mockPortletPreferences(layoutSet, 1);

		_mockSEOPortletPreferences(null, false);

		Set<String> disallowURLEntries =
			_basePortletLayoutSetSEORobotsContributor.
				contributeDisallowURLEntries(layoutSet);

		Assert.assertTrue(
			disallowURLEntries.toString(), disallowURLEntries.isEmpty());
	}

	@Test
	public void testContributeDisallowURLEntriesSystemLayout() {
		LayoutSet layoutSet = _createLayoutSet();

		_mockPortletPreferences(layoutSet, 1);

		_mockLayout(false, "/search-page", 1, true, true);

		_mockSEOPortletPreferences("categoryId", false);

		Set<String> disallowURLEntries =
			_basePortletLayoutSetSEORobotsContributor.
				contributeDisallowURLEntries(layoutSet);

		Assert.assertTrue(
			disallowURLEntries.toString(), disallowURLEntries.isEmpty());
	}

	@Test
	public void testContributeDisallowURLEntriesUnpublishedLayout() {
		LayoutSet layoutSet = _createLayoutSet();

		_mockPortletPreferences(layoutSet, 1);

		_mockLayout(false, "/search-page", 1, false, false);

		_mockSEOPortletPreferences("categoryId", false);

		Set<String> disallowURLEntries =
			_basePortletLayoutSetSEORobotsContributor.
				contributeDisallowURLEntries(layoutSet);

		Assert.assertTrue(
			disallowURLEntries.toString(), disallowURLEntries.isEmpty());
	}

	private LayoutSet _createLayoutSet() {
		LayoutSet layoutSet = Mockito.mock(LayoutSet.class);

		Mockito.when(
			layoutSet.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			layoutSet.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		Mockito.when(
			layoutSet.isPrivateLayout()
		).thenReturn(
			false
		);

		return layoutSet;
	}

	private void _mockLayout(
		boolean draftLayout, String friendlyURL, long plid, boolean published,
		boolean system) {

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			_layoutLocalService.fetchLayout(plid)
		).thenReturn(
			layout
		);

		Mockito.when(
			layout.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			layout.getFriendlyURL()
		).thenReturn(
			friendlyURL
		);

		Mockito.when(
			layout.getPlid()
		).thenReturn(
			plid
		);

		Mockito.when(
			layout.isDraftLayout()
		).thenReturn(
			draftLayout
		);

		Mockito.when(
			layout.isPublished()
		).thenReturn(
			published
		);

		Mockito.when(
			layout.isSystem()
		).thenReturn(
			system
		);
	}

	private void _mockPortletPreferences(LayoutSet layoutSet, long plid) {
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		Mockito.when(
			portletPreferences.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			portletPreferences.getOwnerId()
		).thenReturn(
			PortletKeys.PREFS_OWNER_ID_DEFAULT
		);

		Mockito.when(
			portletPreferences.getOwnerType()
		).thenReturn(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT
		);

		Mockito.when(
			portletPreferences.getPlid()
		).thenReturn(
			plid
		);

		Mockito.when(
			portletPreferences.getPortletId()
		).thenReturn(
			"PORTLET_ID"
		);

		Mockito.when(
			_portletPreferencesLocalService.getPortletPreferences(
				layoutSet.getCompanyId(), layoutSet.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, "PORTLET_ID",
				layoutSet.isPrivateLayout())
		).thenReturn(
			Collections.singletonList(portletPreferences)
		);

		Mockito.when(
			_portletPreferencesLocalService.getPreferences(
				_COMPANY_ID, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, "PORTLET_ID")
		).thenReturn(
			Mockito.mock(jakarta.portlet.PortletPreferences.class)
		);
	}

	private void _mockSEOPortletPreferences(
		String parameterName, boolean webCrawlerIndexingEnabled) {

		SEOPortletPreferences seoPortletPreferences = Mockito.mock(
			SEOPortletPreferences.class);

		Mockito.doReturn(
			seoPortletPreferences
		).when(
			_basePortletLayoutSetSEORobotsContributor
		).getSEOPortletPreferences(
			Mockito.any()
		);

		Mockito.when(
			seoPortletPreferences.getSEOParameterName()
		).thenReturn(
			parameterName
		);

		Mockito.when(
			seoPortletPreferences.isWebCrawlerIndexingEnabled()
		).thenReturn(
			webCrawlerIndexingEnabled
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private BasePortletLayoutSetSEORobotsContributor
		_basePortletLayoutSetSEORobotsContributor;
	private LayoutLocalService _layoutLocalService;
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}