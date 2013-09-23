/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.lar;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public abstract class BaseStagedModelDataHandlerTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		liveGroup = GroupTestUtil.addGroup();
		stagingGroup = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			stagingGroup.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(liveGroup);
		GroupLocalServiceUtil.deleteGroup(stagingGroup);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	@Transactional
	public void testStagedModelDataHandler() throws Exception {

		// Export

		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		StagedModelAssets stagedModelAssets = updateAssetEntry(stagedModel);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		validateExport(
			portletDataContext, stagedModel, dependentStagedModelsMap);

		// Import

		initImport();

		deleteStagedModel(stagedModel, dependentStagedModelsMap, stagingGroup);

		// Reread the staged model for import from ZIP for true testing

		StagedModel exportedStagedModel = readExportedStagedModel(stagedModel);

		Assert.assertNotNull(exportedStagedModel);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedStagedModel);

		validateImport(
			stagedModel, stagedModelAssets, dependentStagedModelsMap,
			liveGroup);
	}

	protected List<StagedModel> addDependentStagedModel(
		Map<String, List<StagedModel>> dependentStagedModelsMap, Class<?> clazz,
		StagedModel dependentStagedModel) {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			clazz.getSimpleName());

		if (dependentStagedModels == null) {
			dependentStagedModels = new ArrayList<StagedModel>();

			dependentStagedModelsMap.put(
				clazz.getSimpleName(), dependentStagedModels);
		}

		dependentStagedModels.add(dependentStagedModel);

		return dependentStagedModels;
	}

	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		return new HashMap<String, List<StagedModel>>();
	}

	protected abstract StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception;

	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {
	}

	protected Date getEndDate() {
		return new Date();
	}

	protected Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE});
		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected abstract StagedModel getStagedModel(String uuid, Group group);

	protected abstract Class<? extends StagedModel> getStagedModelClass();

	protected Date getStartDate() {
		return new Date(System.currentTimeMillis() - Time.HOUR);
	}

	protected void initExport() throws Exception {
		zipWriter = ZipWriterFactoryUtil.getZipWriter();

		portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				getParameterMap(), getStartDate(), getEndDate(), zipWriter);

		rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		missingReferencesElement = SAXReaderUtil.createElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);
	}

	protected void initImport() throws Exception {
		PortletExporter portletExporter = new PortletExporter();

		portletExporter.exportAssetCategories(portletDataContext);
		portletExporter.exportAssetTags(portletDataContext);

		userIdStrategy = new CurrentUserIdStrategy(TestPropsValues.getUser());
		zipReader = ZipReaderFactoryUtil.getZipReader(zipWriter.getFile());

		portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				liveGroup.getCompanyId(), liveGroup.getGroupId(),
				getParameterMap(), userIdStrategy, zipReader);

		portletDataContext.setImportDataRootElement(rootElement);
		portletDataContext.setSourceGroupId(stagingGroup.getGroupId());

		PortletImporter portletImporter = new PortletImporter();

		portletImporter.readAssetCategories(portletDataContext);
		portletImporter.readAssetTags(portletDataContext);
	}

	protected StagedModel readExportedStagedModel(StagedModel stagedModel) {
		String stagedModelPath = ExportImportPathUtil.getModelPath(stagedModel);

		StagedModel exportedStagedModel =
			(StagedModel)portletDataContext.getZipEntryAsObject(
				stagedModelPath);

		return exportedStagedModel;
	}

	protected StagedModelAssets updateAssetEntry(StagedModel stagedModel)
		throws Exception {

		AssetEntry assetEntry = null;

		try {
			assetEntry = AssetEntryLocalServiceUtil.getEntry(
				stagingGroup.getGroupId(), stagedModel.getUuid());
		}
		catch (NoSuchEntryException nsee) {
			return null;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
				serviceContext);

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			assetVocabulary.getVocabularyId(), serviceContext);

		AssetTag assetTag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(), null,
			serviceContext);

		AssetEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(),
			new long[] {assetCategory.getCategoryId()},
			new String[] {assetTag.getName()});

		return new StagedModelAssets(assetCategory, assetTag, assetVocabulary);
	}

	protected void validateAssets(
			String classUuid, StagedModelAssets stagedModelAssets, Group group)
		throws Exception {

		if (stagedModelAssets == null) {
			return;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			group.getGroupId(), classUuid);

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getEntryCategories(
				assetEntry.getEntryId());

		Assert.assertEquals(1, assetCategories.size());

		AssetCategory assetCategory = stagedModelAssets.getAssetCategory();
		AssetCategory importedAssetCategory = assetCategories.get(0);

		Assert.assertEquals(
			assetCategory.getUuid(), importedAssetCategory.getUuid());

		List<AssetTag> assetTags = AssetTagLocalServiceUtil.getEntryTags(
			assetEntry.getEntryId());

		Assert.assertEquals(1, assetTags.size());

		AssetTag assetTag = stagedModelAssets.getAssetTag();
		AssetTag importedAssetTag = assetTags.get(0);

		Assert.assertEquals(assetTag.getName(), importedAssetTag.getName());

		AssetVocabulary assetVocabulary =
			stagedModelAssets.getAssetVocabulary();
		AssetVocabulary importedAssetVocabulary =
			AssetVocabularyLocalServiceUtil.getVocabulary(
				importedAssetCategory.getVocabularyId());

		Assert.assertEquals(
			assetVocabulary.getUuid(), importedAssetVocabulary.getUuid());
	}

	protected void validateExport(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		Element rootElement = portletDataContext.getExportDataRootElement();

		List<Element> stagedModelGroupElements = rootElement.elements();

		for (Element stagedModelGroupElement : stagedModelGroupElements) {
			String className = stagedModelGroupElement.getName();

			List<StagedModel> dependentStagedModels =
				dependentStagedModelsMap.get(className);

			if (dependentStagedModels == null) {
				dependentStagedModels = new ArrayList<StagedModel>();
			}
			else {
				dependentStagedModels = ListUtil.copy(dependentStagedModels);
			}

			Class<?> stagedModelClass = getStagedModelClass();

			if (className.equals(stagedModelClass.getSimpleName())) {
				dependentStagedModels.add(stagedModel);
			}

			List<Element> elements = stagedModelGroupElement.elements();

			Assert.assertEquals(dependentStagedModels.size(), elements.size());

			for (Element element : elements) {
				String path = element.attributeValue("path");

				Assert.assertNotNull(path);

				Iterator<StagedModel> iterator =
					dependentStagedModels.iterator();

				while (iterator.hasNext()) {
					StagedModel dependentStagedModel = iterator.next();

					String dependentStagedModelPath =
						ExportImportPathUtil.getModelPath(dependentStagedModel);

					if (path.equals(dependentStagedModelPath)) {
						iterator.remove();
					}
				}
			}

			Assert.assertTrue(
				"There is more than one element exported with the same path",
				dependentStagedModels.isEmpty());
		}
	}

	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {
	}

	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), group);

		Assert.assertNotNull(importedStagedModel);

		validateAssets(importedStagedModel.getUuid(), stagedModelAssets, group);

		validateImport(dependentStagedModelsMap, group);
	}

	protected Group liveGroup;
	protected Element missingReferencesElement;
	protected PortletDataContext portletDataContext;
	protected Element rootElement;
	protected Group stagingGroup;
	protected UserIdStrategy userIdStrategy;
	protected ZipReader zipReader;
	protected ZipWriter zipWriter;

	protected class StagedModelAssets implements Serializable {

		public StagedModelAssets(
			AssetCategory assetCategory, AssetTag assetTag,
			AssetVocabulary assetVocabulary) {

			_assetCategory = assetCategory;
			_assetTag = assetTag;
			_assetVocabulary = assetVocabulary;
		}

		public AssetCategory getAssetCategory() {
			return _assetCategory;
		}

		public AssetTag getAssetTag() {
			return _assetTag;
		}

		public AssetVocabulary getAssetVocabulary() {
			return _assetVocabulary;
		}

		public void setAssetCategory(AssetCategory assetCategory) {
			_assetCategory = assetCategory;
		}

		public void setAssetTag(AssetTag assetTag) {
			_assetTag = assetTag;
		}

		public void setAssetVocabulary(AssetVocabulary assetVocabulary) {
			_assetVocabulary = assetVocabulary;
		}

		private AssetCategory _assetCategory;
		private AssetTag _assetTag;
		private AssetVocabulary _assetVocabulary;

	}

}