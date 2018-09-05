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

package com.liferay.portal.kernel.workflow.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

/**
 * @author Shuyang Zhou
 */
public class WorkflowDefinitionNameComparator
	extends OrderByComparator<WorkflowDefinition> {

	public WorkflowDefinitionNameComparator(
		boolean ascending, String orderByAsc, String orderByDesc,
		String[] orderByFields) {

		_ascending = ascending;
		_orderByAsc = orderByAsc;
		_orderByDesc = orderByDesc;
		_orderByFields = orderByFields;
	}

	@Override
	public int compare(
		WorkflowDefinition workflowDefinition1,
		WorkflowDefinition workflowDefinition2) {

		String name1 = workflowDefinition1.getName();
		String name2 = workflowDefinition2.getName();

		int value = name1.compareTo(name2);

		if (value == 0) {
			Integer version1 = workflowDefinition1.getVersion();
			Integer version2 = workflowDefinition2.getVersion();

			value = version1.compareTo(version2);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (isAscending()) {
			return _orderByAsc;
		}

		return _orderByDesc;
	}

	@Override
	public String[] getOrderByFields() {
		return _orderByFields;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;
	private final String _orderByAsc;
	private final String _orderByDesc;
	private final String[] _orderByFields;

}