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

package com.liferay.workflow.task.web.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.workflow.task.web.display.context.util.WorkflowTaskRequestHelper;
import com.liferay.workflow.task.web.search.WorkflowTaskDisplayTerms;
import com.liferay.workflow.task.web.search.WorkflowTaskSearch;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * @author Leonardo Barros
 */
public class WorkflowTaskDisplayContext {

	public WorkflowTaskDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());

		_workflowTaskRequestHelper = new WorkflowTaskRequestHelper(
			renderRequest);
	}

	public String getActorName(long actorId) {
		return HtmlUtil.escape(
			PortalUtil.getUserName(actorId, StringPool.BLANK));
	}

	public long[] getActorsIds(WorkflowTask workflowTask)
		throws PortalException {

		List<Long> pooledActorIdsList = new ArrayList<>();

		long[] pooledActorsIds = getPooledActorsIds(workflowTask);

		for (long pooledActorId : pooledActorsIds) {
			if (pooledActorId != _workflowTaskRequestHelper.getUserId()) {
				pooledActorIdsList.add(pooledActorId);
			}
		}

		return ArrayUtil.toLongArray(pooledActorIdsList);
	}

	public AssetEntry getAssetEntry() throws PortalException {
		long assetEntryId = ParamUtil.getLong(_renderRequest, "assetEntryId");

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getAssetEntry(assetEntryId);
	}

	public AssetRenderer<?> getAssetRenderer() throws PortalException {
		long assetEntryClassPK = ParamUtil.getLong(
			_workflowTaskRequestHelper.getRequest(), "assetEntryClassPK");

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getAssetRenderer(
			assetEntryClassPK, AssetRendererFactory.TYPE_LATEST);
	}

	public AssetRenderer<?> getAssetRenderer(WorkflowTask workflowTask)
		throws PortalException, PortletException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getAssetRenderer(classPK);
	}

	public AssetRendererFactory<?> getAssetRendererFactory() {
		String type = ParamUtil.getString(_renderRequest, "type");

		return AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
			type);
	}

	public String getAssetTitle(WorkflowTask workflowTask)
		throws PortalException {

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		return HtmlUtil.escape(
			workflowHandler.getTitle(
				classPK, _workflowTaskRequestHelper.getLocale()));
	}

	public String getAssetType(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		return workflowHandler.getType(_workflowTaskRequestHelper.getLocale());
	}

	public String getAssignedTheTaskMessageKey(WorkflowLog workflowLog)
		throws PortalException {

		User user = _users.get(workflowLog.getUserId());

		if (user.isMale()) {
			return "x-assigned-the-task-to-himself";
		}
		else {
			return "x-assigned-the-task-to-herself";
		}
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

	public WorkflowTaskSearch getCompletedTasksAssignedToMe()
		throws PortalException {

		return searchTasksAssignedToMe(true);
	}

	public String getCreateDate(WorkflowLog workflowLog) {
		return _dateFormatDateTime.format(workflowLog.getCreateDate());
	}

	public String getCreateDate(WorkflowTask workflowTask) {
		return _dateFormatDateTime.format(workflowTask.getCreateDate());
	}

	public String getCurrentURL() {
		PortletURL portletURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		return portletURL.toString();
	}

	public String getDescription(WorkflowTask workflowTask) {
		return HtmlUtil.escape(workflowTask.getDescription());
	}

	public Date getDueDate(WorkflowTask workflowTask) {
		return workflowTask.getDueDate();
	}

	public String getDueDateString(WorkflowTask workflowTask) {
		if (workflowTask.getDueDate() == null) {
			return LanguageUtil.get(
				_workflowTaskRequestHelper.getRequest(), "never");
		}
		else {
			return _dateFormatDateTime.format(workflowTask.getDueDate());
		}
	}

	public PortletURL getEditPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getURLEdit(
			classPK, (LiferayPortletRequest)_renderRequest,
			(LiferayPortletResponse)_renderResponse);
	}

	public String getEditTaskName(WorkflowTask workflowTask) {
		return LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(),
			HtmlUtil.escape(workflowTask.getName()));
	}

	public String getHeaderTitle(WorkflowTask workflowTask)
		throws PortalException {

		String taskName = LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(), workflowTask.getName());

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		String title = workflowHandler.getTitle(
			classPK, _workflowTaskRequestHelper.getLocale());

		return taskName + ": " + title;
	}

	public Date getLastActivityDate(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowLog workflowLog = getWorkflowLog(workflowTask);

		if (workflowLog != null) {
			return workflowLog.getCreateDate();
		}

		return null;
	}

	public String[] getMetadataFields() {
		return new String[] {"author", "categories", "tags"};
	}

	public WorkflowTaskSearch getPendingTasksAssignedToMe()
		throws PortalException {

		return searchTasksAssignedToMe(false);
	}

	public WorkflowTaskSearch getPendingTasksAssignedToMyRoles()
		throws PortalException {

		List<WorkflowTask> results = null;
		int total = 0;

		WorkflowTaskSearch searchContainer = new WorkflowTaskSearch(
			_renderRequest, "cur2", getPortletURL());

		WorkflowTaskDisplayTerms searchTerms =
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();

		if (searchTerms.isAdvancedSearch()) {
			total = WorkflowTaskManagerUtil.searchCount(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(), searchTerms.getName(),
				searchTerms.getType(), null, null, null, false, true,
				searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(), searchTerms.getName(),
				searchTerms.getType(), null, null, null, false, true,
				searchTerms.isAndOperator(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
		else {
			total = WorkflowTaskManagerUtil.searchCount(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(),
				WorkflowHandlerUtil.getSearchableAssetTypes(), false, true);

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(),
				WorkflowHandlerUtil.getSearchableAssetTypes(), false, true,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}

		searchContainer.setResults(results);

		setRolesSearchContainerEmptyResultsMessage(searchContainer);

		return searchContainer;
	}

	public long[] getPooledActorsIds(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowTaskManagerUtil.getPooledActorsIds(
			_workflowTaskRequestHelper.getCompanyId(),
			workflowTask.getWorkflowTaskId());
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("tabs1", getTabs1());

		return portletURL;
	}

	public String getPreviewOfTitle(WorkflowTask workflowTask)
		throws PortalException {

		String className = getWorkflowContextEntryClassName(workflowTask);

		String modelResource = ResourceActionsUtil.getModelResource(
			_workflowTaskRequestHelper.getLocale(), className);

		return LanguageUtil.format(
			_workflowTaskRequestHelper.getRequest(), "preview-of-x",
			modelResource, false);
	}

	public String getPreviousAssigneeMessageArguments(WorkflowLog workflowLog) {
		String userName = PortalUtil.getUserName(
			workflowLog.getPreviousUserId(), StringPool.BLANK);

		return HtmlUtil.escape(userName);
	}

	public List<WorkflowHandler<?>> getSearchableAssetsWorkflowHandlers() {
		List<WorkflowHandler<?>> searchableAssetsWorkflowHandlers =
			new ArrayList<>();

		List<WorkflowHandler<?>> workflowHandlers =
			WorkflowHandlerRegistryUtil.getWorkflowHandlers();

		for (WorkflowHandler<?> workflowHandler : workflowHandlers) {
			if (workflowHandler.isAssetTypeSearchable()) {
				searchableAssetsWorkflowHandlers.add(workflowHandler);
			}
		}

		return searchableAssetsWorkflowHandlers;
	}

	public String getState(WorkflowTask workflowTask) throws PortalException {
		long companyId = getWorkflowCompanyId(workflowTask);
		long groupId = getWorkflowGroupId(workflowTask);
		String className = getWorkflowContextEntryClassName(workflowTask);
		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		String state = WorkflowInstanceLinkLocalServiceUtil.getState(
			companyId, groupId, className, classPK);

		return LanguageUtil.get(_workflowTaskRequestHelper.getRequest(), state);
	}

	public String getTabs1() {
		return ParamUtil.getString(_renderRequest, "tabs1", "pending");
	}

	public String getTaglibEditURL(WorkflowTask workflowTask)
		throws PortalException, PortletException {

		StringBundler sb = new StringBundler(7);

		sb.append("javascript:Liferay.Util.openWindow({id: '");
		sb.append(_renderResponse.getNamespace());
		sb.append("editAsset', title: '");

		AssetRenderer<?> assetRenderer = getAssetRenderer(workflowTask);

		String assetTitle = HtmlUtil.escape(
			assetRenderer.getTitle(_workflowTaskRequestHelper.getLocale()));

		sb.append(
			LanguageUtil.format(
				_workflowTaskRequestHelper.getRequest(), "edit-x", assetTitle));

		sb.append("', uri:'");

		PortletURL editPortletURL = getEditPortletURL(workflowTask);

		ThemeDisplay themeDisplay =
			_workflowTaskRequestHelper.getThemeDisplay();

		editPortletURL.setParameter(
			"refererPlid", String.valueOf(themeDisplay.getPlid()));

		editPortletURL.setPortletMode(PortletMode.VIEW);
		editPortletURL.setWindowState(LiferayWindowState.POP_UP);

		sb.append(HtmlUtil.escapeJS(editPortletURL.toString()));

		sb.append("'});");

		return sb.toString();
	}

	public String getTaglibViewDiffsURL(WorkflowTask workflowTask)
		throws PortalException, PortletException {

		StringBundler sb = new StringBundler(7);

		sb.append("javascript:Liferay.Util.openWindow({id: '");
		sb.append(_renderResponse.getNamespace());
		sb.append("viewDiffs', title: '");

		String title = LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(), "diffs");

		sb.append(HtmlUtil.escapeJS(title));

		sb.append("', uri:'");

		PortletURL viewDiffsPortletURL = getViewDiffsPortletURL(workflowTask);

		viewDiffsPortletURL.setParameter("redirect", getCurrentURL());
		viewDiffsPortletURL.setParameter(
			"hideControls", Boolean.TRUE.toString());
		viewDiffsPortletURL.setWindowState(LiferayWindowState.POP_UP);
		viewDiffsPortletURL.setPortletMode(PortletMode.VIEW);

		sb.append(HtmlUtil.escapeJS(viewDiffsPortletURL.toString()));

		sb.append("'});");

		return sb.toString();
	}

	public Object getTaskCompletionMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);

		return new Object[] {
			HtmlUtil.escape(actorName), HtmlUtil.escape(workflowLog.getState())
		};
	}

	public String getTaskContentTitle(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return HtmlUtil.escape(
			workflowHandler.getTitle(
				classPK, _workflowTaskRequestHelper.getLocale()));
	}

	public String getTaskInitiallyAssignedMessageArguments(
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

	public String getTransitionMessage(String transitionName) {
		if (Validator.isNull(transitionName)) {
			return "proceed";
		}

		return HtmlUtil.escape(transitionName);
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

	public List<String> getTransitionNames(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowTaskManagerUtil.getNextTransitionNames(
			_workflowTaskRequestHelper.getCompanyId(),
			_workflowTaskRequestHelper.getUserId(),
			workflowTask.getWorkflowTaskId());
	}

	public String getUserFullName(WorkflowLog workflowLog) {
		User user = _users.get(workflowLog.getUserId());

		return HtmlUtil.escape(user.getFullName());
	}

	public PortletURL getViewDiffsPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getURLViewDiffs(
			classPK, (LiferayPortletRequest)_renderRequest,
			(LiferayPortletResponse)_renderResponse);
	}

	public WindowState getWindowState() {
		return _renderRequest.getWindowState();
	}

	public long getWorkflowCompanyId(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(workflowTask);

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		return GetterUtil.getLong((String)workflowContext.get(
			WorkflowConstants.CONTEXT_COMPANY_ID));
	}

	public String getWorkflowContextEntryClassName(WorkflowTask workflowTask)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			workflowTask);

		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}

	public long getWorkflowContextEntryClassPK(WorkflowTask workflowTask)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			workflowTask);

		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
	}

	public long getWorkflowGroupId(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(workflowTask);

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		return GetterUtil.getLong((String)workflowContext.get(
			WorkflowConstants.CONTEXT_GROUP_ID));
	}

	public WorkflowHandler<?> getWorkflowHandler(WorkflowTask workflowTask)
		throws PortalException {

		String className = getWorkflowContextEntryClassName(workflowTask);

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
	}

	public WorkflowInstance getWorkflowInstance(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowInstanceManagerUtil.getWorkflowInstance(
			_workflowTaskRequestHelper.getCompanyId(),
			getWorkflowInstanceId(workflowTask));
	}

	public long getWorkflowInstanceId(WorkflowTask workflowTask) {
		return workflowTask.getWorkflowInstanceId();
	}

	public WorkflowLog getWorkflowLog(WorkflowTask workflowTask)
		throws PortalException {

		List<WorkflowLog> workflowLogs =
			WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				_workflowTaskRequestHelper.getCompanyId(),
				getWorkflowInstanceId(workflowTask), null, 0, 1,
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator());

		if (!workflowLogs.isEmpty()) {
			return workflowLogs.get(0);
		}

		return null;
	}

	public String getWorkflowLogComment(WorkflowLog workflowLog) {
		return HtmlUtil.escape(workflowLog.getComment());
	}

	public String getWorkflowLogCreateDate(WorkflowLog workflowLog) {
		return _dateFormatDateTime.format(workflowLog.getCreateDate());
	}

	public List<WorkflowLog> getWorkflowLogs(WorkflowTask workflowTask)
		throws PortalException {

		List<Integer> logTypes = new ArrayList<>();

		logTypes.add(WorkflowLog.TASK_ASSIGN);
		logTypes.add(WorkflowLog.TASK_COMPLETION);
		logTypes.add(WorkflowLog.TASK_UPDATE);
		logTypes.add(WorkflowLog.TRANSITION);

		return WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
			_workflowTaskRequestHelper.getCompanyId(),
			getWorkflowInstanceId(workflowTask), logTypes, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS,
			WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));
	}

	public WorkflowTask getWorkflowTask() {
		ResultRow resultRow = (ResultRow)_renderRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		if (Validator.isNotNull(resultRow)) {
			return (WorkflowTask)resultRow.getParameter("workflowTask");
		}
		else {
			return (WorkflowTask)_renderRequest.getAttribute(
				WebKeys.WORKFLOW_TASK);
		}
	}

	public Map<String, Object> getWorkflowTaskActionLinkData() {
		Map<String, Object> data = new HashMap<>();

		data.put("navigation", Boolean.TRUE);

		return data;
	}

	public String getWorkflowTaskAssigneeUserName(WorkflowTask workflowTask) {
		return PortalUtil.getUserName(
			workflowTask.getAssigneeUserId(), StringPool.BLANK);
	}

	public WorkflowTaskDisplayTerms getWorkflowTaskDisplayTerms() {
		return new WorkflowTaskDisplayTerms(_renderRequest);
	}

	public String getWorkflowTaskRandomId() {
		String randomId = StringPool.BLANK;

		ResultRow resultRow = (ResultRow)_renderRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		if (resultRow != null) {
			randomId = StringUtil.randomId();
		}

		return randomId;
	}

	public String getWorkflowTaskUnassignedUserName() {
		return LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(), "nobody");
	}

	public boolean hasEditPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		PortletURL editPortletURL = getEditPortletURL(workflowTask);

		if (editPortletURL != null) {
			return true;
		}

		return false;
	}

	public boolean hasOtherAssignees(WorkflowTask workflowTask)
		throws PortalException {

		long[] pooledActorsIds = getPooledActorsIds(workflowTask);

		if (pooledActorsIds.length == 0) {
			return false;
		}

		if (workflowTask.isCompleted()) {
			return false;
		}

		if ((pooledActorsIds.length == 1) &&
			(pooledActorsIds[0] == _workflowTaskRequestHelper.getUserId())) {

			return false;
		}

		return true;
	}

	public boolean hasViewDiffsPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		PortletURL viewDiffsPortletURL = getViewDiffsPortletURL(workflowTask);

		if (viewDiffsPortletURL != null) {
			return true;
		}

		return false;
	}

	public boolean isAssignedToUser(WorkflowTask workflowTask) {
		if (workflowTask.getAssigneeUserId() ==
				_workflowTaskRequestHelper.getUserId()) {

			return true;
		}

		return false;
	}

	public boolean isAuditUser(WorkflowLog workflowLog) {
		User user = null;

		if (workflowLog.getUserId() != 0) {
			user = _users.get(workflowLog.getUserId());
		}

		if ((user != null) &&
			(workflowLog.getAuditUserId() == user.getUserId())) {

			return true;
		}

		return false;
	}

	public boolean isCompletedTabSelected() {
		String tabs1 = getTabs1();

		if (tabs1.equals("completed")) {
			return true;
		}

		return false;
	}

	public boolean isPendingTabSelected() {
		String tabs1 = getTabs1();

		if (tabs1.equals("pending")) {
			return true;
		}

		return false;
	}

	public boolean isShowEditURL(WorkflowTask workflowTask) {
		boolean showEditURL = false;

		if ((workflowTask.getAssigneeUserId() ==
				_workflowTaskRequestHelper.getUserId()) &&
			!workflowTask.isCompleted()) {

			showEditURL = true;
		}

		return showEditURL;
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

	protected Map<String, Serializable> getWorkflowContext(
			WorkflowTask workflowTask)
		throws PortalException {

		return getWorkflowInstance(workflowTask).getWorkflowContext();
	}

	protected WorkflowTaskSearch searchTasksAssignedToMe(boolean completedTasks)
		throws PortalException {

		List<WorkflowTask> results = null;
		int total = 0;

		String curParam = SearchContainer.DEFAULT_CUR_PARAM;

		if (!completedTasks) {
			curParam = "cur1";
		}

		WorkflowTaskSearch searchContainer = new WorkflowTaskSearch(
			_renderRequest, curParam, getPortletURL());

		WorkflowTaskDisplayTerms searchTerms =
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();

		if (searchTerms.isAdvancedSearch()) {
			total = WorkflowTaskManagerUtil.searchCount(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(), searchTerms.getName(),
				searchTerms.getType(), null, null, null, false, false,
				searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(), searchTerms.getName(),
				searchTerms.getType(), null, null, null, completedTasks, false,
				searchTerms.isAndOperator(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
		else {
			total = WorkflowTaskManagerUtil.searchCount(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(),
				WorkflowHandlerUtil.getSearchableAssetTypes(), completedTasks,
				false);

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(),
				WorkflowHandlerUtil.getSearchableAssetTypes(), completedTasks,
				false, searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}

		searchContainer.setResults(results);

		setUserSearchContainerEmptyResultsMessage(
			searchContainer, completedTasks);

		return searchContainer;
	}

	protected void setRolesSearchContainerEmptyResultsMessage(
		WorkflowTaskSearch searchContainer) {

		WorkflowTaskDisplayTerms searchTerms =
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();

		searchContainer.setEmptyResultsMessage(
			"there-are-no-pending-tasks-assigned-to-your-roles");

		if (Validator.isNotNull(searchTerms.getKeywords()) ||
			Validator.isNotNull(searchTerms.getName()) ||
			Validator.isNotNull(searchTerms.getType())) {

			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() +
				"-with-the-specified-search-criteria");
		}
	}

	protected void setUserSearchContainerEmptyResultsMessage(
		WorkflowTaskSearch searchContainer, boolean completedTasks) {

		WorkflowTaskDisplayTerms searchTerms =
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();

		if (completedTasks) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-completed-tasks");
		}
		else {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-pending-tasks-assigned-to-you");
		}

		if (Validator.isNotNull(searchTerms.getKeywords()) ||
			Validator.isNotNull(searchTerms.getName()) ||
			Validator.isNotNull(searchTerms.getType())) {

			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() +
				"-with-the-specified-search-criteria");
		}
	}

	private final Format _dateFormatDateTime;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final Map<Long, Role> _roles = new HashMap<>();
	private final Map<Long, User> _users = new HashMap<>();
	private final WorkflowTaskRequestHelper _workflowTaskRequestHelper;

}