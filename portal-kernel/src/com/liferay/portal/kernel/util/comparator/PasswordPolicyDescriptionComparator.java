/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyDescriptionComparator
	extends OrderByComparator<PasswordPolicy> {

	public static final String ORDER_BY_ASC = "PasswordPolicy.description ASC";

	public static final String ORDER_BY_DESC =
		"PasswordPolicy.description DESC";

	public static final String[] ORDER_BY_FIELDS = {"description"};

	public PasswordPolicyDescriptionComparator() {
		this(false);
	}

	public PasswordPolicyDescriptionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		PasswordPolicy passwordPolicy1, PasswordPolicy passwordPolicy2) {

		String description1 = passwordPolicy1.getDescription();
		String description2 = passwordPolicy2.getDescription();

		int value = description1.compareTo(description2);

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