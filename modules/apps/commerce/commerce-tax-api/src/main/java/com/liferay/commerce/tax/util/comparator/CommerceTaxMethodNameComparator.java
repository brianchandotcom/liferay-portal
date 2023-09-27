/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.util.comparator;

import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Marco Leo
 */
public class CommerceTaxMethodNameComparator
	implements Comparator<CommerceTaxMethod>, Serializable {

	public CommerceTaxMethodNameComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(
		CommerceTaxMethod commerceTaxMethod1,
		CommerceTaxMethod commerceTaxMethod2) {

		Collator collator = CollatorUtil.getInstance(_locale);

		String name1 = commerceTaxMethod1.getName(_locale);
		String name2 = commerceTaxMethod2.getName(_locale);

		return collator.compare(name1, name2);
	}

	private final Locale _locale;

}