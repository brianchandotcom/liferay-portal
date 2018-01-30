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

package com.liferay.commerce.organization.web.internal.util;

import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;

import java.util.Objects;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationPortletUtil {

	public static Sort getOrganizationSort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("nameTreePath")) {
			sort = SortFactoryUtil.create(
				"nameTreePath_String_sortable", orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			sort = SortFactoryUtil.create("name_sortable", orderByAsc);
		}

		return sort;
	}

	public static Sort getUserSort(String orderByCol, String orderByType) {
		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("lastName_")) {
			sort = SortFactoryUtil.create("lastName_sortable", orderByAsc);
		}

		return sort;
	}

}