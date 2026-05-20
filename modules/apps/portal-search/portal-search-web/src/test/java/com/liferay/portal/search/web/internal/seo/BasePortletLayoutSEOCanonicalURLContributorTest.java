/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.seo;

import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.PortletPreferences;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Map;

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
public class BasePortletLayoutSEOCanonicalURLContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		PropsUtil.set("feature.flag.LPD-71164", "true");

		_portletPreferencesLocalService = Mockito.mock(
			PortletPreferencesLocalService.class);

		_basePortletLayoutSEOCanonicalURLContributor = Mockito.mock(
			BasePortletLayoutSEOCanonicalURLContributor.class,
			Mockito.CALLS_REAL_METHODS);

		ReflectionTestUtil.setFieldValue(
			_basePortletLayoutSEOCanonicalURLContributor,
			"portletPreferencesLocalService", _portletPreferencesLocalService);
	}

	@After
	public void tearDown() {
		PropsUtil.set("feature.flag.LPD-71164", "false");
	}

	@Test
	public void testContributeURLParametersFeatureFlagDisabled() {
		PropsUtil.set("feature.flag.LPD-71164", "false");

		_mockFetchPreferences(1, "PORTLET_ID");
		_mockSEOPortletPreferences("categoryId", true);

		HttpServletRequest httpServletRequest = _createHttpServletRequest(
			Collections.singletonMap(
				"categoryId", new String[] {"100", "200"}));

		Map<String, String[]> parameters =
			_basePortletLayoutSEOCanonicalURLContributor.
				contributeURLParameters(httpServletRequest, 1, "PORTLET_ID");

		Assert.assertTrue(parameters.toString(), parameters.isEmpty());
	}

	@Test
	public void testContributeURLParametersIndexingDisabled() {
		_mockFetchPreferences(1, "PORTLET_ID");
		_mockSEOPortletPreferences("categoryId", false);

		HttpServletRequest httpServletRequest = _createHttpServletRequest(
			Collections.singletonMap(
				"categoryId", new String[] {"100", "200"}));

		Map<String, String[]> parameters =
			_basePortletLayoutSEOCanonicalURLContributor.
				contributeURLParameters(httpServletRequest, 1, "PORTLET_ID");

		Assert.assertTrue(parameters.toString(), parameters.isEmpty());
	}

	@Test
	public void testContributeURLParametersIndexingEnabled() {
		_mockFetchPreferences(1, "PORTLET_ID");
		_mockSEOPortletPreferences("categoryId", true);

		HttpServletRequest httpServletRequest = _createHttpServletRequest(
			Collections.singletonMap(
				"categoryId", new String[] {"100", "200"}));

		Map<String, String[]> parameters =
			_basePortletLayoutSEOCanonicalURLContributor.
				contributeURLParameters(httpServletRequest, 1, "PORTLET_ID");

		Assert.assertEquals(parameters.toString(), 1, parameters.size());
		Assert.assertArrayEquals(
			new String[] {"100", "200"}, parameters.get("categoryId"));
	}

	@Test
	public void testContributeURLParametersNoMatchingRequestParameter() {
		_mockFetchPreferences(1, "PORTLET_ID");
		_mockSEOPortletPreferences("categoryId", true);

		HttpServletRequest httpServletRequest = _createHttpServletRequest(
			Collections.emptyMap());

		Map<String, String[]> parameters =
			_basePortletLayoutSEOCanonicalURLContributor.
				contributeURLParameters(httpServletRequest, 1, "PORTLET_ID");

		Assert.assertTrue(parameters.toString(), parameters.isEmpty());
	}

	private HttpServletRequest _createHttpServletRequest(
		Map<String, String[]> requestParameters) {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		for (Map.Entry<String, String[]> entry : requestParameters.entrySet()) {
			Mockito.when(
				httpServletRequest.getParameterValues(entry.getKey())
			).thenReturn(
				entry.getValue()
			);
		}

		return httpServletRequest;
	}

	private void _mockFetchPreferences(long plid, String portletId) {
		Mockito.when(
			_portletPreferencesLocalService.fetchPreferences(
				_COMPANY_ID, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId)
		).thenReturn(
			Mockito.mock(PortletPreferences.class)
		);
	}

	private void _mockSEOPortletPreferences(
		String parameterName, boolean webCrawlerIndexingEnabled) {

		SEOPortletPreferences seoPortletPreferences = Mockito.mock(
			SEOPortletPreferences.class);

		Mockito.doReturn(
			seoPortletPreferences
		).when(
			_basePortletLayoutSEOCanonicalURLContributor
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

	private BasePortletLayoutSEOCanonicalURLContributor
		_basePortletLayoutSEOCanonicalURLContributor;
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}