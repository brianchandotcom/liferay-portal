/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Andrea Di Giorgi
 */
public class CommercePaymentMethodNameComparator
	implements Comparator<CommercePaymentMethod>, Serializable {

	public CommercePaymentMethodNameComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(
		CommercePaymentMethod commercePaymentMethod1,
		CommercePaymentMethod commercePaymentMethod2) {

		Collator collator = CollatorUtil.getInstance(_locale);

		String name1 = commercePaymentMethod1.getName(_locale);
		String name2 = commercePaymentMethod2.getName(_locale);

		return collator.compare(name1, name2);
	}

	private final Locale _locale;

}