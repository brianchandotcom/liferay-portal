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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shuyang Zhou
 */
public class ResourceActionBitwiseValueComparator
	extends OrderByComparator<ResourceAction> {

	public static final String ORDER_BY_ASC = "bitwiseValue ASC";

	public static final String ORDER_BY_DESC = "bitwiseValue DESC";

	public static final String[] ORDER_BY_FIELDS = {"bitwiseValue"};

	public ResourceActionBitwiseValueComparator() {
		this(false);
	}

	public ResourceActionBitwiseValueComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ResourceAction resourceAction1, ResourceAction resourceAction2) {

		int value =
			(int)(resourceAction1.getBitwiseValue() -
				resourceAction2.getBitwiseValue());

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