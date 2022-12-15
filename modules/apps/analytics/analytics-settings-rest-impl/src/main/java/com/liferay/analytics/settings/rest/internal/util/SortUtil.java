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

package com.liferay.analytics.settings.rest.internal.util;

import com.liferay.analytics.settings.rest.internal.util.comparator.IgnoreCaseOrderByComparator;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Ferrari
 */
public class SortUtil {

	public static OrderByComparator<Group> getIgnoreCaseOrderByComparator(
		Sort[] sorts) {

		if (sorts == null) {
			return null;
		}

		List<Object> columns = new ArrayList<>();

		for (Sort sort : sorts) {
			columns.add(sort.getFieldName());
			columns.add(!sort.isReverse());
		}

		return new IgnoreCaseOrderByComparator<>(columns.toArray());
	}

}