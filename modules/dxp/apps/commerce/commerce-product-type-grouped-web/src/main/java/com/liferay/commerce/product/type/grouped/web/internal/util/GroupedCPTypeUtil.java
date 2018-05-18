/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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