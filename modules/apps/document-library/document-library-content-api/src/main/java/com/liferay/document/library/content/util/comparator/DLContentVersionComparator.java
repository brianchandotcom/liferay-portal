/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.content.util.comparator;

import com.liferay.document.library.content.model.DLContent;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shuyang Zhou
 */
public class DLContentVersionComparator extends OrderByComparator<DLContent> {

	public static final String ORDER_BY_ASC = "DLContent.version ASC";

	public static final String ORDER_BY_DESC = "DLContent.version DESC";

	public static final String[] ORDER_BY_FIELDS = {"version"};

	public DLContentVersionComparator() {
		this(false);
	}

	public DLContentVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(DLContent content1, DLContent content2) {
		String version1 = content1.getVersion();
		String version2 = content2.getVersion();

		int value = version1.compareTo(version2);

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

}