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

package com.liferay.commerce.product.media.types.web.internal.util;

import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.util.comparator.CPMediaTypePriorityComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPMediaTypesPortletUtil {

	public static OrderByComparator<CPMediaType>
		getCPMediaTypeOrderByComparator(String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPMediaType> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CPMediaTypePriorityComparator(orderByAsc);
		}

		return orderByComparator;
	}

}