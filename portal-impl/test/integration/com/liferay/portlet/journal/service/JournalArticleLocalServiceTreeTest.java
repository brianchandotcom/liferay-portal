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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JournalArticleLocalServiceTreeTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testJournalArticleTreePathWhenMovingSubfolderWithArticle()
		throws Exception {

		JournalFolder folderA = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A");

		JournalFolder folderAA = JournalTestUtil.addFolder(
			_group.getGroupId(), folderA.getFolderId(), "Folder AA");

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), folderAA.getFolderId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalFolderLocalServiceUtil.moveFolder(
			folderAA.getFolderId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		article = JournalArticleLocalServiceUtil.getArticle(
			_group.getGroupId(), article.getArticleId());

		Assert.assertEquals(article.buildTreePath(), article.getTreePath());
	}

	@Test
	public void testRebuildTree() throws Exception {
		List<JournalArticle> articles = createTree();

		for (JournalArticle article : articles) {
			article.setTreePath(null);

			JournalArticleLocalServiceUtil.updateJournalArticle(article);
		}

		JournalArticleLocalServiceUtil.rebuildTree(
			TestPropsValues.getCompanyId());

		for (JournalArticle article : articles) {
			article = JournalArticleLocalServiceUtil.getArticle(
				article.getId());

			Assert.assertEquals(article.buildTreePath(), article.getTreePath());
		}
	}

	protected List<JournalArticle> createTree() throws Exception {
		List<JournalArticle> articles = new ArrayList<JournalArticle>();

		JournalArticle articleA = JournalTestUtil.addArticle(
			_group.getGroupId(), "Article A", RandomTestUtil.randomString());

		articles.add(articleA);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), "Folder A");

		JournalArticle articleAA = JournalTestUtil.addArticle(
			_group.getGroupId(), folder.getFolderId(), "Article AA",
			RandomTestUtil.randomString());

		articles.add(articleAA);

		return articles;
	}

	@DeleteAfterTestRun
	private Group _group;

}