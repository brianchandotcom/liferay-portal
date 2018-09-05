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
import com.liferay.portal.kernel.workflow.WorkflowLog;

import java.util.Date;

/**
 * @author Michael C. Han
 */
public class WorkflowLogCreateDateComparator
	extends OrderByComparator<WorkflowLog> {

	public WorkflowLogCreateDateComparator(
		boolean ascending, String orderByAsc, String orderByDesc,
		String[] orderByFields) {

		_ascending = ascending;
		_orderByAsc = orderByAsc;
		_orderByDesc = orderByDesc;
		_orderByFields = orderByFields;
	}

	@Override
	public int compare(WorkflowLog workflowLog1, WorkflowLog workflowLog2) {
		Date createDate1 = workflowLog1.getCreateDate();
		Date createDate2 = workflowLog2.getCreateDate();

		int value = createDate1.compareTo(createDate2);

		if (value != 0) {
			Long workflowLogId1 = workflowLog1.getWorkflowLogId();
			Long workflowLogId2 = workflowLog2.getWorkflowLogId();

			value = workflowLogId1.compareTo(workflowLogId2);
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