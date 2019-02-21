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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Raymond Augé
 */
public class WorkflowInstanceManagerUtil {

	public static void deleteWorkflowInstance(
			long companyId, long workflowInstanceId)
		throws WorkflowException {

		getWorkflowInstanceManager().deleteWorkflowInstance(
			companyId, workflowInstanceId);
	}

	public static List<String> getNextTransitionNames(
			long companyId, long userId, long workflowInstanceId)
		throws WorkflowException {

		return getWorkflowInstanceManager().getNextTransitionNames(
			companyId, userId, workflowInstanceId);
	}

	public static WorkflowInstance getWorkflowInstance(
			long companyId, long workflowInstanceId)
		throws WorkflowException {

		return getWorkflowInstanceManager().getWorkflowInstance(
			companyId, workflowInstanceId);
	}

	public static int getWorkflowInstanceCount(
			long companyId, Long userId, String assetClassName,
			Long assetClassPK, Boolean completed)
		throws WorkflowException {

		return getWorkflowInstanceManager().getWorkflowInstanceCount(
			companyId, userId, assetClassName, assetClassPK, completed);
	}

	public static int getWorkflowInstanceCount(
			long companyId, Long userId, String[] assetClassNames,
			Boolean completed)
		throws WorkflowException {

		return getWorkflowInstanceManager().getWorkflowInstanceCount(
			companyId, userId, assetClassNames, completed);
	}

	public static int getWorkflowInstanceCount(
			long companyId, String workflowDefinitionName,
			Integer workflowDefinitionVersion, Boolean completed)
		throws WorkflowException {

		return getWorkflowInstanceManager().getWorkflowInstanceCount(
			companyId, workflowDefinitionName, workflowDefinitionVersion,
			completed);
	}

	public static WorkflowInstanceManager getWorkflowInstanceManager() {
		return _workflowInstanceManager;
	}

	public static List<WorkflowInstance> getWorkflowInstances(
			long companyId, Long userId, String assetClassName,
			Long assetClassPK, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return getWorkflowInstanceManager().getWorkflowInstances(
			companyId, userId, assetClassName, assetClassPK, completed, start,
			end, orderByComparator);
	}

	public static List<WorkflowInstance> getWorkflowInstances(
			long companyId, Long userId, String[] assetClassNames,
			Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return getWorkflowInstanceManager().getWorkflowInstances(
			companyId, userId, assetClassNames, completed, start, end,
			orderByComparator);
	}

	public static List<WorkflowInstance> getWorkflowInstances(
			long companyId, String workflowDefinitionName,
			Integer workflowDefinitionVersion, Boolean completed, int start,
			int end, OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return getWorkflowInstanceManager().getWorkflowInstances(
			companyId, workflowDefinitionName, workflowDefinitionVersion,
			completed, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #search(long, Long,
	 *             String, String, String, String, String, Boolean, int, int,
	 *             OrderByComparator)}
	 */
	@Deprecated
	public static List<WorkflowInstance> search(
			long companyId, Long userId, String assetType, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return getWorkflowInstanceManager().search(
			companyId, userId, assetType, nodeName, kaleoDefinitionName,
			completed, start, end, orderByComparator);
	}

	public static List<WorkflowInstance> search(
			long companyId, Long userId, String assetClassName,
			String assetTitle, String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return getWorkflowInstanceManager().search(
			companyId, userId, assetClassName, assetTitle, assetDescription,
			nodeName, kaleoDefinitionName, completed, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #searchCount(long,
	 *             Long, String, String, String, String, String, Boolean)}
	 */
	@Deprecated
	public static int searchCount(
			long companyId, Long userId, String assetType, String nodeName,
			String kaleoDefinitionName, Boolean completed)
		throws WorkflowException {

		return getWorkflowInstanceManager().searchCount(
			companyId, userId, assetType, nodeName, kaleoDefinitionName,
			completed);
	}

	public static int searchCount(
			long companyId, Long userId, String assetClassName,
			String assetTitle, String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed)
		throws WorkflowException {

		return getWorkflowInstanceManager().searchCount(
			companyId, userId, assetClassName, assetTitle, assetDescription,
			nodeName, kaleoDefinitionName, completed);
	}

	public static WorkflowInstance signalWorkflowInstance(
			long companyId, long userId, long workflowInstanceId,
			String transitionName, Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return getWorkflowInstanceManager().signalWorkflowInstance(
			companyId, userId, workflowInstanceId, transitionName,
			workflowContext);
	}

	public static WorkflowInstance startWorkflowInstance(
			long companyId, long groupId, long userId,
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String transitionName, Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return getWorkflowInstanceManager().startWorkflowInstance(
			companyId, groupId, userId, workflowDefinitionName,
			workflowDefinitionVersion, transitionName, workflowContext);
	}

	public static WorkflowInstance updateWorkflowContext(
			long companyId, long workflowInstanceId,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return getWorkflowInstanceManager().updateWorkflowContext(
			companyId, workflowInstanceId, workflowContext);
	}

	public void setWorkflowInstanceManager(
		WorkflowInstanceManager workflowInstanceManager) {

		_workflowInstanceManager = workflowInstanceManager;
	}

	private static WorkflowInstanceManager _workflowInstanceManager;

}