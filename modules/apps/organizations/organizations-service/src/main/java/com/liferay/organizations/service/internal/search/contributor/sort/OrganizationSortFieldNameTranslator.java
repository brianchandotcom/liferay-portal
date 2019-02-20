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

package com.liferay.organizations.service.internal.search.contributor.sort;

import com.liferay.portal.search.contributor.ContributorConstants;
import com.liferay.portal.search.contributor.sort.SortFieldNameTranslator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Fabiano Nazar
 */
@Component(
	immediate = true,
	property = ContributorConstants.ENTRY_CLASS_NAME_PROPERTY_KEY + "=com.liferay.portal.kernel.model.Organization",
	service = SortFieldNameTranslator.class
)
public class OrganizationSortFieldNameTranslator
	implements SortFieldNameTranslator {

	@Override
	public String getSortFieldName(String orderByCol) {
		if (orderByCol.equals("name")) {
			return "name";
		}
		else if (orderByCol.equals("type")) {
			return "type";
		}

		return orderByCol;
	}

}