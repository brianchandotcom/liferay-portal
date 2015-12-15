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

package com.liferay.journal.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.persistence.JournalArticleResourceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.lar.test.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

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

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
@Sync
public class JournalArticleStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

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
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
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

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		JournalArticle article = (JournalArticle)stagedModel;
		JournalArticle importedArticle = (JournalArticle)importedStagedModel;

		Assert.assertEquals(article.getTitle(), importedArticle.getTitle());
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