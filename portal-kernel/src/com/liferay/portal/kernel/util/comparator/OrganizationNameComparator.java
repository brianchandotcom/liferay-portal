/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationNameComparator
	extends OrderByComparator<Organization> {

	public static OrganizationNameComparator get(boolean ascending) {
		if (ascending) {
			return _ASCENDING;
		}

		return _DESCENDING;
	}

	@Override
	public int compare(Organization organization1, Organization organization2) {
		String name1 = organization1.getName();
		String name2 = organization2.getName();

		int value = name1.compareTo(name2);

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

	private OrganizationNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final OrganizationNameComparator _ASCENDING =
		new OrganizationNameComparator(true);

	private static final OrganizationNameComparator _DESCENDING =
		new OrganizationNameComparator(false);

	private static final String _ORDER_BY_ASC = "orgName ASC";

	private static final String _ORDER_BY_DESC = "orgName DESC";

	private static final String[] _ORDER_BY_FIELDS = {"name"};

	private final boolean _ascending;

}