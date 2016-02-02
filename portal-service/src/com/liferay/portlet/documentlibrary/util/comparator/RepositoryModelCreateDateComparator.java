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
 * @author Alexander Chow
 */
public class RepositoryModelCreateDateComparator<T>
	extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static final String ORDER_BY_MODEL_ASC =
		"modelFolder DESC, createDate ASC";

	public static final String ORDER_BY_MODEL_DESC =
		"modelFolder DESC, createDate DESC";

	public RepositoryModelCreateDateComparator() {
		this(false);
	}

	public RepositoryModelCreateDateComparator(boolean ascending) {
		_ascending = ascending;
		_orderByModel = false;
	}

	public RepositoryModelCreateDateComparator(
		boolean ascending, boolean orderByModel) {

		_ascending = ascending;
		_orderByModel = orderByModel;
	}

	@Override
	public int compare(T t1, T t2) {
		int value = 0;

		Date createDate1 = getCreateDate(t1);
		Date createDate2 = getCreateDate(t2);

		if (_orderByModel) {
			if (((t1 instanceof DLFolder) || (t1 instanceof Folder)) &&
				((t2 instanceof DLFolder) || (t2 instanceof Folder))) {

				value = DateUtil.compareTo(createDate1, createDate2);
			}
			else if ((t1 instanceof DLFolder) || (t1 instanceof Folder)) {
				value = -1;
			}
			else if ((t2 instanceof DLFolder) || (t2 instanceof Folder)) {
				value = 1;
			}
			else {
				value = DateUtil.compareTo(createDate1, createDate2);
			}
		}
		else {
			value = DateUtil.compareTo(createDate1, createDate2);
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
				return ORDER_BY_MODEL_ASC;
			}
			else {
				return ORDER_BY_MODEL_DESC;
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

	protected Date getCreateDate(Object obj) {
		if (obj instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)obj;

			return dlFileEntry.getCreateDate();
		}
		else if (obj instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)obj;

			return dlFileShortcut.getCreateDate();
		}
		else if (obj instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)obj;

			return dlFolder.getCreateDate();
		}
		else {
			RepositoryEntry repositoryEntry = (RepositoryEntry)obj;

			return repositoryEntry.getCreateDate();
		}
	}

	private final boolean _ascending;
	private final boolean _orderByModel;

}