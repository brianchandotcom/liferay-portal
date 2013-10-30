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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

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
public class DLFolderLocalServiceTest {

	@After
	public void tearDown() throws Exception {
		for (int i = _folders.size() - 1; i >= 0; i--) {
			Folder folder = _folders.get(i);

			DLAppLocalServiceUtil.deleteFolder(folder.getFolderId());
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
			for (Folder folder : _folders) {
				DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
					folder.getFolderId());

				dlFolder.setTreePath(null);

				DLFolderLocalServiceUtil.updateDLFolder(dlFolder);
			}

			DLFolderLocalServiceUtil.rebuildTree(
				TestPropsValues.getCompanyId());

			for (Folder folder : _folders) {
				DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
					folder.getFolderId());

				Assert.assertEquals(
					dlFolder.buildTreePath(), dlFolder.getTreePath());
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

		Folder folderA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A");

		_folders.add(folderA);

		Folder folderAA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AA");

		_folders.add(folderAA);

		Folder folderAAA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAA");

		_folders.add(folderAAA);

		Folder folderAAB = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAB");

		_folders.add(folderAAB);

		Folder folderAAC = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAC");

		_folders.add(folderAAC);

		Folder folderAAD = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAA.getFolderId(), "Folder AAD");

		_folders.add(folderAAD);

		Folder folderAB = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AB");

		_folders.add(folderAB);

		Folder folderABA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABA");

		_folders.add(folderABA);

		Folder folderABB = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABB");

		_folders.add(folderABB);

		Folder folderABC = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAB.getFolderId(), "Folder ABC");

		_folders.add(folderABC);

		Folder folderAC = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderA.getFolderId(), "Folder AC");

		_folders.add(folderAC);

		Folder folderACA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderAC.getFolderId(), "Folder ACA");

		_folders.add(folderACA);

		Folder folderACB = DLAppTestUtil.addFolder(
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

		Folder folderB = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder B");

		_folders.add(folderB);

		Folder folderBA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BA");

		_folders.add(folderBA);

		Folder folderBAA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBA.getFolderId(), "Folder BAA");

		_folders.add(folderBAA);

		Folder folderBAB = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBA.getFolderId(), "Folder BAB");

		_folders.add(folderBAB);

		Folder folderBB = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BB");

		_folders.add(folderBB);

		Folder folderBBA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBA");

		_folders.add(folderBBA);

		Folder folderBBB = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBB");

		_folders.add(folderBBB);

		Folder folderBBC = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBB.getFolderId(), "Folder BBC");

		_folders.add(folderBBC);

		Folder folderBC = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderB.getFolderId(), "Folder BC");

		_folders.add(folderBC);

		Folder folderBCA = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCA");

		_folders.add(folderBCA);

		Folder folderBCB = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCB");

		_folders.add(folderBCB);

		Folder folderBCC = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCC");

		_folders.add(folderBCC);

		Folder folderBCD = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(), folderBC.getFolderId(), "Folder BCD");

		_folders.add(folderBCD);
	}

	private List<Folder> _folders = new ArrayList<Folder>();

}