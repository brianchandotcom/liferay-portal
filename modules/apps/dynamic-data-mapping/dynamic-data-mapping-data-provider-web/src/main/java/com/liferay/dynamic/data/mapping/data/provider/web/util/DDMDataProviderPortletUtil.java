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

package com.liferay.dynamic.data.mapping.data.provider.web.util;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.util.comparator.DataProviderModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.DataProviderNameComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderPortletUtil {

	public static OrderByComparator<DDMDataProviderInstance>
		getDDMDataProviderOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDMDataProviderInstance> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new DataProviderModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new DataProviderNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}