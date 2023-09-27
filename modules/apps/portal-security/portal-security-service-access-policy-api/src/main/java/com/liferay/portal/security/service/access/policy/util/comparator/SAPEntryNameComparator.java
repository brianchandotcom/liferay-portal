/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

/**
 * @author Eudaldo Alonso
 */
public class SAPEntryNameComparator extends OrderByComparator<SAPEntry> {

	public static final String ORDER_BY_ASC = "SAPEntry.name ASC";

	public static final String ORDER_BY_DESC = "SAPEntry.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public SAPEntryNameComparator() {
		this(false);
	}

	public SAPEntryNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(SAPEntry sapEntry1, SAPEntry sapEntry2) {
		String name1 = sapEntry1.getName();
		String name2 = sapEntry2.getName();

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

	private final boolean _ascending;

}