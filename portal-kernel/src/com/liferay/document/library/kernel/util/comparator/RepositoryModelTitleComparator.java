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

	protected String getName(Object obj) {
		if (obj instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)obj;

			return dlFileEntry.getTitle();
		}
		else if (obj instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)obj;

			return dlFileShortcut.getToTitle();
		}
		else if (obj instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)obj;

			return dlFolder.getName();
		}
		else if (obj instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)obj;

			return fileEntry.getTitle();
		}
		else if (obj instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)obj;

			return fileShortcut.getToTitle();
		}
		else {
			Folder folder = (Folder)obj;

			return folder.getName();
		}
	}

	private final boolean _ascending;
	private final boolean _orderByModel;

}