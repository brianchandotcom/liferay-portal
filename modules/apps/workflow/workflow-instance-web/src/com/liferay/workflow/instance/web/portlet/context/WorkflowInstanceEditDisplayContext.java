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

package com.liferay.workflow.instance.web.portlet.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class WorkflowInstanceEditDisplayContext {

	public WorkflowInstanceEditDisplayContext(
			LiferayPortletRequest liferayPortletRequest)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;

		ThemeDisplay themeDisplay =
				(ThemeDisplay)_liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

		_locale = themeDisplay.getLocale();
		Company company = themeDisplay.getCompany();
		long userId = themeDisplay.getUserId();
		String portletName = themeDisplay.getPortletDisplay().getPortletName();

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			_locale, themeDisplay.getTimeZone());

		_workflowInstance =
			(WorkflowInstance)_liferayPortletRequest.getAttribute(
				WebKeys.WORKFLOW_INSTANCE);

		Map<String, Serializable> workflowContext =
			_workflowInstance.getWorkflowContext();

		String className = (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);

		long classPK = GetterUtil.getLong((String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		_assetRenderer = workflowHandler.getAssetRenderer(classPK);
		_assetRendererFactory = workflowHandler.getAssetRendererFactory();

		if (_assetRenderer != null) {
			_assetEntry = _assetRendererFactory.getAssetEntry(
					_assetRendererFactory.getClassName(),
				_assetRenderer.getClassPK());
		}

		_headerTitle = LanguageUtil.get(
			_liferayPortletRequest.getHttpServletRequest(),
			_workflowInstance.getWorkflowDefinitionName());

		if (_assetEntry != null) {
			_headerTitle = _headerTitle.concat(
				StringPool.COLON + StringPool.SPACE +
				_assetRenderer.getTitle(themeDisplay.getLocale()));
			_assetEntryVersionId = String.valueOf(classPK);
		}

		if (portletName.equals(PortletKeys.WORKFLOW_DEFINITIONS)) {
			_workflowTasks =
				WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(
					company.getCompanyId(), null,
					_workflowInstance.getWorkflowInstanceId(), null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		else {
			_workflowTasks =
				WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(
					company.getCompanyId(), userId,
					_workflowInstance.getWorkflowInstanceId(), false,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		List<Integer> logTypes = new ArrayList<>();

		logTypes.add(WorkflowLog.TASK_ASSIGN);
		logTypes.add(WorkflowLog.TASK_COMPLETION);
		logTypes.add(WorkflowLog.TASK_UPDATE);
		logTypes.add(WorkflowLog.TRANSITION);

		_workflowLogs =
			WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				company.getCompanyId(),
				_workflowInstance.getWorkflowInstanceId(), logTypes,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));

		if (!_workflowLogs.isEmpty()) {
			_roleMap = new HashMap<>();
			_userMap = new HashMap<>();
		}

		_panelTitle = LanguageUtil.format(
			_liferayPortletRequest.getHttpServletRequest(), "preview-of-x",
			ResourceActionsUtil.getModelResource(_locale, className), false);

		_taskContentTitleMessage = HtmlUtil.escape(
			workflowHandler.getTitle(classPK, _locale));

		_iconCssClass = workflowHandler.getIconCssClass();
	}

	public String getActorName(WorkflowLog workflowLog) throws PortalException {
		if (workflowLog.getRoleId() != 0) {
			if (!_roleMap.containsKey(workflowLog.getRoleId())) {
				Role curRole = RoleLocalServiceUtil.getRole(
					workflowLog.getRoleId());
				_roleMap.put(workflowLog.getRoleId(), curRole);
			}

			return _roleMap.get(workflowLog.getRoleId()).getDescriptiveName();
		}
		else if (workflowLog.getUserId() != 0) {
			if (!_userMap.containsKey(workflowLog.getUserId())) {
				User curUser = UserLocalServiceUtil.getUser(
					workflowLog.getUserId());
				_userMap.put(workflowLog.getUserId(), curUser);
			}

			return _userMap.get(workflowLog.getUserId()).getFullName();
		}

		return null;
	}

	public AssetEntry getAssetEntry() {
		return _assetEntry;
	}

	public String getAssetEntryVersionId() {
		return _assetEntryVersionId;
	}

	public String getAssetName() {
		return HtmlUtil.escape(_workflowInstance.getWorkflowDefinitionName());
	}

	public AssetRenderer getAssetRenderer() {
		return _assetRenderer;
	}

	public AssetRendererFactory getAssetRendererFactory() {
		return _assetRendererFactory;
	}

	public String getAssignedTheTaskMessageKey(WorkflowLog workflowLog)
		throws PortalException {

		User curUser = _userMap.get(workflowLog.getUserId());
		return curUser.isMale() ? "x-assigned-the-task-to-himself" :
			"x-assigned-the-task-to-herself";
	}

	public Object getAssignedTheTaskToMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return new Object[] {
			HtmlUtil.escape(
				PortalUtil.getUserName(
					workflowLog.getAuditUserId(), StringPool.BLANK)),
			HtmlUtil.escape(actorName)
		};
	}

	public String getEndDate() {
		if (_workflowInstance.getEndDate() == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "never");
		}
		else {
			return _dateFormatDateTime.format(_workflowInstance.getEndDate());
		}
	}

	public String getHeaderTitle() {
		return _headerTitle;
	}

	public String getIconCssClass() {
		return _iconCssClass;
	}

	public String getPanelTitle() {
		return _panelTitle;
	}

	public Object getPreviousAssigneeMessageArguments(WorkflowLog workflowLog) {
		return HtmlUtil.escape(
			PortalUtil.getUserName(
				workflowLog.getPreviousUserId(), StringPool.BLANK));
	}

	public String getState() {
		return LanguageUtil.get(
			_liferayPortletRequest.getHttpServletRequest(),
			_workflowInstance.getState());
	}

	public String getTaskCompleted(WorkflowTask workflowTask) {
		HttpServletRequest httpServletRequest =
			_liferayPortletRequest.getHttpServletRequest();
		return workflowTask.isCompleted() ?
			LanguageUtil.get(httpServletRequest, "yes") : LanguageUtil.get(
				httpServletRequest, "no");
	}

	public Object getTaskCompletionMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return new Object[] {HtmlUtil.escape(actorName), HtmlUtil.escape(
			workflowLog.getState())
		};
	}

	public String getTaskContentTitleMessage() {
		return _taskContentTitleMessage;
	}

	public String getTaskDueDate(WorkflowTask workflowTask) {
		if (workflowTask.getDueDate() == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "never");
		}
		else {
			return _dateFormatDateTime.format(workflowTask.getDueDate());
		}
	}

	public Object getTaskInitiallyAssignedMessageArguments(
			WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return HtmlUtil.escape(actorName);
	}

	public String getTaskName(WorkflowTask workflowTask) {
		return HtmlUtil.escape(workflowTask.getName());
	}

	public Object getTaskUpdateMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return HtmlUtil.escape(actorName);
	}

	public Object getTransitionMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return new Object[] {HtmlUtil.escape(actorName), HtmlUtil.escape(
			workflowLog.getPreviousState()),
			HtmlUtil.escape(workflowLog.getState())
			};
	}

	public String getUserFullName(WorkflowLog workflowLog) {
		User curUser = _userMap.get(workflowLog.getUserId());
		return HtmlUtil.escape(curUser.getFullName());
	}

	public String getWorkflowLogComment(WorkflowLog workflowLog) {
		return HtmlUtil.escape(workflowLog.getComment());
	}

	public String getWorkflowLogCreateDate(WorkflowLog workflowLog) {
		return _dateFormatDateTime.format(workflowLog.getCreateDate());
	}

	public List<WorkflowLog> getWorkflowLogs() {
		return _workflowLogs;
	}

	public List<WorkflowTask> getWorkflowTasks() {
		return _workflowTasks;
	}

	public boolean isAuditUser(WorkflowLog workflowLog) {
		User curUser = null;

		if (workflowLog.getUserId() != 0) {
			curUser = _userMap.get(workflowLog.getUserId());
		}

		return (curUser != null) &&
			(workflowLog.getAuditUserId() == curUser.getUserId());
	}

	private AssetEntry _assetEntry;
	private String _assetEntryVersionId;
	private final AssetRenderer _assetRenderer;
	private final AssetRendererFactory _assetRendererFactory;
	private final Format _dateFormatDateTime;
	private String _headerTitle;
	private final String _iconCssClass;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final Locale _locale;
	private final String _panelTitle;
	private Map<Long, Role> _roleMap;
	private final String _taskContentTitleMessage;
	private Map<Long, User> _userMap;
	private final WorkflowInstance _workflowInstance;
	private final List<WorkflowLog> _workflowLogs;
	private List<WorkflowTask> _workflowTasks;

}