/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow.comparator;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowNode;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowInstanceStateComparator
	extends OrderByComparator<WorkflowInstance> {

	public WorkflowInstanceStateComparator(
		boolean ascending, String orderByAsc, String orderByDesc,
		String[] orderByFields) {

		_ascending = ascending;
		_orderByAsc = orderByAsc;
		_orderByDesc = orderByDesc;
		_orderByFields = orderByFields;
	}

	@Override
	public int compare(
		WorkflowInstance workflowInstance1,
		WorkflowInstance workflowInstance2) {

		List<String> currentWorkflowNodeNames1 = ListUtil.toList(
			workflowInstance1.getCurrentWorkflowNodes(), WorkflowNode::getName);

		String currentWorkflowNodeName1 = currentWorkflowNodeNames1.get(0);

		List<String> currentNodeNames2 = ListUtil.toList(
			workflowInstance2.getCurrentWorkflowNodes(), WorkflowNode::getName);

		String currentNodeName2 = currentNodeNames2.get(0);

		int value = currentWorkflowNodeName1.compareTo(currentNodeName2);

		if (value == 0) {
			Long workflowInstanceId1 =
				workflowInstance1.getWorkflowInstanceId();
			Long workflowInstanceId2 =
				workflowInstance2.getWorkflowInstanceId();

			value = workflowInstanceId1.compareTo(workflowInstanceId2);
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