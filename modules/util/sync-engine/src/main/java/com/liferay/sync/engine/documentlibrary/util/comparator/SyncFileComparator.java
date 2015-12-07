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

package com.liferay.sync.engine.documentlibrary.util.comparator;

import com.liferay.sync.engine.model.SyncFile;

import java.util.Comparator;

/**
 * @author Shinn Lok
 */
public class SyncFileComparator implements Comparator<SyncFile> {

	@Override
	public int compare(SyncFile syncFile1, SyncFile syncFile2) {
		String name1 = syncFile1.getName();
		String name2 = syncFile2.getName();

		if ((syncFile1.getParentFolderId() == syncFile2.getParentFolderId()) &&
			name1.equals(name2)) {

			return Long.compare(
				syncFile1.getModifiedTime(), syncFile2.getModifiedTime());
		}
		else {
			return Long.compare(syncFile1.getSize(), syncFile2.getSize());
		}
	}

}