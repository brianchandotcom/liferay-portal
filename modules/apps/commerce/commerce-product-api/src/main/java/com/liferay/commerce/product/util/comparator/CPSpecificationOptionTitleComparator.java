/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionTitleComparator
	extends OrderByComparator<CPSpecificationOption> {

	public static final String ORDER_BY_ASC = "CPSpecificationOption.title ASC";

	public static final String ORDER_BY_DESC =
		"CPSpecificationOption.title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public CPSpecificationOptionTitleComparator() {
		this(false);
	}

	public CPSpecificationOptionTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CPSpecificationOption cpSpecificationOption1,
		CPSpecificationOption cpSpecificationOption2) {

		String title1 = StringUtil.toLowerCase(
			cpSpecificationOption1.getTitle());
		String title2 = StringUtil.toLowerCase(
			cpSpecificationOption2.getTitle());

		int value = title1.compareTo(title2);

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
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