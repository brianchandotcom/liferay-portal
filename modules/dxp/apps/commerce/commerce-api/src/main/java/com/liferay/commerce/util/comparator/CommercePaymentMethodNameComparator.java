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

import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Andrea Di Giorgi
 */
public class CommercePaymentMethodNameComparator
	implements Comparator<CommercePaymentMethod> {

	public CommercePaymentMethodNameComparator(Locale locale) {
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(
		CommercePaymentMethod commercePaymentMethod1,
		CommercePaymentMethod commercePaymentMethod2) {

		String name1 = commercePaymentMethod1.getName(_locale);
		String name2 = commercePaymentMethod2.getName(_locale);

		return _collator.compare(name1, name2);
	}

	private final Collator _collator;
	private final Locale _locale;

}