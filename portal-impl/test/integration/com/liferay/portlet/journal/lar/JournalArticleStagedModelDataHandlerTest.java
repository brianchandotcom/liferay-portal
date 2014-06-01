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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalArticleStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	public static TransactionalTestRule transactionalTestRule =
		new TransactionalTestRule();

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

	protected Map<String, List<StagedModel>> addCompanyDependencies()
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		Company company = CompanyLocalServiceUtil.fetchCompany(
			stagingGroup.getCompanyId());

		Group companyGroup = company.getGroup();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			companyGroup.getGroupId(), JournalArticle.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			companyGroup.getGroupId(), ddmStructure.getStructureId());

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
			new HashMap<String, List<StagedModel>>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate);

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
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			DDMStructureTestUtil.getSampleStructuredContent(),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey());
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<StagedModel>();

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
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return JournalArticleLocalServiceUtil.
				getJournalArticleByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
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

		Assert.assertEquals(1, ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		Assert.assertNull(
			"Company DDM structure dependency should not be imported",
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				ddmStructure.getUuid(), group.getGroupId()));

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		Assert.assertEquals(1, ddmTemplateDependentStagedModels.size());

		DDMTemplate ddmTemplate =
			(DDMTemplate)ddmTemplateDependentStagedModels.get(0);

		Assert.assertNull(
			"Company DDM template dependency should not be imported",
			DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
				ddmTemplate.getUuid(), group.getGroupId()));

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(JournalFolder.class.getSimpleName());

		Assert.assertEquals(1, folderDependentStagedModels.size());

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

		Assert.assertEquals(1, ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		Assert.assertEquals(1, ddmTemplateDependentStagedModels.size());

		DDMTemplate ddmTemplate =
			(DDMTemplate)ddmTemplateDependentStagedModels.get(0);

		DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
			ddmTemplate.getUuid(), group.getGroupId());

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(JournalFolder.class.getSimpleName());

		Assert.assertEquals(1, folderDependentStagedModels.size());

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

}