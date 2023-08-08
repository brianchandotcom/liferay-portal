/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.util.comparator;

import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class ObjectFieldCreateDateComparator
	extends OrderByComparator<ObjectField> {

	public static final String ORDER_BY_ASC = "ObjectField.createDate ASC";

	public static final String ORDER_BY_DESC = "ObjectField.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public ObjectFieldCreateDateComparator() {
		this(false);
	}

	public ObjectFieldCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(ObjectField objectField1, ObjectField objectField2) {
		int value = DateUtil.compareTo(
			objectField1.getCreateDate(), objectField1.getCreateDate());

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