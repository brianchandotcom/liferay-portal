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

		JournalArticle article = _addArticle(
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
	public void testSearch() throws Exception {
		Group group = ServiceTestUtil.addGroup("Test Group");

		JournalFolder folder = _addFolder(group.getGroupId(), 0, "Test Folder");

		JournalArticle article = _addArticle(
			group.getGroupId(), folder.getFolderId(),
			"Test Article about sports",
			"This is a test article about football", true);

		_indexedSearch(article, true, "football", true);
		_indexedSearch(article, true, "sports", true);
		_indexedSearch(article, true, "tennis", false);

		_databaseSearch(article, true, "football", true);
		_databaseSearch(article, true, "sports", true);
		_databaseSearch(article, true, "tennis", false);

		Assert.assertEquals(article.getFolderId(), folder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(folder);
		GroupLocalServiceUtil.deleteGroup(group);
	}

	protected JournalArticle _addArticle(
			long groupId, long folderId, String name, String content,
			boolean index)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale englishLocale = new Locale("en", "US");

		titleMap.put(englishLocale, name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?><root available-locales=");
		sb.append("\"en_US\" default-locale=\"en_US\">");
		sb.append("<static-content language-id=\"en_US\"><![CDATA[<p>");
		sb.append(content);
		sb.append("</p>]]>");
		sb.append("</static-content></root>");

		if (index) {
			return JournalArticleLocalServiceUtil.addArticle(
				TestPropsValues.getUserId(), groupId, folderId, titleMap,
				descriptionMap, sb.toString(), serviceContext);
		}
		else {
			return JournalArticleLocalServiceUtil.addArticle(
				TestPropsValues.getUserId(), groupId, folderId, 0, 0,
				StringPool.BLANK, true, 1, titleMap, descriptionMap,
				sb.toString(), "general", null, null, null, 1, 1, 1965, 0, 0, 0,
				0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, false, false, null, null,
				null, null,	serviceContext);
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

	protected void _databaseSearch(
			JournalArticle article, boolean rootFolder, String keywords,
			boolean assertTrue)
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

	protected void _indexedSearch(
			JournalArticle article, boolean rootFolder, String keywords,
			boolean assertTrue)
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