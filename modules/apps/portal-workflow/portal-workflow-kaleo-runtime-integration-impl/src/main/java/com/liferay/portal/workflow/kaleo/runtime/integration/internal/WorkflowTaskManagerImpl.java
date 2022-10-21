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

package com.liferay.portal.workflow.kaleo.runtime.integration.internal;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.kernel.workflow.DefaultWorkflowTransition;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.kernel.workflow.WorkflowTransition;
import com.liferay.portal.kernel.workflow.search.WorkflowModelSearchResult;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.KaleoSignaler;
import com.liferay.portal.workflow.kaleo.runtime.TaskManager;
import com.liferay.portal.workflow.kaleo.runtime.assignment.AggregateKaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelectorRegistry;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.runtime.util.comparator.KaleoTaskInstanceTokenOrderByComparator;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@Component(service = WorkflowTaskManager.class)
@CTAware
public class WorkflowTaskManagerImpl implements WorkflowTaskManager {

	@Override
	public WorkflowTask assignWorkflowTaskToRole(
			long companyId, long userId, long workflowTaskId, long roleId,
			String comment, Date dueDate,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		return _taskManager.assignWorkflowTaskToRole(
			workflowTaskId, roleId, comment, dueDate, workflowContext,
			serviceContext);
	}

	@Override
	public WorkflowTask assignWorkflowTaskToUser(
			long companyId, long userId, long workflowTaskId,
			long assigneeUserId, String comment, Date dueDate,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		List<User> assignableUsers = getAssignableUsers(workflowTaskId);

		User assigneeUser = _userLocalService.getUser(assigneeUserId);

		if (!assignableUsers.contains(assigneeUser) ||
			(permissionChecker.getUserId() != userId)) {

			throw new PrincipalException.MustHavePermission(
				userId, WorkflowTask.class.getName(), workflowTaskId,
				ActionKeys.VIEW);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		return _taskManager.assignWorkflowTaskToUser(
			workflowTaskId, assigneeUserId, comment, dueDate, workflowContext,
			serviceContext);
	}

	@Override
	public WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskId,
			String transitionName, String comment,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		return completeWorkflowTask(
			companyId, userId, workflowTaskId, transitionName, comment,
			workflowContext, false);
	}

