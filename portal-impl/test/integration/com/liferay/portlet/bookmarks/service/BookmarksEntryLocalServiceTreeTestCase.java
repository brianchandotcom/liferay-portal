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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Shinn Lok
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BookmarksEntryLocalServiceTreeTestCase {

	@After
	public void tearDown() throws Exception {
		for (int i = _entries.size() - 1; i >= 0; i--) {
			BookmarksEntryLocalServiceUtil.deleteBookmarksEntry(
				_entries.get(i));
		}

		BookmarksFolderLocalServiceUtil.deleteBookmarksFolder(_folder);
	}

	@Test
	public void testRebuildTree() throws Exception {
		createTree();

		for (BookmarksEntry entry : _entries) {
			entry.setTreePath(null);

			BookmarksEntryLocalServiceUtil.updateBookmarksEntry(entry);
		}

		BookmarksEntryLocalServiceUtil.rebuildTree(
			TestPropsValues.getCompanyId());

		for (BookmarksEntry entry : _entries) {
			entry = BookmarksEntryLocalServiceUtil.getEntry(entry.getEntryId());

			Assert.assertEquals(entry.buildTreePath(), entry.getTreePath());
		}
	}

	protected void createTree() throws Exception {
		BookmarksEntry entryA = BookmarksTestUtil.addEntry(true);

		_entries.add(entryA);

		_folder = BookmarksTestUtil.addFolder("Folder A");

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId());

		BookmarksEntry entryAA = BookmarksTestUtil.addEntry(
			_folder.getFolderId(), true, serviceContext);

		_entries.add(entryAA);
	}

	private List<BookmarksEntry> _entries = new ArrayList<BookmarksEntry>();
	private BookmarksFolder _folder;

}