/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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