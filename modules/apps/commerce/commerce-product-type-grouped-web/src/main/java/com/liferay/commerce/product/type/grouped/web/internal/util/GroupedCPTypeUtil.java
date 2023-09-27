/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.web.internal.util;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.util.comparator.CPDefinitionGroupedEntryPriorityComparator;
import com.liferay.commerce.product.type.grouped.util.comparator.CPDefinitionGroupedEntryQuantityComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Di Giorgi
 */
public class GroupedCPTypeUtil {

	public static OrderByComparator<CPDefinitionGroupedEntry>
		getCPDefinitionGroupedEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CPDefinitionGroupedEntryPriorityComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("quantity")) {
			orderByComparator = new CPDefinitionGroupedEntryQuantityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}