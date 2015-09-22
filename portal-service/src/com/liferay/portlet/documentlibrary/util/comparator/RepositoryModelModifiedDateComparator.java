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

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class RepositoryModelModifiedDateComparator<T>
	extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC = "modifiedDate ASC";

	public static final String ORDER_BY_ASC_MODEL =
		"modelFolder DESC, modifiedDate ASC";

	public static final String ORDER_BY_DESC = "modifiedDate DESC";

	public static final String ORDER_BY_DESC_MODEL =
		"modelFolder DESC, modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public RepositoryModelModifiedDateComparator() {
		this(false);
	}

	public RepositoryModelModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
		_orderByModel = false;
	}

	public RepositoryModelModifiedDateComparator(
		boolean ascending, boolean orderByModel) {

		_ascending = ascending;
		_orderByModel = orderByModel;
	}

	@Override
	public int compare(T t1, T t2) {
		Date modifiedDate1 = getModifiedDate(t1);
		Date modifiedDate2 = getModifiedDate(t2);

		int value = 0;

		if (_orderByModel) {
			if (((t1 instanceof DLFolder) || (t1 instanceof Folder)) &&
				((t2 instanceof DLFolder) || (t2 instanceof Folder))) {

				value = DateUtil.compareTo(modifiedDate1, modifiedDate2);
			}
			else if ((t1 instanceof DLFolder) || (t1 instanceof Folder)) {
				value = -1;
			}
			else if ((t2 instanceof DLFolder) || (t2 instanceof Folder)) {
				value = 1;
			}
			else {
				value = DateUtil.compareTo(modifiedDate1, modifiedDate2);
			}
		}
		else {
			value = DateUtil.compareTo(modifiedDate1, modifiedDate2);
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_orderByModel) {
			if (_ascending) {
				return ORDER_BY_ASC_MODEL;
			}
			else {
				return ORDER_BY_DESC_MODEL;
			}
		}
		else {
			if (_ascending) {
				return ORDER_BY_ASC;
			}
			else {
				return ORDER_BY_DESC;
			}
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

	protected Date getModifiedDate(Object obj) {
		if (obj instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)obj;

			return dlFileEntry.getModifiedDate();
		}
		else if (obj instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)obj;

			return dlFileShortcut.getModifiedDate();
		}
		else if (obj instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)obj;

			return dlFolder.getModifiedDate();
		}
		else {
			RepositoryEntry repositoryEntry = (RepositoryEntry)obj;

			return repositoryEntry.getModifiedDate();
		}
	}

	private final boolean _ascending;
	private final boolean _orderByModel;

}