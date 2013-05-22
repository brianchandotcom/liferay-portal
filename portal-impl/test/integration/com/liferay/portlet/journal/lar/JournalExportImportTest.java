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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.BasePortletExportImportTestCase;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalExportImportTest extends BasePortletExportImportTestCase {

	@Override
	public String getNamespace() {
		return JournalPortletDataHandler.NAMESPACE;
	}

	@Override
	public String getPortletId() {
		return PortletKeys.JOURNAL;
	}

	@Test
	public void testExportImportBasicJournalArticle() throws Exception {
		exportImportJournalArticle(false);
	}

	@Test
	public void testExportImportStructuredJournalArticle() throws Exception {
		exportImportJournalArticle(true);
	}

	@Override
	protected StagedModel addStagedModel(long groupId) throws Exception {
		return JournalTestUtil.addArticle(
			groupId, ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString());
	}

	protected void exportImportJournalArticle(boolean structuredContent)
		throws Exception {

		JournalArticle article = null;
		DDMStructure ddmStructure = null;
		DDMTemplate ddmTemplate = null;

		if (structuredContent) {
			ddmStructure = DDMStructureTestUtil.addStructure(
				group.getGroupId(), JournalArticle.class.getName());

			ddmTemplate = DDMTemplateTestUtil.addTemplate(
				group.getGroupId(), ddmStructure.getStructureId());

			String content = DDMStructureTestUtil.getSampleStructuredContent();

			article = JournalTestUtil.addArticleWithXMLContent(
				group.getGroupId(), content, ddmStructure.getStructureKey(),
				ddmTemplate.getTemplateKey());
		}
		else {
			article = JournalTestUtil.addArticle(
				group.getGroupId(), ServiceTestUtil.randomString(),
				ServiceTestUtil.randomString());
		}

		String exportedResourceUuid = article.getArticleResourceUuid();

		doExportImportPortlet(PortletKeys.JOURNAL);

		int articlesCount = JournalArticleLocalServiceUtil.getArticlesCount(
			importedGroup.getGroupId());

		Assert.assertEquals(1, articlesCount);

		JournalArticleResource importedJournalArticleResource =
			JournalArticleResourceLocalServiceUtil.fetchArticleResource(
				exportedResourceUuid, importedGroup.getGroupId());

		Assert.assertNotNull(importedJournalArticleResource);

		if (!structuredContent) {
			return;
		}

		DDMStructure importedDDMStructure =
			DDMStructureLocalServiceUtil.fetchStructure(
				ddmStructure.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(importedDDMStructure);

		DDMTemplate importedDDMTemplate =
			DDMTemplateLocalServiceUtil.fetchTemplate(
				ddmTemplate.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(importedDDMTemplate);
		Assert.assertEquals(
			article.getStructureId(), importedDDMStructure.getStructureKey());
		Assert.assertEquals(
			article.getTemplateId(), importedDDMTemplate.getTemplateKey());
		Assert.assertEquals(
			importedDDMTemplate.getClassPK(),
			importedDDMStructure.getStructureId());
	}

	protected Map<String, String[]> getBaseParameterMap(long groupId, long plid)
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.FALSE.toString()});

		addParameter(parameterMap, "categories", true);
		addParameter(parameterMap, "comments", true);
		addParameter(parameterMap, "doAsGroupId", String.valueOf(groupId));
		addParameter(parameterMap, "embedded-assets", true);
		addParameter(parameterMap, "feeds", true);
		addParameter(parameterMap, "groupId", String.valueOf(groupId));
		addParameter(
			parameterMap, "permissionsAssignedToRoles",
			Boolean.TRUE.toString());
		addParameter(parameterMap, "plid", String.valueOf(plid));
		addParameter(parameterMap, "portletResource", PortletKeys.JOURNAL);
		addParameter(parameterMap, "ratings", true);
		addParameter(parameterMap, "structures-and-templates", true);
		addParameter(parameterMap, "tags", true);
		addParameter(parameterMap, "version-history", true);
		addParameter(parameterMap, "web-content", true);

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = getBaseParameterMap(
			group.getGroupId(), layout.getPlid());

		parameterMap.put(Constants.CMD, new String[] {Constants.EXPORT});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				PortletKeys.JOURNAL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = getBaseParameterMap(
			importedGroup.getGroupId(), importedLayout.getPlid());

		parameterMap.put(Constants.CMD, new String[] {Constants.IMPORT});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, long groupId)
		throws PortalException, SystemException {

		JournalArticleResource importedArticleResource =
			JournalArticleResourceUtil.fetchByUUID_G(uuid, groupId);

		return JournalArticleLocalServiceUtil.getLatestArticle(
			importedArticleResource.getResourcePrimKey());
	}

	@Override
	protected String getStagedModelUuid(StagedModel stagedModel)
		throws PortalException, SystemException {

		JournalArticle article = (JournalArticle)stagedModel;

		return article.getArticleResourceUuid();
	}

}