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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.text.Collator;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class GroupDescriptiveNameComparator extends OrderByComparator<Group> {

	public static final String ORDER_BY_ASC = "groupName ASC";

	public static final String ORDER_BY_DESC = "groupName DESC";

	public static final String[] ORDER_BY_FIELDS = {"groupName"};

	public GroupDescriptiveNameComparator() {
		this(false);
	}

	public GroupDescriptiveNameComparator(boolean ascending) {
		this(ascending, LocaleUtil.getDefault());
	}

	public GroupDescriptiveNameComparator(boolean ascending, Locale locale) {
		_ascending = ascending;

		_collator = CollatorUtil.getInstance(locale);
		_locale = locale;
	}

	@Override
	public int compare(Group group1, Group group2) {
		String name1 = StringPool.BLANK;
		String name2 = StringPool.BLANK;

		try {
			name1 = group1.getDescriptiveName(_locale);
			name2 = group2.getDescriptiveName(_locale);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

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

	private static final Log _log = LogFactoryUtil.getLog(
		GroupDescriptiveNameComparator.class);

	private final boolean _ascending;
	private final Collator _collator;
	private final Locale _locale;

}