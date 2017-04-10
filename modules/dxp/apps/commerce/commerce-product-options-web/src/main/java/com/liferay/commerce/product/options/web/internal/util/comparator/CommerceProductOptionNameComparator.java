/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.options.web.internal.util.comparator;

import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceProductOptionNameComparator
	extends OrderByComparator<CommerceProductOption> {

	public static final String ORDER_BY_ASC = "CommerceProductOption.name ASC";

	public static final String ORDER_BY_DESC =
		"CommerceProductOption.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public CommerceProductOptionNameComparator() {
		this(false);
	}

	public CommerceProductOptionNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceProductOption productOption1,
		CommerceProductOption productOption2) {

		String name1 = StringUtil.toLowerCase(productOption1.getName());
		String name2 = StringUtil.toLowerCase(productOption2.getName());

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
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