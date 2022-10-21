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
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowTask;

/**
 * @author Michael C. Han
 */
public class WorkflowComparatorFactoryUtil {

	public static OrderByComparator<WorkflowDefinition>
		getDefinitionNameComparator() {

		return _workflowComparatorFactory.getDefinitionNameComparator(false);
	}

	public static OrderByComparator<WorkflowDefinition>
		getDefinitionNameComparator(boolean ascending) {

		return _workflowComparatorFactory.getDefinitionNameComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceCompletedComparator() {

		return _workflowComparatorFactory.getInstanceCompletedComparator(false);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceCompletedComparator(boolean ascending) {

		return _workflowComparatorFactory.getInstanceCompletedComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceEndDateComparator() {

		return _workflowComparatorFactory.getInstanceEndDateComparator(false);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceEndDateComparator(boolean ascending) {

		return _workflowComparatorFactory.getInstanceEndDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceStartDateComparator() {

		return _workflowComparatorFactory.getInstanceStartDateComparator(false);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceStartDateComparator(boolean ascending) {

		return _workflowComparatorFactory.getInstanceStartDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceStateComparator() {

		return _workflowComparatorFactory.getInstanceStateComparator(false);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceStateComparator(boolean ascending) {

		return _workflowComparatorFactory.getInstanceStateComparator(ascending);
	}

	public static OrderByComparator<WorkflowLog> getLogCreateDateComparator() {
		return _workflowComparatorFactory.getLogCreateDateComparator(false);
	}

	public static OrderByComparator<WorkflowLog> getLogCreateDateComparator(
		boolean ascending) {

		return _workflowComparatorFactory.getLogCreateDateComparator(ascending);
	}

	public static OrderByComparator<WorkflowLog> getLogUserIdComparator() {
		return _workflowComparatorFactory.getLogUserIdComparator(false);
	}

	public static OrderByComparator<WorkflowLog> getLogUserIdComparator(
		boolean ascending) {

		return _workflowComparatorFactory.getLogUserIdComparator(ascending);
	}

	public static OrderByComparator<WorkflowTask>
		getTaskCompletionDateComparator() {

		return _workflowComparatorFactory.getTaskCompletionDateComparator(
			false);
	}

	public static OrderByComparator<WorkflowTask>
		getTaskCompletionDateComparator(boolean ascending) {

		return _workflowComparatorFactory.getTaskCompletionDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowTask>
		getTaskCreateDateComparator() {

		return _workflowComparatorFactory.getTaskCreateDateComparator(false);
	}

	public static OrderByComparator<WorkflowTask> getTaskCreateDateComparator(
		boolean ascending) {

		return _workflowComparatorFactory.getTaskCreateDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowTask> getTaskDueDateComparator() {
		return _workflowComparatorFactory.getTaskDueDateComparator(false);
	}

	public static OrderByComparator<WorkflowTask> getTaskDueDateComparator(
		boolean ascending) {

		return _workflowComparatorFactory.getTaskDueDateComparator(ascending);
	}

	public static OrderByComparator<WorkflowTask> getTaskInstanceIdComparator(
		boolean ascending) {

		return _workflowComparatorFactory.getTaskInstanceIdComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowTask>
		getTaskModifiedDateComparator() {

		return _workflowComparatorFactory.getTaskModifiedDateComparator(false);
	}

	public static OrderByComparator<WorkflowTask> getTaskModifiedDateComparator(
		boolean ascending) {

		return _workflowComparatorFactory.getTaskModifiedDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowTask> getTaskNameComparator() {
		return _workflowComparatorFactory.getTaskNameComparator(false);
	}

	public static OrderByComparator<WorkflowTask> getTaskNameComparator(
		boolean ascending) {

		return _workflowComparatorFactory.getTaskNameComparator(ascending);
	}

	public static OrderByComparator<WorkflowTask> getTaskUserIdComparator() {
		return _workflowComparatorFactory.getTaskUserIdComparator(false);
	}

	public static OrderByComparator<WorkflowTask> getTaskUserIdComparator(
		boolean ascending) {

		return _workflowComparatorFactory.getTaskUserIdComparator(ascending);
	}

	private static volatile WorkflowComparatorFactory
		_workflowComparatorFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				WorkflowComparatorFactory.class,
				WorkflowComparatorFactoryUtil.class,
				"_workflowComparatorFactory", true);

}