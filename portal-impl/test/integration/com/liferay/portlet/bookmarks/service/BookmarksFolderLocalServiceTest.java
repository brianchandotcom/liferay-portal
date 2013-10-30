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
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import java.lang.reflect.Field;

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
public class BookmarksFolderLocalServiceTest {

	@After
	public void tearDown() throws Exception {
		for (int i = _folders.size() - 1; i >= 0; i--) {
			BookmarksFolderLocalServiceUtil.deleteBookmarksFolder(
				_folders.get(i));
		}
	}

	@Test
	public void testRebuildTree() throws Exception {
		createTree();

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE");

		int oldSize = field.getInt(null);

		field.setInt(null, 3);

		try {
			for (BookmarksFolder folder : _folders) {
				folder.setTreePath(null);

				BookmarksFolderLocalServiceUtil.updateBookmarksFolder(folder);
			}

			BookmarksFolderLocalServiceUtil.rebuildTree(
				TestPropsValues.getCompanyId());

			for (BookmarksFolder folder : _folders) {
				folder = BookmarksFolderLocalServiceUtil.getFolder(
					folder.getFolderId());

				Assert.assertEquals(
					folder.buildTreePath(), folder.getTreePath());
			}
		}
		finally {
			field.setInt(null, oldSize);
		}
	}

	protected void createTree() throws Exception {

		/**
		 * Tree 1
		 *
		 * /A--->/A--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |     |--->/D
		 *  |
		 *  |--->/B--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |
		 *  |--->/C--->/A
		 *        |--->/B
		 */

		BookmarksFolder folderA = BookmarksTestUtil.addFolder("Folder A");

		_folders.add(folderA);

		BookmarksFolder folderAA = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AA");

		_folders.add(folderAA);

		BookmarksFolder folderAAA = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAA");

		_folders.add(folderAAA);

		BookmarksFolder folderAAB = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAB");

		_folders.add(folderAAB);

		BookmarksFolder folderAAC = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAC");

		_folders.add(folderAAC);

		BookmarksFolder folderAAD = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAD");

		_folders.add(folderAAD);

		BookmarksFolder folderAB = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AB");

		_folders.add(folderAB);

		BookmarksFolder folderABA = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABA");

		_folders.add(folderABA);

		BookmarksFolder folderABB = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABB");

		_folders.add(folderABB);

		BookmarksFolder folderABC = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABC");

		_folders.add(folderABC);

		BookmarksFolder folderAC = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AC");

		_folders.add(folderAC);

		BookmarksFolder folderACA = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAC.getFolderId(), "Folder ACA");

		_folders.add(folderACA);

		BookmarksFolder folderACB = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAC.getFolderId(), "Folder ACB");

		_folders.add(folderACB);

		/**
		 * Tree 2
		 *
		 * /B--->/A--->/A
		 *  |     |--->/B
		 *  |
		 *  |--->/B--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |
		 *  |--->/C--->/A
		 *        |--->/B
		 *        |--->/C
		 *        |--->/D
		 */

		BookmarksFolder folderB = BookmarksTestUtil.addFolder("Folder B");

		_folders.add(folderB);

		BookmarksFolder folderBA = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BA");

		_folders.add(folderBA);

		BookmarksFolder folderBAA = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBA.getFolderId(), "Folder BAA");

		_folders.add(folderBAA);

		BookmarksFolder folderBAB = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBA.getFolderId(), "Folder BAB");

		_folders.add(folderBAB);

		BookmarksFolder folderBB = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BB");

		_folders.add(folderBB);

		BookmarksFolder folderBBA = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBA");

		_folders.add(folderBBA);

		BookmarksFolder folderBBB = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBB");

		_folders.add(folderBBB);

		BookmarksFolder folderBBC = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBC");

		_folders.add(folderBBC);

		BookmarksFolder folderBC = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BC");

		_folders.add(folderBC);

		BookmarksFolder folderBCA = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCA");

		_folders.add(folderBCA);

		BookmarksFolder folderBCB = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCB");

		_folders.add(folderBCB);

		BookmarksFolder folderBCC = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCC");

		_folders.add(folderBCC);

		BookmarksFolder folderBCD = BookmarksTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCD");

		_folders.add(folderBCD);
	}

	private List<BookmarksFolder> _folders = new ArrayList<BookmarksFolder>();

}