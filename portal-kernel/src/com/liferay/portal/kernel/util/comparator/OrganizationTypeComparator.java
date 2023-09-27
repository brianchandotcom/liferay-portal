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
public class OrganizationTypeComparator
	extends OrderByComparator<Organization> {

	public static final String ORDER_BY_ASC = "orgType ASC, orgName ASC";

	public static final String ORDER_BY_DESC = "orgType DESC, orgName DESC";

	public static final String[] ORDER_BY_FIELDS = {"type", "name"};

	public OrganizationTypeComparator() {
		this(false);
	}

	public OrganizationTypeComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Organization organization1, Organization organization2) {
		int typeOrder1 = organization1.getTypeOrder();
		int typeOrder2 = organization2.getTypeOrder();

		int value = typeOrder1 - typeOrder2;

		if (value == 0) {
			String name1 = organization1.getName();
			String name2 = organization2.getName();

			value = name1.compareTo(name2);
		}

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