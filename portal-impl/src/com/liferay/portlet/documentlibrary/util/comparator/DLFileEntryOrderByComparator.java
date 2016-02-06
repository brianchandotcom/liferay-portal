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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorAdapter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

/**
 * @author William Newbury
 */
public class DLFileEntryOrderByComparator
	extends OrderByComparatorAdapter<DLFileEntry, FileEntry> {

	public static OrderByComparator<DLFileEntry> getOrderByComparator(
		OrderByComparator<FileEntry> orderByComparator) {

		if (orderByComparator == null) {
			return null;
		}

		return new DLFileEntryOrderByComparator(orderByComparator);
	}

	@Override
	public FileEntry adapt(DLFileEntry dlFileEntry) {
		return new LiferayFileEntry(dlFileEntry);
	}

	private DLFileEntryOrderByComparator(
		OrderByComparator<FileEntry> orderByComparator) {

		super(orderByComparator);
	}

}