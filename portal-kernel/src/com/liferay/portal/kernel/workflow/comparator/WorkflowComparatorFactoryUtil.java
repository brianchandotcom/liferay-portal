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
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowTask;

/**
 * @author Michael C. Han
 */
public class WorkflowComparatorFactoryUtil {

	public static OrderByComparator<WorkflowDefinition>
		getDefinitionNameComparator() {

		return getWorkflowComparatorFactory().getDefinitionNameComparator(
			false);
	}

	public static OrderByComparator<WorkflowDefinition>
		getDefinitionNameComparator(boolean ascending) {

		return getWorkflowComparatorFactory().getDefinitionNameComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceCompletedComparator() {

		return getWorkflowComparatorFactory().getInstanceCompletedComparator(
			false);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceCompletedComparator(boolean ascending) {

		return getWorkflowComparatorFactory().getInstanceCompletedComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceEndDateComparator() {

		return getWorkflowComparatorFactory().getInstanceEndDateComparator(
			false);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceEndDateComparator(boolean ascending) {

		return getWorkflowComparatorFactory().getInstanceEndDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceStartDateComparator() {

		return getWorkflowComparatorFactory().getInstanceStartDateComparator(
			false);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceStartDateComparator(boolean ascending) {

		return getWorkflowComparatorFactory().getInstanceStartDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceStateComparator() {

		return getWorkflowComparatorFactory().getInstanceStateComparator(false);
	}

	public static OrderByComparator<WorkflowInstance>
		getInstanceStateComparator(boolean ascending) {

		return getWorkflowComparatorFactory().getInstanceStateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowLog> getLogCreateDateComparator() {
		return getWorkflowComparatorFactory().getLogCreateDateComparator(false);
	}

	public static OrderByComparator<WorkflowLog> getLogCreateDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getLogCreateDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowLog> getLogUserIdComparator() {
		return getWorkflowComparatorFactory().getLogUserIdComparator(false);
	}

	public static OrderByComparator<WorkflowLog> getLogUserIdComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getLogUserIdComparator(ascending);
	}

	public static OrderByComparator<WorkflowTask>
		getTaskCompletionDateComparator() {

		return getWorkflowComparatorFactory().getTaskCompletionDateComparator(
			false);
	}

	public static OrderByComparator<WorkflowTask>
		getTaskCompletionDateComparator(boolean ascending) {

		return getWorkflowComparatorFactory().getTaskCompletionDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowTask>
		getTaskCreateDateComparator() {

		return getWorkflowComparatorFactory().getTaskCreateDateComparator(
			false);
	}

	public static OrderByComparator<WorkflowTask> getTaskCreateDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getTaskCreateDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowTask> getTaskDueDateComparator() {
		return getWorkflowComparatorFactory().getTaskDueDateComparator(false);
	}

	public static OrderByComparator<WorkflowTask> getTaskDueDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getTaskDueDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowTask>
		getTaskModifiedDateComparator() {

		return getWorkflowComparatorFactory().getTaskModifiedDateComparator(
			false);
	}

	public static OrderByComparator<WorkflowTask> getTaskModifiedDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getTaskModifiedDateComparator(
			ascending);
	}

	public static OrderByComparator<WorkflowTask> getTaskNameComparator() {
		return getWorkflowComparatorFactory().getTaskNameComparator(false);
	}

	public static OrderByComparator<WorkflowTask> getTaskNameComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getTaskNameComparator(ascending);
	}

	public static OrderByComparator<WorkflowTask> getTaskUserIdComparator() {
		return getWorkflowComparatorFactory().getTaskUserIdComparator(false);
	}

	public static OrderByComparator<WorkflowTask> getTaskUserIdComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getTaskUserIdComparator(
			ascending);
	}

	public static WorkflowComparatorFactory getWorkflowComparatorFactory() {
		return _workflowComparatorFactory;
	}

	public void setWorkflowComparatorFactory(
		WorkflowComparatorFactory workflowComparatorFactory) {

		_workflowComparatorFactory = workflowComparatorFactory;
	}

	private static WorkflowComparatorFactory _workflowComparatorFactory;

}