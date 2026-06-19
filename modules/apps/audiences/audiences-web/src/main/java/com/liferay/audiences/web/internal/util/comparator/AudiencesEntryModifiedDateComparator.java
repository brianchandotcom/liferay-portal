/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.util.comparator;

import com.liferay.audiences.model.AudiencesEntry;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesEntryModifiedDateComparator
	extends OrderByComparator<AudiencesEntry> {

	public static final String ORDER_BY_ASC = "AudiencesEntry.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"AudiencesEntry.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public static AudiencesEntryModifiedDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		AudiencesEntry audiencesEntry1, AudiencesEntry audiencesEntry2) {

		int value = DateUtil.compareTo(
			audiencesEntry1.getModifiedDate(),
			audiencesEntry2.getModifiedDate());

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

	private AudiencesEntryModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final AudiencesEntryModifiedDateComparator
		_INSTANCE_ASCENDING = new AudiencesEntryModifiedDateComparator(true);

	private static final AudiencesEntryModifiedDateComparator
		_INSTANCE_DESCENDING = new AudiencesEntryModifiedDateComparator(false);

	private final boolean _ascending;

}