/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.softwarecatalog.util.comparator.ProductEntryCreateDateComparator;
import com.liferay.portlet.softwarecatalog.util.comparator.ProductEntryModifiedDateComparator;
import com.liferay.portlet.softwarecatalog.util.comparator.ProductEntryNameComparator;
import com.liferay.portlet.softwarecatalog.util.comparator.ProductEntryTypeComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class SCUtil {

	public static OrderByComparator getProductEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new ProductEntryCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new ProductEntryModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new ProductEntryNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new ProductEntryTypeComparator(orderByAsc);
		}

		return orderByComparator;
	}

}