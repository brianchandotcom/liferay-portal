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

package com.liferay.journal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.persistence.JournalArticleResourceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class JournalArticleStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public boolean isAssetPrioritySupported() {
		return true;
	}

	@Test
	public void testArticleCreatedBeforeImportingLayoutDependencies()
		throws Exception {

		initExport();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Layout layout = LayoutTestUtil.addTypePortletLayout(stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, journalArticle);

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, journalArticle, layout,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY);

		initImport();

		StagedModel exportedStagedModel = readExportedStagedModel(
			journalArticle);

		Assert.assertNotNull(exportedStagedModel);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		StagedModelDataHandler<Layout> originalLayoutStagedModelDataHandler =
			(StagedModelDataHandler<Layout>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					Layout.class.getName());

		TestLayoutStagedModelDataHandler testLayoutStagedModelDataHandler =
			new TestLayoutStagedModelDataHandler(
				originalLayoutStagedModelDataHandler);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				StagedModelDataHandler.class, testLayoutStagedModelDataHandler,
				MapUtil.singletonDictionary("service.ranking", 100));

		try {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);

			serviceRegistration.unregister();
		}

		JournalArticle importJournalArticle =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				journalArticle.getUuid(), liveGroup.getGroupId());

		Assert.assertNotNull(importJournalArticle);

		Map<Long, Long> primaryKeys =
			testLayoutStagedModelDataHandler.getPrimaryKeys();

		Assert.assertNotNull(primaryKeys);

		long importedResourcePrimKey = MapUtil.getLong(
			primaryKeys, journalArticle.getResourcePrimKey());

		Assert.assertEquals(
			importJournalArticle.getResourcePrimKey(), importedResourcePrimKey);
	}

	@Test
	public void testArticlesWithSameResourceUUID() throws Exception {
		initExport();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(liveGroup.getGroupId());

		serviceContext.setAttribute(
			"articleResourceUuid", journalArticle.getArticleResourceUuid());
		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		JournalArticle importJournalArticle = JournalTestUtil.addArticle(
			liveGroup.getGroupId(), journalArticle.getFolderId(),
			serviceContext);

		Assert.assertEquals(
			journalArticle.getArticleResourceUuid(),
			importJournalArticle.getArticleResourceUuid());
		Assert.assertEquals(
			liveGroup.getGroupId(), importJournalArticle.getGroupId());
		Assert.assertNotEquals(
			journalArticle.getUuid(), importJournalArticle.getUuid());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, journalArticle);

		initImport();

		StagedModel exportedStagedModel = readExportedStagedModel(
			journalArticle);

		Assert.assertNotNull(exportedStagedModel);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		try {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		importJournalArticle =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				journalArticle.getUuid(), liveGroup.getGroupId());

		Assert.assertNotNull(importJournalArticle);
		Assert.assertEquals(
			journalArticle.getVersion(), importJournalArticle.getVersion(), 0D);
	}

	@Test
	public void testArticleWithSmallImageURL() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		journalArticle.setSmallImage(true);
		journalArticle.setSmallImageURL(RandomTestUtil.randomString());

		journalArticle = JournalTestUtil.updateArticle(journalArticle);

		exportImportStagedModel(journalArticle);
	}

	@Override
	@Test
	public void testCleanAssetCategoriesAndTags() throws Exception {
		ExportImportThreadLocal.setLayoutImportInProcess(true);

		try {
			super.testCleanAssetCategoriesAndTags();
		}
		finally {
			ExportImportThreadLocal.setLayoutImportInProcess(false);
		}
	}

	@Test
	public void testCompanyScopeDependencies() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addCompanyDependencies();

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		initImport();

		StagedModel exportedStagedModel = readExportedStagedModel(stagedModel);

		Assert.assertNotNull(exportedStagedModel);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedStagedModel);

		validateCompanyDependenciesImport(dependentStagedModelsMap, liveGroup);
	}

	@Test
	public void testPreloadedArticlesWithDifferentResourceUUID()
		throws Exception {

		initExport();

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), defaultUser.getUserId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), false, serviceContext);

		JournalTestUtil.addArticle(
			liveGroup.getGroupId(), journalArticle.getFolderId(),
			journalArticle.getArticleId(), false);

		User user = UserTestUtil.addUser();

		journalArticle.setUserId(user.getUserId());

		journalArticle = JournalTestUtil.updateArticle(journalArticle);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, journalArticle);

		initImport();

		StagedModel exportedStagedModel = readExportedStagedModel(
			journalArticle);

		Assert.assertNotNull(exportedStagedModel);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		try {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		JournalArticle importJournalArticle =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				journalArticle.getUuid(), liveGroup.getGroupId());

		Assert.assertNotNull(importJournalArticle);
		Assert.assertEquals(
			journalArticle.getArticleId(), importJournalArticle.getArticleId());
		Assert.assertNotEquals(
			journalArticle.getArticleResourceUuid(),
			importJournalArticle.getArticleResourceUuid());
	}

	public class TestLayoutStagedModelDataHandler
		implements StagedModelDataHandler<Layout> {

		public TestLayoutStagedModelDataHandler(
			StagedModelDataHandler<Layout>
				wrappedLayoutStagedModelDataHandler) {

			_wrappedLayoutStagedModelDataHandler =
				wrappedLayoutStagedModelDataHandler;
		}

		@Override
		public void deleteStagedModel(Layout stagedModel)
			throws PortalException {

			_wrappedLayoutStagedModelDataHandler.deleteStagedModel(stagedModel);
		}

		@Override
		public void deleteStagedModel(
				String uuid, long groupId, String className, String extraData)
			throws PortalException {

			_wrappedLayoutStagedModelDataHandler.deleteStagedModel(
				uuid, groupId, className, extraData);
		}

		@Override
		public void exportStagedModel(
				PortletDataContext portletDataContext, Layout stagedModel)
			throws PortletDataException {

			_wrappedLayoutStagedModelDataHandler.exportStagedModel(
				portletDataContext, stagedModel);
		}

		@Override
		public Layout fetchMissingReference(String uuid, long groupId) {
			return _wrappedLayoutStagedModelDataHandler.fetchMissingReference(
				uuid, groupId);
		}

		@Override
		public Layout fetchStagedModelByUuidAndGroupId(
			String uuid, long groupId) {

			return _wrappedLayoutStagedModelDataHandler.
				fetchStagedModelByUuidAndGroupId(uuid, groupId);
		}

		@Override
		public List<Layout> fetchStagedModelsByUuidAndCompanyId(
			String uuid, long companyId) {

			return _wrappedLayoutStagedModelDataHandler.
				fetchStagedModelsByUuidAndCompanyId(uuid, companyId);
		}

		@Override
		public String[] getClassNames() {
			return _wrappedLayoutStagedModelDataHandler.getClassNames();
		}

		@Override
		public String getDisplayName(Layout stagedModel) {
			return _wrappedLayoutStagedModelDataHandler.getDisplayName(
				stagedModel);
		}

		@Override
		public int[] getExportableStatuses() {
			return _wrappedLayoutStagedModelDataHandler.getExportableStatuses();
		}

		public Map<Long, Long> getPrimaryKeys() {
			return _primaryKeys;
		}

		@Override
		public Map<String, String> getReferenceAttributes(
			PortletDataContext portletDataContext, Layout stagedModel) {

			return _wrappedLayoutStagedModelDataHandler.getReferenceAttributes(
				portletDataContext, stagedModel);
		}

		@Override
		public void importMissingReference(
				PortletDataContext portletDataContext, Element referenceElement)
			throws PortletDataException {

			_wrappedLayoutStagedModelDataHandler.importMissingReference(
				portletDataContext, referenceElement);
		}

		@Override
		public void importMissingReference(
				PortletDataContext portletDataContext, String uuid,
				long groupId, long classPK)
			throws PortletDataException {

			_wrappedLayoutStagedModelDataHandler.importMissingReference(
				portletDataContext, uuid, groupId, classPK);
		}

		@Override
		public void importStagedModel(
				PortletDataContext portletDataContext, Layout stagedModel)
			throws PortletDataException {

			if (_primaryKeys == null) {
				_primaryKeys = new HashMap<>();

				Map<Long, Long> primaryKeys =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						JournalArticle.class);

				MapUtil.copy(primaryKeys, _primaryKeys);
			}

			_wrappedLayoutStagedModelDataHandler.importStagedModel(
				portletDataContext, stagedModel);
		}

		@Override
		public void restoreStagedModel(
				PortletDataContext portletDataContext, Layout stagedModel)
			throws PortletDataException {

			_wrappedLayoutStagedModelDataHandler.restoreStagedModel(
				portletDataContext, stagedModel);
		}

		@Override
		public boolean validateReference(
			PortletDataContext portletDataContext, Element referenceElement) {

			return _wrappedLayoutStagedModelDataHandler.validateReference(
				portletDataContext, referenceElement);
		}

		private Map<Long, Long> _primaryKeys;
		private final StagedModelDataHandler<Layout>
			_wrappedLayoutStagedModelDataHandler;

	}

	protected Map<String, List<StagedModel>> addCompanyDependencies()
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Company company = CompanyLocalServiceUtil.fetchCompany(
			stagingGroup.getCompanyId());

		Group companyGroup = company.getGroup();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			companyGroup.getGroupId(), JournalArticle.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			companyGroup.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate);

		JournalFolder folder = JournalTestUtil.addFolder(
			stagingGroup.getGroupId(), RandomTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, JournalFolder.class, folder);

		return dependentStagedModelsMap;
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new LinkedHashMap<>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate);

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, JournalFolder.class, folder);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(JournalFolder.class.getSimpleName());

		JournalFolder folder = (JournalFolder)folderDependentStagedModels.get(
			0);

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		DDMTemplate ddmTemplate =
			(DDMTemplate)ddmTemplateDependentStagedModels.get(0);

		return JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), folder.getFolderId(),
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			DDMStructureTestUtil.getSampleStructuredContent(),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey());
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		stagedModels.add(
			JournalTestUtil.addArticleWithWorkflow(group.getGroupId(), true));

		stagedModels.add(
			JournalTestUtil.addArticleWithWorkflow(group.getGroupId(), false));

		JournalArticle expiredArticle = JournalTestUtil.addArticleWithWorkflow(
			group.getGroupId(), true);

		expiredArticle = JournalArticleLocalServiceUtil.expireArticle(
			TestPropsValues.getUserId(), group.getGroupId(),
			expiredArticle.getArticleId(), expiredArticle.getVersion(),
			expiredArticle.getUrlTitle(),
			ServiceContextTestUtil.getServiceContext());

		stagedModels.add(expiredArticle);

		return stagedModels;
	}

	@Override
	protected AssetEntry fetchAssetEntry(StagedModel stagedModel, Group group)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		JournalArticleResource articleResource =
			JournalArticleResourceLocalServiceUtil.getArticleResource(
				article.getResourcePrimKey());

		return AssetEntryLocalServiceUtil.fetchEntry(
			group.getGroupId(), articleResource.getUuid());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return JournalArticleLocalServiceUtil.getJournalArticleByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return JournalArticle.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

	protected void validateCompanyDependenciesImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		Assert.assertEquals(
			ddmStructureDependentStagedModels.toString(), 1,
			ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		Assert.assertNull(
			"Company DDM structure dependency should not be imported",
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				ddmStructure.getUuid(), group.getGroupId()));

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		Assert.assertEquals(
			ddmTemplateDependentStagedModels.toString(), 1,
			ddmTemplateDependentStagedModels.size());

		DDMTemplate ddmTemplate =
			(DDMTemplate)ddmTemplateDependentStagedModels.get(0);

		Assert.assertNull(
			"Company DDM template dependency should not be imported",
			DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
				ddmTemplate.getUuid(), group.getGroupId()));

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(JournalFolder.class.getSimpleName());

		Assert.assertEquals(
			folderDependentStagedModels.toString(), 1,
			folderDependentStagedModels.size());

		JournalFolder folder = (JournalFolder)folderDependentStagedModels.get(
			0);

		JournalFolderLocalServiceUtil.getJournalFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		Assert.assertEquals(
			ddmStructureDependentStagedModels.toString(), 1,
			ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		Assert.assertEquals(
			ddmTemplateDependentStagedModels.toString(), 1,
			ddmTemplateDependentStagedModels.size());

		DDMTemplate ddmTemplate =
			(DDMTemplate)ddmTemplateDependentStagedModels.get(0);

		DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
			ddmTemplate.getUuid(), group.getGroupId());

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(JournalFolder.class.getSimpleName());

		Assert.assertEquals(
			folderDependentStagedModels.toString(), 1,
			folderDependentStagedModels.size());

		JournalFolder folder = (JournalFolder)folderDependentStagedModels.get(
			0);

		JournalFolderLocalServiceUtil.getJournalFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		JournalArticleResource articleResource =
			JournalArticleResourceUtil.fetchByUUID_G(
				article.getArticleResourceUuid(), group.getGroupId());

		Assert.assertNotNull(articleResource);

		JournalArticle importedArticle =
			JournalArticleLocalServiceUtil.getLatestArticle(
				articleResource.getResourcePrimKey(), article.getStatus(),
				false);

		validateAssets(importedArticle, stagedModelAssets, group);

		validateComments(article, importedArticle, group);

		validateImport(dependentStagedModelsMap, group);
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		JournalArticle article = (JournalArticle)stagedModel;
		JournalArticle importedArticle = (JournalArticle)importedStagedModel;

		Assert.assertEquals(
			article.getExternalReferenceCode(),
			importedArticle.getExternalReferenceCode());
		Assert.assertEquals(
			article.getUrlTitle(), importedArticle.getUrlTitle());
		Assert.assertEquals(
			article.getDescription(), importedArticle.getDescription());
		Assert.assertEquals(
			article.getDisplayDate(), importedArticle.getDisplayDate());
		Assert.assertEquals(
			article.getExpirationDate(), importedArticle.getExpirationDate());
		Assert.assertEquals(
			article.getReviewDate(), importedArticle.getReviewDate());
		Assert.assertEquals(
			article.isIndexable(), importedArticle.isIndexable());
		Assert.assertEquals(
			article.isSmallImage(), importedArticle.isSmallImage());
		Assert.assertEquals(
			article.getSmallImageURL(), importedArticle.getSmallImageURL());
	}

}