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
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.BaseDLAppTestCase;
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
public class DLFileEntryTrashHandlerTest extends BaseDLAppTestCase {

	@Test
	public void testTrashAndDelete() throws Exception {
		testTrash(true, false, false);
	}

	@Test
	public void testTrashAndDeleteVersioned() throws Exception {
		testTrash(true, true, false);
	}

	@Test
	public void testTrashAndDeleteVersionedAndCheckedOut() throws Exception {
		testTrash(true, true, true);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		testTrash(false, false, false);
	}

	@Test
	public void testTrashAndRestoreVersioned() throws Exception {
		testTrash(false, true, false);
	}

	@Test
	public void testTrashAndRestoreVersionedAndCheckedOut() throws Exception {
		testTrash(false, true, true);
	}

	protected AssetEntry fetchAssetEntry(long fileEntryId) throws Exception {
		return AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntryId);
	}

	protected int getFileEntriesNotInTrashCount() throws Exception {
		return DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
			folder.getRepositoryId(), folder.getFolderId(),
			WorkflowConstants.STATUS_ANY, false);
	}

	protected int getTrashEntriesCount() throws Exception {
		Object[] result = TrashEntryServiceUtil.getEntries(folder.getGroupId());

		return (Integer)result[1];
	}

	protected boolean isAssetEntryVisible(long fileEntryId) throws Exception {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			DLFileEntryConstants.getClassName(), fileEntryId);

		return assetEntry.isVisible();
	}

	protected void testTrash(
			boolean delete, boolean versioned, boolean leaveCheckedOut)
		throws Exception {

		int initialFileEntriesCount = getFileEntriesNotInTrashCount();
		int initialTrashEntriesCount = getTrashEntriesCount();

		FileEntry fileEntry = addFileEntry(false, "Test Basic.txt");

		long fileEntryId = fileEntry.getFileEntryId();

		if (versioned) {
			updateFileEntry(fileEntryId, null, "Test Basic 2.txt");
		}

		if (leaveCheckedOut) {
			DLAppServiceUtil.checkOutFileEntry(
				fileEntryId, new ServiceContext());
		}

		Assert.assertEquals(
			initialFileEntriesCount + 1, getFileEntriesNotInTrashCount());
		Assert.assertEquals(initialTrashEntriesCount, getTrashEntriesCount());
		Assert.assertTrue(isAssetEntryVisible(fileEntryId));

		DLAppServiceUtil.moveFileEntryToTrash(fileEntryId);

		Assert.assertEquals(
			initialFileEntriesCount, getFileEntriesNotInTrashCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 1, getTrashEntriesCount());
		Assert.assertFalse(isAssetEntryVisible(fileEntryId));

		if (delete) {
			TrashEntryServiceUtil.deleteEntries(folder.getGroupId());

			Assert.assertEquals(
				initialFileEntriesCount, getFileEntriesNotInTrashCount());
			Assert.assertNull(fetchAssetEntry(fileEntryId));
		}
		else {
			DLAppServiceUtil.restoreFileEntryFromTrash(fileEntryId);

			Assert.assertEquals(
				initialFileEntriesCount + 1, getFileEntriesNotInTrashCount());
			Assert.assertTrue(isAssetEntryVisible(fileEntryId));
		}

		Assert.assertEquals(initialTrashEntriesCount, getTrashEntriesCount());
	}

}