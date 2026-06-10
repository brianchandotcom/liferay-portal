/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.util.comparator;

import com.liferay.audience.model.AudienceEntry;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class AudienceEntryModifiedDateComparator
	extends OrderByComparator<AudienceEntry> {

	public static final String ORDER_BY_ASC = "AudienceEntry.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"AudienceEntry.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public static AudienceEntryModifiedDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		AudienceEntry audienceEntry1, AudienceEntry audienceEntry2) {

		int value = DateUtil.compareTo(
			audienceEntry1.getModifiedDate(), audienceEntry2.getModifiedDate());

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

	private AudienceEntryModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final AudienceEntryModifiedDateComparator
		_INSTANCE_ASCENDING = new AudienceEntryModifiedDateComparator(true);

	private static final AudienceEntryModifiedDateComparator
		_INSTANCE_DESCENDING = new AudienceEntryModifiedDateComparator(false);

	private final boolean _ascending;

}