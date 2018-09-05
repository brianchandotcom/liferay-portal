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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.text.Collator;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupNameComparator extends OrderByComparator<Group> {

	public static final String ORDER_BY_ASC = "groupName ASC";

	public static final String ORDER_BY_DESC = "groupName DESC";

	public static final String[] ORDER_BY_FIELDS = {"groupName"};

	public GroupNameComparator() {
		this(false);
	}

	public GroupNameComparator(boolean ascending) {
		this(ascending, LocaleUtil.getDefault());
	}

	public GroupNameComparator(boolean ascending, Locale locale) {
		_ascending = ascending;

		_collator = CollatorUtil.getInstance(locale);
		_languageId = LocaleUtil.toLanguageId(locale);
	}

	@Override
	public int compare(Group group1, Group group2) {
		String name1 = group1.getName(_languageId);
		String name2 = group2.getName(_languageId);

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
	private final String _languageId;

}