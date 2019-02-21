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

package com.liferay.portal.workflow.kaleo.util.comparator;

import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.text.Collator;

import java.util.Locale;

/**
 * @author Inácio Nery
 */
public class KaleoDefinitionVersionTitleComparator
	extends OrderByComparator<KaleoDefinitionVersion> {

	public KaleoDefinitionVersionTitleComparator() {
		this(false);
	}

	public KaleoDefinitionVersionTitleComparator(boolean ascending) {
		_ascending = ascending;

		_locale = LocaleUtil.getSiteDefault();

		_collator = CollatorUtil.getInstance(_locale);
	}

	public KaleoDefinitionVersionTitleComparator(
		boolean ascending, Locale locale) {

		_ascending = ascending;

		_locale = locale;

		_collator = CollatorUtil.getInstance(_locale);
	}

	@Override
	public int compare(
		KaleoDefinitionVersion kaleoDefinitionVersion1,
		KaleoDefinitionVersion kaleoDefinitionVersion2) {

		String title1 = StringUtil.toLowerCase(
			kaleoDefinitionVersion1.getTitle(_locale));
		String title2 = StringUtil.toLowerCase(
			kaleoDefinitionVersion2.getTitle(_locale));

		int value = _collator.compare(title1, title2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private static final String _ORDER_BY_ASC =
		"KaleoDefinitionVersion.title ASC";

	private static final String _ORDER_BY_DESC =
		"KaleoDefinitionVersion.title DESC";

	private static final String[] _ORDER_BY_FIELDS = {"title"};

	private final boolean _ascending;
	private final Collator _collator;
	private final Locale _locale;

}