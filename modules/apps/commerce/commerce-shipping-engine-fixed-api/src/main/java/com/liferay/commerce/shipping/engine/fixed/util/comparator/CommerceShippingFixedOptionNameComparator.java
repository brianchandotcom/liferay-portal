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

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.text.Collator;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionNameComparator
	extends OrderByComparator<CommerceShippingFixedOption> {

	public static final String ORDER_BY_ASC =
		"CommerceShippingFixedOption.name ASC";

	public static final String ORDER_BY_DESC =
		"CommerceShippingFixedOption.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public CommerceShippingFixedOptionNameComparator(
		boolean ascending, Locale locale) {

		_ascending = ascending;
		_locale = locale;
	}

	public CommerceShippingFixedOptionNameComparator(Locale locale) {
		this(false, locale);
	}

	@Override
	public int compare(
		CommerceShippingFixedOption commerceShippingFixedOption1,
		CommerceShippingFixedOption commerceShippingFixedOption2) {

		Collator collator = CollatorUtil.getInstance(_locale);

		String name1 = commerceShippingFixedOption1.getName(_locale);
		String name2 = commerceShippingFixedOption2.getName(_locale);

		int value = collator.compare(name1, name2);

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
	private final Locale _locale;

}