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

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceCart;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartNameComparator
	extends OrderByComparator<CommerceCart> {

	public static final String ORDER_BY_ASC = "CommerceCart.name ASC";

	public static final String ORDER_BY_DESC = "CommerceCart.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public CommerceCartNameComparator() {
		this(false);
	}

	public CommerceCartNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(CommerceCart commerceCart1, CommerceCart commerceCart2) {
		String name1 = StringUtil.toLowerCase(commerceCart1.getName());
		String name2 = StringUtil.toLowerCase(commerceCart2.getName());

		int value = name1.compareTo(name2);

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