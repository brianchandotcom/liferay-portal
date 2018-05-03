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

package com.liferay.portal.workflow.rest.internal.helper;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.rest.internal.model.WorkflowActivityModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowAssetModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowAssigneeModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowTaskModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowTaskTransitionOperationModel;
import com.liferay.portal.workflow.rest.internal.model.WorkflowUserModel;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = WorkflowHelper.class)
public class WorkflowHelper {

	public WorkflowTask assignWorkflowTask(
			long companyId, long userId, long workflowTaskId)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		return _workflowTaskManager.assignWorkflowTaskToUser(
			companyId, userId, workflowTaskId, userId, null, null,
			workflowContext);
	}

	public WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskId,
			WorkflowTaskTransitionOperationModel
				workflowTaskTransitionOperationModel)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		return _workflowTaskManager.completeWorkflowTask(
			companyId, userId, workflowTaskId,
			workflowTaskTransitionOperationModel.getTransition(),
			workflowTaskTransitionOperationModel.getComment(), workflowContext);
	}

	public String getAssetType(
			long companyId, long workflowTaskId, Locale locale)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		String className = (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);

		String modelResource = _resourceActions.getModelResource(
			locale, className);

		return LanguageUtil.get(locale, modelResource);
	}

	public List<WorkflowActivityModel> getWorkflowActivityModels(
			long companyId, long userId, long workflowTaskId, Locale locale)
		throws PortalException {

		List<WorkflowLog> workflowLogs = getWorkflowLogs(
			companyId, workflowTaskId, true);

		String assetType = getAssetType(companyId, workflowTaskId, locale);

		List<WorkflowActivityModel> workflowActivityModels =
			getWorkflowActivityModels(
				companyId, workflowLogs, assetType, locale);

		Collections.reverse(workflowActivityModels);

		return workflowActivityModels;
	}

	public List<WorkflowLog> getWorkflowLogs(
			long companyId, long workflowTaskId)
		throws PortalException {

		return getWorkflowLogs(companyId, workflowTaskId, false);
	}

	public List<WorkflowLog> getWorkflowLogs(
			long companyId, long workflowTaskId, boolean oldestFirst)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(
			companyId, workflowTaskId);

		List<Integer> logTypes = new ArrayList<>();

		logTypes.add(WorkflowLog.TASK_ASSIGN);
		logTypes.add(WorkflowLog.TASK_COMPLETION);
		logTypes.add(WorkflowLog.TASK_UPDATE);
		logTypes.add(WorkflowLog.TRANSITION);

		return _workflowLogManager.getWorkflowLogsByWorkflowInstance(
			companyId, workflowInstance.getWorkflowInstanceId(), logTypes,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			_workflowComparatorFactory.getLogCreateDateComparator(oldestFirst));
	}

	public WorkflowTaskModel getWorkflowTaskModel(
			long companyId, long userId, long workflowTaskId, Locale locale)
		throws PortalException {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		WorkflowAssigneeModel workflowAssigneeModel = getWorkflowAssigneeModel(
			workflowTask);

		WorkflowAssetModel workflowAssetModel = getWorkflowAssetModel(
			companyId, workflowTaskId, locale);

		List<WorkflowLog> workflowLogs = getWorkflowLogs(
			companyId, workflowTaskId);

		long lastActivityTime = 0;

		if (!workflowLogs.isEmpty()) {
			WorkflowLog workflowLog = workflowLogs.get(0);

			Date createDate = workflowLog.getCreateDate();

			lastActivityTime = createDate.getTime();
		}

		String state = getState(companyId, workflowTaskId, locale);
		List<String> transitions = _workflowTaskManager.getNextTransitionNames(
			companyId, userId, workflowTaskId);

		return new WorkflowTaskModel(
			workflowTask, workflowAssigneeModel, workflowAssetModel,
			lastActivityTime, state, transitions);
	}

	protected String getAssignmentDetails(WorkflowLog workflowLog) {
		return null;
	}

	protected String getAssignmentMessage(
			WorkflowLog workflowLog, Locale locale)
		throws PortalException {

		String message = "";

		String auditUserName = getAuditUserName(workflowLog);

		if (workflowLog.getUserId() != 0) {
			User user = _userLocalService.getUser(workflowLog.getUserId());

			if (workflowLog.getAuditUserId() == workflowLog.getUserId()) {
				if (user.isMale()) {
					message = LanguageUtil.format(
						locale, "x-assigned-the-task-to-himself",
						quote(auditUserName));
				}
				else {
					message = LanguageUtil.format(
						locale, "x-assigned-the-task-to-herself",
						quote(auditUserName));
				}
			}
		}
		else if (workflowLog.getRoleId() != 0) {
			Role role = _roleLocalService.getRole(workflowLog.getRoleId());

			message = LanguageUtil.format(
				locale, "x-assigned-the-task-to-the-x-role",
				new String[] {quote(auditUserName), role.getDescriptiveName()});
		}

		return message;
	}

	protected String getAuditUserName(WorkflowLog workflowLog)
		throws PortalException {

		User user = _userLocalService.getUser(workflowLog.getAuditUserId());

		return user.getFullName();
	}

	protected String getCompletionDetails(WorkflowLog workflowLog) {
		return workflowLog.getState();
	}

	protected String getCompletionMessage(
			long companyId, WorkflowLog workflowLog, Locale locale)
		throws PortalException {

		String userName = getAuditUserName(workflowLog);
		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowLog.getWorkflowTaskId());

		return LanguageUtil.format(
			locale, "x-completed-the-task-x",
			new String[] {userName, workflowTask.getName()});
	}

	protected String getState(
			long companyId, long workflowTaskId, Locale locale)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		long groupId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
		String className = GetterUtil.getString(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));
		long classPK = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		String state = _workflowInstanceLinkLocalService.getState(
			companyId, groupId, className, classPK);

		return LanguageUtil.get(locale, state);
	}

	protected String getSubmissionForPublicationDetails(
			WorkflowLog workflowLog, Locale locale)
		throws PortalException {

		if (workflowLog.getUserId() != 0) {
			User user = _userLocalService.getUser(workflowLog.getUserId());

			return LanguageUtil.format(
				locale, "task-initially-assigned-to-x", user.getFullName());
		}
		else {
			Role role = _roleLocalService.getRole(workflowLog.getRoleId());

			return LanguageUtil.format(
				locale, "task-initially-assigned-to-the-x-role",
				role.getDescriptiveName());
		}
	}

	protected String getSubmissionForPublicationMessage(
			WorkflowLog workflowLog, String assetType, Locale locale)
		throws PortalException {

		String userName = getAuditUserName(workflowLog);

		return LanguageUtil.format(
			locale, "x-submitted-a-x-for-publication",
			new String[] {quote(userName), assetType});
	}

	protected String getUpdateDetails(WorkflowLog workflowLog) {
		return null;
	}

	protected String getUpdateMessage(WorkflowLog workflowLog, Locale locale)
		throws PortalException {

		String userName = getAuditUserName(workflowLog);

		return LanguageUtil.format(
			locale, "x-updated-the-task", quote(userName));
	}

	protected WorkflowActivityModel getWorkflowActivityModel(
			long companyId, WorkflowLog workflowLog, int index,
			String assetType, Locale locale)
		throws PortalException {

		Date createDate = workflowLog.getCreateDate();

		String message = "";
		String details = "";

		if (index == 0) {
			message = getSubmissionForPublicationMessage(
				workflowLog, assetType, locale);
			details = getSubmissionForPublicationDetails(workflowLog, locale);
		}
		else if (workflowLog.getType() == WorkflowLog.TASK_ASSIGN) {
			message = getAssignmentMessage(workflowLog, locale);
			details = getAssignmentDetails(workflowLog);
		}
		else if (workflowLog.getType() == WorkflowLog.TASK_COMPLETION) {
			message = getCompletionMessage(companyId, workflowLog, locale);
			details = getCompletionDetails(workflowLog);
		}
		else if (workflowLog.getType() == WorkflowLog.TASK_UPDATE) {
			message = getUpdateMessage(workflowLog, locale);
			details = getUpdateDetails(workflowLog);
		}

		return new WorkflowActivityModel(
			createDate.getTime(), message, details);
	}

	protected List<WorkflowActivityModel> getWorkflowActivityModels(
			long companyId, List<WorkflowLog> workflowLogs, String assetType,
			Locale locale)
		throws PortalException {

		List<WorkflowActivityModel> workflowActivityModels = new ArrayList<>();

		for (int i = 0; i < workflowLogs.size(); i++) {
			WorkflowLog workflowLog = workflowLogs.get(i);

			WorkflowActivityModel workflowActivityModel =
				getWorkflowActivityModel(
					companyId, workflowLog, i, assetType, locale);

			workflowActivityModels.add(workflowActivityModel);
		}

		return workflowActivityModels;
	}

	protected WorkflowAssetModel getWorkflowAssetModel(
			long companyId, long workflowTaskId, Locale locale)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			companyId, workflowTaskId);

		String className = GetterUtil.getString(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		AssetRendererFactory<?> assetRendererFactory =
			workflowHandler.getAssetRendererFactory();

		long classPK = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			className, classPK);

		String assetType = getAssetType(companyId, workflowTaskId, locale);

		WorkflowUserModel workflowUserModel = getWorkflowUserModel(
			assetEntry.getUserId());

		return new WorkflowAssetModel(
			assetEntry, assetType, workflowUserModel, locale);
	}

	protected WorkflowAssigneeModel getWorkflowAssigneeModel(
			WorkflowTask workflowTask)
		throws PortalException {

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			workflowTask.getWorkflowTaskAssignees();

		for (WorkflowTaskAssignee workflowTaskAssignee :
				workflowTaskAssignees) {

			String assigneeClassName =
				workflowTaskAssignee.getAssigneeClassName();

			if (assigneeClassName.equals(User.class.getName())) {
				User user = _userLocalService.fetchUser(
					workflowTaskAssignee.getAssigneeClassPK());

				return new WorkflowAssigneeModel(user);
			}
			else if (assigneeClassName.equals(Role.class.getName())) {
				Role role = _roleLocalService.fetchRole(
					workflowTaskAssignee.getAssigneeClassPK());

				return new WorkflowAssigneeModel(role);
			}
		}

		return null;
	}

	protected Map<String, Serializable> getWorkflowContext(
			long companyId, long workflowTaskId)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(
			companyId, workflowTaskId);

		return workflowInstance.getWorkflowContext();
	}

	protected WorkflowInstance getWorkflowInstance(
			long companyId, long workflowTaskId)
		throws PortalException {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		return WorkflowInstanceManagerUtil.getWorkflowInstance(
			companyId, workflowTask.getWorkflowInstanceId());
	}

	protected WorkflowUserModel getWorkflowUserModel(long userId)
		throws PortalException {

		User user = _userLocalService.fetchUser(userId);

		if (user != null) {
			return new WorkflowUserModel(user);
		}

		return null;
	}

	protected String quote(String userName) {
		return StringUtil.quote(userName, "%~{}~%");
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowLogManager _workflowLogManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}