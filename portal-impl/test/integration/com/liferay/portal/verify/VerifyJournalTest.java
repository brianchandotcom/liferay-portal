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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.test.portal.util.GroupTestUtil;
import com.liferay.test.portal.util.RandomTestUtil;
import com.liferay.test.portal.util.TestPropsValues;
import com.liferay.test.portlet.journal.util.JournalTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class VerifyJournalTest extends BaseVerifyTestCase {

	@Test
	public void testJournalArticleTreePathWithJournalArticleInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), parentFolder.getFolderId(), "title", "content");

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), group.getGroupId(),
			article.getArticleId());

		JournalFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testJournalArticleTreePathWithParentJournalFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		JournalFolder grandparentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), grandparentFolder.getFolderId(),
			RandomTestUtil.randomString());

		JournalTestUtil.addArticle(
			group.getGroupId(), parentFolder.getFolderId(), "title", "content");

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testJournalFolderTreePathWithJournalFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testJournalFolderTreePathWithParentJournalFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		JournalFolder grandparentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), grandparentFolder.getFolderId(),
			RandomTestUtil.randomString());

		JournalTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyJournal();
	}

}