/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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