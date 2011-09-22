/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Resource;

/**
 * @author Alexander Chow
 */
public class ResourceComparator extends OrderByComparator {

	public static final String ORDER_BY_DESC = "Resource_.resourceId DESC";

	public static final String[] ORDER_BY_FIELDS = {"resourceId"};

	@Override
	public int compare(Object obj1, Object obj2) {
		Resource resource1 = (Resource)obj1;
		Resource resource2 = (Resource)obj2;

		long resourceId1 = resource1.getResourceId();
		long resourceId2 = resource2.getResourceId();

		if (resourceId1 > resourceId2) {
			return -1;
		}
		else if (resourceId1 < resourceId2) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String getOrderBy() {
		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return false;
	}

}