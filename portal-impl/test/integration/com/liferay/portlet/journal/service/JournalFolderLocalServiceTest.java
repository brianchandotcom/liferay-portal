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
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

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
public class JournalFolderLocalServiceTest {

	@After
	public void tearDown() throws Exception {
		for (int i = _folders.size() - 1; i >= 0; i--) {
			JournalFolderLocalServiceUtil.deleteJournalFolder(_folders.get(i));
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
			for (JournalFolder folder : _folders) {
				folder.setTreePath(null);

				JournalFolderLocalServiceUtil.updateJournalFolder(folder);
			}

			JournalFolderLocalServiceUtil.rebuildTree(
				TestPropsValues.getCompanyId());

			for (JournalFolder folder : _folders) {
				folder = JournalFolderLocalServiceUtil.getFolder(
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

		JournalFolder folderA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), "Folder A");

		_folders.add(folderA);

		JournalFolder folderAA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AA");

		_folders.add(folderAA);

		JournalFolder folderAAA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAA");

		_folders.add(folderAAA);

		JournalFolder folderAAB = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAB");

		_folders.add(folderAAB);

		JournalFolder folderAAC = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAC");

		_folders.add(folderAAC);

		JournalFolder folderAAD = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAD");

		_folders.add(folderAAD);

		JournalFolder folderAB = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AB");

		_folders.add(folderAB);

		JournalFolder folderABA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABA");

		_folders.add(folderABA);

		JournalFolder folderABB = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABB");

		_folders.add(folderABB);

		JournalFolder folderABC = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABC");

		_folders.add(folderABC);

		JournalFolder folderAC = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AC");

		_folders.add(folderAC);

		JournalFolder folderACA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAC.getFolderId(), "Folder ACA");

		_folders.add(folderACA);

		JournalFolder folderACB = JournalTestUtil.addFolder(
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

		JournalFolder folderB = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), "Folder B");

		_folders.add(folderB);

		JournalFolder folderBA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BA");

		_folders.add(folderBA);

		JournalFolder folderBAA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBA.getFolderId(), "Folder BAA");

		_folders.add(folderBAA);

		JournalFolder folderBAB = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBA.getFolderId(), "Folder BAB");

		_folders.add(folderBAB);

		JournalFolder folderBB = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BB");

		_folders.add(folderBB);

		JournalFolder folderBBA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBA");

		_folders.add(folderBBA);

		JournalFolder folderBBB = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBB");

		_folders.add(folderBBB);

		JournalFolder folderBBC = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBC");

		_folders.add(folderBBC);

		JournalFolder folderBC = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BC");

		_folders.add(folderBC);

		JournalFolder folderBCA = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCA");

		_folders.add(folderBCA);

		JournalFolder folderBCB = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCB");

		_folders.add(folderBCB);

		JournalFolder folderBCC = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCC");

		_folders.add(folderBCC);

		JournalFolder folderBCD = JournalTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCD");

		_folders.add(folderBCD);
	}

	private List<JournalFolder> _folders = new ArrayList<JournalFolder>();

}