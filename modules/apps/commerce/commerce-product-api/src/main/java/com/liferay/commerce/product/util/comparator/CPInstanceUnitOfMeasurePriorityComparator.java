/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Stefano Motta
 */
public class CPInstanceUnitOfMeasurePriorityComparator
	extends OrderByComparator<CPInstanceUnitOfMeasure> {

	public static final String ORDER_BY_ASC = "CPInstanceUOM.priority ASC";

	public static final String ORDER_BY_DESC = "CPInstanceUOM.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public CPInstanceUnitOfMeasurePriorityComparator() {
		this(true);
	}

	public CPInstanceUnitOfMeasurePriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure1,
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure2) {

		int value = Double.compare(
			cpInstanceUnitOfMeasure1.getPriority(),
			cpInstanceUnitOfMeasure2.getPriority());

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