/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchEntry;
import com.liferay.portal.workflow.web.internal.util.comparator.WorkflowDefinitionLinkSearchEntryResourceComparator;
import com.liferay.portal.workflow.web.internal.util.comparator.WorkflowDefinitionLinkSearchEntryWorkflowComparator;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionLinkPortletUtil {

	public static OrderByComparator<WorkflowDefinitionLinkSearchEntry>
		getWorkflowDefinitionLinkOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WorkflowDefinitionLinkSearchEntry> orderByComparator =
			null;

		if (orderByCol.equals("resource")) {
			orderByComparator =
				new WorkflowDefinitionLinkSearchEntryResourceComparator(
					orderByAsc);
		}
		else if (orderByCol.equals("workflow")) {
			orderByComparator =
				new WorkflowDefinitionLinkSearchEntryWorkflowComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}