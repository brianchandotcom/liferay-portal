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

package com.liferay.document.library.kernel.util.comparator;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class RepositoryModelReadCountComparator<T>
	extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC = "readCount ASC";

	public static final String ORDER_BY_DESC = "readCount DESC";

	public static final String[] ORDER_BY_FIELDS = {"readCount"};

	public static final String ORDER_BY_MODEL_ASC =
		"modelFolder DESC, readCount ASC";

	public static final String ORDER_BY_MODEL_DESC =
		"modelFolder DESC, readCount DESC";

	public RepositoryModelReadCountComparator() {
		this(false);
	}

	public RepositoryModelReadCountComparator(boolean ascending) {
		_ascending = ascending;

		_orderByModel = false;
	}

	public RepositoryModelReadCountComparator(
		boolean ascending, boolean orderByModel) {

		_ascending = ascending;
		_orderByModel = orderByModel;
	}

	@Override
	public int compare(T t1, T t2) {
		int value = 0;

		Long readCount1 = getReadCount(t1);
		Long readCount2 = getReadCount(t2);

		if (_orderByModel) {
			if ((t1 instanceof DLFolder || t1 instanceof Folder) &&
				(t2 instanceof DLFolder || t2 instanceof Folder)) {

				value = readCount1.compareTo(readCount2);
			}
			else if (t1 instanceof DLFolder || t1 instanceof Folder) {
				value = -1;
			}
			else if (t2 instanceof DLFolder || t2 instanceof Folder) {
				value = 1;
			}
			else {
				value = readCount1.compareTo(readCount2);
			}
		}
		else {
			value = readCount1.compareTo(readCount2);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_orderByModel) {
			if (_ascending) {
				return ORDER_BY_MODEL_ASC;
			}

			return ORDER_BY_MODEL_DESC;
		}
		else {
			if (_ascending) {
				return ORDER_BY_ASC;
			}

			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	protected long getFileShortcutReadCount(Object obj) {
		long toFileEntryId = 0;

		if (obj instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)obj;

			toFileEntryId = fileShortcut.getToFileEntryId();
		}
		else {
			DLFileShortcut fileShortcut = (DLFileShortcut)obj;

			toFileEntryId = fileShortcut.getToFileEntryId();
		}

		try {
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				toFileEntryId);

			return dlFileEntry.getReadCount();
		}
		catch (Exception e) {
			return 0;
		}
	}

	protected long getReadCount(Object obj) {
		if (obj instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)obj;

			return dlFileEntry.getReadCount();
		}
		else if (obj instanceof DLFileShortcut || obj instanceof FileShortcut) {
			return getFileShortcutReadCount(obj);
		}
		else if (obj instanceof DLFolder || obj instanceof Folder) {
			return 0;
		}
		else {
			FileEntry fileEntry = (FileEntry)obj;

			return fileEntry.getReadCount();
		}
	}

	private final boolean _ascending;
	private final boolean _orderByModel;

}