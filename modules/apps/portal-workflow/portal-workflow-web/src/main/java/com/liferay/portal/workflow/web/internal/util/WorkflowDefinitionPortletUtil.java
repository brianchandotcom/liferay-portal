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