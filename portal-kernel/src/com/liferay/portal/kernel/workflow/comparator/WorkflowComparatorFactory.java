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

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowTask;

/**
 * @author Michael C. Han
 */
@MessagingProxy(mode = ProxyMode.SYNC)
public interface WorkflowComparatorFactory {

	public OrderByComparator<WorkflowDefinition> getDefinitionNameComparator(
		boolean ascending);

	public OrderByComparator<WorkflowInstance> getInstanceCompletedComparator(
		boolean ascending);

	public OrderByComparator<WorkflowInstance> getInstanceEndDateComparator(
		boolean ascending);

	public OrderByComparator<WorkflowInstance> getInstanceStartDateComparator(
		boolean ascending);

	public OrderByComparator<WorkflowInstance> getInstanceStateComparator(
		boolean ascending);

	public OrderByComparator<WorkflowLog> getLogCreateDateComparator(
		boolean ascending);

	public OrderByComparator<WorkflowLog> getLogUserIdComparator(
		boolean ascending);

	public OrderByComparator<WorkflowTask> getTaskCompletionDateComparator(
		boolean ascending);

	public OrderByComparator<WorkflowTask> getTaskCreateDateComparator(
		boolean ascending);

	public OrderByComparator<WorkflowTask> getTaskDueDateComparator(
		boolean ascending);

	public default OrderByComparator<WorkflowTask>
		getTaskModifiedDateComparator(boolean ascending) {

		throw new UnsupportedOperationException();
	}

	public OrderByComparator<WorkflowTask> getTaskNameComparator(
		boolean ascending);

	public OrderByComparator<WorkflowTask> getTaskUserIdComparator(
		boolean ascending);

}