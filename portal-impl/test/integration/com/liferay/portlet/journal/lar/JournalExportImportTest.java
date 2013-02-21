/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.PortletImporter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
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
public class JournalExportImportTest {

	@After
	public void tearDown() throws Exception {
		if ((_larFile != null) && _larFile.exists()) {
			FileUtil.delete(_larFile);
		}
	}

	@Test
	public void testExportImportBasicJournalContent() throws Exception {
		exportImportJournalArticle(false);
	}

	@Test
	public void testExportImportStructuredJournalContent() throws Exception {
		exportImportJournalArticle(true);
	}

	protected void exportImportJournalArticle(boolean structuredContent)
		throws Exception {

		// Add a site and a layout

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		// Add a Journal Article and DDM Structure and Template

		JournalArticle article = null;
		DDMStructure ddmStructure = null;
		DDMTemplate ddmTemplate = null;

		if (structuredContent) {
			ddmStructure = JournalTestUtil.addDDMStructure(group.getGroupId());

			ddmTemplate = JournalTestUtil.addDDMTemplate(
				group.getGroupId(), ddmStructure.getStructureId());

			String content = JournalTestUtil.getSampleStructuredContent();

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

		// Export Portlet Content

		Map<String, String[]> parameterMap = getExportParameterMap(
			group.getGroupId(), layout.getPlid());

		_larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			layout.getPlid(), group.getGroupId(), PortletKeys.JOURNAL,
			parameterMap, null, null);

		// Remove the site

		GroupLocalServiceUtil.deleteGroup(group.getGroupId());

		// Add another site and layout

		group = GroupTestUtil.addGroup();

		layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		int initialArticlesCount =
			JournalArticleLocalServiceUtil.getArticlesCount(group.getGroupId());

		// Import Portlet Content

		PortletImporter portletImporter = new PortletImporter();

		parameterMap = getImportParameterMap(
			group.getGroupId(), layout.getPlid());

		portletImporter.importPortletInfo(
			TestPropsValues.getUserId(), layout.getPlid(), group.getGroupId(),
			PortletKeys.JOURNAL, parameterMap, _larFile);

		// Verify there is just one new article in the new site

		int articlesCount = JournalArticleLocalServiceUtil.getArticlesCount(
			group.getGroupId());

		Assert.assertEquals(initialArticlesCount + 1, articlesCount);

		// Verify the same article exists in the new site

		JournalArticleResource importedJournalArticleResource =
			JournalArticleResourceLocalServiceUtil.fetchArticleResource(
				exportedResourceUuid, group.getGroupId());

		Assert.assertNotNull(importedJournalArticleResource);

		if (structuredContent) {

			// Verify the structure and template exist in the new site

			DDMStructure importedDDMStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					ddmStructure.getUuid(), group.getGroupId());

			Assert.assertNotNull(importedDDMStructure);

			DDMTemplate importedDDMTemplate =
				DDMTemplateLocalServiceUtil.fetchTemplate(
					ddmTemplate.getUuid(), group.getGroupId());

			Assert.assertNotNull(importedDDMTemplate);

			// Check Relationships

			Assert.assertEquals(
				article.getStructureId(),
				importedDDMStructure.getStructureKey());

			Assert.assertEquals(
				article.getTemplateId(), importedDDMTemplate.getTemplateKey());

			Assert.assertEquals(
				importedDDMTemplate.getClassPK(),
				importedDDMStructure.getStructureId());
		}
	}

	protected Map<String, String[]> getExportParameterMap(
			long groupId, long plid)
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		parameterMap.put(Constants.CMD, new String[]{Constants.EXPORT});
		parameterMap.put("doAsGroupId", new String[]{String.valueOf(groupId)});
		parameterMap.put("groupId", new String[]{String.valueOf(groupId)});
		parameterMap.put(
			"permissionsAssignedToRoles",
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put("plid", new String[]{String.valueOf(plid)});
		parameterMap.put("portletResource", new String[]{PortletKeys.JOURNAL});
		parameterMap.put("range", new String[]{"fromLastPublishDate"});

		// Journal Options

		parameterMap.put(
			"_journal_categories", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_ddmStructures-ddmTemplates-and-feeds",
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_embedded-assets", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_version-history", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_images", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_web-content", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_ratings", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_tags", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_comments", new String[]{Boolean.TRUE.toString()});

		// General Options

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[]{Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_METADATA_ALL,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				PortletKeys.JOURNAL, new String[]{Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected Map<String, String[]> getImportParameterMap(
			long groupId, long plid)
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		parameterMap.put(Constants.CMD, new String[]{Constants.IMPORT});
		parameterMap.put("doAsGroupId", new String[]{String.valueOf(groupId)});
		parameterMap.put("groupId", new String[]{String.valueOf(groupId)});
		parameterMap.put(
			"permissionsAssignedToRoles",
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put("plid", new String[]{String.valueOf(plid)});
		parameterMap.put("portletResource", new String[]{PortletKeys.JOURNAL});

		// Journal Options

		parameterMap.put(
			"_journal_web-content", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_tags", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_ddmStructures-ddmTemplates-and-feeds",
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_categories", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_comments", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_images", new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			"_journal_ratings", new String[]{Boolean.TRUE.toString()});

		// General Options

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[]{PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[]{Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[]{Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_METADATA_ALL,
			new String[]{Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[]{UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	private File _larFile;

}