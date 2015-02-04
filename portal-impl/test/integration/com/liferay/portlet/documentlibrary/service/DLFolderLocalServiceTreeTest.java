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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.TreeModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.BaseLocalServiceTreeTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class DLFolderLocalServiceTreeTest extends BaseLocalServiceTreeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testFolderTreePathWhenMovingFolderWithSubfolder()
		throws Exception {

		List<Folder> folders = new ArrayList<>();

		Folder folderA = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		folders.add(folderA);

		Folder folderAA = DLAppTestUtil.addFolder(
			group.getGroupId(), folderA.getFolderId(), "Folder AA");

		folders.add(folderAA);

		Folder folderAAA = DLAppTestUtil.addFolder(
			group.getGroupId(), folderAA.getFolderId(), "Folder AAA");

		folders.add(folderAAA);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DLAppLocalServiceUtil.moveFolder(
			TestPropsValues.getUserId(), folderAA.getFolderId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		for (Folder folder : folders) {
			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
				folder.getFolderId());

			Assert.assertEquals(
				dlFolder.buildTreePath(), dlFolder.getTreePath());
		}
	}

	@Override
	protected TreeModel addTreeModel(TreeModel parentTreeModel)
		throws Exception {

		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (parentTreeModel != null) {
			DLFolder folder = (DLFolder)parentTreeModel;

			parentFolderId = folder.getFolderId();
		}

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), parentFolderId);

		DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
			folder.getFolderId());

		dlFolder.setTreePath(null);

		return DLFolderLocalServiceUtil.updateDLFolder(dlFolder);
	}

	@Override
	protected void deleteTreeModel(TreeModel treeModel) throws Exception {
		DLFolder folder = (DLFolder)treeModel;

		DLFolderLocalServiceUtil.deleteFolder(folder.getFolderId());
	}

	@Override
	protected TreeModel getTreeModel(long primaryKey) throws Exception {
		return DLFolderLocalServiceUtil.getFolder(primaryKey);
	}

	@Override
	protected void rebuildTree() throws Exception {
		DLFolderLocalServiceUtil.rebuildTree(TestPropsValues.getCompanyId());
	}

}