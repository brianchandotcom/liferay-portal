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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.TreeModel;
import com.liferay.portal.service.BaseLocalServiceTreeTestCase;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BookmarksFolderLocalServiceTreeTestCase
	extends BaseLocalServiceTreeTestCase {

	@Override
	protected TreeModel addTreeModel(TreeModel parentTreeModel)
		throws Exception {

		long parentFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (parentTreeModel != null) {
			BookmarksFolder folder = (BookmarksFolder)parentTreeModel;

			parentFolderId = folder.getFolderId();
		}

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), parentFolderId,
			ServiceTestUtil.randomString());

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