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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.test.BasePortletExportImportTestCase;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
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
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Juan Fernández
 */
@Sync
public class JournalExportImportTest extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getNamespace() {
		return JournalPortletDataHandler.NAMESPACE;
	}

	@Override
	public String getPortletId() {
		return PortletKeys.JOURNAL;
	}

	@Test
	public void testExportImportCompanyScopeStructuredJournalArticle()
		throws Exception {

		exportImportJournalArticle(true);
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
			groupId, ddmStructure.getStructureId());

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		article = JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		exportImportPortlet(PortletKeys.JOURNAL);

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
		addParameter(parameterMap, "portletResource", PortletKeys.JOURNAL);
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
				PortletKeys.JOURNAL,
			new String[]{Boolean.TRUE.toString()});

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
	protected void testExportImportDisplayStyle(long groupId, String scopeType)
		throws Exception {
	}

}