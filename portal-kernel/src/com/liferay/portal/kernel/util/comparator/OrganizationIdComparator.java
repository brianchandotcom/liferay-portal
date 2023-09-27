/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shinn Lok
 */
public class OrganizationIdComparator extends OrderByComparator<Organization> {

	public static final String ORDER_BY_ASC =
		"Organization_.organizationId ASC";

	public static final String ORDER_BY_DESC =
		"Organization_.organizationId DESC";

	public static final String[] ORDER_BY_FIELDS = {"organizationId"};

	public OrganizationIdComparator() {
		this(false);
	}

	public OrganizationIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Organization organization1, Organization organization2) {
		long organizationId1 = organization1.getOrganizationId();
		long organizationId2 = organization2.getOrganizationId();

		int value = 0;

		if (organizationId1 < organizationId2) {
			value = -1;
		}
		else if (organizationId1 > organizationId2) {
			value = 1;
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