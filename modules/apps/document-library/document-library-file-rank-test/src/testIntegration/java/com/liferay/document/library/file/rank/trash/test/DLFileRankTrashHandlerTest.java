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

package com.liferay.document.library.file.rank.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.file.rank.model.DLFileRank;
import com.liferay.document.library.file.rank.service.DLFileRankLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 * @author Julio Camarero
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class DLFileRankTrashHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testTrashDLFileRank() throws Exception {
		trashDLFileRank();
	}

	protected FileEntry addFileEntry(
			Folder folder, ServiceContext serviceContext)
		throws Exception {

		return DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), folder.getGroupId(),
			folder.getFolderId(), RandomTestUtil.randomString() + ".txt",
			_FILE_ENTRY_TITLE, true, serviceContext);
	}

	protected Folder addFolder(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		return DLAppServiceUtil.addFolder(
			group.getGroupId(), parentBaseModelId,
			RandomTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH),
			RandomTestUtil.randomString(), serviceContext);
	}

	protected int getActiveDLFileRanksCount(long groupId, long fileEntryId)
		throws Exception {

		List<DLFileRank> dlFileRanks = _dlFileRankLocalService.getFileRanks(
			groupId, TestPropsValues.getUserId());

		int count = 0;

		for (DLFileRank dlFileRank : dlFileRanks) {
			if (dlFileRank.getFileEntryId() == fileEntryId) {
				count++;
			}
		}

		return count;
	}

	protected void trashDLFileRank() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Folder folder = addFolder(
			group, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFileEntry.class.getName());

		FileEntry fileEntry = null;

		try {
			boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

			try {
				WorkflowThreadLocal.setEnabled(true);

				fileEntry = addFileEntry(folder, serviceContext);
			}
			finally {
				WorkflowThreadLocal.setEnabled(workflowEnabled);
			}

			_dlFileRankLocalService.addFileRank(
				group.getGroupId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
				serviceContext);

			Assert.assertEquals(
				1,
				getActiveDLFileRanksCount(
					group.getGroupId(), fileEntry.getFileEntryId()));

			DLTrashServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

			Assert.assertEquals(
				0,
				getActiveDLFileRanksCount(
					group.getGroupId(), fileEntry.getFileEntryId()));

			trashHandler.restoreTrashEntry(
				TestPropsValues.getUserId(), fileEntry.getFileEntryId());

			Assert.assertEquals(
				1,
				getActiveDLFileRanksCount(
					group.getGroupId(), fileEntry.getFileEntryId()));
		}
		finally {
			trashHandler.deleteTrashEntry(fileEntry.getFileEntryId());
		}
	}

	private static final String _FILE_ENTRY_TITLE = RandomTestUtil.randomString(
		255);

	private static final int _FOLDER_NAME_MAX_LENGTH = 100;

	@Inject
	private static DLFileRankLocalService _dlFileRankLocalService;

}