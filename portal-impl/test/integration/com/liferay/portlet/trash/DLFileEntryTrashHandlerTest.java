/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 * @author Julio Camarero
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryTrashHandlerTest extends BaseDLTrashHandlerTestCase {

	@Test
	public void testTrashAndDelete() throws Exception {
		testTrash(false, true, false, false, false);
	}

	@Test
	public void testTrashAndDeleteVersioned() throws Exception {
		testTrash(false, true, true, false, false);
	}

	@Test
	public void testTrashAndDeleteVersionedAndCheckedOut() throws Exception {
		testTrash(false, true, true, true, false);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		testTrash(false, false, false, false, false);
	}

	@Test
	public void testTrashAndRestoreVersioned() throws Exception {
		testTrash(false, false, true, false, false);
	}

	@Test
	public void testTrashAndRestoreVersionedAndCheckedOut() throws Exception {
		testTrash(false, false, true, true, false);
	}

	@Test
	public void testTrashAndRestoreWithFileRank() throws Exception {
		testTrash(false, false, false, false, true);
	}

	@Test
	public void testTrashDraftAndCheckedOutAndRestore() throws Exception {
		testTrash(true, false, false, true, false);
	}

	@Test
	public void testTrashDraftAndRestore() throws Exception {
		testTrash(true, false, false, false, false);
	}

	protected void testTrash(
			boolean draft, boolean delete, boolean versioned,
			boolean leaveCheckedOut, boolean fileRank)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		int initialFileRanksCount = 0;
		int initialNotInTrashCount = getNotInTrashCount();
		int initialTrashEntriesCount = getTrashEntriesCount();
		int initialSearchFileEntriesCount = searchFileEntriesCount();
		int initialSearchTrashFileEntriesCount = searchTrashEntriesCount(
			"Basic", serviceContext);

		FileEntry fileEntry = null;

		if (draft) {
			fileEntry = addFileEntry(
				parentFolder.getFolderId(), "Test Basic.txt", "Test Basic.txt",
				null, WorkflowConstants.ACTION_SAVE_DRAFT);
		}
		else {
			fileEntry = addFileEntry(false, "Test Basic.txt");
		}

		long fileEntryId = fileEntry.getFileEntryId();

		if (versioned) {
			updateFileEntry(fileEntryId, null, "Test Basic 2.txt");
		}

		if (fileRank) {
			addFileRank(fileEntryId);
		}

		if (leaveCheckedOut) {
			DLAppServiceUtil.checkOutFileEntry(
				fileEntryId, new ServiceContext());
		}

		Assert.assertEquals(initialNotInTrashCount + 1, getNotInTrashCount());
		Assert.assertEquals(initialTrashEntriesCount, getTrashEntriesCount());

		if (draft) {
			Assert.assertFalse(
				isAssetEntryVisible(
					DLFileEntryConstants.getClassName(), fileEntryId));

			Assert.assertEquals(
				initialSearchFileEntriesCount, searchFileEntriesCount());
		}
		else {
			Assert.assertTrue(
				isAssetEntryVisible(
					DLFileEntryConstants.getClassName(), fileEntryId));

			Assert.assertEquals(
				initialSearchFileEntriesCount + 1, searchFileEntriesCount());
		}

		Assert.assertEquals(
			initialSearchTrashFileEntriesCount,
			searchTrashEntriesCount("Basic", serviceContext));

		DLAppServiceUtil.moveFileEntryToTrash(fileEntryId);

		Assert.assertEquals(initialNotInTrashCount, getNotInTrashCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 1, getTrashEntriesCount());
		Assert.assertFalse(
			isAssetEntryVisible(
				DLFileEntryConstants.getClassName(), fileEntryId));
		Assert.assertEquals(
			initialSearchFileEntriesCount, searchFileEntriesCount());
		Assert.assertEquals(
			initialSearchTrashFileEntriesCount + 1,
			searchTrashEntriesCount("Basic", serviceContext));

		if (fileRank) {
			Assert.assertEquals(
				initialFileRanksCount, getActiveFileRanksCount(fileEntryId));
		}

		if (delete) {
			TrashEntryServiceUtil.deleteEntries(parentFolder.getGroupId());

			Assert.assertEquals(initialNotInTrashCount, getNotInTrashCount());
			Assert.assertNull(
				fetchAssetEntry(
					DLFileEntryConstants.getClassName(), fileEntryId));
			Assert.assertEquals(
				initialSearchFileEntriesCount, searchFileEntriesCount());
		}
		else {
			DLAppServiceUtil.restoreFileEntryFromTrash(fileEntryId);

			Assert.assertEquals(
				initialNotInTrashCount + 1, getNotInTrashCount());

			if (draft) {
				Assert.assertFalse(
					isAssetEntryVisible(
						DLFileEntryConstants.getClassName(), fileEntryId));
				Assert.assertEquals(
					initialSearchFileEntriesCount, searchFileEntriesCount());
			}
			else {
				Assert.assertTrue(
					isAssetEntryVisible(
						DLFileEntryConstants.getClassName(), fileEntryId));
				Assert.assertEquals(
					initialSearchFileEntriesCount + 1,
					searchFileEntriesCount());
			}

			if (fileRank) {
				Assert.assertEquals(
					initialFileRanksCount + 1,
					getActiveFileRanksCount(fileEntryId));
			}
		}

		Assert.assertEquals(initialTrashEntriesCount, getTrashEntriesCount());
		Assert.assertEquals(
			initialSearchTrashFileEntriesCount,
			searchTrashEntriesCount("Basic", serviceContext));
	}

}