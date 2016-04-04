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
		String event1 = syncFile1.getEvent();
		String event2 = syncFile2.getEvent();

		if (event1.equals(SyncFile.EVENT_DELETE) ||
			event1.equals(SyncFile.EVENT_MOVE) ||
			event1.equals(SyncFile.EVENT_TRASH)) {

			if (event2.equals(SyncFile.EVENT_DELETE) ||
				event2.equals(SyncFile.EVENT_MOVE) ||
				event2.equals(SyncFile.EVENT_TRASH)) {

				return 0;
			}

			return -1;
		}
		else if (event2.equals(SyncFile.EVENT_DELETE) ||
				 event2.equals(SyncFile.EVENT_MOVE) ||
				 event2.equals(SyncFile.EVENT_TRASH)) {

			return 1;
		}
		else {
			return Long.compare(syncFile1.getSize(), syncFile2.getSize());
		}
	}

}