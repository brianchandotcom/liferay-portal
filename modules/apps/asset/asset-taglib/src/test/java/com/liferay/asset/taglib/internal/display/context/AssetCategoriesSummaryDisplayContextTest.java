/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.taglib.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
 * @author Jürgen Kappler
 */
public class AssetCategoriesSummaryDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_assetVocabulary = Mockito.mock(AssetVocabulary.class);
		_cmsGroupId = RandomTestUtil.randomLong();
		_companyId = RandomTestUtil.randomLong();

		_groupId = RandomTestUtil.randomLong();

		_setUpAssetEntryLocalServiceUtil(_groupId);

		_setUpAssetVocabularyServiceUtil(_assetVocabulary);
		_setUpGroupLocalServiceUtil(_cmsGroupId, _companyId);
		_setUpSiteConnectedGroupProviderUtil(_groupId);
	}

	@After
	public void tearDown() {
		_assetEntryLocalServiceUtilMockedStatic.close();
		_assetVocabularyServiceUtilMockedStatic.close();
		_depotEntryLocalServiceUtilMockedStatic.close();
		_frameworkUtilMockedStatic.close();
		_groupLocalServiceUtilMockedStatic.close();
		_siteConnectedGroupGroupProviderUtilMockedStatic.close();
	}

	@Test
	public void testGetVocabularies() throws Exception {
		AssetCategoriesSummaryDisplayContext
			assetCategoriesSummaryDisplayContext =
				new AssetCategoriesSummaryDisplayContext(
					new MockHttpServletRequest());

		_testGetVocabularies(
			assetCategoriesSummaryDisplayContext,
			DepotConstants.TYPE_ASSET_LIBRARY,
			assetVocabularies -> Assert.assertEquals(
				assetVocabularies.toString(), 0, assetVocabularies.size()));
		_testGetVocabularies(
			assetCategoriesSummaryDisplayContext, DepotConstants.TYPE_SPACE,
			assetVocabularies -> {
				Assert.assertEquals(
					assetVocabularies.toString(), 1, assetVocabularies.size());

				Assert.assertEquals(_assetVocabulary, assetVocabularies.get(0));
			});
	}

	private void _setUpAssetEntryLocalServiceUtil(long groupId) {
		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.when(
			assetEntry.getGroupId()
		).thenReturn(
			groupId
		);

		_assetEntryLocalServiceUtilMockedStatic.when(
			() -> AssetEntryLocalServiceUtil.fetchEntry(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			assetEntry
		);
	}

	private void _setUpAssetVocabularyServiceUtil(
		AssetVocabulary assetVocabulary) {

		_assetVocabularyServiceUtilMockedStatic.when(
			() -> AssetVocabularyServiceUtil.getGroupVocabularies(new long[0])
		).thenReturn(
			Collections.emptyList()
		);

		_assetVocabularyServiceUtilMockedStatic.when(
			() -> AssetVocabularyServiceUtil.getGroupVocabularies(
				new long[] {_cmsGroupId})
		).thenReturn(
			Arrays.asList(assetVocabulary)
		);
	}

	private void _setUpGroupLocalServiceUtil(long cmsGroupId, long companyId) {
		Group cmsGroup = Mockito.mock(Group.class);

		Mockito.when(
			cmsGroup.getGroupId()
		).thenReturn(
			cmsGroupId
		);

		_groupLocalServiceUtilMockedStatic.when(
			() -> GroupLocalServiceUtil.fetchGroup(
				companyId, GroupConstants.CMS)
		).thenReturn(
			cmsGroup
		);
	}

	private void _setUpSiteConnectedGroupProviderUtil(long groupId) {
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
					getCurrentAndAncestorSiteAndDepotGroupIds(groupId)
		).thenReturn(
			new long[0]
		);
	}

	private void _testGetVocabularies(
			AssetCategoriesSummaryDisplayContext
				assetCategoriesSummaryDisplayContext,
			int type,
			UnsafeConsumer<List<AssetVocabulary>, Exception> unsafeConsumer)
		throws Exception {

		DepotEntry depotEntry = Mockito.mock(DepotEntry.class);

		Mockito.when(
			depotEntry.getCompanyId()
		).thenReturn(
			_companyId
		);

		Mockito.when(
			depotEntry.getType()
		).thenReturn(
			type
		);

		_depotEntryLocalServiceUtilMockedStatic.reset();

		_depotEntryLocalServiceUtilMockedStatic.when(
			() -> DepotEntryLocalServiceUtil.fetchGroupDepotEntry(_groupId)
		).thenReturn(
			depotEntry
		);

		unsafeConsumer.accept(
			assetCategoriesSummaryDisplayContext.getVocabularies(_groupId));
	}

	private static final MockedStatic<AssetEntryLocalServiceUtil>
		_assetEntryLocalServiceUtilMockedStatic = Mockito.mockStatic(
			AssetEntryLocalServiceUtil.class);
	private static final MockedStatic<AssetVocabularyServiceUtil>
		_assetVocabularyServiceUtilMockedStatic = Mockito.mockStatic(
			AssetVocabularyServiceUtil.class);
	private static final MockedStatic<DepotEntryLocalServiceUtil>
		_depotEntryLocalServiceUtilMockedStatic = Mockito.mockStatic(
			DepotEntryLocalServiceUtil.class);
	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);
	private static final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);
	private static MockedStatic<SiteConnectedGroupGroupProviderUtil>
		_siteConnectedGroupGroupProviderUtilMockedStatic;

	private AssetVocabulary _assetVocabulary;
	private long _cmsGroupId;
	private long _companyId;
	private long _groupId;

}