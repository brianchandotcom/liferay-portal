/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.util.comparator;

import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Pavel Savinov
 */
public class FragmentCompositionFragmentEntryNameComparator
	extends OrderByComparator<Object> {

	public static final String ORDER_BY_ASC = "name ASC";

	public static final String ORDER_BY_DESC = "name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public static FragmentCompositionFragmentEntryNameComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(Object object1, Object object2) {
		String name1 = getName(object1);
		String name2 = getName(object2);

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

	protected String getName(Object object) {
		if (object instanceof FragmentComposition) {
			FragmentComposition fragmentComposition =
				(FragmentComposition)object;

			return fragmentComposition.getName();
		}

		FragmentEntry fragmentEntry = (FragmentEntry)object;

		return fragmentEntry.getName();
	}

	private FragmentCompositionFragmentEntryNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final FragmentCompositionFragmentEntryNameComparator
		_INSTANCE_ASCENDING =
			new FragmentCompositionFragmentEntryNameComparator(true);

	private static final FragmentCompositionFragmentEntryNameComparator
		_INSTANCE_DESCENDING =
			new FragmentCompositionFragmentEntryNameComparator(false);

	private final boolean _ascending;

}