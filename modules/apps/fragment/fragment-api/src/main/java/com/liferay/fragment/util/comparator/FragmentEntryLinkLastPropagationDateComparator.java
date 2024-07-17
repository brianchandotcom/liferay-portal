/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.util.comparator;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkLastPropagationDateComparator
	extends OrderByComparator<FragmentEntryLink> {

	public static final String ORDER_BY_ASC =
		"FragmentEntryLink.lastPropagationDate ASC";

	public static final String ORDER_BY_DESC =
		"FragmentEntryLink.lastPropagationDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"lastPropagationDate"};

	public static FragmentEntryLinkLastPropagationDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		FragmentEntryLink fragmentEntryLink1,
		FragmentEntryLink fragmentEntryLink2) {

		int value = DateUtil.compareTo(
			fragmentEntryLink1.getLastPropagationDate(),
			fragmentEntryLink2.getLastPropagationDate());

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

	private FragmentEntryLinkLastPropagationDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final FragmentEntryLinkLastPropagationDateComparator
		_INSTANCE_ASCENDING =
			new FragmentEntryLinkLastPropagationDateComparator(true);

	private static final FragmentEntryLinkLastPropagationDateComparator
		_INSTANCE_DESCENDING =
			new FragmentEntryLinkLastPropagationDateComparator(false);

	private final boolean _ascending;

}