	@Override
	public WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskId,
			String transitionName, String comment,
			Map<String, Serializable> workflowContext,
			boolean waitForCompletion)
		throws PortalException {

		WorkflowTask workflowTask = getWorkflowTask(workflowTaskId);

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			workflowTask.getWorkflowTaskAssignees();

		WorkflowTaskAssignee workflowTaskAssignee = workflowTaskAssignees.get(
			0);

		if (workflowTaskAssignee.getAssigneeClassPK() != userId) {
			throw new PrincipalException.MustHavePermission(
				userId, WorkflowTask.class.getName(), workflowTaskId,
				ActionKeys.VIEW);
		}

		Lock lock = null;

		try {
			lock = _lockManager.lock(
				userId, WorkflowTask.class.getName(), workflowTaskId,
				String.valueOf(userId), false, 1000);
		}
		catch (PortalException portalException) {
			if (portalException instanceof DuplicateLockException) {
				throw new WorkflowException(
					StringBundler.concat(
						"Workflow task ", workflowTaskId, " is locked by user ",
						userId),
					portalException);
			}

			throw new WorkflowException(
				"Unable to lock workflow task " + workflowTaskId,
				portalException);
		}

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			workflowTask = _taskManager.completeWorkflowTask(
				workflowTaskId, transitionName, comment, workflowContext,
				serviceContext);

			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTask.getWorkflowTaskId());

			KaleoInstanceToken kaleoInstanceToken =
				kaleoTaskInstanceToken.getKaleoInstanceToken();

			if (workflowContext == null) {
				KaleoInstance kaleoInstance =
					kaleoInstanceToken.getKaleoInstance();

				workflowContext = WorkflowContextUtil.convert(
					kaleoInstance.getWorkflowContext());
			}

			workflowContext.put(
				WorkflowConstants.CONTEXT_TASK_COMMENTS, comment);
			workflowContext.put(
				WorkflowConstants.CONTEXT_TRANSITION_NAME, transitionName);

			ExecutionContext executionContext = new ExecutionContext(
				kaleoInstanceToken, kaleoTaskInstanceToken, workflowContext,
				serviceContext);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					try {
						_kaleoSignaler.signalExit(
							transitionName, executionContext,
							waitForCompletion);
					}
					catch (Exception exception) {
						throw new WorkflowException(
							"Unable to signal next transition", exception);
					}

					return null;
				});

			return workflowTask;
		}
		catch (WorkflowException workflowException) {
			throw workflowException;
		}
		catch (Exception exception) {
			throw new WorkflowException("Unable to complete task", exception);
		}
		finally {
			_lockManager.unlock(lock.getClassName(), lock.getKey());
		}
	}

	@Override
	public WorkflowTask fetchWorkflowTask(long workflowTaskId)
		throws WorkflowException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.fetchKaleoTaskInstanceToken(
				workflowTaskId);

		if (kaleoTaskInstanceToken == null) {
			return null;
		}

		try {
			return _kaleoWorkflowModelConverter.toWorkflowTask(
				kaleoTaskInstanceToken,
				WorkflowContextUtil.convert(
					kaleoTaskInstanceToken.getWorkflowContext()));
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public List<User> getAssignableUsers(long workflowTaskId)
		throws WorkflowException {

		return _getUsers(_ACTION_TYPE_ASSIGN, workflowTaskId);
	}

	@Override
	public List<String> getNextTransitionNames(long userId, long workflowTaskId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskId);

			if (kaleoTaskInstanceToken.isCompleted()) {
				return Collections.emptyList();
			}

			KaleoTask kaleoTask = kaleoTaskInstanceToken.getKaleoTask();

			KaleoNode kaleoNode = kaleoTask.getKaleoNode();

			return Stream.of(
				kaleoNode.getKaleoTransitions()
			).flatMap(
				List::parallelStream
			).map(
				KaleoTransition::getName
			).collect(
				Collectors.toList()
			);
		}
		catch (WorkflowException workflowException) {
			throw workflowException;
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public List<User> getNotifiableUsers(long workflowTaskId)
		throws WorkflowException {

		return _getUsers(_ACTION_TYPE_VIEW_NOTIFICATION, workflowTaskId);
	}

	@Override
	public WorkflowTask getWorkflowTask(long workflowTaskId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskId);

			return _kaleoWorkflowModelConverter.toWorkflowTask(
				kaleoTaskInstanceToken,
				WorkflowContextUtil.convert(
					kaleoTaskInstanceToken.getWorkflowContext()));
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
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
		catch (Exception exception) {
			throw new WorkflowException(exception);
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
		catch (Exception exception) {
			throw new WorkflowException(exception);
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
		catch (Exception exception) {
			throw new WorkflowException(exception);
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
		catch (Exception exception) {
			throw new WorkflowException(exception);
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
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public int getWorkflowTaskCountByUserRoles(
			long companyId, long userId, long workflowInstanceId,
			Boolean completed)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			return _kaleoTaskInstanceTokenLocalService.searchCount(
				workflowInstanceId, completed, Boolean.TRUE, serviceContext);
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
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
		catch (Exception exception) {
			throw new WorkflowException(exception);
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

			return _toWorkflowTasks(
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
					completed, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext));
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
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

			return _toWorkflowTasks(
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
					Role.class.getName(), roleId, completed, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext));
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
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

			return _toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
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

			return _toWorkflowTasks(
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
					User.class.getName(), userId, completed, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext));
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
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

			return _toWorkflowTasks(kaleoTaskInstanceTokens);
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
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

			return _toWorkflowTasks(
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
					workflowInstanceId, completed, start, end,
					KaleoTaskInstanceTokenOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext));
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public List<WorkflowTransition> getWorkflowTaskWorkflowTransitions(
			long workflowTaskId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskId);

			if (kaleoTaskInstanceToken.isCompleted()) {
				return Collections.emptyList();
			}

			KaleoTask kaleoTask = kaleoTaskInstanceToken.getKaleoTask();

			KaleoNode kaleoNode = kaleoTask.getKaleoNode();

			return Stream.of(
				kaleoNode.getKaleoTransitions()
			).flatMap(
				List::stream
			).map(
				kaleoTransition -> {
					DefaultWorkflowTransition defaultWorkflowTransition =
						new DefaultWorkflowTransition();

					defaultWorkflowTransition.setLabelMap(
						kaleoTransition.getLabelMap());
					defaultWorkflowTransition.setName(
						kaleoTransition.getName());
					defaultWorkflowTransition.setSourceNodeName(
						kaleoTransition.getSourceKaleoNodeName());
					defaultWorkflowTransition.setTargetNodeName(
						kaleoTransition.getTargetKaleoNodeName());

					return defaultWorkflowTransition;
				}
			).collect(
				Collectors.toList()
			);
		}
		catch (PortalException portalException) {
			throw new WorkflowException(portalException);
		}
	}

	@Override
	public boolean hasAssignableUsers(long workflowTaskId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskId);

			if (kaleoTaskInstanceToken.isCompleted()) {
				return false;
			}

			long assignedUserId = _getAssignedUserId(workflowTaskId);

			ExecutionContext executionContext = _createExecutionContext(
				kaleoTaskInstanceToken);

			List<KaleoTaskAssignment> configuredKaleoTaskAssignments =
				_kaleoTaskAssignmentLocalService.getKaleoTaskAssignments(
					kaleoTaskInstanceToken.getKaleoTaskId());

			for (KaleoTaskAssignment configuredKaleoTaskAssignment :
					configuredKaleoTaskAssignments) {

				Collection<KaleoTaskAssignment> calculatedKaleoTaskAssignments =
					_getKaleoTaskAssignments(
						configuredKaleoTaskAssignment, executionContext);

				for (KaleoTaskAssignment calculatedKaleoTaskAssignment :
						calculatedKaleoTaskAssignments) {

					if (_hasAssignableUsers(
							calculatedKaleoTaskAssignment,
							kaleoTaskInstanceToken, assignedUserId)) {

						return true;
					}
				}
			}

			return false;
		}
		catch (WorkflowException workflowException) {
			throw workflowException;
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public List<WorkflowTask> search(
			long companyId, long userId, String assetTitle, String[] taskNames,
			String[] assetTypes, Long[] assetPrimaryKeys,
			String assigneeClassName, Long[] assigneeIds, Date dueDateGT,
			Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
			Long workflowDefinitionId, Long[] workflowInstanceIds,
			Boolean andOperator, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			WorkflowModelSearchResult<WorkflowTask> workflowModelSearchResult =
				searchWorkflowTasks(
					companyId, userId, assetTitle, taskNames, assetTypes,
					assetPrimaryKeys, assigneeClassName, assigneeIds, dueDateGT,
					dueDateLT, completed, false, searchByUserRoles,
					workflowDefinitionId, workflowInstanceIds, andOperator,
					start, end, orderByComparator);

			return workflowModelSearchResult.getWorkflowModels();
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public int searchCount(
			long companyId, long userId, String assetTitle, String[] taskNames,
			String[] assetTypes, Long[] assetPrimaryKeys,
			String assigneeClassName, Long[] assigneeIds, Date dueDateGT,
			Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
			Long workflowDefinitionId, Long[] workflowInstanceIds,
			Boolean andOperator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			return _kaleoTaskInstanceTokenLocalService.searchCount(
				assetTitle, taskNames, assetTypes, assetPrimaryKeys,
				assigneeClassName, assigneeIds, dueDateGT, dueDateLT, completed,
				workflowDefinitionId, workflowInstanceIds, searchByUserRoles,
				andOperator, serviceContext);
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public WorkflowModelSearchResult<WorkflowTask> searchWorkflowTasks(
			long companyId, long userId, String assetTitle, String[] taskNames,
			String[] assetTypes, Long[] assetPrimaryKeys,
			String assigneeClassName, Long[] assigneeIds, Date dueDateGT,
			Date dueDateLT, Boolean completed,
			boolean searchByActiveWorkflowHandlers, Boolean searchByUserRoles,
			Long workflowDefinitionId, Long[] workflowInstanceIds,
			Boolean andOperator, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			BaseModelSearchResult<KaleoTaskInstanceToken>
				baseModelSearchResult =
					_kaleoTaskInstanceTokenLocalService.
						searchKaleoTaskInstanceTokens(
							assetTitle, taskNames, assetTypes, assetPrimaryKeys,
							assigneeClassName, assigneeIds, dueDateGT,
							dueDateLT, completed, workflowDefinitionId,
							workflowInstanceIds, searchByActiveWorkflowHandlers,
							searchByUserRoles, andOperator, start, end,
							KaleoTaskInstanceTokenOrderByComparator.
								getOrderByComparator(
									orderByComparator,
									_kaleoWorkflowModelConverter),
							serviceContext);

			return new WorkflowModelSearchResult<>(
				_toWorkflowTasks(baseModelSearchResult.getBaseModels()),
				baseModelSearchResult.getLength());
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public WorkflowTask updateDueDate(
			long companyId, long userId, long workflowTaskId, String comment,
			Date dueDate)
		throws WorkflowException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		return _taskManager.updateDueDate(
			workflowTaskId, comment, dueDate, serviceContext);
	}

	private ExecutionContext _createExecutionContext(
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			kaleoTaskInstanceToken.getKaleoInstanceToken();

		Map<String, Serializable> workflowContext = WorkflowContextUtil.convert(
			kaleoTaskInstanceToken.getWorkflowContext());

		ServiceContext workflowContextServiceContext =
			(ServiceContext)workflowContext.get(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT);

		return new ExecutionContext(
			kaleoInstanceToken, workflowContext, workflowContextServiceContext);
	}

	private long _getAssignedUserId(long kaleoTaskInstanceTokenId) {
		return Stream.of(
			_kaleoTaskAssignmentInstanceLocalService.
				getKaleoTaskAssignmentInstances(kaleoTaskInstanceTokenId)
		).flatMap(
			List::parallelStream
		).filter(
			kaleoTaskAssignmentInstance -> {
				String assigneeClassName =
					kaleoTaskAssignmentInstance.getAssigneeClassName();

				if (assigneeClassName.equals(User.class.getName())) {
					return true;
				}

				return false;
			}
		).map(
			KaleoTaskAssignmentInstance::getAssigneeClassPK
		).findFirst(
		).orElseGet(
			() -> 0L
		);
	}

	private Collection<KaleoTaskAssignment> _getKaleoTaskAssignments(
			KaleoTaskAssignment kaleoTaskAssignment,
			ExecutionContext executionContext)
		throws PortalException {

		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector =
			_kaleoTaskAssignmentSelectorRegistry.getKaleoTaskAssignmentSelector(
				kaleoTaskAssignment.getAssigneeClassName());

		return kaleoTaskAssignmentSelector.getKaleoTaskAssignments(
			kaleoTaskAssignment, executionContext);
	}

	private List<User> _getUsers(int actionType, long workflowTaskId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskId);

			if (kaleoTaskInstanceToken.isCompleted() &&
				(actionType == _ACTION_TYPE_ASSIGN)) {

				return Collections.emptyList();
			}

			Set<User> allowedUsers = new TreeSet<>(
				new UserScreenNameComparator(true));

			long assignedUserId = _getAssignedUserId(workflowTaskId);

			Collection<KaleoTaskAssignment> kaleoTaskAssignments =
				_aggregateKaleoTaskAssignmentSelector.getKaleoTaskAssignments(
					_kaleoTaskAssignmentLocalService.getKaleoTaskAssignments(
						kaleoTaskInstanceToken.getKaleoTaskId()),
					_createExecutionContext(kaleoTaskInstanceToken));

			for (KaleoTaskAssignment kaleoTaskAssignment :
					kaleoTaskAssignments) {

				_populateAllowedUsers(
					actionType, allowedUsers, assignedUserId,
					kaleoTaskAssignment, kaleoTaskInstanceToken);
			}

			return ListUtil.fromCollection(allowedUsers);
		}
		catch (WorkflowException workflowException) {
			throw workflowException;
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	private List<User> _getUsers(long groupId, long roleId)
		throws PortalException {

		List<User> users = new ArrayList<>();

		List<UserGroupRole> userGroupGroupRoles =
			_userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				groupId, roleId);

		for (UserGroupRole userGroupRole : userGroupGroupRoles) {
			users.add(userGroupRole.getUser());
		}

		return users;
	}

	private boolean _hasAssignableUsers(
			KaleoTaskAssignment kaleoTaskAssignment,
			KaleoTaskInstanceToken kaleoTaskInstanceToken, long assignedUserId)
		throws PortalException {

		String assigneeClassName = kaleoTaskAssignment.getAssigneeClassName();
		long assigneeClassPK = kaleoTaskAssignment.getAssigneeClassPK();

		if (assigneeClassName.equals(User.class.getName())) {
			if (assignedUserId == assigneeClassPK) {
				return false;
			}

			User user = _userLocalService.fetchUser(assigneeClassPK);

			if ((user != null) && user.isActive()) {
				return true;
			}

			return false;
		}

		Role role = _roleLocalService.getRole(assigneeClassPK);

		if ((role.getType() == RoleConstants.TYPE_DEPOT) ||
			(role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
			(role.getType() == RoleConstants.TYPE_SITE)) {

			if (Objects.equals(
					role.getName(), DepotRolesConstants.ASSET_LIBRARY_MEMBER) ||
				Objects.equals(role.getName(), RoleConstants.SITE_MEMBER)) {

				List<User> users = _userLocalService.getGroupUsers(
					kaleoTaskInstanceToken.getGroupId(),
					WorkflowConstants.STATUS_APPROVED, null);

				for (User user : users) {
					if (user.getUserId() != assignedUserId) {
						return true;
					}
				}
			}

			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
					kaleoTaskInstanceToken.getGroupId(), assigneeClassPK);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				User user = userGroupRole.getUser();

				if (user.isActive() && (user.getUserId() != assignedUserId)) {
					return true;
				}
			}

			List<UserGroupGroupRole> userGroupGroupRoles =
				_userGroupGroupRoleLocalService.
					getUserGroupGroupRolesByGroupAndRole(
						kaleoTaskInstanceToken.getGroupId(), assigneeClassPK);

			for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
				List<User> userGroupUsers = _userLocalService.getUserGroupUsers(
					userGroupGroupRole.getUserGroupId());

				for (User user : userGroupUsers) {
					if (user.isActive() &&
						(user.getUserId() != assignedUserId)) {

						return true;
					}
				}
			}
		}
		else {
			List<User> inheritedRoleUsers =
				_userLocalService.getInheritedRoleUsers(
					assigneeClassPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

			for (User user : inheritedRoleUsers) {
				if (user.isActive() && (user.getUserId() != assignedUserId)) {
					return true;
				}
			}
		}

		return false;
	}

	private void _populateAllowedUsers(
			int actionType, Set<User> allowedUsers, long assignedUserId,
			KaleoTaskAssignment kaleoTaskAssignment,
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		if (Objects.equals(
				kaleoTaskAssignment.getAssigneeClassName(),
				User.class.getName())) {

			User user = null;

			if (actionType == _ACTION_TYPE_ASSIGN) {
				if (assignedUserId ==
						kaleoTaskAssignment.getAssigneeClassPK()) {

					return;
				}

				user = _userLocalService.fetchUser(
					kaleoTaskAssignment.getAssigneeClassPK());
			}
			else {
				List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
					_kaleoTaskAssignmentInstanceLocalService.
						getKaleoTaskAssignmentInstances(
							kaleoTaskInstanceToken.
								getKaleoTaskInstanceTokenId());

				KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
					kaleoTaskAssignmentInstances.get(0);

				user = _userLocalService.fetchUser(
					kaleoTaskAssignmentInstance.getAssigneeClassPK());
			}

			if ((user != null) && user.isActive()) {
				allowedUsers.add(user);
			}

			return;
		}

		Role role = _roleLocalService.getRole(
			kaleoTaskAssignment.getAssigneeClassPK());

		if ((role.getType() == RoleConstants.TYPE_DEPOT) ||
			(role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
			(role.getType() == RoleConstants.TYPE_SITE)) {

			if (Objects.equals(
					role.getName(), DepotRolesConstants.ASSET_LIBRARY_MEMBER) ||
				Objects.equals(role.getName(), RoleConstants.SITE_MEMBER)) {

				List<User> groupUsers = _userLocalService.getGroupUsers(
					kaleoTaskInstanceToken.getGroupId(),
					WorkflowConstants.STATUS_APPROVED, null);

				if (actionType == _ACTION_TYPE_ASSIGN) {
					groupUsers = ListUtil.filter(
						groupUsers, user -> user.getUserId() != assignedUserId);
				}

				allowedUsers.addAll(groupUsers);

				return;
			}

			List<User> users = _getUsers(
				kaleoTaskInstanceToken.getGroupId(),
				kaleoTaskAssignment.getAssigneeClassPK());

			Group group = _groupLocalService.getGroup(
				kaleoTaskInstanceToken.getGroupId());

			if (group.isOrganization()) {
				Organization organization =
					_organizationLocalService.getOrganization(
						group.getOrganizationId());

				for (Organization ancestorOrganization :
						organization.getAncestors()) {

					users.addAll(
						_getUsers(
							ancestorOrganization.getGroupId(),
							kaleoTaskAssignment.getAssigneeClassPK()));
				}
			}

			if (actionType == _ACTION_TYPE_ASSIGN) {
				users = ListUtil.filter(
					users,
					user ->
						(user != null) && user.isActive() &&
						(user.getUserId() != assignedUserId));
			}
			else {
				users = ListUtil.filter(
					users, user -> (user != null) && user.isActive());
			}

			allowedUsers.addAll(users);

			List<User> userGroupGroupRolesUsers = Stream.of(
				_userGroupGroupRoleLocalService.
					getUserGroupGroupRolesByGroupAndRole(
						kaleoTaskInstanceToken.getGroupId(),
						kaleoTaskAssignment.getAssigneeClassPK())
			).flatMap(
				List::parallelStream
			).map(
				userGroupGroupRole -> _userLocalService.getUserGroupUsers(
					userGroupGroupRole.getUserGroupId())
			).flatMap(
				List::parallelStream
			).collect(
				Collectors.toList()
			);

			if (actionType == _ACTION_TYPE_ASSIGN) {
				ListUtil.filter(
					userGroupGroupRolesUsers,
					user ->
						user.isActive() &&
						(user.getUserId() != assignedUserId));
			}
			else {
				ListUtil.filter(userGroupGroupRolesUsers, User::isActive);
			}

			allowedUsers.addAll(userGroupGroupRolesUsers);
		}
		else {
			List<User> inheritedRoleUsers =
				_userLocalService.getInheritedRoleUsers(
					kaleoTaskAssignment.getAssigneeClassPK(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			if (actionType == _ACTION_TYPE_ASSIGN) {
				inheritedRoleUsers = ListUtil.filter(
					inheritedRoleUsers,
					user ->
						user.isActive() &&
						(user.getUserId() != assignedUserId));
			}
			else {
				inheritedRoleUsers = ListUtil.filter(
					inheritedRoleUsers, User::isActive);
			}

			allowedUsers.addAll(inheritedRoleUsers);
		}
	}

	private List<WorkflowTask> _toWorkflowTasks(
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

	private static final int _ACTION_TYPE_ASSIGN = 1;

	private static final int _ACTION_TYPE_VIEW_NOTIFICATION = 2;

	@Reference
	private AggregateKaleoTaskAssignmentSelector
		_aggregateKaleoTaskAssignmentSelector;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private KaleoSignaler _kaleoSignaler;

	@Reference
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Reference
	private KaleoTaskAssignmentLocalService _kaleoTaskAssignmentLocalService;

	@Reference
	private KaleoTaskAssignmentSelectorRegistry
		_kaleoTaskAssignmentSelectorRegistry;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	@Reference
	private LockManager _lockManager;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private TaskManager _taskManager;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}