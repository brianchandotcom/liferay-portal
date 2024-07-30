/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.util.comparator;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionRelCountryIdComparator
	extends OrderByComparator<CommerceShippingFixedOptionRel> {

	public static final String ORDER_BY_ASC = "countryId ASC";

	public static final String ORDER_BY_DESC = "countryId DESC";

	public static final String[] ORDER_BY_FIELDS = {"countryId"};

	public static CommerceShippingFixedOptionRelCountryIdComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel1,
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel2) {

		int value = Long.compare(
			commerceShippingFixedOptionRel1.getCountryId(),
			commerceShippingFixedOptionRel2.getCountryId());

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

	private CommerceShippingFixedOptionRelCountryIdComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	private static final CommerceShippingFixedOptionRelCountryIdComparator
		_INSTANCE_ASCENDING =
			new CommerceShippingFixedOptionRelCountryIdComparator(true);

	private static final CommerceShippingFixedOptionRelCountryIdComparator
		_INSTANCE_DESCENDING =
			new CommerceShippingFixedOptionRelCountryIdComparator(false);

	private final boolean _ascending;

}