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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
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
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "proxy.bean=false",
	service = WorkflowTaskManager.class
)
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
		throws WorkflowException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker.getUserId() != userId) {
			ReflectionUtil.throwException(new PrincipalException());
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
		throws WorkflowException {

		Lock lock = null;

		try {
			lock = lockManager.lock(
				userId, WorkflowTask.class.getName(), workflowTaskId,
				String.valueOf(userId), false, 1000);
		}
		catch (PortalException pe) {
			if (pe instanceof DuplicateLockException) {
				throw new WorkflowException(
					StringBundler.concat(
						"Workflow task ", workflowTaskId, " is locked by user ",
						userId),
					pe);
			}

			throw new WorkflowException(
				"Unable to lock workflow task " + workflowTaskId, pe);
		}

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			WorkflowTask workflowTask = _taskManager.completeWorkflowTask(
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
	public WorkflowTask fetchWorkflowTask(long companyId, long workflowTaskId)
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
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<String> getNextTransitionNames(
			long companyId, long userId, long workflowTaskId)
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
	public List<User> getPooledActors(long companyId, long workflowTaskId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskId);

			List<KaleoTaskAssignment> calculatedKaleoTaskAssignments =
				getCalculatedKaleoTaskAssignments(kaleoTaskInstanceToken);

			Set<User> users = new TreeSet<>(new UserFirstNameComparator(true));

			for (KaleoTaskAssignment calculatedKaleoTaskAssignment :
					calculatedKaleoTaskAssignments) {

				populateUsers(
					calculatedKaleoTaskAssignment, kaleoTaskInstanceToken,
					users);
			}

			return ListUtil.fromCollection(users);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getPooledActors(long, long)}
	 */
	@Deprecated
	@Override
	public long[] getPooledActorsIds(long companyId, long workflowTaskId)
		throws WorkflowException {

		List<User> users = getPooledActors(companyId, workflowTaskId);

		Stream<User> stream = users.stream();

		return stream.mapToLong(
			User::getUserId
		).toArray();
	}

	@Override
	public WorkflowTask getWorkflowTask(long companyId, long workflowTaskId)
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
	public boolean hasOtherAssignees(long workflowTaskId, long userId)
		throws WorkflowException {

		try {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					workflowTaskId);

			ExecutionContext executionContext = createExecutionContext(
				kaleoTaskInstanceToken);

			List<KaleoTaskAssignment> configuredKaleoTaskAssignments =
				_kaleoTaskAssignmentLocalService.getKaleoTaskAssignments(
					kaleoTaskInstanceToken.getKaleoTaskId());

			for (KaleoTaskAssignment configuredKaleoTaskAssignment :
					configuredKaleoTaskAssignments) {

				Collection<KaleoTaskAssignment> calculatedKaleoTaskAssignments =
					getKaleoTaskAssignments(
						configuredKaleoTaskAssignment, executionContext);

				for (KaleoTaskAssignment calculatedKaleoTaskAssignment :
						calculatedKaleoTaskAssignments) {

					if (hasOtherPooledActors(
							calculatedKaleoTaskAssignment,
							kaleoTaskInstanceToken, userId)) {

						return true;
					}
				}
			}

			return false;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(long,
	 *             long, String, String, String[], Long[], Date, Date, Boolean,
	 *             Boolean, Boolean, int, int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<WorkflowTask> search(
			long companyId, long userId, String keywords, Boolean completed,
			Boolean searchByUserRoles, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return search(
			companyId, userId, null, keywords, getAssetTypes(keywords), null,
			null, null, completed, searchByUserRoles, false, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(long,
	 *             long, String, String, String[], Long[], Date, Date, Boolean,
	 *             Boolean, Boolean, int, int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<WorkflowTask> search(
			long companyId, long userId, String taskName, String assetType,
			Long[] assetPrimaryKeys, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator,
			int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return search(
			companyId, userId, null, taskName, getAssetTypes(assetType),
			assetPrimaryKeys, dueDateGT, dueDateLT, completed,
			searchByUserRoles, andOperator, start, end, orderByComparator);
	}

	@Override
	public List<WorkflowTask> search(
			long companyId, long userId, String assetTitle, String taskName,
			String[] assetTypes, Long[] assetPrimaryKeys, Date dueDateGT,
			Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
			Boolean andOperator, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.search(
					assetTitle, taskName, assetTypes, assetPrimaryKeys,
					dueDateGT, dueDateLT, completed, searchByUserRoles,
					andOperator, start, end,
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(long,
	 *             long, String, String, String[], Long[], Date, Date, Boolean,
	 *             Boolean, Boolean, int, int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<WorkflowTask> search(
			long companyId, long userId, String keywords, String[] assetTypes,
			Boolean completed, Boolean searchByUserRoles, int start, int end,
			OrderByComparator<WorkflowTask> orderByComparator)
		throws WorkflowException {

		return search(
			companyId, userId, keywords, keywords, assetTypes, null, null, null,
			completed, searchByUserRoles, false, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #searchCount(long, long, String, String, String[], Long[],
	 *             Date, Date, Boolean, Boolean, Boolean)}
	 */
	@Deprecated
	@Override
	public int searchCount(
			long companyId, long userId, String keywords, Boolean completed,
			Boolean searchByUserRoles)
		throws WorkflowException {

		return searchCount(
			companyId, userId, null, keywords, getAssetTypes(keywords), null,
			null, null, completed, searchByUserRoles, false);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #searchCount(long, long, String, String, String[], Long[],
	 *             Date, Date, Boolean, Boolean, Boolean)}
	 */
	@Deprecated
	@Override
	public int searchCount(
			long companyId, long userId, String taskName, String assetType,
			Long[] assetPrimaryKeys, Date dueDateGT, Date dueDateLT,
			Boolean completed, Boolean searchByUserRoles, boolean andOperator)
		throws WorkflowException {

		return searchCount(
			companyId, userId, null, taskName, getAssetTypes(assetType),
			assetPrimaryKeys, dueDateGT, dueDateLT, completed,
			searchByUserRoles, andOperator);
	}

	@Override
	public int searchCount(
			long companyId, long userId, String assetTitle, String taskName,
			String[] assetTypes, Long[] assetPrimaryKeys, Date dueDateGT,
			Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
			Boolean andOperator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			return _kaleoTaskInstanceTokenLocalService.searchCount(
				null, assetTitle, taskName, assetTypes, assetPrimaryKeys,
				dueDateGT, dueDateLT, completed, searchByUserRoles, andOperator,
				serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #searchCount(long, long, String, String, String[], Long[],
	 *             Date, Date, Boolean, Boolean, Boolean)}
	 */
	@Deprecated
	@Override
	public int searchCount(
			long companyId, long userId, String keywords, String[] assetTypes,
			Boolean completed, Boolean searchByUserRoles)
		throws WorkflowException {

		return searchCount(
			companyId, userId, keywords, keywords, assetTypes, null, null, null,
			completed, searchByUserRoles, false);
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

	protected ExecutionContext createExecutionContext(
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

	protected String[] getAssetTypes(String assetType) {
		if (Validator.isNull(assetType)) {
			return null;
		}

		return new String[] {assetType};
	}

	protected List<KaleoTaskAssignment> getCalculatedKaleoTaskAssignments(
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		List<KaleoTaskAssignment> calculatedKaleoTaskAssignments =
			new ArrayList<>();

		ExecutionContext executionContext = createExecutionContext(
			kaleoTaskInstanceToken);

		List<KaleoTaskAssignment> configuredKaleoTaskAssignments =
			_kaleoTaskAssignmentLocalService.getKaleoTaskAssignments(
				kaleoTaskInstanceToken.getKaleoTaskId());

		for (KaleoTaskAssignment configuredKaleoTaskAssignment :
				configuredKaleoTaskAssignments) {

			calculatedKaleoTaskAssignments.addAll(
				getKaleoTaskAssignments(
					configuredKaleoTaskAssignment, executionContext));
		}

		return calculatedKaleoTaskAssignments;
	}

	protected Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
			KaleoTaskAssignment kaleoTaskAssignment,
			ExecutionContext executionContext)
		throws PortalException {

		TaskAssignmentSelector taskAssignmentSelector =
			_taskAssignmentSelectorRegistry.getTaskAssignmentSelector(
				kaleoTaskAssignment.getAssigneeClassName());

		return taskAssignmentSelector.calculateTaskAssignments(
			kaleoTaskAssignment, executionContext);
	}

	protected boolean hasOtherPooledActors(
			KaleoTaskAssignment kaleoTaskAssignment,
			KaleoTaskInstanceToken kaleoTaskInstanceToken, long userId)
		throws PortalException {

		String assigneeClassName = kaleoTaskAssignment.getAssigneeClassName();
		long assigneeClassPK = kaleoTaskAssignment.getAssigneeClassPK();

		if (assigneeClassName.equals(User.class.getName())) {
			if (userId == assigneeClassPK) {
				return false;
			}

			User user = _userLocalService.fetchUser(assigneeClassPK);

			if ((user != null) && user.isActive()) {
				return true;
			}

			return false;
		}

		Role role = _roleLocalService.getRole(assigneeClassPK);

		if ((role.getType() == RoleConstants.TYPE_SITE) ||
			(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

			if (Objects.equals(role.getName(), RoleConstants.SITE_MEMBER)) {
				List<User> users = _userLocalService.getGroupUsers(
					kaleoTaskInstanceToken.getGroupId(),
					WorkflowConstants.STATUS_APPROVED, null);

				for (User user : users) {
					if (user.getUserId() != userId) {
						return true;
					}
				}
			}

			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
					kaleoTaskInstanceToken.getGroupId(), assigneeClassPK);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				User user = userGroupRole.getUser();

				if ((user != null) && user.isActive() &&
					(user.getUserId() != userId)) {

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
					if (user.isActive() && (user.getUserId() != userId)) {
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
				if (user.isActive() && (user.getUserId() != userId)) {
					return true;
				}
			}
		}

		return false;
	}

	protected void populateUsers(
			KaleoTaskAssignment kaleoTaskAssignment,
			KaleoTaskInstanceToken kaleoTaskInstanceToken, Set<User> users)
		throws PortalException {

		String assigneeClassName = kaleoTaskAssignment.getAssigneeClassName();
		long assigneeClassPK = kaleoTaskAssignment.getAssigneeClassPK();

		if (assigneeClassName.equals(User.class.getName())) {
			User user = _userLocalService.fetchUser(assigneeClassPK);

			if ((user != null) && user.isActive()) {
				users.add(user);
			}

			return;
		}

		Role role = _roleLocalService.getRole(assigneeClassPK);

		if ((role.getType() == RoleConstants.TYPE_SITE) ||
			(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

			if (Objects.equals(role.getName(), RoleConstants.SITE_MEMBER)) {
				users.addAll(
					_userLocalService.getGroupUsers(
						kaleoTaskInstanceToken.getGroupId(),
						WorkflowConstants.STATUS_APPROVED, null));

				return;
			}

			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
					kaleoTaskInstanceToken.getGroupId(), assigneeClassPK);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				User user = userGroupRole.getUser();

				if (user.isActive()) {
					users.add(user);
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
					if (user.isActive()) {
						users.add(user);
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
				if (user.isActive()) {
					users.add(user);
				}
			}
		}
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