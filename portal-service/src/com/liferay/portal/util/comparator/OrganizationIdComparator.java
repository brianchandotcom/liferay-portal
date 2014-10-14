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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Organization;

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
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
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