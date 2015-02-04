/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar.test;

import com.liferay.portal.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.lar.CurrentUserIdStrategy;
import com.liferay.portal.lar.PortletExporter;
import com.liferay.portal.lar.PortletImporter;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.util.test.AssetTestUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.ratings.util.test.RatingsTestUtil;

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

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public abstract class BaseStagedModelDataHandlerTestCase {

	@Before
	public void setUp() throws Exception {
		liveGroup = GroupTestUtil.addGroup();
		stagingGroup = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(stagingGroup.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testStagedModelDataHandler() throws Exception {

		// Export

		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		// Assets

		StagedModelAssets stagedModelAssets = updateAssetEntry(
			stagedModel, stagingGroup);

		// Comments

		addComments(stagedModel);

		// Ratings

		addRatings(stagedModel);

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

	protected void addComments(StagedModel stagedModel) throws Exception {
		if (!isCommentableStagedModel()) {
			return;
		}

		MBTestUtil.addDiscussionMessage(
			stagingGroup.getGroupId(),
			ExportImportClassedModelUtil.getClassName(stagedModel),
			ExportImportClassedModelUtil.getClassPK(stagedModel));
	}

	protected List<StagedModel> addDependentStagedModel(
		Map<String, List<StagedModel>> dependentStagedModelsMap, Class<?> clazz,
		StagedModel dependentStagedModel) {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			clazz.getSimpleName());

		if (dependentStagedModels == null) {
			dependentStagedModels = new ArrayList<>();

			dependentStagedModelsMap.put(
				clazz.getSimpleName(), dependentStagedModels);
		}

		dependentStagedModels.add(dependentStagedModel);

		return dependentStagedModels;
	}

	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		return new HashMap<>();
	}

	protected void addRatings(StagedModel stagedModel) throws Exception {
		RatingsTestUtil.addEntry(
			ExportImportClassedModelUtil.getClassName(stagedModel),
			ExportImportClassedModelUtil.getClassPK(stagedModel));
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

	protected AssetEntry fetchAssetEntry(StagedModel stagedModel, Group group)
		throws Exception {

		return AssetEntryLocalServiceUtil.fetchEntry(
			group.getGroupId(), stagedModel.getUuid());
	}

	protected Date getEndDate() {
		return new Date();
	}

	protected Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap = new LinkedHashMap<>();

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
		PortletExporter portletExporter = PortletExporter.getInstance();

		ReflectionTestUtil.invoke(
			portletExporter, "exportAssetTags",
			new Class<?>[] {PortletDataContext.class}, portletDataContext);

		userIdStrategy = new CurrentUserIdStrategy(TestPropsValues.getUser());
		zipReader = ZipReaderFactoryUtil.getZipReader(zipWriter.getFile());

		String xml = zipReader.getEntryAsString("/manifest.xml");

		if (xml == null) {
			Document document = SAXReaderUtil.createDocument();

			Element rootElement = document.addElement("root");

			rootElement.addElement("header");

			zipWriter.addEntry("/manifest.xml", document.asXML());

			zipReader = ZipReaderFactoryUtil.getZipReader(zipWriter.getFile());
		}

		portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				liveGroup.getCompanyId(), liveGroup.getGroupId(),
				getParameterMap(), userIdStrategy, zipReader);

		portletDataContext.setImportDataRootElement(rootElement);

		Group sourceCompanyGroup = GroupLocalServiceUtil.getCompanyGroup(
			stagingGroup.getCompanyId());

		portletDataContext.setSourceCompanyGroupId(
			sourceCompanyGroup.getGroupId());

		portletDataContext.setSourceCompanyId(stagingGroup.getCompanyId());
		portletDataContext.setSourceGroupId(stagingGroup.getGroupId());

		PortletImporter portletImporter = PortletImporter.getInstance();

		ReflectionTestUtil.invoke(
			portletImporter, "readAssetTags",
			new Class<?>[] {PortletDataContext.class}, portletDataContext);
	}

	protected boolean isCommentableStagedModel() {
		return false;
	}

	protected StagedModel readExportedStagedModel(StagedModel stagedModel) {
		String stagedModelPath = ExportImportPathUtil.getModelPath(stagedModel);

		StagedModel exportedStagedModel =
			(StagedModel)portletDataContext.getZipEntryAsObject(
				stagedModelPath);

		return exportedStagedModel;
	}

	protected StagedModelAssets updateAssetEntry(
			StagedModel stagedModel, Group group)
		throws Exception {

		AssetEntry assetEntry = fetchAssetEntry(stagedModel, group);

		if (assetEntry == null) {
			return null;
		}

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			stagingGroup.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			stagingGroup.getGroupId(), assetVocabulary.getVocabularyId());

		Company company = CompanyLocalServiceUtil.getCompany(
			stagedModel.getCompanyId());

		Group companyGroup = company.getGroup();

		AssetVocabulary companyAssetVocabulary = AssetTestUtil.addVocabulary(
			companyGroup.getGroupId());

		AssetCategory companyAssetCategory = AssetTestUtil.addCategory(
			companyGroup.getGroupId(),
			companyAssetVocabulary.getVocabularyId());

		AssetTag assetTag = AssetTestUtil.addTag(stagingGroup.getGroupId());

		AssetEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(),
			new long[] {
				assetCategory.getCategoryId(),
				companyAssetCategory.getCategoryId()
			},
			new String[] {assetTag.getName()});

		return new StagedModelAssets(assetCategory, assetTag, assetVocabulary);
	}

	protected void validateAssets(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Group group)
		throws Exception {

		if (stagedModelAssets == null) {
			return;
		}

		AssetEntry assetEntry = fetchAssetEntry(stagedModel, group);

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getEntryCategories(
				assetEntry.getEntryId());

		Assert.assertEquals(2, assetCategories.size());

		AssetCategory stagedAssetCategory =
			stagedModelAssets.getAssetCategory();

		AssetCategory importedAssetCategory = null;

		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		long companyGroupId = company.getGroupId();

		for (AssetCategory assetCategory : assetCategories) {
			long groupId = assetCategory.getGroupId();

			if (groupId != companyGroupId) {
				importedAssetCategory = assetCategory;

				break;
			}
		}

		Assert.assertEquals(
			stagedAssetCategory.getUuid(), importedAssetCategory.getUuid());

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

	protected void validateComments(
			StagedModel stagedModel, StagedModel importedStagedModel,
			Group group)
		throws Exception {

		if (!isCommentableStagedModel()) {
			return;
		}

		List<MBMessage> discussionMBMessages =
			MBMessageLocalServiceUtil.getMessages(
				ExportImportClassedModelUtil.getClassName(stagedModel),
				ExportImportClassedModelUtil.getClassPK(stagedModel),
				WorkflowConstants.STATUS_ANY);

		if (ListUtil.isEmpty(discussionMBMessages)) {
			return;
		}

		int importedDiscussionMBMessagesCount =
			MBMessageLocalServiceUtil.getDiscussionMessagesCount(
				ExportImportClassedModelUtil.getClassName(importedStagedModel),
				ExportImportClassedModelUtil.getClassPK(importedStagedModel),
				WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			discussionMBMessages.size(), importedDiscussionMBMessagesCount + 1);

		for (MBMessage discussionMBMessage : discussionMBMessages) {
			if (discussionMBMessage.isRoot()) {
				continue;
			}

			MBMessage importedDiscussionMBMessage =
				MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
					discussionMBMessage.getUuid(), group.getGroupId());

			Assert.assertNotNull(importedDiscussionMBMessage);
		}
	}

	protected void validateExport(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		Element rootElement = portletDataContext.getExportDataRootElement();

		List<Element> stagedModelGroupElements = new ArrayList<>();

		Class<?> stagedModelClass = getStagedModelClass();

		String stagedModelClassSimpleName = stagedModelClass.getSimpleName();

		stagedModelGroupElements.addAll(
			rootElement.elements(stagedModelClassSimpleName));

		for (String dependentStagedModelClassSimpleName :
				dependentStagedModelsMap.keySet()) {

			stagedModelGroupElements.addAll(
				rootElement.elements(dependentStagedModelClassSimpleName));
		}

		for (Element stagedModelGroupElement : stagedModelGroupElements) {
			String className = stagedModelGroupElement.getName();

			List<StagedModel> dependentStagedModels =
				dependentStagedModelsMap.get(className);

			if (dependentStagedModels == null) {
				dependentStagedModels = new ArrayList<>();
			}
			else {
				dependentStagedModels = ListUtil.copy(dependentStagedModels);
			}

			if (className.equals(stagedModelClassSimpleName)) {
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

		validateAssets(importedStagedModel, stagedModelAssets, group);

		validateComments(stagedModel, importedStagedModel, group);

		validateImport(dependentStagedModelsMap, group);

		validateRatings(stagedModel, importedStagedModel);
	}

	protected void validateRatings(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		List<RatingsEntry> ratingsEntries =
			RatingsEntryLocalServiceUtil.getEntries(
				ExportImportClassedModelUtil.getClassName(stagedModel),
				ExportImportClassedModelUtil.getClassPK(stagedModel),
				WorkflowConstants.STATUS_ANY);

		List<RatingsEntry> importedRatingsEntries =
			RatingsEntryLocalServiceUtil.getEntries(
				ExportImportClassedModelUtil.getClassName(importedStagedModel),
				ExportImportClassedModelUtil.getClassPK(importedStagedModel),
				WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			ratingsEntries.size(), importedRatingsEntries.size());

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			Iterator<RatingsEntry> iterator = importedRatingsEntries.iterator();

			while (iterator.hasNext()) {
				RatingsEntry importedRatingsEntry = iterator.next();

				if (ratingsEntry.getScore() ==
						importedRatingsEntry.getScore()) {

					iterator.remove();

					break;
				}
			}
		}

		Assert.assertTrue(importedRatingsEntries.isEmpty());
	}

	@DeleteAfterTestRun
	protected Group liveGroup;

	protected Element missingReferencesElement;
	protected PortletDataContext portletDataContext;
	protected Element rootElement;

	@DeleteAfterTestRun
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