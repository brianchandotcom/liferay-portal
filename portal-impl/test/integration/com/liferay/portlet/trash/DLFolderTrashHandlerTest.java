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
import com.liferay.portal.kernel.repository.model.Folder;
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
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFolderTrashHandlerTest extends BaseDLTrashHandlerTestCase {

	@Test
	public void testTrashAndDelete() throws Exception {
		testTrash(true, false, false);
	}

	@Test
	public void testTrashAndDeleteAndAddFile() throws Exception {
		testTrash(true, true, false);
	}

	@Test
	public void testTrashAndDeleteAndTrashFile() throws Exception {
		testTrash(true, true, true);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		testTrash(false, false, false);
	}

	@Test
	public void testTrashAndRestoreAndAddFile() throws Exception {
		testTrash(false, true, false);
	}

	@Test
	public void testTrashAndRestoreAndTrashFile() throws Exception {
		testTrash(false, true, true);
	}

	protected void testTrash(boolean delete, boolean file, boolean trashFile)
		throws Exception {

		int initialFileRanksCount = 0;
		int initialNotInTrashCount = getNotInTrashCount();
		int initialTrashEntriesCount = getTrashEntriesCount();
		int initialSearchFileEntriesCount = searchFileEntriesCount();

		Folder folder = addFolder(parentFolder.getFolderId(), "Test Folder");

		Folder subfolder = addFolder(folder.getFolderId(), "Test Subfolder");

		long fileEntryId = 0;

		if (file) {
			FileEntry fileEntry = addFileEntry(
				subfolder.getFolderId(), "Test File.txt");

			fileEntryId = fileEntry.getFileEntryId();

			addFileRank(fileEntryId);

			Assert.assertEquals(
				initialSearchFileEntriesCount + 1, searchFileEntriesCount());
			Assert.assertTrue(
				isAssetEntryVisible(
					DLFileEntryConstants.getClassName(), fileEntryId));

			if (trashFile) {
				DLAppServiceUtil.moveFileEntryToTrash(fileEntryId);
			}
		}

		Assert.assertEquals(initialNotInTrashCount + 1, getNotInTrashCount());

		if (trashFile) {
			Assert.assertEquals(
				initialTrashEntriesCount + 1, getTrashEntriesCount());
		}
		else {
			Assert.assertEquals(
				initialTrashEntriesCount, getTrashEntriesCount());
		}

		DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		Assert.assertEquals(initialNotInTrashCount, getNotInTrashCount());

		if (trashFile) {
			Assert.assertEquals(
				initialTrashEntriesCount + 2, getTrashEntriesCount());
		}
		else {
			Assert.assertEquals(
				initialTrashEntriesCount + 1, getTrashEntriesCount());
		}

		Assert.assertEquals(
			initialSearchFileEntriesCount, searchFileEntriesCount());

		if (file) {
			Assert.assertFalse(
				isAssetEntryVisible(
					DLFileEntryConstants.getClassName(), fileEntryId));
			Assert.assertEquals(
				initialFileRanksCount, getActiveFileRankCount(fileEntryId));
		}

		if (delete) {
			TrashEntryServiceUtil.deleteEntries(folder.getGroupId());

			Assert.assertEquals(initialNotInTrashCount, getNotInTrashCount());
			Assert.assertEquals(
				initialSearchFileEntriesCount, searchFileEntriesCount());

			if (file) {
				Assert.assertNull(
					fetchAssetEntry(
						DLFileEntryConstants.getClassName(), fileEntryId));
			}
		}
		else {
			DLAppServiceUtil.restoreFolderFromTrash(folder.getFolderId());

			Assert.assertEquals(
				initialNotInTrashCount + 1, getNotInTrashCount());

			if (file) {
				if (trashFile) {
					Assert.assertEquals(
						initialTrashEntriesCount + 1, getTrashEntriesCount());
					Assert.assertEquals(
						initialSearchFileEntriesCount,
						searchFileEntriesCount());
					Assert.assertFalse(
						isAssetEntryVisible(
							DLFileEntryConstants.getClassName(), fileEntryId));

					return;
				}
				else {
					Assert.assertEquals(
						initialSearchFileEntriesCount + 1,
						searchFileEntriesCount());
					Assert.assertTrue(
						isAssetEntryVisible(
							DLFileEntryConstants.getClassName(), fileEntryId));
					Assert.assertEquals(
						initialFileRanksCount + 1,
						getActiveFileRankCount(fileEntryId));
				}
			}
		}

		Assert.assertEquals(initialTrashEntriesCount, getTrashEntriesCount());
	}

}