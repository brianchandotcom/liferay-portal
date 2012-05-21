/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Michael C. Han
 */
public class WorkflowComparatorFactoryUtil {

	public static OrderByComparator getDefinitionNameComparator() {
		return getWorkflowComparatorFactory().getDefinitionNameComparator(
			false);
	}

	public static OrderByComparator getDefinitionNameComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getDefinitionNameComparator(
			ascending);
	}

	public static OrderByComparator getInstanceEndDateComparator() {
		return getWorkflowComparatorFactory().getInstanceEndDateComparator(
			false);
	}

	public static OrderByComparator getInstanceEndDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getInstanceEndDateComparator(
			ascending);
	}

	public static OrderByComparator getInstanceStartDateComparator() {
		return getWorkflowComparatorFactory().getInstanceStartDateComparator(
			false);
	}

	public static OrderByComparator getInstanceStartDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getInstanceStartDateComparator(
			ascending);
	}

	public static OrderByComparator getInstanceStateComparator() {
		return getWorkflowComparatorFactory().getInstanceStateComparator(false);
	}

	public static OrderByComparator getInstanceStateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getInstanceStateComparator(
			ascending);
	}

	public static OrderByComparator getLogCreateDateComparator() {
		return getWorkflowComparatorFactory().getLogCreateDateComparator(false);
	}

	public static OrderByComparator getLogCreateDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getLogCreateDateComparator(
			ascending);
	}

	public static OrderByComparator getLogUserIdComparator() {
		return getWorkflowComparatorFactory().getLogUserIdComparator(false);
	}

	public static OrderByComparator getLogUserIdComparator(boolean ascending) {
		return getWorkflowComparatorFactory().getLogUserIdComparator(ascending);
	}

	public static OrderByComparator getTaskCompletionDateComparator() {
		return getWorkflowComparatorFactory().getTaskCompletionDateComparator(
			false);
	}

	public static OrderByComparator getTaskCompletionDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getTaskCompletionDateComparator(
			ascending);
	}

	public static OrderByComparator getTaskCreateDateComparator() {
		return getWorkflowComparatorFactory().getTaskCreateDateComparator(
			false);
	}

	public static OrderByComparator getTaskCreateDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getTaskCreateDateComparator(
			ascending);
	}

	public static OrderByComparator getTaskDueDateComparator() {
		return getWorkflowComparatorFactory().getTaskDueDateComparator(false);
	}

	public static OrderByComparator getTaskDueDateComparator(
		boolean ascending) {

		return getWorkflowComparatorFactory().getTaskDueDateComparator(
			ascending);
	}

	public static OrderByComparator getTaskNameComparator() {
		return getWorkflowComparatorFactory().getTaskNameComparator(false);
	}

	public static OrderByComparator getTaskNameComparator(boolean ascending) {
		return getWorkflowComparatorFactory().getTaskNameComparator(ascending);
	}

	public static OrderByComparator getTaskUserIdComparator() {
		return getWorkflowComparatorFactory().getTaskUserIdComparator(false);
	}

	public static OrderByComparator getTaskUserIdComparator(boolean ascending) {
		return getWorkflowComparatorFactory().getTaskUserIdComparator(
			ascending);
	}

	public static WorkflowComparatorFactory getWorkflowComparatorFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			WorkflowComparatorFactoryUtil.class);

		return _workflowComparatorFactory;
	}

	public void setWorkflowComparatorFactory(
		WorkflowComparatorFactory workflowComparatorFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_workflowComparatorFactory = workflowComparatorFactory;
	}

	private static WorkflowComparatorFactory _workflowComparatorFactory;

}