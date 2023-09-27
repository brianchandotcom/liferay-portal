/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util.comparator;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class RepositoryModelTitleComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC = "title ASC";

	public static final String ORDER_BY_DESC = "title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public static final String ORDER_BY_MODEL_ASC =
		"modelFolder DESC, title ASC";

	public static final String ORDER_BY_MODEL_DESC =
		"modelFolder DESC, title DESC";

	public RepositoryModelTitleComparator() {
		this(false);
	}

	public RepositoryModelTitleComparator(boolean ascending) {
		_ascending = ascending;

		_orderByModel = false;
	}

	public RepositoryModelTitleComparator(
		boolean ascending, boolean orderByModel) {

		_ascending = ascending;
		_orderByModel = orderByModel;
	}

	@Override
	public int compare(T t1, T t2) {
		int value = 0;

		String name1 = getName(t1);
		String name2 = getName(t2);

		if (_orderByModel) {
			if ((t1 instanceof DLFolder || t1 instanceof Folder) &&
				(t2 instanceof DLFolder || t2 instanceof Folder)) {

				name1.compareToIgnoreCase(name2);
			}
			else if (t1 instanceof DLFolder || t1 instanceof Folder) {
				value = -1;
			}
			else if (t2 instanceof DLFolder || t2 instanceof Folder) {
				value = 1;
			}
			else {
				name1.compareToIgnoreCase(name2);
			}
		}
		else {
			value = name1.compareToIgnoreCase(name2);
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

	protected String getName(Object object) {
		if (object instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)object;

			return dlFileEntry.getTitle();
		}
		else if (object instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

			return dlFileShortcut.getToTitle();
		}
		else if (object instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)object;

			return dlFolder.getName();
		}
		else if (object instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)object;

			return fileEntry.getTitle();
		}
		else if (object instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)object;

			return fileShortcut.getToTitle();
		}

		Folder folder = (Folder)object;

		return folder.getName();
	}

	private final boolean _ascending;
	private final boolean _orderByModel;

}