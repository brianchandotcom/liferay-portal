/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
public class WorkflowDefinitionNameComparator extends OrderByComparator {

	public WorkflowDefinitionNameComparator(
		boolean ascending, String orderByAsc, String orderByDesc,
		String[] orderByFields) {

		_ascending = ascending;
		_orderByAsc = orderByAsc;
		_orderByDesc = orderByDesc;
		_orderByFields = orderByFields;
	}

	public WorkflowDefinitionNameComparator(
		String orderByAsc, String orderByDesc, String[] orderByFields) {

		this(false, orderByAsc, orderByDesc, orderByFields);
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		WorkflowDefinition workflowDefinition1 = (WorkflowDefinition)obj1;
		WorkflowDefinition workflowDefinition2 = (WorkflowDefinition)obj2;

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
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (isAscending()) {
			return _orderByAsc;
		}
		else {
			return _orderByDesc;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return _orderByFields;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;
	private String _orderByAsc;
	private String _orderByDesc;
	private String[] _orderByFields;

}