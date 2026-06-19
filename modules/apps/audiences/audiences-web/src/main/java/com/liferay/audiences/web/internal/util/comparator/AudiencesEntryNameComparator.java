/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.util.comparator;

import com.liferay.audiences.model.AudiencesEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesEntryNameComparator
	extends OrderByComparator<AudiencesEntry> {

	public static final String ORDER_BY_ASC = "AudiencesEntry.name ASC";

	public static final String ORDER_BY_DESC = "AudiencesEntry.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public static AudiencesEntryNameComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		AudiencesEntry audiencesEntry1, AudiencesEntry audiencesEntry2) {

		String name1 = StringUtil.toLowerCase(audiencesEntry1.getName());
		String name2 = StringUtil.toLowerCase(audiencesEntry2.getName());

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

	private AudiencesEntryNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final AudiencesEntryNameComparator _INSTANCE_ASCENDING =
		new AudiencesEntryNameComparator(true);

	private static final AudiencesEntryNameComparator _INSTANCE_DESCENDING =
		new AudiencesEntryNameComparator(false);

	private final boolean _ascending;

}