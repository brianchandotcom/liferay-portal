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

package com.liferay.portlet.documentlibrary.util.comparator;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

/**
 * @author Alexander Chow
 */
public class RepositoryModelSizeComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC = "size_ ASC";

	public static final String ORDER_BY_DESC = "size_ DESC";

	public static final String[] ORDER_BY_FIELDS = {"size"};

	public RepositoryModelSizeComparator() {
		this(false);
	}

	public RepositoryModelSizeComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(T t1, T t2) {
		Long size1 = getSize(t1);
		Long size2 = getSize(t2);

		int value = size1.compareTo(size2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
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

	protected long getSize(Object obj) {
		if (obj instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)obj;

			return dlFileEntry.getSize();
		}
		else if (obj instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)obj;

			long toFileEntryId = dlFileShortcut.getToFileEntryId();

			try {
				DLFileEntry dlFileEntry =
					DLFileEntryLocalServiceUtil.getFileEntry(toFileEntryId);

				return dlFileEntry.getSize();
			}
			catch (Exception e) {
				return 0;
			}
		}
		else if ((obj instanceof DLFolder) || (obj instanceof Folder)) {
			return 0;
		}
		else {
			FileEntry fileEntry = (FileEntry)obj;

			return fileEntry.getSize();
		}
	}

	private final boolean _ascending;

}