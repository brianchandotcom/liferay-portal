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

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPMeasurementUnitPriorityComparator
	extends OrderByComparator<CPMeasurementUnit> {

	public static final String ORDER_BY_ASC = "CPMeasurementUnit.priority ASC";

	public static final String ORDER_BY_DESC =
		"CPMeasurementUnit.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public CPMeasurementUnitPriorityComparator() {
		this(false);
	}

	public CPMeasurementUnitPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CPMeasurementUnit cpMeasurementUnit1,
		CPMeasurementUnit cpMeasurementUnit2) {

		int value = Double.compare(
			cpMeasurementUnit1.getPriority(), cpMeasurementUnit2.getPriority());

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