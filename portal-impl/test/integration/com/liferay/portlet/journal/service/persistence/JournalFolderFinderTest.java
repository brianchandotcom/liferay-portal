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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.spring.hibernate.LastSessionRecorderUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class JournalFolderFinderTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder1 = JournalTestUtil.addFolder(_group.getGroupId(), "Folder 1");
		_folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(), _folder1.getFolderId(), "Folder 2");

		JournalTestUtil.addArticle(
			_group.getGroupId(), _folder1.getFolderId(), "Article 1",
			StringPool.BLANK);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), _folder1.getFolderId(), "Article 2",
			StringPool.BLANK);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);
	}

	@Test
	public void testCountF_A_ByG_F() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			3,
			JournalFolderFinderUtil.countF_A_ByG_F(
				_group.getGroupId(), _folder1.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			JournalFolderFinderUtil.countF_A_ByG_F(
				_group.getGroupId(), _folder1.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2,
			JournalFolderFinderUtil.countF_A_ByG_F(
				_group.getGroupId(), _folder1.getFolderId(), queryDefinition));
	}

	@Test
	public void testFindF_A_ByG_F() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<Object> results = JournalFolderFinderUtil.findF_A_ByG_F(
			_group.getGroupId(), _folder1.getFolderId(), queryDefinition);

		Assert.assertEquals(3, results.size());

		for (Object result : results) {
			if (result instanceof JournalFolder) {
				JournalFolder folder = (JournalFolder)result;

				Assert.assertEquals("Folder 2", folder.getName());
			}
			else if (result instanceof JournalArticle) {
				JournalArticle article = (JournalArticle)result;

				String title = article.getTitleCurrentValue();

				if (!title.equals("Article 1") && !title.equals("Article 2")) {
					Assert.fail(title);
				}
			}
		}

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		results = JournalFolderFinderUtil.findF_A_ByG_F(
			_group.getGroupId(), _folder1.getFolderId(), queryDefinition);

		Assert.assertEquals(1, results.size());

		for (Object result : results) {
			if (result instanceof JournalFolder) {
				JournalFolder folder = (JournalFolder)result;

				Assert.assertEquals("Folder 2", folder.getName());
			}
			else if (result instanceof JournalArticle) {
				JournalArticle article = (JournalArticle)result;

				Assert.assertEquals(
					"Article 2", article.getTitleCurrentValue());
			}
		}

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		results = JournalFolderFinderUtil.findF_A_ByG_F(
			_group.getGroupId(), _folder1.getFolderId(), queryDefinition);

		Assert.assertEquals(2, results.size());

		for (Object result : results) {
			if (result instanceof JournalFolder) {
				JournalFolder folder = (JournalFolder)result;

				Assert.assertEquals("Folder 2", folder.getName());
			}
			else if (result instanceof JournalArticle) {
				JournalArticle article = (JournalArticle)result;

				Assert.assertEquals(
					"Article 1", article.getTitleCurrentValue());
			}
		}
	}

	@Test
	public void testFindF_ByNoAssets() throws Exception {
		AssetEntryLocalServiceUtil.deleteEntry(
			JournalFolder.class.getName(), _folder2.getFolderId());

		LastSessionRecorderUtil.syncLastSessionState();

		List<JournalFolder> folders =
			JournalFolderFinderUtil.findF_ByNoAssets();

		Assert.assertEquals(1, folders.size());

		JournalFolder folder = folders.get(0);

		Assert.assertEquals(_folder2.getFolderId(), folder.getFolderId());
	}

	private JournalFolder _folder1;
	private JournalFolder _folder2;
	private Group _group;

}