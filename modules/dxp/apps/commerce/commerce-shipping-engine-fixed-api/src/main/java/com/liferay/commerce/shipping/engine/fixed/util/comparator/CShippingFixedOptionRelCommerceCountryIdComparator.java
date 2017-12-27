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

package com.liferay.commerce.shipping.engine.fixed.util.comparator;

import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CShippingFixedOptionRelCommerceCountryIdComparator
	extends OrderByComparator<CShippingFixedOptionRel> {

	public static final String ORDER_BY_ASC = "commerceCountryId ASC";

	public static final String ORDER_BY_DESC = "commerceCountryId DESC";

	public static final String[] ORDER_BY_FIELDS = {"commerceCountryId"};

	public CShippingFixedOptionRelCommerceCountryIdComparator() {
		this(false);
	}

	public CShippingFixedOptionRelCommerceCountryIdComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		CShippingFixedOptionRel cShippingFixedOptionRel1,
		CShippingFixedOptionRel cShippingFixedOptionRel2) {

		int value = Long.compare(
			cShippingFixedOptionRel1.getCommerceCountryId(),
			cShippingFixedOptionRel2.getCommerceCountryId());

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