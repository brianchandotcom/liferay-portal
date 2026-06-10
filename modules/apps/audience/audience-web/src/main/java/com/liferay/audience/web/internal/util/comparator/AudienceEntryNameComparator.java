/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.util.comparator;

import com.liferay.audience.model.AudienceEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Eudaldo Alonso
 */
public class AudienceEntryNameComparator
	extends OrderByComparator<AudienceEntry> {

	public static final String ORDER_BY_ASC = "AudienceEntry.name ASC";

	public static final String ORDER_BY_DESC = "AudienceEntry.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public static AudienceEntryNameComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		AudienceEntry audienceEntry1, AudienceEntry audienceEntry2) {

		String name1 = StringUtil.toLowerCase(audienceEntry1.getName());
		String name2 = StringUtil.toLowerCase(audienceEntry2.getName());

		int value = name1.compareTo(name2);

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

	private AudienceEntryNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final AudienceEntryNameComparator _INSTANCE_ASCENDING =
		new AudienceEntryNameComparator(true);

	private static final AudienceEntryNameComparator _INSTANCE_DESCENDING =
		new AudienceEntryNameComparator(false);

	private final boolean _ascending;

}