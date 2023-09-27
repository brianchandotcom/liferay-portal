/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.internal.search.contributor.sort;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.contributor.sort.SortFieldNameTranslator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Fabiano Nazar
 */
@Component(service = SortFieldNameTranslator.class)
public class OrganizationSortFieldNameTranslator
	implements SortFieldNameTranslator {

	@Override
	public Class<?> getEntityClass() {
		return Organization.class;
	}

	@Override
	public String getSortFieldName(String orderByCol) {
		if (orderByCol.equals("name")) {
			return "name";
		}
		else if (orderByCol.equals("type")) {
			return Field.getSortableFieldName("type_String");
		}

		return orderByCol;
	}

}