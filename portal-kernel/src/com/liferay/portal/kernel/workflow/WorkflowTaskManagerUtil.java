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

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Raymond Augé
 */
public class WorkflowTaskManagerUtil {

	public static WorkflowTask assignWorkflowTaskToRole(
			long companyId, long userId, long workflowTaskId, long roleId,
			String comment, Date dueDate,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return getWorkflowTaskManager().assignWorkflowTaskToRole(
			companyId, userId, workflowTaskId, roleId, comment, dueDate,
			workflowContext);
	}

	public static WorkflowTask assignWorkflowTaskToUser(
			long companyId, long userId, long workflowTaskId,
			long assigneeUserId, String comment, Date dueDate,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return getWorkflowTaskManager().assignWorkflowTaskToUser(
			companyId, userId, workflowTaskId, assigneeUserId, comment, dueDate,
			workflowContext);
	}

	public static WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskId,
			String transitionName, String comment,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		return getWorkflowTaskManager().completeWorkflowTask(
			companyId, userId, workflowTaskId, transitionName, comment,
			workflowContext);
	}

	public static WorkflowTask fetchWorkflowTask(
			long companyId, long workflowTaskId)
		throws WorkflowException {

		return getWorkflowTaskManager().fetchWorkflowTask(
			companyId, workflowTaskId);
	}

	public static List<User> getAssignableUsers(
			long companyId, long workflowTaskId)
		throws WorkflowException {

		return getWorkflowTaskManager().getAssignableUsers(
			companyId, workflowTaskId);
	}

	public static List<String> getNextTransitionNames(
			long companyId, long userId, long workflowTaskId)
		throws WorkflowException {

		return getWorkflowTaskManager().getNextTransitionNames(
			companyId, userId, workflowTaskId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getAssignableUsers(long, long)}
	 */
	@Deprecated
	public static List<User> getPooledActors(
			long companyId, long workflowTaskId)
		throws WorkflowException {

		return getWorkflowTaskManager().getPooledActors(
			companyId, workflowTaskId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getAssignableUsers(long, long)}
	 */
	@Deprecated
	public static long[] getPooledActorsIds(long companyId, long workflowTaskId)
		throws WorkflowException {

		return getWorkflowTaskManager().getPooledActorsIds(
			companyId, workflowTaskId);
	}

	public static WorkflowTask getWorkflowTask(
			long companyId, long workflowTaskId)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTask(
			companyId, workflowTaskId);
	}

	public static int getWorkflowTaskCount(long companyId, Boolean completed)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTaskCount(
			companyId, completed);
	}

	public static int getWorkflowTaskCountByRole(
			long companyId, long roleId, Boolean completed)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTaskCountByRole(
			companyId, roleId, completed);
	}

	public static int getWorkflowTaskCountBySubmittingUser(
			long companyId, long userId, Boolean completed)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTaskCountBySubmittingUser(
			companyId, userId, completed);
	}

	public static int getWorkflowTaskCountByUser(
			long companyId, long userId, Boolean completed)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTaskCountByUser(
			companyId, userId, completed);
	}

	public static int getWorkflowTaskCountByUserRoles(
			long companyId, long userId, Boolean completed)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTaskCountByUserRoles(
			companyId, userId, completed);
	}

	public static int getWorkflowTaskCountByUserRoles(
			long companyId, long userId, long workflowInstanceId,
			Boolean completed)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTaskCountByUserRoles(
			companyId, userId, workflowInstanceId, completed);
	}

	public static int getWorkflowTaskCountByWorkflowInstance(
			long companyId, Long userId, long workflowInstanceId,
			Boolean completed)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTaskCountByWorkflowInstance(
			companyId, userId, workflowInstanceId, completed);
	}

	public static WorkflowTaskManager getWorkflowTaskManager() {
		return _workflowTaskManager;
	}

	public static List<WorkflowTask> getWorkflowTasks(
			long companyId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTasks(
			companyId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByRole(
			long companyId, long roleId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTasksByRole(
			companyId, roleId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksBySubmittingUser(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTasksBySubmittingUser(
			companyId, userId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByUser(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTasksByUser(
			companyId, userId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByUserRoles(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTasksByUserRoles(
			companyId, userId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByWorkflowInstance(
			long companyId, Long userId, long workflowInstanceId,
			Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().getWorkflowTasksByWorkflowInstance(
			companyId, userId, workflowInstanceId, completed, start, end,
			orderByComparator);
	}

	public static boolean hasAssignableUsers(
			long companyId, long workflowTaskId)
		throws WorkflowException {

		return getWorkflowTaskManager().hasAssignableUsers(
			companyId, workflowTaskId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #hasAssignableUsers(long, long)}
	 */
	@Deprecated
	public static boolean hasOtherAssignees(long workflowTaskId, long userId)
		throws WorkflowException {

		return getWorkflowTaskManager().hasOtherAssignees(
			workflowTaskId, userId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(long,
	 *             long, String, String[], String[], Long[], Long[], Date, Date,
	 *             Boolean, Boolean, Long[], Boolean, int, int,
	 *             OrderByComparator)}
	 */
	@Deprecated
	public static List<WorkflowTask> search(
			long companyId, long userId, String keywords, Boolean completed,
			Boolean searchByUserRoles, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().search(
			companyId, userId, keywords, completed, searchByUserRoles, start,
			end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(long,
	 *             long, String, String[], String[], Long[], Long[], Date, Date,
	 *             Boolean, Boolean, Long[], Boolean, int, int,
	 *             OrderByComparator)}
	 */
	@Deprecated
	public static List<WorkflowTask> search(
			long companyId, long userId, String taskName, String assetType,
			Long[] assetPrimaryKeys, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator,
			int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().search(
			companyId, userId, taskName, assetType, assetPrimaryKeys, dueDateGT,
			dueDateLT, completed, searchByUserRoles, andOperator, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(long,
	 *             long, String, String[], String[], Long[], Long[], Date, Date,
	 *             Boolean, Boolean, Long[], Boolean, int, int,
	 *             OrderByComparator)}
	 */
	@Deprecated
	public static List<WorkflowTask> search(
			long companyId, long userId, String assetTitle, String taskName,
			String[] assetTypes, Long[] assetPrimaryKeys, Date dueDateGT,
			Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
			Boolean andOperator, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().search(
			companyId, userId, assetTitle, taskName, assetTypes,
			assetPrimaryKeys, dueDateGT, dueDateLT, completed,
			searchByUserRoles, andOperator, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(long,
	 *             long, String, String[], String[], Long[], Long[], Date, Date,
	 *             Boolean, Boolean, Long[], Boolean, int, int,
	 *             OrderByComparator)}
	 */
	@Deprecated
	public static List<WorkflowTask> search(
			long companyId, long userId, String keywords, String[] assetTypes,
			Boolean completed, Boolean searchByUserRoles, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().search(
			companyId, userId, keywords, assetTypes, completed,
			searchByUserRoles, start, end, orderByComparator);
	}

	public static List<WorkflowTask> search(
			long companyId, long userId, String assetTitle, String[] taskNames,
			String[] assetTypes, Long[] assetPrimaryKeys,
			Long[] assigneeUserIds, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles,
			Long[] workflowInstanceIds, Boolean andOperator, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return getWorkflowTaskManager().search(
			companyId, userId, assetTitle, taskNames, assetTypes,
			assetPrimaryKeys, assigneeUserIds, dueDateGT, dueDateLT, completed,
			searchByUserRoles, workflowInstanceIds, andOperator, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #searchCount(long, long, String, String[], String[], Long[],
	 *             Long[], Date, Date, Boolean, Boolean, Long[], Boolean)}
	 */
	@Deprecated
	public static int searchCount(
			long companyId, long userId, String keywords, Boolean completed,
			Boolean searchByUserRoles)
		throws WorkflowException {

		return getWorkflowTaskManager().searchCount(
			companyId, userId, keywords, completed, searchByUserRoles);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #searchCount(long, long, String, String[], String[], Long[],
	 *             Long[], Date, Date, Boolean, Boolean, Long[], Boolean)}
	 */
	@Deprecated
	public static int searchCount(
			long companyId, long userId, String taskName, String assetType,
			Long[] assetPrimaryKeys, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator)
		throws WorkflowException {

		return getWorkflowTaskManager().searchCount(
			companyId, userId, taskName, assetType, assetPrimaryKeys, dueDateGT,
			dueDateLT, completed, searchByUserRoles, andOperator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #searchCount(long, long, String, String[], String[], Long[],
	 *             Long[], Date, Date, Boolean, Boolean, Long[], Boolean)}
	 */
	@Deprecated
	public static int searchCount(
			long companyId, long userId, String assetTitle, String taskName,
			String[] assetTypes, Long[] assetPrimaryKeys, Date dueDateGT,
			Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
			Boolean andOperator)
		throws WorkflowException {

		return getWorkflowTaskManager().searchCount(
			companyId, userId, assetTitle, taskName, assetTypes,
			assetPrimaryKeys, dueDateGT, dueDateLT, completed,
			searchByUserRoles, andOperator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #searchCount(long, long, String, String[], String[], Long[],
	 *             Long[], Date, Date, Boolean, Boolean, Long[], Boolean)}
	 */
	@Deprecated
	public static int searchCount(
			long companyId, long userId, String keywords, String[] assetTypes,
			Boolean completed, Boolean searchByUserRoles)
		throws WorkflowException {

		return getWorkflowTaskManager().searchCount(
			companyId, userId, keywords, assetTypes, completed,
			searchByUserRoles);
	}

	public static int searchCount(
			long companyId, long userId, String assetTitle, String[] taskNames,
			String[] assetTypes, Long[] assetPrimaryKeys,
			Long[] assigneeUserIds, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles,
			Long[] workflowInstanceIds, Boolean andOperator)
		throws WorkflowException {

		return getWorkflowTaskManager().searchCount(
			companyId, userId, assetTitle, taskNames, assetTypes,
			assetPrimaryKeys, assigneeUserIds, dueDateGT, dueDateLT, completed,
			searchByUserRoles, workflowInstanceIds, andOperator);
	}

	public static WorkflowTask updateDueDate(
			long companyId, long userId, long workflowTaskId, String comment,
			Date dueDate)
		throws WorkflowException {

		return getWorkflowTaskManager().updateDueDate(
			companyId, userId, workflowTaskId, comment, dueDate);
	}

	public void setWorkflowTaskManager(
		WorkflowTaskManager workflowTaskManager) {

		_workflowTaskManager = workflowTaskManager;
	}

	private static WorkflowTaskManager _workflowTaskManager;

}