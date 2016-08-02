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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exportimport.data.handler.JournalPortletDataHandler;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.test.BasePortletExportImportTestCase;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@RunWith(Arquillian.class)
@Sync
public class JournalExportImportTest extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getNamespace() {
		return JournalPortletDataHandler.NAMESPACE;
	}

	@Override
	public String getPortletId() {
		return JournalPortletKeys.JOURNAL;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testExportImportCompanyScopeStructuredJournalArticle()
		throws Exception {

		exportImportJournalArticle(true);
	}

	@Test
	public void testExportImportJournalArticleWithoutVersionHistory()
		throws Exception {

		JournalArticle article = (JournalArticle)addStagedModel(
			group.getGroupId());

		article = (JournalArticle)addVersion(article);

		int articlesCount = JournalArticleLocalServiceUtil.getArticlesCount(
			group.getGroupId(), article.getArticleId());

		Assert.assertEquals(2, articlesCount);

		Map<String, String[]> exportParameterMap = new HashMap<>();

		addParameter(exportParameterMap, "version-history", false);

		exportImportPortlet(
			JournalPortletKeys.JOURNAL, exportParameterMap,
			new HashMap<String, String[]>());

		JournalArticle importedArticle = (JournalArticle)getStagedModel(
			article.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(importedArticle);

		articlesCount = JournalArticleLocalServiceUtil.getArticlesCount(
			importedGroup.getGroupId(), importedArticle.getArticleId());

		Assert.assertEquals(1, articlesCount);
	}

	@Test
	public void testExportImportStructuredJournalArticle() throws Exception {
		exportImportJournalArticle(false);
	}

	@Override
	protected StagedModel addStagedModel(long groupId) throws Exception {
		return JournalTestUtil.addArticle(
			groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Override
	protected StagedModel addStagedModel(long groupId, Date createdDate)
		throws Exception {

		String title = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setCreateDate(createdDate);
		serviceContext.setLayoutFullURL("http://localhost");
		serviceContext.setModifiedDate(createdDate);

		return JournalTestUtil.addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, title, title,
			RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(), false,
			false, serviceContext);
	}

	@Override
	protected StagedModel addVersion(StagedModel stagedModel) throws Exception {
		JournalArticle article = (JournalArticle)stagedModel;

		return JournalTestUtil.updateArticle(
			article, RandomTestUtil.randomString());
	}

	@Override
	protected void deleteFirstVersion(StagedModel stagedModel)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles(
				article.getGroupId(), article.getArticleId());

		JournalArticle firstArticle = null;

		for (JournalArticle journalArticle : articles) {
			if ((firstArticle == null) ||
				(journalArticle.getVersion() < firstArticle.getVersion())) {

				firstArticle = journalArticle;
			}
		}

		deleteStagedModel(firstArticle);
	}

	@Override
	protected void deleteLatestVersion(StagedModel stagedModel)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.getLatestArticle(
				article.getGroupId(), article.getArticleId());

		deleteStagedModel(latestArticle);
	}

	@Override
	protected void deleteStagedModel(StagedModel stagedModel) throws Exception {
		JournalArticleLocalServiceUtil.deleteArticle(
			(JournalArticle)stagedModel);
	}

	protected void exportImportJournalArticle(boolean companyScopeDependencies)
		throws Exception {

		JournalArticle article = null;
		DDMStructure ddmStructure = null;
		DDMTemplate ddmTemplate = null;

		long groupId = group.getGroupId();

		Company company = CompanyLocalServiceUtil.fetchCompany(
			group.getCompanyId());

		Group companyGroup = company.getGroup();

		if (companyScopeDependencies) {
			groupId = companyGroup.getGroupId();
		}

		ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, JournalArticle.class.getName());

		ddmTemplate = DDMTemplateTestUtil.addTemplate(
			groupId, ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		article = JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		exportImportPortlet(JournalPortletKeys.JOURNAL);

		int articlesCount = JournalArticleLocalServiceUtil.getArticlesCount(
			importedGroup.getGroupId());

		Assert.assertEquals(1, articlesCount);

		JournalArticle groupArticle =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				article.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(groupArticle);

		groupId = importedGroup.getGroupId();

		if (companyScopeDependencies) {
			DDMStructure importedDDMStructure =
				DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
					ddmStructure.getUuid(), groupId);

			Assert.assertNull(importedDDMStructure);

			DDMTemplate importedDDMTemplate =
				DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
					ddmTemplate.getUuid(), groupId);

			Assert.assertNull(importedDDMTemplate);

			groupId = companyGroup.getGroupId();
		}

		DDMStructure dependentDDMStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				ddmStructure.getUuid(), groupId);

		Assert.assertNotNull(dependentDDMStructure);

		DDMTemplate dependentDDMTemplate =
			DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
				ddmTemplate.getUuid(), groupId);

		Assert.assertNotNull(dependentDDMTemplate);
		Assert.assertEquals(
			article.getDDMStructureKey(),
			dependentDDMStructure.getStructureKey());
		Assert.assertEquals(
			article.getDDMTemplateKey(), dependentDDMTemplate.getTemplateKey());
		Assert.assertEquals(
			dependentDDMTemplate.getClassPK(),
			dependentDDMStructure.getStructureId());
	}

	@Override
	protected AssetEntry getAssetEntry(StagedModel stagedModel)
		throws PortalException {

		JournalArticle article = (JournalArticle)stagedModel;

		return AssetEntryLocalServiceUtil.getEntry(
			article.getGroupId(), article.getArticleResourceUuid());
	}

	protected Map<String, String[]> getBaseParameterMap(long groupId, long plid)
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.FALSE.toString()});

		addParameter(parameterMap, "doAsGroupId", String.valueOf(groupId));
		addParameter(parameterMap, "feeds", true);
		addParameter(parameterMap, "groupId", String.valueOf(groupId));
		addParameter(
			parameterMap, "permissionsAssignedToRoles",
			Boolean.TRUE.toString());
		addParameter(parameterMap, "plid", String.valueOf(plid));
		addParameter(
			parameterMap, "portletResource", JournalPortletKeys.JOURNAL);
		addParameter(parameterMap, "referenced-content", true);
		addParameter(parameterMap, "structures", true);
		addParameter(parameterMap, "version-history", true);
		addParameter(parameterMap, "web-content", true);

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		MapUtil.merge(
			parameterMap,
			getBaseParameterMap(group.getGroupId(), layout.getPlid()));

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				JournalPortletKeys.JOURNAL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getImportParameterMap();

		MapUtil.merge(
			parameterMap,
			getBaseParameterMap(
				importedGroup.getGroupId(), importedLayout.getPlid()));

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, long groupId) {
		return
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				uuid, groupId);
	}

	@Override
	protected boolean isVersioningEnabled() {
		return true;
	}

	@Override
	protected void testExportImportDisplayStyle(long groupId, String scopeType)
		throws Exception {
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		JournalArticle article = (JournalArticle)stagedModel;
		JournalArticle importedArticle = (JournalArticle)importedStagedModel;

		Assert.assertEquals(
			(Double)article.getVersion(), (Double)importedArticle.getVersion());
		Assert.assertEquals(article.getTitle(), importedArticle.getTitle());
		Assert.assertEquals(
			article.getUrlTitle(), importedArticle.getUrlTitle());
		Assert.assertEquals(
			article.getDescription(), importedArticle.getDescription());
		Assert.assertEquals(article.getContent(), importedArticle.getContent());
		Assert.assertTrue(
			String.valueOf(article.getDisplayDate()) + StringPool.SPACE +
				importedArticle.getDisplayDate(),
			DateUtil.equals(
				article.getDisplayDate(), importedArticle.getDisplayDate()));
		Assert.assertTrue(
			String.valueOf(article.getExpirationDate()) + StringPool.SPACE +
				importedArticle.getExpirationDate(),
			DateUtil.equals(
				article.getExpirationDate(),
				importedArticle.getExpirationDate()));
		Assert.assertTrue(
			String.valueOf(article.getReviewDate()) + StringPool.SPACE +
				importedArticle.getReviewDate(),
			DateUtil.equals(
				article.getReviewDate(), importedArticle.getReviewDate()));
		Assert.assertEquals(
			article.getSmallImage(), importedArticle.getSmallImage());
		Assert.assertEquals(
			article.getSmallImageURL(), importedArticle.getSmallImageURL());
		Assert.assertEquals(article.getStatus(), importedArticle.getStatus());
		Assert.assertTrue(
			String.valueOf(article.getStatusDate()) + StringPool.SPACE +
				importedArticle.getStatusDate(),
			DateUtil.equals(
				article.getStatusDate(), importedArticle.getStatusDate()));

		JournalArticleResource articleResource = article.getArticleResource();
		JournalArticleResource importedArticleArticleResource =
			importedArticle.getArticleResource();

		Assert.assertEquals(
			articleResource.getUuid(),
			importedArticleArticleResource.getUuid());
	}

	@Override
	protected void validateVersions() throws Exception {
		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles(group.getGroupId());

		for (JournalArticle article : articles) {
			JournalArticle importedArticle = (JournalArticle)getStagedModel(
				article.getUuid(), importedGroup.getGroupId());

			Assert.assertNotNull(importedArticle);

			validateImportedStagedModel(article, importedArticle);
		}
	}

}