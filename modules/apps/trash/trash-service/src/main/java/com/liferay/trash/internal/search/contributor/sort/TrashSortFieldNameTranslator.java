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

package com.liferay.trash.internal.search.contributor.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.contributor.sort.SortFieldNameTranslator;
import com.liferay.trash.model.TrashEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = SortFieldNameTranslator.class)
public class TrashSortFieldNameTranslator implements SortFieldNameTranslator {

	@Override
	public Class<?> getEntityClass() {
		return TrashEntry.class;
	}

	@Override
	public String getSortFieldName(String orderByCol) {
		if (orderByCol.equals("removed-date")) {
			return Field.REMOVED_DATE;
		}
		else if (orderByCol.equals("removed-by")) {
			return Field.REMOVED_BY_USER_NAME;
		}

		return orderByCol;
	}

}