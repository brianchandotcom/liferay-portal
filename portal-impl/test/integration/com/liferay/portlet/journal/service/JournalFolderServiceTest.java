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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.journal.DuplicateFolderNameException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JournalFolderServiceTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testContent() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalArticle article = _addArticleWithoutStructure(
			group.getGroupId(), folder.getFolderId(), "Test Article",
			"This is a test article.", false);

		Assert.assertEquals(article.getFolderId(), folder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(folder);
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	@Transactional
	public void testSubfolders() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder1 = _addFolder(group.getGroupId(), 0, "Test 1");
		JournalFolder folder11 = _addFolder(
			group.getGroupId(), folder1.getFolderId(), "Test 1.1");
		JournalFolder folder111 = _addFolder(
			group.getGroupId(), folder11.getFolderId(), "Test 1.1.1");

		Assert.assertTrue(folder1.isRoot());
		Assert.assertFalse(folder11.isRoot());
		Assert.assertFalse(folder111.isRoot());

		Assert.assertEquals(
			folder1.getFolderId(), folder11.getParentFolderId());

		Assert.assertEquals(
			folder11.getFolderId(), folder111.getParentFolderId());
	}

	@Test
	@Transactional
	public void testMoveFolder() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder1 = _addFolder(group.getGroupId(), 0, "Test 1");
		JournalFolder folder11 = _addFolder(
			group.getGroupId(), folder1.getFolderId(), "Test Same Name");
		JournalFolder folder111 = _addFolder(
			group.getGroupId(), folder11.getFolderId(), "Test 1.1.1");

		JournalFolder folder2 = _addFolder(group.getGroupId(), 0, "Test 2");
		JournalFolder folder21 = null;

		try {
			folder21 = _addFolder(
				group.getGroupId(), folder2.getFolderId(), "Test Same Name");
		}
		catch (DuplicateFolderNameException dfne) {
			Assert.fail(
				"Unable to add two folders of the same name in different " +
					"folders");
		}

		Assert.assertTrue(folder1.isRoot());
		Assert.assertFalse(folder111.isRoot());

		JournalFolderLocalServiceUtil.moveFolder(
			folder111.getFolderId(), folder1.getFolderId(),
			new ServiceContext());

		Assert.assertEquals(
			folder1.getFolderId(), folder111.getParentFolderId());

		JournalFolderLocalServiceUtil.moveFolder(
			folder111.getFolderId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new ServiceContext());

		Assert.assertTrue(folder111.isRoot());

		try {
			JournalFolderLocalServiceUtil.moveFolder(
				folder21.getFolderId(), folder1.getFolderId(),
				new ServiceContext());
			
			Assert.fail("Able to add two folders of the same name");
		}
		catch (DuplicateFolderNameException dfne) {
		}
	}

	@Test
	@Transactional
	public void testSearch() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalArticle article = _addArticleWithoutStructure(
			group.getGroupId(), folder.getFolderId(),
			"Test Article about sports",
			"This is a test article about football", true);

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		_indexedSearch(article, "football", true, true);
		_indexedSearch(article, "sports", true, true);
		_indexedSearch(article, "tennis", false, true);

		_databaseSearch(article, "football", true, true);
		_databaseSearch(article, "sports", true, true);
		_databaseSearch(article, "tennis", false, true);

		Assert.assertEquals(article.getFolderId(), folder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(folder);
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	@Transactional
	public void testAdvancedSearchStructure() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalStructure structure1 = _addStructure(
			group.getGroupId(), "sport");

		JournalStructure structure2 = _addStructure(
			group.getGroupId(), "sport 2");

		JournalTemplate template1 = _addTemplate(
			group.getGroupId(), "sport", structure1.getStructureId());

		JournalTemplate template2 = _addTemplate(
			group.getGroupId(), "sport 2", structure2.getStructureId());

		JournalArticle article1 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Test Article about sports", "Test article about football",
			structure1.getStructureId(), template1.getTemplateId(), true);

		JournalArticle article2 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Sports article", "This is sport article about shot put",
			structure2.getStructureId(), template2.getTemplateId(), true);

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		_indexedAdvancedSearch(
			article1, null, null, null, structure1.getStructureId(), null, null,
			null, false, true, true);

		_indexedAdvancedSearch(
			article2, null, null, null, structure1.getStructureId(), null, null,
			null, false, false, true);

		_indexedAdvancedSearch(
			article2, null, null, null, structure2.getStructureId(), null, null,
			null, false, true, true);

		_databaseAdvanceSearch(
			article1, null, null, null, structure1.getStructureId(), null, null,
			null, false, true, true);

		_databaseAdvanceSearch(
			article2, null, null, null, structure1.getStructureId(), null, null,
			null, false, false, true);

		_databaseAdvanceSearch(
			article2, null, null, null, structure2.getStructureId(), null, null,
			null, false, true, true);
	}

	@Test
	@Transactional
	public void testAdvancedSearchTemplate() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalStructure structure1 = _addStructure(
			group.getGroupId(), "sport");

		JournalStructure structure2 = _addStructure(
			group.getGroupId(), "sport 2");

		JournalTemplate template1 = _addTemplate(
			group.getGroupId(), "sport", structure1.getStructureId());

		JournalTemplate template2 = _addTemplate(
			group.getGroupId(), "sport 2", structure2.getStructureId());

		JournalArticle article1 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Test Article about sports", "Test article about football",
			structure1.getStructureId(), template1.getTemplateId(), true);

		JournalArticle article2 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Sports article", "This is sport article about shot put",
			structure2.getStructureId(), template2.getTemplateId(), true);

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		_indexedAdvancedSearch(
			article1, null, null, null, null, template1.getTemplateId(), null,
			null, false, true, true);

		_indexedAdvancedSearch(
			article2, null, null, null, null, template1.getTemplateId(), null,
			null, false, false, true);

		_indexedAdvancedSearch(
			article2, null, null, null, null, template2.getTemplateId(), null,
			null, false, true, true);

		_databaseAdvanceSearch(
			article1, null, null, null, null, template1.getTemplateId(), null,
			null, false, true, true);

		_databaseAdvanceSearch(
			article2, null, null, null, null, template1.getTemplateId(), null,
			null, false, false, true);

		_databaseAdvanceSearch(
			article2, null, null, null, null, template2.getTemplateId(), null,
			null, false, true, true);
	}

	@Test
	@Transactional
	public void testAdvancedSearchTitle() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalStructure structure1 = _addStructure(
			group.getGroupId(), "sport");

		JournalStructure structure2 = _addStructure(
			group.getGroupId(), "sport 2");

		JournalTemplate template1 = _addTemplate(
			group.getGroupId(), "sport", structure1.getStructureId());

		JournalTemplate template2 = _addTemplate(
			group.getGroupId(), "sport 2", structure2.getStructureId());

		JournalArticle article1 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Test Article about sports", "Test article about football",
			structure1.getStructureId(), template1.getTemplateId(), true);

		JournalArticle article2 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Sports article", "This is sport article about shot put",
			structure2.getStructureId(), template2.getTemplateId(), true);

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		_indexedAdvancedSearch(
			article1, null, null, null, null, null, "Sports", null, false, true,
			true);

		_indexedAdvancedSearch(
			article2, null, null, null, null, null, "Sports", null, false, true,
			true);

		_indexedAdvancedSearch(
			article1, null, null, null, null, null, "Home", null, false, false,
			true);

		_databaseAdvanceSearch(
			article1, null, null, null, null, null, "Sports", null, false, true,
			true);

		_databaseAdvanceSearch(
			article2, null, null, null, null, null, "Sports", null, false, true,
			true);

		_databaseAdvanceSearch(
			article1, null, null, null, null, null, "Home", null, false, false,
			true);
	}

	@Test
	@Transactional
	public void testAdvancedSearchDescription() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalStructure structure1 = _addStructure(
			group.getGroupId(), "sport");

		JournalStructure structure2 = _addStructure(
			group.getGroupId(), "sport 2");

		JournalTemplate template1 = _addTemplate(
			group.getGroupId(), "sport", structure1.getStructureId());

		JournalTemplate template2 = _addTemplate(
			group.getGroupId(), "sport 2", structure2.getStructureId());

		JournalArticle article1 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Test article about sports", "Test article about football",
			structure1.getStructureId(), template1.getTemplateId(), true);

		JournalArticle article2 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Sports article", "This is sport article about shot put",
			structure2.getStructureId(), template2.getTemplateId(), true);

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		_indexedAdvancedSearch(
			article1, null, null, "article", null, null, null, null, false,
			true, true);

		_indexedAdvancedSearch(
			article2, null, null, "football", null, null, null, null, false,
			false, true);

		_indexedAdvancedSearch(
			article1, null, null, "sports", null, null, null, null, false, true,
			true);

		_databaseAdvanceSearch(
			article1, null, null, "article", null, null, null, null, false,
			true, true);

		_databaseAdvanceSearch(
			article2, null, null, "football", null, null, null, null, false,
			false, true);

		_databaseAdvanceSearch(
			article1, null, null, "sports", null, null, null, null, false, true,
			true);
	}

	@Test
	@Transactional
	public void testAdvancedSearchArticleId() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalStructure structure1 = _addStructure(
			group.getGroupId(), "sport");

		JournalStructure structure2 = _addStructure(
			group.getGroupId(), "sport 2");

		JournalTemplate template1 = _addTemplate(
			group.getGroupId(), "sport", structure1.getStructureId());

		JournalTemplate template2 = _addTemplate(
			group.getGroupId(), "sport 2", structure2.getStructureId());

		JournalArticle article1 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Test Article about sports", "Test article about football",
			structure1.getStructureId(), template1.getTemplateId(), true);

		JournalArticle article2 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Sports article", "This is sport article about shot put",
			structure2.getStructureId(), template2.getTemplateId(), true);

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		_indexedAdvancedSearch(
			article1, article1.getArticleId(), null, null, null, null, null,
			null, false, true, true);

		_indexedAdvancedSearch(
			article2, article2.getArticleId(), null, null, null, null, null,
			null, false, true, true);

		_indexedAdvancedSearch(
			article2, article1.getArticleId(), null, null, null, null, null,
			null, false, false, true);

		_databaseAdvanceSearch(
			article1, article1.getArticleId(), null, null, null, null, null,
			null, false, true, true);

		_databaseAdvanceSearch(
			article2, article2.getArticleId(), null, null, null, null, null,
			null, false, true, true);

		_databaseAdvanceSearch(
			article2, article1.getArticleId(), null, null, null, null, null,
			null, false, false, true);
	}

	@Test
	@Transactional
	public void testAdvancedSearchContent() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalStructure structure1 = _addStructure(
			group.getGroupId(), "sport");

		JournalStructure structure2 = _addStructure(
			group.getGroupId(), "sport 2");

		JournalTemplate template1 = _addTemplate(
			group.getGroupId(), "sport", structure1.getStructureId());

		JournalTemplate template2 = _addTemplate(
			group.getGroupId(), "sport 2", structure2.getStructureId());

		JournalArticle article1 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Test Article about sports", "Test article about football",
			structure1.getStructureId(), template1.getTemplateId(), true);

		JournalArticle article2 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Sports article", "This is sport article about shot put",
			structure2.getStructureId(), template2.getTemplateId(), true);

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		_indexedAdvancedSearch(
			article1, null, "article", null, null, null, null, null, false,
			true, true);

		_indexedAdvancedSearch(
			article2, null, "sport", null, null, null, null, null, false, true,
			true);

		_indexedAdvancedSearch(
			article2, null, "tennis", null, null, null, null, null, false,
			false, true);

		_databaseAdvanceSearch(
			article1, null, "article", null, null, null, null, null, false,
			true, true);

		_databaseAdvanceSearch(
			article2, null, "sport", null, null, null, null, null, false, true,
			true);

		_databaseAdvanceSearch(
			article2, null, "tennis", null, null, null, null, null, false,
			false, true);
	}

	@Test
	@Transactional
	public void testAdvancedSearchAndOperator() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalStructure structure1 = _addStructure(
			group.getGroupId(), "sport");

		JournalStructure structure2 = _addStructure(
			group.getGroupId(), "sport 2");

		JournalTemplate template1 = _addTemplate(
			group.getGroupId(), "sport", structure1.getStructureId());

		JournalTemplate template2 = _addTemplate(
			group.getGroupId(), "sport 2", structure2.getStructureId());

		JournalArticle article1 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Test article about sports", "Test article about football",
			structure1.getStructureId(), template1.getTemplateId(), true);

		JournalArticle article2 = _addArticleWithStructure(
			group.getGroupId(), folder.getFolderId(),
			"Sports article", "This is sport article about shot put",
			structure2.getStructureId(), template2.getTemplateId(), true);

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		_indexedAdvancedSearch(
			article1, article1.getArticleId(), null, "article",
			structure1.getStructureId(), template1.getTemplateId(), "sports",
			null, true, true, true);

		_indexedAdvancedSearch(
			article2, article1.getArticleId(), null, "football",
			structure2.getStructureId(), template2.getTemplateId(), "sports",
			null, true, false, true);

		_indexedAdvancedSearch(
			article2, article1.getArticleId(), null, "football",
			structure1.getStructureId(), template1.getTemplateId(), "tennis",
			null, false, false, true);

		_databaseAdvanceSearch(
			article1, article1.getArticleId(), null, "article",
			structure1.getStructureId(), template1.getTemplateId(), "sports",
			null, true, true, true);

		_databaseAdvanceSearch(
			article2, article1.getArticleId(), null, "football",
			structure2.getStructureId(), template2.getTemplateId(), "sports",
			null, true, false, true);

		_databaseAdvanceSearch(
			article2, article1.getArticleId(), null, "football",
			structure1.getStructureId(), template1.getTemplateId(), "tennis",
			null, false, false, true);
	}

	protected JournalArticle _addArticleWithoutStructure(
			long groupId, long folderId, String name, String content,
			boolean index)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?><root available-locales=");
		sb.append("\"en_US\" default-locale=\"en_US\">");
		sb.append("<static-content language-id=\"en_US\"><![CDATA[<p>");
		sb.append(content);
		sb.append("</p>]]>");
		sb.append("</static-content></root>");

		return _addArticle(
			groupId, folderId, name, sb.toString(), null, null, index);
	}

	protected JournalArticle _addArticleWithStructure(
			long groupId, long folderId, String name, String content,
			String structureId, String templateId, boolean index)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?><root available-locales=");
		sb.append("\"en_US\" default-locale=\"en_US\">");
		sb.append("<dynamic-element instance-id=\"nZCCI81a\" name=\"Title\" type=\"text\" index-type=\"\"><dynamic-content><![CDATA[<p>");
		sb.append(content);
		sb.append("</p>]]></dynamic-content>");
		sb.append("</dynamic-element></root>");

		return _addArticle(
			groupId, folderId, name, sb.toString(), structureId, templateId,
			index);
	}


	protected JournalArticle _addArticle(
			long groupId, long folderId, String name, String content,
			String structureId, String templateId, boolean index)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale englishLocale = new Locale("en", "US");

		titleMap.put(englishLocale, name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(englishLocale, name);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		if (index) {
			return JournalArticleLocalServiceUtil.addArticle(
				TestPropsValues.getUserId(), groupId, folderId, titleMap,
				descriptionMap, content, structureId, templateId,
				serviceContext);
		}
		else {
			return JournalArticleLocalServiceUtil.addArticle(
				TestPropsValues.getUserId(), groupId, folderId, 0, 0,
				StringPool.BLANK, true, 1, titleMap, descriptionMap,
				content, "general", structureId, templateId, null, 1, 1,
				1965, 0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, false,
				false, null, null, null, null,	serviceContext);
		}
	}

	protected JournalFolder _addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return JournalFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), groupId, parentFolderId, name,
			"This is a test folder.", serviceContext);
	}

	protected JournalStructure _addStructure(long groupId, String name)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		Locale englishLocale = new Locale("en", "US");

		nameMap.put(englishLocale, name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<root>");
		sb.append("<dynamic-element name=\"Title\" type=\"text\" ");
		sb.append("index-type=\"\" repeatable=\"false\"/>");
		sb.append("</root>");

		return JournalStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId, StringPool.BLANK, true,
			StringPool.BLANK, nameMap, descriptionMap, sb.toString(),
			serviceContext);
	}

	protected JournalTemplate _addTemplate(
			long groupId, String name, String structureId)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		Locale englishLocale = new Locale("en", "US");

		nameMap.put(englishLocale, name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return JournalTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), groupId, StringPool.BLANK, true,
			structureId, nameMap, descriptionMap, "<p>Economia</p>", false,
			"vm", true, false, null, null, serviceContext);
	}

	protected void _databaseAdvanceSearch(
			JournalArticle article, String articleId, String content,
			String description, String structureId, String templateId,
			String title, String type, boolean andOperator, boolean assertTrue,
			boolean rootFolder)
		throws Exception {

		List<JournalArticle> results = JournalArticleServiceUtil.search(
			article.getCompanyId(), article.getGroupId(), article.getFolderId(),
			0, articleId, null, title, description, content, type, structureId,
			templateId, null, null, WorkflowConstants.STATUS_APPROVED, null,
			andOperator, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		boolean found = false;

		for (JournalArticle curArticle : results) {
			String curArticleId = curArticle.getArticleId();

			if (curArticleId.equals(article.getArticleId())) {
				found = true;

				break;
			}
		}

		String message = "Database search engine could not find ";

		if (rootFolder) {
			message += "root article by advanced search";
		}
		else {
			message += "article by advanced search";
		}

		if (assertTrue) {
			Assert.assertTrue(message, found);
		}
		else {
			Assert.assertFalse(message, found);
		}
	}

	protected void _databaseSearch(
			JournalArticle article, String keywords, boolean assertTrue,
			boolean rootFolder)
		throws Exception {

		List<JournalArticle> results = JournalArticleServiceUtil.search(
			article.getCompanyId(), article.getGroupId(), article.getFolderId(),
			0, keywords, null, "", "", "", null, null,
			WorkflowConstants.STATUS_APPROVED, null, 0,
			SearchContainer.DEFAULT_DELTA, null);

		boolean found = false;

		for (JournalArticle curArticle : results) {
			String articleId = curArticle.getArticleId();

			if (articleId.equals(article.getArticleId())) {
				found = true;

				break;
			}
		}

		String message = "Database search engine could not find ";

		if (rootFolder) {
			message += "root article by " + keywords;
		}
		else {
			message += "article by " + keywords;
		}

		if (assertTrue) {
			Assert.assertTrue(message, found);
		}
		else {
			Assert.assertFalse(message, found);
		}
	}

	protected void _indexedAdvancedSearch(
			JournalArticle article, String articleId, String content,
			String description, String structureId, String templateId,
			String title, String type, boolean andSearch, boolean assertTrue,
			boolean rootFolder)
		throws Exception {

		Hits hits = JournalArticleLocalServiceUtil.search(
			article.getCompanyId(), article.getGroupId(), article.getFolderId(),
			0, articleId, title, description, content, type,
			String.valueOf(WorkflowConstants.STATUS_APPROVED), structureId,
			templateId, null, andSearch, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);

		List<Document> documents = hits.toList();

		boolean found = false;

		for (Document document : documents) {
			String curArticleId = GetterUtil.getString(
				document.get("articleId"));

			if (curArticleId.equals(article.getArticleId())) {
				found = true;

				break;
			}
		}

		String message = "Search engine could not find ";

		if (rootFolder) {
			message += "root article by advanced search";
		}
		else {
			message += "article by advanced search";
		}

		message += " using query " + hits.getQuery();

		if (assertTrue) {
			Assert.assertTrue(message, found);
		}
		else {
			Assert.assertFalse(message, found);
		}
	}

	protected void _indexedSearch(
			JournalArticle article, String keywords, boolean assertTrue,
			boolean rootFolder)
		throws Exception {

		Hits hits = JournalArticleLocalServiceUtil.search(
			article.getCompanyId(), article.getGroupId(), article.getFolderId(),
			0, "", "", keywords, null, 0, SearchContainer.DEFAULT_DELTA,
			null);

		List<Document> documents = hits.toList();

		boolean found = false;

		for (Document document : documents) {
			String articleId = GetterUtil.getString(
				document.get("articleId"));

			if (articleId.equals(article.getArticleId())) {
				found = true;

				break;
			}
		}

		String message = "Search engine could not find ";

		if (rootFolder) {
			message += "root article by " + keywords;
		}
		else {
			message += "article by " + keywords;
		}

		message += " using query " + hits.getQuery();

		if (assertTrue) {
			Assert.assertTrue(message, found);
		}
		else {
			Assert.assertFalse(message, found);
		}
	}

}