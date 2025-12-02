/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.resource.v1_0.test;

import com.liferay.analytics.cms.rest.client.dto.v1_0.InventoryAnalysis;
import com.liferay.analytics.cms.rest.client.dto.v1_0.InventoryAnalysisItem;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rachael Koestartyo
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-34594")}
)
@RunWith(Arquillian.class)
public class InventoryAnalysisResourceTest
	extends BaseInventoryAnalysisResourceTestCase {

	@Override
	@Test
	public void testGetInventoryAnalysis() throws Exception {
		_setUpCMSContext();

		InventoryAnalysis inventoryAnalysis =
			inventoryAnalysisResource.getInventoryAnalysis(
				null, _depotEntry.getDepotEntryId(), null, null, null, null,
				null, null, null, null, null);

		InventoryAnalysisItem[] inventoryAnalysisItems =
			inventoryAnalysis.getInventoryAnalysisItems();

		Assert.assertEquals(
			inventoryAnalysisItems.toString(), 1,
			inventoryAnalysisItems.length);

		InventoryAnalysisItem inventoryAnalysisItem = inventoryAnalysisItems[0];

		Assert.assertEquals(3L, (long)inventoryAnalysisItem.getCount());

		Assert.assertEquals(
			"Basic Web Content", inventoryAnalysisItem.getTitle());

		inventoryAnalysis = inventoryAnalysisResource.getInventoryAnalysis(
			null, _depotEntry.getDepotEntryId(), "category", null, null, null,
			null, null, null, null, null);

		Assert.assertEquals(
			2L, (long)inventoryAnalysis.getInventoryAnalysisItemsCount());

		inventoryAnalysisItems = inventoryAnalysis.getInventoryAnalysisItems();

		Assert.assertEquals(
			inventoryAnalysisItems.toString(), 2,
			inventoryAnalysisItems.length);

		inventoryAnalysisItem = inventoryAnalysisItems[0];

		Assert.assertEquals(2L, (long)inventoryAnalysisItem.getCount());

		Assert.assertEquals("Unknown", inventoryAnalysisItem.getTitle());

		inventoryAnalysisItem = inventoryAnalysisItems[1];

		Assert.assertEquals(1L, (long)inventoryAnalysisItem.getCount());

		Assert.assertEquals("My Category", inventoryAnalysisItem.getTitle());

		inventoryAnalysis = inventoryAnalysisResource.getInventoryAnalysis(
			_assetCategory.getCategoryId(), _depotEntry.getDepotEntryId(),
			"category", null, null, null, null, null, null, null, null);

		Assert.assertEquals(
			1L, (long)inventoryAnalysis.getInventoryAnalysisItemsCount());

		inventoryAnalysisItems = inventoryAnalysis.getInventoryAnalysisItems();

		Assert.assertEquals(
			inventoryAnalysisItems.toString(), 1,
			inventoryAnalysisItems.length);

		inventoryAnalysisItem = inventoryAnalysisItems[0];

		Assert.assertEquals(1L, (long)inventoryAnalysisItem.getCount());

		Assert.assertEquals("My Category", inventoryAnalysisItem.getTitle());
	}

	private void _setUpCMSContext() throws Exception {
		CMSTestUtil.getOrAddGroup(InventoryAnalysisResourceTest.class);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testGroup.getGroupId(), TestPropsValues.getUserId());

		_depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_ASSET_LIBRARY, _serviceContext);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT", testCompany.getCompanyId());

		Map<String, Serializable> objectEntryValues =
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build();

		_objectEntries.add(
			ObjectEntryTestUtil.addObjectEntry(
				_depotEntry.getGroupId(), objectDefinition, objectEntryValues));

		_assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), _depotEntry.getGroupId(),
			"My Vocabulary", _serviceContext);

		_assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _depotEntry.getGroupId(),
			"My Category", _assetVocabulary.getVocabularyId(), _serviceContext);

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			_depotEntry.getGroupId(), objectDefinition, objectEntryValues);

		_objectEntries.add(objectEntry);

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			objectDefinition.getClassName(), objectEntry.getObjectEntryId());

		_assetEntryAssetCategoryRel =
			_assetEntryAssetCategoryRelLocalService.
				addAssetEntryAssetCategoryRel(
					assetEntry.getEntryId(), _assetCategory.getCategoryId());

		_objectEntries.add(
			ObjectEntryTestUtil.addObjectEntry(
				_depotEntry.getGroupId(), objectDefinition, objectEntryValues,
				RandomTestUtil.randomString()));
	}

	@DeleteAfterTestRun
	private AssetCategory _assetCategory;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@DeleteAfterTestRun
	private AssetEntryAssetCategoryRel _assetEntryAssetCategoryRel;

	@Inject
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private List<ObjectEntry> _objectEntries = new ArrayList<>();

	private ServiceContext _serviceContext;

}