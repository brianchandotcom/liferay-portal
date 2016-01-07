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

package com.liferay.workflow.definition.web.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionNameComparator
	extends OrderByComparator<WorkflowDefinition> {

	public static final String ORDER_BY_ASC = "name ASC";

	public static final String ORDER_BY_DESC = "name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public WorkflowDefinitionNameComparator() {
		this(false);
	}

	public WorkflowDefinitionNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		String workflowDefinitionName1 = workflowDefinition1.getName();
		String workflowDefinitionName2 = workflowDefinition2.getName();

		int value = workflowDefinitionName1.compareTo(workflowDefinitionName2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}