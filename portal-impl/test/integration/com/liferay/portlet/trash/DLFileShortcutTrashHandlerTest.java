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
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileShortcutTrashHandlerTest extends BaseDLTrashHandlerTestCase {

	@Test
	public void testTrashAndDelete() throws Exception {
		testTrash(true);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		testTrash(false);
	}

	protected void testTrash(boolean delete) throws Exception {
		int initialNotInTrashCount = getNotInTrashCount();
		int initialTrashEntriesCount = getTrashEntriesCount();

		FileEntry fileEntry = addFileEntry(false, "Test Basic.txt");

		long fileEntryId = fileEntry.getFileEntryId();

		DLFileShortcut fileShortcut = addFileShortcut(fileEntry);

		long fileShortcutId = fileShortcut.getFileShortcutId();

		Assert.assertEquals(initialNotInTrashCount + 2, getNotInTrashCount());
		Assert.assertEquals(initialTrashEntriesCount, getTrashEntriesCount());

		DLAppServiceUtil.moveFileEntryToTrash(fileEntryId);

		Assert.assertEquals(initialNotInTrashCount, getNotInTrashCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 1, getTrashEntriesCount());

		DLAppServiceUtil.restoreFileEntryFromTrash(fileEntryId);

		Assert.assertEquals(initialNotInTrashCount + 2, getNotInTrashCount());

		DLAppServiceUtil.moveFileShortcutToTrash(fileShortcutId);

		Assert.assertEquals(initialNotInTrashCount + 1, getNotInTrashCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 1, getTrashEntriesCount());

		if (delete) {
			TrashEntryServiceUtil.deleteEntries(parentFolder.getGroupId());

			Assert.assertEquals(
				initialNotInTrashCount + 1, getNotInTrashCount());
			Assert.assertNull(
				fetchAssetEntry(
					DLFileShortcut.class.getName(), fileShortcutId));
		}
		else {
			DLAppServiceUtil.restoreFileShortcutFromTrash(fileShortcutId);

			Assert.assertEquals(
				initialNotInTrashCount + 2, getNotInTrashCount());
		}

		Assert.assertEquals(initialTrashEntriesCount, getTrashEntriesCount());
	}

}