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
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.workflow.search.WorkflowModelSearchResult;

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

		_workflowInstanceManager.deleteWorkflowInstance(
			companyId, workflowInstanceId);
	}

	public static List<String> getNextTransitionNames(
			long companyId, long userId, long workflowInstanceId)
		throws WorkflowException {

		return _workflowInstanceManager.getNextTransitionNames(
			companyId, userId, workflowInstanceId);
	}

	public static WorkflowInstance getWorkflowInstance(
			long companyId, long workflowInstanceId)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstance(
			companyId, workflowInstanceId);
	}

	public static WorkflowInstance getWorkflowInstance(
			long companyId, long userId, long workflowInstanceId)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstance(
			companyId, userId, workflowInstanceId);
	}

	public static int getWorkflowInstanceCount(
			long companyId, Long userId, String assetClassName,
			Long assetClassPK, Boolean completed)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceCount(
			companyId, userId, assetClassName, assetClassPK, completed);
	}

	public static int getWorkflowInstanceCount(
			long companyId, Long userId, String[] assetClassNames,
			Boolean completed)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceCount(
			companyId, userId, assetClassNames, completed);
	}

	public static int getWorkflowInstanceCount(
			long companyId, String workflowDefinitionName,
			Integer workflowDefinitionVersion, Boolean completed)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceCount(
			companyId, workflowDefinitionName, workflowDefinitionVersion,
			completed);
	}

	public static List<WorkflowInstance> getWorkflowInstances(
			long companyId, Long userId, String assetClassName,
			Long assetClassPK, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstances(
			companyId, userId, assetClassName, assetClassPK, completed, start,
			end, orderByComparator);
	}

	public static List<WorkflowInstance> getWorkflowInstances(
			long companyId, Long userId, String[] assetClassNames,
			Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstances(
			companyId, userId, assetClassNames, completed, start, end,
			orderByComparator);
	}

	public static List<WorkflowInstance> getWorkflowInstances(
			long companyId, String workflowDefinitionName,
			Integer workflowDefinitionVersion, Boolean completed, int start,
			int end, OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstances(
			companyId, workflowDefinitionName, workflowDefinitionVersion,
			completed, start, end, orderByComparator);
	}

	public static List<WorkflowInstance> search(
			long companyId, Long userId, Boolean active, String assetClassName,
			String assetTitle, String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.search(
			companyId, userId, active, assetClassName, assetTitle,
			assetDescription, nodeName, kaleoDefinitionName, completed, start,
			end, orderByComparator);
	}

	public static int searchCount(
			long companyId, Long userId, Boolean active, String assetClassName,
			String assetTitle, String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed)
		throws WorkflowException {

		return _workflowInstanceManager.searchCount(
			companyId, userId, active, assetClassName, assetTitle,
			assetDescription, nodeName, kaleoDefinitionName, completed);
	}

	public static WorkflowModelSearchResult<WorkflowInstance>
			searchWorkflowInstances(
				long companyId, Long userId, Boolean active,
				String assetClassName, String assetTitle,
				String assetDescription, String nodeName,
				String kaleoDefinitionName, Boolean completed,
				boolean searchByActiveWorkflowHandlers, int start, int end,
				OrderByComparator<WorkflowInstance> orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.searchWorkflowInstances(
			companyId, userId, active, assetClassName, assetTitle,
			assetDescription, nodeName, kaleoDefinitionName, completed,
			searchByActiveWorkflowHandlers, start, end, orderByComparator);
	}

	public static WorkflowInstance signalWorkflowInstance(
			long companyId, long userId, long workflowInstanceId,
			String transitionName, Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return _workflowInstanceManager.signalWorkflowInstance(
			companyId, userId, workflowInstanceId, transitionName,
			workflowContext);
	}

	public static WorkflowInstance signalWorkflowInstance(
			long companyId, long userId, long workflowInstanceId,
			String transitionName, Map<String, Serializable> workflowContext,
			boolean waitForCompletion)
		throws WorkflowException {

		return _workflowInstanceManager.signalWorkflowInstance(
			companyId, userId, workflowInstanceId, transitionName,
			workflowContext, waitForCompletion);
	}

	public static WorkflowInstance startWorkflowInstance(
			long companyId, long groupId, long userId,
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String transitionName, Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return _workflowInstanceManager.startWorkflowInstance(
			companyId, groupId, userId, workflowDefinitionName,
			workflowDefinitionVersion, transitionName, workflowContext);
	}

	public static WorkflowInstance startWorkflowInstance(
			long companyId, long groupId, long userId,
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String transitionName, Map<String, Serializable> workflowContext,
			boolean waitForCompletion)
		throws WorkflowException {

		return _workflowInstanceManager.startWorkflowInstance(
			companyId, groupId, userId, workflowDefinitionName,
			workflowDefinitionVersion, transitionName, workflowContext,
			waitForCompletion);
	}

	public static WorkflowInstance updateWorkflowContext(
			long companyId, long workflowInstanceId,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return _workflowInstanceManager.updateWorkflowContext(
			companyId, workflowInstanceId, workflowContext);
	}

	private static volatile WorkflowInstanceManager _workflowInstanceManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			WorkflowInstanceManager.class, WorkflowInstanceManagerUtil.class,
			"_workflowInstanceManager", true);

}