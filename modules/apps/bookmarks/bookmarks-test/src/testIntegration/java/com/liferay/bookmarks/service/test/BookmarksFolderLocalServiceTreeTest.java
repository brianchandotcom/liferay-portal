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

package com.liferay.bookmarks.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.bookmarks.test.util.BookmarksTestUtil;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.local.service.tree.test.util.BaseLocalServiceTreeTestCase;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@RunWith(Arquillian.class)
public class BookmarksFolderLocalServiceTreeTest
	extends BaseLocalServiceTreeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Test
	public void testFolderTreePathWhenMovingFolderWithSubfolder()
		throws Exception {

		List<BookmarksFolder> folders = new ArrayList<>();

		BookmarksFolder folderA = BookmarksTestUtil.addFolder(
			group.getGroupId(), "Folder A");

		folders.add(folderA);

		BookmarksFolder folderAA = BookmarksTestUtil.addFolder(
			group.getGroupId(), folderA.getFolderId(), "Folder AA");

		folders.add(folderAA);

		BookmarksFolder folderAAA = BookmarksTestUtil.addFolder(
			group.getGroupId(), folderAA.getFolderId(), "Folder AAA");

		folders.add(folderAAA);

		BookmarksFolderServiceUtil.moveFolder(
			folderAA.getFolderId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (BookmarksFolder folder : folders) {
			folder = BookmarksFolderLocalServiceUtil.fetchBookmarksFolder(
				folder.getFolderId());

			Assert.assertEquals(folder.buildTreePath(), folder.getTreePath());
		}
	}

	@Override
	protected TreeModel addTreeModel(TreeModel parentTreeModel)
		throws Exception {

		long parentFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (parentTreeModel != null) {
			BookmarksFolder folder = (BookmarksFolder)parentTreeModel;

			parentFolderId = folder.getFolderId();
		}

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			group.getGroupId(), parentFolderId, RandomTestUtil.randomString());

		folder.setTreePath(null);

		return BookmarksFolderLocalServiceUtil.updateBookmarksFolder(folder);
	}

	@Override
	protected void deleteTreeModel(TreeModel treeModel) throws Exception {
		BookmarksFolder folder = (BookmarksFolder)treeModel;

		BookmarksFolderLocalServiceUtil.deleteFolder(folder);
	}

	@Override
	protected TreeModel getTreeModel(long primaryKey) throws Exception {
		return BookmarksFolderLocalServiceUtil.getFolder(primaryKey);
	}

	@Override
	protected void rebuildTree() throws Exception {
		BookmarksFolderLocalServiceUtil.rebuildTree(
			TestPropsValues.getCompanyId());
	}

}