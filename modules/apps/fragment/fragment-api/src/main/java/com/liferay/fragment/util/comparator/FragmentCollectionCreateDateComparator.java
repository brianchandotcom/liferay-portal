/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.util.comparator;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Jürgen Kappler
 */
public class FragmentCollectionCreateDateComparator
	extends OrderByComparator<FragmentCollection> {

	public static final String ORDER_BY_ASC =
		"FragmentCollection.createDate ASC";

	public static final String ORDER_BY_DESC =
		"FragmentCollection.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public FragmentCollectionCreateDateComparator() {
		this(true);
	}

	public FragmentCollectionCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		FragmentCollection fragmentCollection1,
		FragmentCollection fragmentCollection2) {

		int value = DateUtil.compareTo(
			fragmentCollection1.getCreateDate(),
			fragmentCollection2.getCreateDate());

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