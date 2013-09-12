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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 * @author Eudaldo Alonso
 * @author Sergio González
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class VerifyBookmarksTest extends BaseVerifyTestCase {

	@Test
	@Transactional
	public void testBookmarksEntryTreePathWithBookmarksEntryInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), "parentFolder");

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			parentFolder.getFolderId(), true, serviceContext);

		BookmarksEntryLocalServiceUtil.moveEntryToTrash(
			TestPropsValues.getUserId(), entry.getEntryId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	@Transactional
	public void testBookmarksEntryTreePathWithBookmarksParentFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BookmarksFolder grandParentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), "grandParentFolder");

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), grandParentFolder.getFolderId(),
			"parentFolder");

		BookmarksTestUtil.addEntry(
			parentFolder.getFolderId(), true, serviceContext);

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			grandParentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	@Transactional
	public void testBookmarksFolderTreePathWithBookmarksFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), "parentFolder");

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "folder");

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	@Transactional
	public void testBookmarksFolderTreePathWithBookmarksParentFolderInTrash()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		BookmarksFolder grandParentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), "grandParentFolder");

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), grandParentFolder.getFolderId(),
			"parentFolder");

		BookmarksTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "folder");

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			grandParentFolder.getFolderId(), false);

		doVerify();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyBookmarks();
	}

}