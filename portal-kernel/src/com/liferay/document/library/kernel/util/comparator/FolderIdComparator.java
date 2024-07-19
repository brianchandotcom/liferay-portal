/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util.comparator;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shinn Lok
 */
public class FolderIdComparator extends OrderByComparator<DLFolder> {

	public static final String ORDER_BY_ASC = "DLFolder.folderId ASC";

	public static final String ORDER_BY_DESC = "DLFolder.folderId DESC";

	public static final String[] ORDER_BY_FIELDS = {"folderId"};

	public static FolderIdComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(DLFolder folder1, DLFolder folder2) {
		long folderId1 = folder1.getFolderId();
		long folderId2 = folder2.getFolderId();

		int value = 0;

		if (folderId1 < folderId2) {
			value = -1;
		}
		else if (folderId1 > folderId2) {
			value = 1;
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
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

	private FolderIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final FolderIdComparator _INSTANCE_ASCENDING =
		new FolderIdComparator(true);

	private static final FolderIdComparator _INSTANCE_DESCENDING =
		new FolderIdComparator(false);

	private final boolean _ascending;

}