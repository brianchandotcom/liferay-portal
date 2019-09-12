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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.local.service.tree.test.util.BaseLocalServiceTreeTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@RunWith(Arquillian.class)
public class DLFolderLocalServiceTreeTest extends BaseLocalServiceTreeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testFolderTreePathWhenMovingFolderWithSubfolder()
		throws Exception {

		List<Folder> folders = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		Folder folderA = DLAppServiceUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A", RandomTestUtil.randomString(), serviceContext);

		folders.add(folderA);

		Folder folderAA = DLAppServiceUtil.addFolder(
			group.getGroupId(), folderA.getFolderId(), "Folder AA",
			RandomTestUtil.randomString(), serviceContext);

		folders.add(folderAA);

		Folder folderAAA = DLAppServiceUtil.addFolder(
			group.getGroupId(), folderAA.getFolderId(), "Folder AAA",
			RandomTestUtil.randomString(), serviceContext);

		folders.add(folderAAA);

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			group.getGroupId(), parentFolderId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

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