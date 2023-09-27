/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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