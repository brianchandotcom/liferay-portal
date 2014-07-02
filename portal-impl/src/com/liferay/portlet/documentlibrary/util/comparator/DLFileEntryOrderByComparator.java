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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 *
 * @author wnewbury
 */
public class DLFileEntryOrderByComparator
	extends OrderByComparator<DLFileEntry> {

	public DLFileEntryOrderByComparator(
		OrderByComparator<FileEntry> orderByComparator) {

		_orderByComparator = orderByComparator;
	}

	@Override
	public int compare(DLFileEntry o1, DLFileEntry o2) {
		return _orderByComparator.compare(toFileEntry(o1), toFileEntry(o2));
	}

	protected FileEntry toFileEntry(DLFileEntry dlFileEntry) {
		return new LiferayFileEntry(dlFileEntry);
	}

	private OrderByComparator<FileEntry> _orderByComparator;

}