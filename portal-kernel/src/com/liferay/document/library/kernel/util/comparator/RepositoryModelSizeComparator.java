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
 * @author Alexander Chow
 */
public class RepositoryModelSizeComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC = "size_ ASC";

	public static final String ORDER_BY_DESC = "size_ DESC";

	public static final String[] ORDER_BY_FIELDS = {"size_"};

	public static final String ORDER_BY_MODEL_ASC =
		"modelFolder DESC, size_ ASC";

	public static final String ORDER_BY_MODEL_DESC =
		"modelFolder DESC, size_ DESC";

	public RepositoryModelSizeComparator() {
		this(false);
	}

	public RepositoryModelSizeComparator(boolean ascending) {
		_ascending = ascending;

		_orderByModel = false;
	}

	public RepositoryModelSizeComparator(
		boolean ascending, boolean orderByModel) {

		_ascending = ascending;
		_orderByModel = orderByModel;
	}

	@Override
	public int compare(T t1, T t2) {
		int value = 0;

		Long size1 = getSize(t1);
		Long size2 = getSize(t2);

		if (_orderByModel) {
			if ((t1 instanceof DLFolder || t1 instanceof Folder) &&
				(t2 instanceof DLFolder || t2 instanceof Folder)) {

				value = size1.compareTo(size2);
			}
			else if (t1 instanceof DLFolder || t1 instanceof Folder) {
				value = -1;
			}
			else if (t2 instanceof DLFolder || t2 instanceof Folder) {
				value = 1;
			}
			else {
				value = size1.compareTo(size2);
			}
		}
		else {
			value = size1.compareTo(size2);
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

	protected long getFileShortcutSize(Object object) {
		long toFileEntryId = 0;

		if (object instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)object;

			toFileEntryId = fileShortcut.getToFileEntryId();
		}
		else {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

			toFileEntryId = dlFileShortcut.getToFileEntryId();
		}

		try {
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				toFileEntryId);

			return dlFileEntry.getSize();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return 0;
		}
	}

	protected long getSize(Object object) {
		if (object instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)object;

			return dlFileEntry.getSize();
		}
		else if (object instanceof DLFileShortcut ||
				 object instanceof FileShortcut) {

			return getFileShortcutSize(object);
		}
		else if (object instanceof DLFolder || object instanceof Folder) {
			return 0;
		}

		FileEntry fileEntry = (FileEntry)object;

		return fileEntry.getSize();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryModelSizeComparator.class);

	private final boolean _ascending;
	private final boolean _orderByModel;

}