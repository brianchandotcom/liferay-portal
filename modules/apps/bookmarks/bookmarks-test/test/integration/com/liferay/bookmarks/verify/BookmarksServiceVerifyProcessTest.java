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

package com.liferay.bookmarks.verify;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.util.BookmarksTestUtil;
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
import com.liferay.portal.verify.BaseVerifyProcessTestCase;
import com.liferay.portal.verify.VerifyProcess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 * @author Eudaldo Alonso
 * @author Sergio González
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BookmarksServiceVerifyProcessTest
	extends BaseVerifyProcessTestCase {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testBookmarksEntryTreePathWithBookmarksEntryInTrash()
		throws Exception {

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			parentFolder.getFolderId(), true, serviceContext);

		BookmarksEntryLocalServiceUtil.moveEntryToTrash(
			TestPropsValues.getUserId(), entry.getEntryId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testBookmarksEntryTreePathWithBookmarksParentFolderInTrash()
		throws Exception {

		BookmarksFolder grandparentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId(),
			RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BookmarksTestUtil.addEntry(
			parentFolder.getFolderId(), true, serviceContext);

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testBookmarksFolderTreePathWithBookmarksFolderInTrash()
		throws Exception {

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testBookmarksFolderTreePathWithBookmarksParentFolderInTrash()
		throws Exception {

		BookmarksFolder grandparentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId(),
			RandomTestUtil.randomString());

		BookmarksTestUtil.addFolder(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		BookmarksFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new BookmarksServiceVerifyProcess();
	}

	@DeleteAfterTestRun
	private Group _group;

}