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

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingMethodNameComparator
	implements Comparator<CommerceShippingMethod>, Serializable {

	public CommerceShippingMethodNameComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(
		CommerceShippingMethod commerceShippingMethod1,
		CommerceShippingMethod commerceShippingMethod2) {

		Collator collator = CollatorUtil.getInstance(_locale);

		String name1 = commerceShippingMethod1.getName(_locale);
		String name2 = commerceShippingMethod2.getName(_locale);

		return collator.compare(name1, name2);
	}

	private final Locale _locale;

}