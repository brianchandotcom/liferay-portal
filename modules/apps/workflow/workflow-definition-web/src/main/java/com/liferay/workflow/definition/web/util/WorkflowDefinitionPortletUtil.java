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

package com.liferay.workflow.definition.web.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.workflow.definition.web.util.comparator.WorkflowDefinitionActiveComparator;
import com.liferay.workflow.definition.web.util.comparator.WorkflowDefinitionNameComparator;
import com.liferay.workflow.definition.web.util.comparator.WorkflowDefinitionTitleComparator;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionPortletUtil {

	public static OrderByComparator<WorkflowDefinition>
		getWorkflowDefitionOrderByComparator(
			String languageId, String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WorkflowDefinition> orderByComparator = null;

		if (orderByCol.equals("active")) {
			orderByComparator = new WorkflowDefinitionActiveComparator(
				orderByAsc, languageId);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new WorkflowDefinitionTitleComparator(
				orderByAsc, languageId);
		}
		else {
			orderByComparator = new WorkflowDefinitionNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}