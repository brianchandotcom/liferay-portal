/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class SiteExportImportParameterUtilTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetCurrentSiteExternalReferenceCodeWhenBlank() {
		Assert.assertNull(
			SiteExportImportParameterUtil.getCurrentSiteExternalReferenceCode(
				SiteExportImportParameterUtil.toSiteExportParameterMap(
					null, StringPool.BLANK)));
	}

	@Test
	public void testGetCurrentSiteExternalReferenceCodeWhenNull() {
		Assert.assertNull(
			SiteExportImportParameterUtil.getCurrentSiteExternalReferenceCode(
				(Map)null));
	}

	@Test
	public void testGetCurrentSiteExternalReferenceCodeWhenSet() {
		Assert.assertEquals(
			"erc1",
			SiteExportImportParameterUtil.getCurrentSiteExternalReferenceCode(
				SiteExportImportParameterUtil.toSiteExportParameterMap(
					null, "erc1")));
	}

	@Test
	public void testGetSelectedSiteExternalReferenceCodesWhenBlank() {
		Assert.assertArrayEquals(
			new String[0],
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				HashMapBuilder.put(
					PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
					new String[] {StringPool.BLANK, null, "   "}
				).build()));
	}

	@Test
	public void testGetSelectedSiteExternalReferenceCodesWhenDuplicated() {
		Assert.assertArrayEquals(
			new String[] {"erc1", "erc2"},
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				HashMapBuilder.put(
					PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
					new String[] {"erc1", "erc2", "erc1"}
				).build()));
	}

	@Test
	public void testGetSelectedSiteExternalReferenceCodesWhenMissing() {
		Assert.assertArrayEquals(
			new String[0],
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				HashMapBuilder.put(
					PortletDataHandlerKeys.PORTLET_DATA,
					new String[] {Boolean.TRUE.toString()}
				).build()));
	}

	@Test
	public void testGetSelectedSiteExternalReferenceCodesWhenNull() {
		Assert.assertArrayEquals(
			new String[0],
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				(Map)null));
	}

	@Test
	public void testGetSelectedSiteExternalReferenceCodesWhenPadded() {
		Assert.assertArrayEquals(
			new String[] {"erc1"},
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				HashMapBuilder.put(
					PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
					new String[] {"  erc1  "}
				).build()));
	}

	@Test
	public void testIsSiteExportImportEnabledFollowsTheFeatureFlag() {
		long companyId = RandomTestUtil.randomLong();

		try (MockedStatic<FeatureFlagManagerUtil>
				featureFlagManagerUtilMockedStatic = Mockito.mockStatic(
					FeatureFlagManagerUtil.class)) {

			Assert.assertFalse(
				SiteExportImportParameterUtil.isSiteExportImportEnabled(
					companyId));

			featureFlagManagerUtilMockedStatic.when(
				() -> FeatureFlagManagerUtil.isEnabled(companyId, "LPD-85946")
			).thenReturn(
				true
			);

			Assert.assertTrue(
				SiteExportImportParameterUtil.isSiteExportImportEnabled(
					companyId));
		}
	}

	@Test
	public void testIsSiteScopedWhenCompanyLevel() {
		Assert.assertFalse(
			SiteExportImportParameterUtil.isSiteScoped(
				HashMapBuilder.put(
					PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
					new String[] {"erc1"}
				).build()));
	}

	@Test
	public void testIsSiteScopedWhenSiteLevel() {
		PortletDataContext portletDataContext = Mockito.mock(
			PortletDataContext.class);

		Mockito.when(
			portletDataContext.getParameterMap()
		).thenReturn(
			SiteExportImportParameterUtil.toSiteExportParameterMap(null, "erc1")
		);

		Assert.assertTrue(
			SiteExportImportParameterUtil.isSiteScoped(portletDataContext));
	}

	@Test
	public void testToSiteExportParameterMapDropsTheSelection() {

		// Dropping the selection is what keeps a per-site pass from starting
		// per-site passes of its own

		Map<String, String[]> siteParameterMap =
			SiteExportImportParameterUtil.toSiteExportParameterMap(
				HashMapBuilder.put(
					PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
					new String[] {"erc1", "erc2"}
				).build(),
				"erc1");

		Assert.assertArrayEquals(
			new String[0],
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				siteParameterMap));
		Assert.assertEquals(
			"erc1",
			SiteExportImportParameterUtil.getCurrentSiteExternalReferenceCode(
				siteParameterMap));
		Assert.assertTrue(
			SiteExportImportParameterUtil.isSiteScoped(siteParameterMap));
	}

	@Test
	public void testToSiteExportParameterMapKeepsWhatItDoesNotDecide() {
		Map<String, String[]> siteParameterMap =
			SiteExportImportParameterUtil.toSiteExportParameterMap(
				HashMapBuilder.put(
					PortletDataHandlerKeys.COMMENTS,
					new String[] {Boolean.TRUE.toString()}
				).put(
					PortletDataHandlerKeys.RATINGS,
					new String[] {Boolean.TRUE.toString()}
				).build(),
				"erc1");

		Assert.assertTrue(
			MapUtil.getBoolean(
				siteParameterMap, PortletDataHandlerKeys.COMMENTS));
		Assert.assertTrue(
			MapUtil.getBoolean(
				siteParameterMap, PortletDataHandlerKeys.RATINGS));
	}

	@Test
	public void testToSiteExportParameterMapLeavesOutOfScopeOff() {
		Map<String, String[]> siteParameterMap =
			SiteExportImportParameterUtil.toSiteExportParameterMap(
				HashMapBuilder.put(
					PortletDataHandlerKeys.DELETIONS,
					new String[] {Boolean.TRUE.toString()}
				).put(
					PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
					new String[] {Boolean.TRUE.toString()}
				).put(
					PortletDataHandlerKeys.PERMISSIONS,
					new String[] {Boolean.TRUE.toString()}
				).build(),
				"erc1");

		Assert.assertFalse(
			MapUtil.getBoolean(
				siteParameterMap, PortletDataHandlerKeys.DELETIONS));
		Assert.assertFalse(
			MapUtil.getBoolean(
				siteParameterMap,
				PortletDataHandlerKeys.LAYOUT_SET_PRIVATE_LAYOUT));
		Assert.assertFalse(
			MapUtil.getBoolean(
				siteParameterMap,
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS));
		Assert.assertFalse(
			MapUtil.getBoolean(
				siteParameterMap, PortletDataHandlerKeys.PERMISSIONS));
	}

	@Test
	public void testToSiteExportParameterMapWithDisabledPortletData() {
		Map<String, String[]> siteParameterMap =
			SiteExportImportParameterUtil.toSiteExportParameterMap(
				HashMapBuilder.put(
					PortletDataHandlerKeys.PORTLET_DATA,
					new String[] {Boolean.FALSE.toString()}
				).put(
					PortletDataHandlerKeys.PORTLET_DATA_ALL,
					new String[] {Boolean.FALSE.toString()}
				).put(
					PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
					new String[] {Boolean.FALSE.toString()}
				).build(),
				"erc1");

		Assert.assertTrue(
			MapUtil.getBoolean(
				siteParameterMap, PortletDataHandlerKeys.PORTLET_DATA));
		Assert.assertTrue(
			MapUtil.getBoolean(
				siteParameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL));
		Assert.assertTrue(
			MapUtil.getBoolean(
				siteParameterMap,
				PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT));
		Assert.assertTrue(
			MapUtil.getBoolean(
				siteParameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS));
	}

	@Test
	public void testToSiteImportParameterMapMirrors() {
		Map<String, String[]> siteParameterMap =
			SiteExportImportParameterUtil.toSiteImportParameterMap(
				HashMapBuilder.put(
					PortletDataHandlerKeys.DATA_STRATEGY,
					new String[] {
						PortletDataHandlerKeys.DATA_STRATEGY_COPY_AS_NEW
					}
				).put(
					PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
					new String[] {
						PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_ADD_AS_NEW
					}
				).build(),
				"erc1");

		Assert.assertEquals(
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR,
			MapUtil.getString(
				siteParameterMap, PortletDataHandlerKeys.DATA_STRATEGY));
		Assert.assertEquals(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID,
			MapUtil.getString(
				siteParameterMap, PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE));
	}

	@Test
	public void testToSiteImportParameterMapRemovesNothing() {
		Map<String, String[]> siteParameterMap =
			SiteExportImportParameterUtil.toSiteImportParameterMap(
				HashMapBuilder.put(
					PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
					new String[] {Boolean.TRUE.toString()}
				).put(
					PortletDataHandlerKeys.DELETE_PORTLET_DATA,
					new String[] {Boolean.TRUE.toString()}
				).build(),
				"erc1");

		Assert.assertFalse(
			MapUtil.getBoolean(
				siteParameterMap,
				PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS));
		Assert.assertFalse(
			MapUtil.getBoolean(
				siteParameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA));
	}

}