/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(Group group1, Group group2) {
		String name1 = StringPool.BLANK;
		String name2 = StringPool.BLANK;

		try {
			name1 = group1.getDescriptiveName(_locale);
			name2 = group2.getDescriptiveName(_locale);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
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