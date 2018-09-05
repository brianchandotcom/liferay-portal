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

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class WorkflowInstanceEditDisplayContext
	extends BaseWorkflowInstanceDisplayContext {

	public WorkflowInstanceEditDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public AssetEntry getAssetEntry() throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer();

		if (assetRenderer == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getAssetEntry(
			assetRendererFactory.getClassName(), assetRenderer.getClassPK());
	}

	public String getAssetEntryVersionId() {
		long classPK = getWorkflowContextEntryClassPK();

		return String.valueOf(classPK);
	}

	public String getAssetName() throws PortalException {
		return getWorkflowDefinitionName();
	}

	public AssetRenderer<?> getAssetRenderer() throws PortalException {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();

		return workflowHandler.getAssetRenderer(
			getWorkflowContextEntryClassPK());
	}

	public AssetRendererFactory<?> getAssetRendererFactory() {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();

		return workflowHandler.getAssetRendererFactory();
	}

	public String getAssignedTheTaskMessageKey(WorkflowLog workflowLog)
		throws PortalException {

		User user = getUser(workflowLog.getUserId());

		if (user.isMale()) {
			return "x-assigned-the-task-to-himself";
		}

		return "x-assigned-the-task-to-herself";
	}

	public Object getAssignedTheTaskToMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String userName = PortalUtil.getUserName(
			workflowLog.getAuditUserId(), StringPool.BLANK);
		String actorName = getActorName(workflowLog);

		return new Object[] {
			HtmlUtil.escape(userName), HtmlUtil.escape(actorName)
		};
	}

	public String getHeaderTitle() throws PortalException {
		return LanguageUtil.get(
			workflowInstanceRequestHelper.getRequest(),
			getWorkflowDefinitionName());
	}

	public String getIconCssClass() {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();

		return workflowHandler.getIconCssClass();
	}

	public String getPanelTitle() {
		String modelResource = ResourceActionsUtil.getModelResource(
			workflowInstanceRequestHelper.getLocale(),
			getWorkflowContextEntryClassName());

		return LanguageUtil.format(
			workflowInstanceRequestHelper.getRequest(), "preview-of-x",
			modelResource, false);
	}

	public Object getPreviousAssigneeMessageArguments(WorkflowLog workflowLog) {
		return HtmlUtil.escape(
			PortalUtil.getUserName(
				workflowLog.getPreviousUserId(), StringPool.BLANK));
	}

	public String getTaskCompleted(WorkflowTask workflowTask) {
		if (workflowTask.isCompleted()) {
			return LanguageUtil.get(
				workflowInstanceRequestHelper.getRequest(), "yes");
		}

		return LanguageUtil.get(
			workflowInstanceRequestHelper.getRequest(), "no");
	}

	public Object getTaskCompletionMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);

		return new Object[] {
			HtmlUtil.escape(actorName), HtmlUtil.escape(workflowLog.getState())
		};
	}

	public String getTaskContentTitleMessage() {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler();

		long classPK = getWorkflowContextEntryClassPK();

		return HtmlUtil.escape(
			workflowHandler.getTitle(
				classPK, workflowInstanceRequestHelper.getLocale()));
	}

	public String getTaskDueDate(WorkflowTask workflowTask) {
		if (workflowTask.getDueDate() == null) {
			return LanguageUtil.get(
				workflowInstanceRequestHelper.getRequest(), "never");
		}

		return dateFormatDateTime.format(workflowTask.getDueDate());
	}

	public Object getTaskInitiallyAssignedMessageArguments(
			WorkflowLog workflowLog)
		throws PortalException {

		return HtmlUtil.escape(getActorName(workflowLog));
	}

	public String getTaskName(WorkflowTask workflowTask) {
		return HtmlUtil.escape(workflowTask.getName());
	}

	public Object getTaskUpdateMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		return HtmlUtil.escape(getActorName(workflowLog));
	}

	public Object getTransitionMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);

		return new Object[] {
			HtmlUtil.escape(actorName),
			HtmlUtil.escape(workflowLog.getPreviousState()),
			HtmlUtil.escape(workflowLog.getState())
		};
	}

	public String getUserFullName(WorkflowLog workflowLog)
		throws PortalException {

		User user = getUser(workflowLog.getUserId());

		return HtmlUtil.escape(user.getFullName());
	}

	public String getWorkflowInstanceEndDate() {
		WorkflowInstance workflowInstance = getWorkflowInstance();

		if (workflowInstance.getEndDate() == null) {
			return LanguageUtil.get(
				workflowInstanceRequestHelper.getRequest(), "never");
		}

		return dateFormatDateTime.format(workflowInstance.getEndDate());
	}

	public String getWorkflowInstanceState() {
		WorkflowInstance workflowInstance = getWorkflowInstance();

		return LanguageUtil.get(
			workflowInstanceRequestHelper.getRequest(),
			workflowInstance.getState());
	}

	public String getWorkflowLogComment(WorkflowLog workflowLog) {
		return HtmlUtil.escape(workflowLog.getComment());
	}

	public String getWorkflowLogCreateDate(WorkflowLog workflowLog) {
		return dateFormatDateTime.format(workflowLog.getCreateDate());
	}

	public List<WorkflowLog> getWorkflowLogs() throws WorkflowException {
		if (_workflowLogs == null) {
			OrderByComparator<WorkflowLog> orderByComparator =
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator(false);

			_workflowLogs =
				WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
					workflowInstanceRequestHelper.getCompanyId(),
					getWorkflowInstanceId(), _logTypes, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator);
		}

		return _workflowLogs;
	}

	public List<WorkflowTask> getWorkflowTasks() throws PortalException {
		if (workflowTasks == null) {
			workflowTasks =
				WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(
					workflowInstanceRequestHelper.getCompanyId(), null,
					getWorkflowInstanceId(), null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);
		}

		return workflowTasks;
	}

	public boolean isAuditUser(WorkflowLog workflowLog) {
		if (workflowLog.getUserId() == 0) {
			return false;
		}

		if (workflowLog.getAuditUserId() == workflowLog.getUserId()) {
			return true;
		}

		return false;
	}

	public boolean isWorkflowTasksEmpty() throws PortalException {
		return getWorkflowTasks().isEmpty();
	}

	protected String getActorName(WorkflowLog workflowLog)
		throws PortalException {

		if (workflowLog.getRoleId() != 0) {
			Role role = getRole(workflowLog.getRoleId());

			return role.getDescriptiveName();
		}
		else if (workflowLog.getUserId() != 0) {
			User user = getUser(workflowLog.getUserId());

			return user.getFullName();
		}

		return StringPool.BLANK;
	}

	protected Role getRole(long roleId) throws PortalException {
		Role role = _roles.get(roleId);

		if (role == null) {
			role = RoleLocalServiceUtil.getRole(roleId);

			_roles.put(roleId, role);
		}

		return role;
	}

	protected User getUser(long userId) throws PortalException {
		User user = _users.get(userId);

		if (user == null) {
			user = UserLocalServiceUtil.getUser(userId);

			_users.put(userId, user);
		}

		return user;
	}

	protected Map<String, Serializable> getWorkflowContext() {
		WorkflowInstance workflowInstance = getWorkflowInstance();

		return workflowInstance.getWorkflowContext();
	}

	protected String getWorkflowContextEntryClassName() {
		Map<String, Serializable> workflowContext = getWorkflowContext();

		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}

	protected long getWorkflowContextEntryClassPK() {
		Map<String, Serializable> workflowContext = getWorkflowContext();

		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
	}

	protected String getWorkflowDefinitionName() throws PortalException {
		WorkflowInstance workflowInstance = getWorkflowInstance();

		WorkflowDefinition workflowDefinition =
			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				workflowInstanceRequestHelper.getCompanyId(),
				workflowInstance.getWorkflowDefinitionName(),
				workflowInstance.getWorkflowDefinitionVersion());

		return HtmlUtil.escape(
			workflowDefinition.getTitle(
				LanguageUtil.getLanguageId(
					workflowInstanceRequestHelper.getRequest())));
	}

	protected WorkflowHandler<?> getWorkflowHandler() {
		String className = getWorkflowContextEntryClassName();

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
	}

	protected WorkflowInstance getWorkflowInstance() {
		return (WorkflowInstance)liferayPortletRequest.getAttribute(
			WebKeys.WORKFLOW_INSTANCE);
	}

	protected long getWorkflowInstanceId() {
		WorkflowInstance workflowInstance = getWorkflowInstance();

		return workflowInstance.getWorkflowInstanceId();
	}

	protected List<WorkflowTask> workflowTasks;

	private static final List<Integer> _logTypes = Arrays.asList(
		WorkflowLog.TASK_ASSIGN, WorkflowLog.TASK_COMPLETION,
		WorkflowLog.TASK_UPDATE, WorkflowLog.TRANSITION);

	private final Map<Long, Role> _roles = new HashMap<>();
	private final Map<Long, User> _users = new HashMap<>();
	private List<WorkflowLog> _workflowLogs;

}