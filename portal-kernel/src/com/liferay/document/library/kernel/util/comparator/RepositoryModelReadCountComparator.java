/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util.comparator;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	protected long getFileShortcutReadCount(Object object) {
		long toFileEntryId = 0;

		if (object instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)object;

			toFileEntryId = fileShortcut.getToFileEntryId();
		}
		else {
			DLFileShortcut fileShortcut = (DLFileShortcut)object;

			toFileEntryId = fileShortcut.getToFileEntryId();
		}

		try {
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				toFileEntryId);

			return dlFileEntry.getReadCount();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return 0;
		}
	}

	protected long getReadCount(Object object) {
		if (object instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)object;

			return dlFileEntry.getReadCount();
		}
		else if (object instanceof DLFileShortcut ||
				 object instanceof FileShortcut) {

			return getFileShortcutReadCount(object);
		}
		else if (object instanceof DLFolder || object instanceof Folder) {
			return 0;
		}

		FileEntry fileEntry = (FileEntry)object;

		return fileEntry.getReadCount();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryModelReadCountComparator.class);

	private final boolean _ascending;
	private final boolean _orderByModel;

}