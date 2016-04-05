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

package com.liferay.sync.engine.documentlibrary.util;

import com.liferay.sync.engine.documentlibrary.util.comparator.SyncFileComparator;
import com.liferay.sync.engine.model.SyncFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class SyncFileComparatorTest {

	@Test
	public void testSort() {
		SyncFile syncFile1 = addSyncFile(1, SyncFile.EVENT_ADD, 100);
		SyncFile syncFile2 = addSyncFile(2, SyncFile.EVENT_ADD, 200);
		SyncFile syncFile3 = addSyncFile(3, SyncFile.EVENT_DELETE, 0);
		SyncFile syncFile4 = addSyncFile(4, SyncFile.EVENT_TRASH, 300);
		SyncFile syncFile5 = addSyncFile(5, SyncFile.EVENT_UPDATE, 0);
		SyncFile syncFile6 = addSyncFile(6, SyncFile.EVENT_MOVE, 0);

		Collections.sort(_syncFiles, new SyncFileComparator());

		Assert.assertArrayEquals(
			new SyncFile[] {
				syncFile3, syncFile4, syncFile6, syncFile5, syncFile1, syncFile2
			},
			_syncFiles.toArray());
	}

	protected SyncFile addSyncFile(long syncFileId, String event, long size) {
		SyncFile syncFile = new SyncFile();

		syncFile.setSyncFileId(syncFileId);
		syncFile.setEvent(event);
		syncFile.setSize(size);

		_syncFiles.add(syncFile);

		return syncFile;
	}

	private final List<SyncFile> _syncFiles = new ArrayList<>();

}