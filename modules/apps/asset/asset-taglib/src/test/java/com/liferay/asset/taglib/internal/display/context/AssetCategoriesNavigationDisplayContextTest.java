/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.taglib.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.RenderResponse;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Shakir Shamim
 */
public class AssetCategoriesNavigationDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_assetVocabularyServiceUtilMockedStatic = Mockito.mockStatic(
			AssetVocabularyServiceUtil.class);
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

		_scopeGroupId = RandomTestUtil.randomLong();

		_setUpSiteConnectedGroupProviderUtil();
		_setUpThemeDisplay();
	}

	@After
	public void tearDown() {
		_assetVocabularyServiceUtilMockedStatic.close();
		_frameworkUtilMockedStatic.close();
		_siteConnectedGroupGroupProviderUtilMockedStatic.close();
	}

	@Test
	public void testGetVocabularies() throws Exception {
		_testGetVocabulariesWhenFilteredByVocabularyIds();
		_testGetVocabulariesWhenShowingAllVocabularies();
	}

	private AssetCategoriesNavigationDisplayContext _createDisplayContext(
		long[] vocabularyIds) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		if (vocabularyIds != null) {
			mockHttpServletRequest.setAttribute(
				"liferay-asset:asset-tags-navigation:vocabularyIds",
				vocabularyIds);
		}

		return new AssetCategoriesNavigationDisplayContext(
			mockHttpServletRequest, Mockito.mock(RenderResponse.class));
	}

	private AssetVocabulary _mockAssetVocabulary(String assetVocabularyTitle) {
		AssetVocabulary assetVocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.when(
			assetVocabulary.getGroupId()
		).thenReturn(
			_scopeGroupId
		);

		Mockito.when(
			assetVocabulary.getTitle(_themeDisplay.getLocale())
		).thenReturn(
			assetVocabularyTitle
		);

		return assetVocabulary;
	}

	private void _mockFetchVocabulary(
		long vocabularyId, AssetVocabulary assetVocabulary) {

		_assetVocabularyServiceUtilMockedStatic.when(
			() -> AssetVocabularyServiceUtil.fetchVocabulary(vocabularyId)
		).thenReturn(
			assetVocabulary
		);
	}

	private void _setUpSiteConnectedGroupProviderUtil() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(
				SiteConnectedGroupGroupProviderUtil.class)
		).thenReturn(
			bundleContext.getBundle()
		);

		_siteConnectedGroupGroupProviderUtilMockedStatic = Mockito.mockStatic(
			SiteConnectedGroupGroupProviderUtil.class);

		_siteConnectedGroupGroupProviderUtilMockedStatic.when(
			() ->
				SiteConnectedGroupGroupProviderUtil.
					getCurrentAndAncestorSiteAndDepotGroupIds(_scopeGroupId)
		).thenReturn(
			new long[] {_scopeGroupId}
		);
	}

	private void _setUpThemeDisplay() {
		_themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			_themeDisplay.getScopeGroupId()
		).thenReturn(
			_scopeGroupId
		);
	}

	private void _testGetVocabulariesWhenFilteredByVocabularyIds()
		throws Exception {

		AssetVocabulary assetVocabulary1 = _mockAssetVocabulary(
			"A-" + RandomTestUtil.randomString());
		AssetVocabulary assetVocabulary2 = _mockAssetVocabulary(
			"B-" + RandomTestUtil.randomString());
		AssetVocabulary assetVocabulary3 = _mockAssetVocabulary(
			"C-" + RandomTestUtil.randomString());

		long vocabularyId1 = RandomTestUtil.randomLong();
		long vocabularyId2 = RandomTestUtil.randomLong();
		long vocabularyId3 = RandomTestUtil.randomLong();

		_mockFetchVocabulary(vocabularyId1, assetVocabulary1);
		_mockFetchVocabulary(vocabularyId2, assetVocabulary2);
		_mockFetchVocabulary(vocabularyId3, assetVocabulary3);

		AssetCategoriesNavigationDisplayContext
			assetCategoriesNavigationDisplayContext = _createDisplayContext(
				new long[] {vocabularyId3, vocabularyId1, vocabularyId2});

		Assert.assertEquals(
			Arrays.asList(assetVocabulary3, assetVocabulary1, assetVocabulary2),
			assetCategoriesNavigationDisplayContext.getVocabularies());
	}

	private void _testGetVocabulariesWhenShowingAllVocabularies()
		throws Exception {

		AssetVocabulary assetVocabulary1 = _mockAssetVocabulary(
			"A-" + RandomTestUtil.randomString());
		AssetVocabulary assetVocabulary2 = _mockAssetVocabulary(
			"B-" + RandomTestUtil.randomString());

		_assetVocabularyServiceUtilMockedStatic.when(
			() -> AssetVocabularyServiceUtil.getGroupVocabularies(
				new long[] {_scopeGroupId},
				new int[] {AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC})
		).thenReturn(
			Arrays.asList(assetVocabulary2, assetVocabulary1)
		);

		AssetCategoriesNavigationDisplayContext
			assetCategoriesNavigationDisplayContext = _createDisplayContext(
				null);

		Assert.assertEquals(
			Arrays.asList(assetVocabulary1, assetVocabulary2),
			assetCategoriesNavigationDisplayContext.getVocabularies());
	}

	private MockedStatic<AssetVocabularyServiceUtil>
		_assetVocabularyServiceUtilMockedStatic;
	private MockedStatic<FrameworkUtil> _frameworkUtilMockedStatic;
	private long _scopeGroupId;
	private MockedStatic<SiteConnectedGroupGroupProviderUtil>
		_siteConnectedGroupGroupProviderUtilMockedStatic;
	private ThemeDisplay _themeDisplay;

}