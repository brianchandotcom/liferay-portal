/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.util.comparator;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shuyang Zhou
 */
public class DLFileEntryMetadataIdComparator
	extends OrderByComparator<DLFileEntryMetadata> {

	public static DLFileEntryMetadataIdComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		DLFileEntryMetadata dlFileEntryMetadata1,
		DLFileEntryMetadata dlFileEntryMetadata2) {

		int value = Long.compare(
			dlFileEntryMetadata1.getFileEntryMetadataId(),
			dlFileEntryMetadata2.getFileEntryMetadataId());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private DLFileEntryMetadataIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final DLFileEntryMetadataIdComparator _INSTANCE_ASCENDING =
		new DLFileEntryMetadataIdComparator(true);

	private static final DLFileEntryMetadataIdComparator _INSTANCE_DESCENDING =
		new DLFileEntryMetadataIdComparator(false);

	private static final String _ORDER_BY_ASC =
		"DLFileEntryMetadata.fileEntryMetadataId ASC";

	private static final String _ORDER_BY_DESC =
		"DLFileEntryMetadata.fileEntryMetadataId DESC";

	private static final String[] _ORDER_BY_FIELDS = {"fileEntryMetadataId"};

	private final boolean _ascending;

}