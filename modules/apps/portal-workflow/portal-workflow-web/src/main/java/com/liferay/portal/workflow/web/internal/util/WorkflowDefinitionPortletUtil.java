/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.web.internal.util.comparator.WorkflowDefinitionModifiedDateComparator;
import com.liferay.portal.workflow.web.internal.util.comparator.WorkflowDefinitionTitleComparator;

import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionPortletUtil {

	public static OrderByComparator<WorkflowDefinition>
		getWorkflowDefitionOrderByComparator(
			String orderByCol, String orderByType, Locale locale) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WorkflowDefinition> orderByComparator = null;

		if (orderByCol.equals("last-modified")) {
			orderByComparator = new WorkflowDefinitionModifiedDateComparator(
				orderByAsc);
		}
		else {
			orderByComparator = new WorkflowDefinitionTitleComparator(
				orderByAsc, locale);
		}

		return orderByComparator;
	}

}