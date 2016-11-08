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

package com.liferay.portal.kaleo.runtime.integration.impl.internal;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.KaleoSignaler;
import com.liferay.portal.workflow.kaleo.runtime.TaskManager;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelectorRegistry;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.runtime.util.comparator.KaleoTaskInstanceTokenOrderByComparator;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = {"proxy.bean=false"},
	service = WorkflowTaskManager.class
)
public class WorkflowTaskManagerImpl implements WorkflowTaskManager {

	@Override
	public WorkflowTask assignWorkflowTaskToRole(
			long companyId, long userId, long workflowTaskInstanceId,
			long roleId, String comment, Date dueDate,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		return _taskManager.assignWorkflowTaskToRole(
			workflowTaskInstanceId, roleId, comment, dueDate, workflowContext,
			serviceContext);
	}

	@Override
	public WorkflowTask assignWorkflowTaskToUser(
			long companyId, long userId, long workflowTaskInstanceId,
			long assigneeUserId, String comment, Date dueDate,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		return _taskManager.assignWorkflowTaskToUser(
			workflowTaskInstanceId, assigneeUserId, comment, dueDate,
			workflowContext, serviceContext);
	}

	@Override
	public WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskInstanceId,
			String transitionName, String comment,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		Lock lock = null;

		try {
			lock = lockManager.lock(
				userId, WorkflowTask.class.getName(), workflowTaskInstanceId,
				String.valueOf(userId), false, 1000);
		}
		catch (PortalException pe) {
			if (pe instanceof DuplicateLockException) {
				throw new WorkflowException(
					"Workflow task " + workflowTaskInstanceId +
						" is locked by user " + userId,
					pe);
			}

			throw new WorkflowException(
				"Unable to lock workflow task " + workflowTaskInstanceId, pe);
		}

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			WorkflowTask workflowTask = _taskManager.completeWorkflowTask(
				workflowTaskInstanceId, transitionName, comment,
				workflowContext, serviceContext);

			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTask.getWorkflowTaskId());

			KaleoInstanceToken kaleoInstanceToken =
				kaleoTaskInstanceToken.getKaleoInstanceToken();

			if (workflowContext == null) {
				workflowContext = WorkflowContextUtil.convert(
					kaleoInstanceToken.getKaleoInstance().getWorkflowContext());
			}

			workflowContext.put(
				WorkflowConstants.CONTEXT_TASK_COMMENTS, comment);
			workflowContext.put(
				WorkflowConstants.CONTEXT_TRANSITION_NAME, transitionName);

			ExecutionContext executionContext = new ExecutionContext(
				kaleoInstanceToken, kaleoTaskInstanceToken, workflowContext,
				serviceContext);

			_kaleoSignaler.signalExit(transitionName, executionContext);

			return workflowTask;
		}
		catch (Exception e) {
			throw new WorkflowException("Unable to complete task", e);
		}
		finally {
			lockManager.unlock(lock.getClassName(), lock.getKey());
		}
	}

	@Override
	public WorkflowTask fetchWorkflowTask(
			long companyId, long workflowTaskInstanceId)
		throws WorkflowException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.fetchKaleoTaskInstanceToken(
				workflowTaskInstanceId);

		if (kaleoTaskInstanceToken == null) {
			return null;
		}

		try {
			return _kaleoWorkflowModelConverter.toWorkflowTask(
				kaleoTaskInstanceToken,
				WorkflowContextUtil.convert(
					kaleoTaskInstanceToken.getWorkflowContext()));
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<String> getNextTransitionNames(
			long companyId, long userId, long workflowTaskInstanceId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskInstanceId);

			if (kaleoTaskInstanceToken.isCompleted()) {
				return Collections.emptyList();
			}

			KaleoTask kaleoTask = kaleoTaskInstanceToken.getKaleoTask();

			KaleoNode kaleoNode = kaleoTask.getKaleoNode();

			List<KaleoTransition> kaleoTransitions =
				kaleoNode.getKaleoTransitions();

			List<String> transitionNames = new ArrayList<>(
				kaleoTransitions.size());

			for (KaleoTransition kaleoTransition : kaleoTransitions) {
				transitionNames.add(kaleoTransition.getName());
			}

			return transitionNames;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public long[] getPooledActorsIds(
			long companyId, long workflowTaskInstanceId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskInstanceId);

			List<KaleoTaskAssignment> calculatedKaleoTaskAssignments =
				getCalculatedKaleoTaskAssignments(kaleoTaskInstanceToken);

			Map<String, Long> pooledActors = new TreeMap<>(
				new NaturalOrderStringComparator());

			for (KaleoTaskAssignment calculatedKaleoTaskAssignment :
					calculatedKaleoTaskAssignments) {

				String assigneeClassName =
					calculatedKaleoTaskAssignment.getAssigneeClassName();
				long assigneeClassPK =
					calculatedKaleoTaskAssignment.getAssigneeClassPK();

				if (assigneeClassName.equals(User.class.getName())) {
					User user = _userLocalService.fetchUser(assigneeClassPK);

					if (user != null) {
						pooledActors.put(user.getFullName(), user.getUserId());
					}

					continue;
				}

				Role role = _roleLocalService.getRole(
					calculatedKaleoTaskAssignment.getAssigneeClassPK());

				if ((role.getType() == RoleConstants.TYPE_SITE) ||
					(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

					List<UserGroupRole> userGroupRoles =
						_userGroupRoleLocalService.
							getUserGroupRolesByGroupAndRole(
								kaleoTaskInstanceToken.getGroupId(),
								assigneeClassPK);

					for (UserGroupRole userGroupRole : userGroupRoles) {
						User user = userGroupRole.getUser();

						pooledActors.put(user.getFullName(), user.getUserId());
					}

					List<UserGroupGroupRole> userGroupGroupRoles =
						_userGroupGroupRoleLocalService.
							getUserGroupGroupRolesByGroupAndRole(
								kaleoTaskInstanceToken.getGroupId(),
								assigneeClassPK);

					for (UserGroupGroupRole userGroupGroupRole :
							userGroupGroupRoles) {

						List<User> userGroupUsers =
							_userLocalService.getUserGroupUsers(
								userGroupGroupRole.getUserGroupId());

						for (User user : userGroupUsers) {
							pooledActors.put(
								user.getFullName(), user.getUserId());
						}
					}
				}
				else {
					List<User> inheritedRoleUsers =
						_userLocalService.getInheritedRoleUsers(
							assigneeClassPK, QueryUtil.ALL_POS,
							QueryUtil.ALL_POS, null);

					for (User user : inheritedRoleUsers) {
						pooledActors.put(user.getFullName(), user.getUserId());
					}
				}
			}

			return ArrayUtil.toLongArray(pooledActors.values());
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowTask getWorkflowTask(
			long companyId, long workflowTaskInstanceId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskInstanceId);

			return _kaleoWorkflowModelConverter.toWorkflowTask(
				kaleoTaskInstanceToken,
				WorkflowContextUtil.convert(
					kaleoTaskInstanceToken.getWorkflowContext()));
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowTaskCount(long companyId, Boolean completed)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			return _kaleoTaskInstanceTokenLocalService.
				getKaleoTaskInstanceTokensCount(completed, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowTaskCountByRole(
			long companyId, long roleId, Boolean completed)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			return _kaleoTaskInstanceTokenLocalService.
				getKaleoTaskInstanceTokensCount(
					Role.class.getName(), roleId, completed, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowTaskCountBySubmittingUser(
			long companyId, long userId, Boolean completed)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			return _kaleoTaskInstanceTokenLocalService.
				getSubmittingUserKaleoTaskInstanceTokensCount(
					userId, completed, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowTaskCountByUser(
			long companyId, long userId, Boolean completed)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			return _kaleoTaskInstanceTokenLocalService.
				getKaleoTaskInstanceTokensCount(
					User.class.getName(), serviceContext.getUserId(), completed,
					serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowTaskCountByUserRoles(
			long companyId, long userId, Boolean completed)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			return _kaleoTaskInstanceTokenLocalService.searchCount(
				null, completed, Boolean.TRUE, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowTaskCountByWorkflowInstance(
			long companyId, Long userId, long workflowInstanceId,
			Boolean completed)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			if (userId != null) {
				serviceContext.setUserId(userId);
			}

			return _kaleoTaskInstanceTokenLocalService.
				getKaleoTaskInstanceTokensCount(
					workflowInstanceId, completed, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> getWorkflowTasks(
			long companyId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
					completed, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> getWorkflowTasksByRole(
			long companyId, long roleId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
					Role.class.getName(), roleId, completed, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> getWorkflowTasksBySubmittingUser(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.
					getSubmittingUserKaleoTaskInstanceTokens(
						userId, completed, start, end,
						KaleoTaskInstanceTokenOrderByComparator.
							getOrderByComparator(
								orderByComparator,
								_kaleoWorkflowModelConverter),
						serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> getWorkflowTasksByUser(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
					User.class.getName(), userId, completed, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> getWorkflowTasksByUserRoles(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.search(
					null, completed, Boolean.TRUE, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> getWorkflowTasksByWorkflowInstance(
			long companyId, Long userId, long workflowInstanceId,
			Boolean completed, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			if (userId != null) {
				serviceContext.setUserId(userId);
			}

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
					workflowInstanceId, completed, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> search(
			long companyId, long userId, String keywords, Boolean completed,
			Boolean searchByUserRoles, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.search(
					keywords, completed, searchByUserRoles, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> search(
			long companyId, long userId, String taskName, String assetType,
			Long[] assetPrimaryKey, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator,
			int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.search(
					taskName, assetType, assetPrimaryKey, dueDateGT, dueDateLT,
					completed, searchByUserRoles, andOperator, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowTask> search(
			long companyId, long userId, String keywords, String[] assetTypes,
			Boolean completed, Boolean searchByUserRoles, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.search(
					keywords, assetTypes, completed, searchByUserRoles, start,
					end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			return toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int searchCount(
			long companyId, long userId, String keywords, Boolean completed,
			Boolean searchByUserRoles)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			return _kaleoTaskInstanceTokenLocalService.searchCount(
				keywords, completed, searchByUserRoles, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int searchCount(
			long companyId, long userId, String taskName, String assetType,
			Long[] assetPrimaryKey, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			return _kaleoTaskInstanceTokenLocalService.searchCount(
				taskName, assetType, assetPrimaryKey, dueDateGT, dueDateLT,
				completed, searchByUserRoles, andOperator, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int searchCount(
			long companyId, long userId, String keywords, String[] assetTypes,
			Boolean completed, Boolean searchByUserRoles)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			return _kaleoTaskInstanceTokenLocalService.searchCount(
				keywords, assetTypes, completed, searchByUserRoles,
				serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowTask updateDueDate(
			long companyId, long userId, long workflowTaskInstanceId,
			String comment, Date dueDate)
		throws WorkflowException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		return _taskManager.updateDueDate(
			workflowTaskInstanceId, comment, dueDate, serviceContext);
	}

	protected List<KaleoTaskAssignment> getCalculatedKaleoTaskAssignments(
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		List<KaleoTaskAssignment> calculatedKaleoTaskAssignments =
			new ArrayList<>();

		KaleoInstanceToken kaleoInstanceToken =
			kaleoTaskInstanceToken.getKaleoInstanceToken();

		Map<String, Serializable> workflowContext = WorkflowContextUtil.convert(
			kaleoTaskInstanceToken.getWorkflowContext());

		ServiceContext workflowContextServiceContext =
			(ServiceContext)workflowContext.get(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT);

		ExecutionContext executionContext = new ExecutionContext(
			kaleoInstanceToken, workflowContext, workflowContextServiceContext);

		List<KaleoTaskAssignment> configuredKaleoTaskAssignments =
			_kaleoTaskAssignmentLocalService.getKaleoTaskAssignments(
				kaleoTaskInstanceToken.getKaleoTaskId());

		for (KaleoTaskAssignment configuredKaleoTaskAssignment :
				configuredKaleoTaskAssignments) {

			String assigneeClassName =
				configuredKaleoTaskAssignment.getAssigneeClassName();

			TaskAssignmentSelector taskAssignmentSelector =
				_taskAssignmentSelectorRegistry.getTaskAssignmentSelector(
					assigneeClassName);

			Collection<KaleoTaskAssignment> kaleoTaskAssignments =
				taskAssignmentSelector.calculateTaskAssignments(
					configuredKaleoTaskAssignment, executionContext);

			calculatedKaleoTaskAssignments.addAll(kaleoTaskAssignments);
		}

		return calculatedKaleoTaskAssignments;
	}

	protected List<WorkflowTask> toWorkflowTasks(
			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens)
		throws PortalException {

		List<WorkflowTask> workflowTasks = new ArrayList<>(
			kaleoTaskInstanceTokens.size());

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				kaleoTaskInstanceTokens) {

			WorkflowTask workflowTask =
				_kaleoWorkflowModelConverter.toWorkflowTask(
					kaleoTaskInstanceToken,
					WorkflowContextUtil.convert(
						kaleoTaskInstanceToken.getWorkflowContext()));

			workflowTasks.add(workflowTask);
		}

		return workflowTasks;
	}

	@Reference
	protected LockManager lockManager;

	@Reference
	private KaleoSignaler _kaleoSignaler;

	@Reference
	private KaleoTaskAssignmentLocalService _kaleoTaskAssignmentLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private TaskAssignmentSelectorRegistry _taskAssignmentSelectorRegistry;

	@Reference
	private TaskManager _taskManager;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}