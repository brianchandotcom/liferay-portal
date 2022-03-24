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

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.Collator;

import java.util.Locale;

/**
 * @author Attila Bakay
 */
public class DDMTemplateNameComparator extends OrderByComparator<DDMTemplate> {

	public static final String ORDER_BY_ASC = "DDMTemplate.name ASC";

	public static final String ORDER_BY_DESC = "DDMTemplate.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public DDMTemplateNameComparator() {
		this(false);
	}

	public DDMTemplateNameComparator(boolean ascending) {
		this(ascending, LocaleUtil.getDefault());
	}

	public DDMTemplateNameComparator(boolean ascending, Locale locale) {
		_ascending = ascending;
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(DDMTemplate ddmTemplate1, DDMTemplate ddmTemplate2) {
		String name1 = StringUtil.toLowerCase(ddmTemplate1.getName(_locale));
		String name2 = StringUtil.toLowerCase(ddmTemplate2.getName(_locale));

		int value = _collator.compare(name1, name2);

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
	private final Collator _collator;
	private final Locale _locale;

}