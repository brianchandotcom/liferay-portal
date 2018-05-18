/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		else {
			return Math.negateExact(value);
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
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