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
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
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
	public void testFileIsInTrashFolder() throws Exception {
		Folder folder = addFolder(false, "Test Folder");

		Folder subfolder = addFolder(folder.getFolderId(), "Test Subfolder");

		FileEntry fileEntry = addFileEntry(
			subfolder.getFolderId(), "Test File.txt");

		LiferayFileVersion liferayFileVersion =
			(LiferayFileVersion)fileEntry.getFileVersion();

		Assert.assertFalse(liferayFileVersion.isInTrashFolder());

		DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		Assert.assertTrue(liferayFileVersion.isInTrashFolder());
	}

	@Test
	public void testTrashAndDelete() throws Exception {
		testTrash(true, false, false, false);
	}

	@Test
	public void testTrashAndDeleteAndAddFile() throws Exception {
		testTrash(true, true, false, false);
	}

	@Test
	public void testTrashAndDeleteAndTrashFile() throws Exception {
		testTrash(true, true, true, false);
	}

	@Test
	public void testTrashAndMoveFile() throws Exception {
		testTrash(false, true, false, true);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		testTrash(false, false, false, false);
	}

	@Test
	public void testTrashAndRestoreAndAddFile() throws Exception {
		testTrash(false, true, false, false);
	}

	@Test
	public void testTrashAndRestoreAndTrashFile() throws Exception {
		testTrash(false, true, true, false);
	}

	protected void testTrash(
			boolean delete, boolean file, boolean trashFile,
			boolean moveFileFromTrash)
		throws Exception {

		int initialNotInTrashCount = getNotInTrashCount();
		int initialTrashEntriesCount = getTrashEntriesCount();
		int initialSearchFileEntriesCount = searchFileEntriesCount();

		Folder folder = addFolder(false, "Test Folder");

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
			Assert.assertEquals(0, getActiveFileRanksCount(fileEntryId));
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
		else if (moveFileFromTrash) {
			DLAppServiceUtil.moveFileEntryFromTrash(
				fileEntryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ServiceTestUtil.getServiceContext());

			Assert.assertTrue(
				isAssetEntryVisible(
					DLFileEntryConstants.getClassName(), fileEntryId));
			Assert.assertEquals(1, getActiveFileRanksCount(fileEntryId));
			Assert.assertEquals(
				initialSearchFileEntriesCount + 1, searchFileEntriesCount());
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
						1, getActiveFileRanksCount(fileEntryId));
				}
			}
		}

		if (moveFileFromTrash) {
			Assert.assertEquals(
				initialTrashEntriesCount +1, getTrashEntriesCount());

			DLAppServiceUtil.deleteFileEntry(fileEntryId);
		}
		else {
			Assert.assertEquals(
				initialTrashEntriesCount, getTrashEntriesCount());
		}
	}

}