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

package com.liferay.workflow.task.web.context;

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
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.workflow.task.web.constants.WorkflowTaskConstants;
import com.liferay.workflow.task.web.context.util.WorkflowTaskRequestHelper;
import com.liferay.workflow.task.web.search.WorkflowTaskDisplayTerms;
import com.liferay.workflow.task.web.search.WorkflowTaskSearch;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
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

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		this._dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());

		this._renderResponse = renderResponse;
		this._renderRequest = renderRequest;

		this._workflowTaskRequestHelper = new WorkflowTaskRequestHelper(
			renderRequest);

		this._roleMap = new HashMap<>();
		this._userMap = new HashMap<>();
	}

	public PortletURL getPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	public List<WorkflowHandler<?>> getWorkflowHandlersOfSearchableAssets() {
		List<WorkflowHandler<?>> workflowHandlersOfSearchableAssets =
			new ArrayList<>();

		List<WorkflowHandler<?>> workflowHandlers =
			WorkflowHandlerRegistryUtil.getWorkflowHandlers();

		for (WorkflowHandler<?> workflowHandler : workflowHandlers) {
			if (workflowHandler.isAssetTypeSearchable()) {
				workflowHandlersOfSearchableAssets.add(workflowHandler);
			}
		}

		return workflowHandlersOfSearchableAssets;
	}

	public String getSelectedTab() {
		return ParamUtil.getString(
			_renderRequest, WorkflowTaskConstants.TABS1,
			WorkflowTaskConstants.PENDING);
	}

	public WorkflowTaskDisplayTerms getDisplayTerms() {
		return new WorkflowTaskDisplayTerms(_renderRequest);
	}

	public boolean isPendingTab() {
		return WorkflowTaskConstants.PENDING.equals(getSelectedTab());
	}

	public WorkflowTaskSearch getPendingTasksAssignedToMe()
		throws PortalException {

		return searchTasksAssignedToMe(false);
	}

	public WorkflowTaskSearch getCompletedTasksAssignedToMe()
		throws PortalException {

		return searchTasksAssignedToMe(true);
	}

	public boolean isCompletedTab() {
		return WorkflowTaskConstants.COMPLETED.equals(getSelectedTab());
	}

	public long[] getPooledActorsIds(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowTaskManagerUtil.getPooledActorsIds(
			_workflowTaskRequestHelper.getCompanyId(),
			workflowTask.getWorkflowTaskId());
	}

	public List<String> getTransitionNames(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowTaskManagerUtil.getNextTransitionNames(
			_workflowTaskRequestHelper.getCompanyId(),
			_workflowTaskRequestHelper.getUserId(),
			workflowTask.getWorkflowTaskId());
	}

	public String getWorkflowTaskRandomId() {
		String randomId = StringPool.BLANK;

		ResultRow row = (ResultRow)_renderRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		if (Validator.isNotNull(row)) {
			randomId = StringUtil.randomId();
		}

		return randomId;
	}

	public String getTransitionMessage(String transitionName) {
		String message = WorkflowTaskConstants.PROCEED;

		if (Validator.isNotNull(transitionName)) {
			message = HtmlUtil.escape(transitionName);
		}

		return message;
	}

	public boolean isAssignedToUser(WorkflowTask workflowTask) {
		if (workflowTask.getAssigneeUserId() ==
				_workflowTaskRequestHelper.getUserId()) {

			return true;
		}

		return false;
	}

	public AssetRendererFactory getAssetRendererFactoryFromRequest() {

		String type = ParamUtil.getString(
			_workflowTaskRequestHelper.getRequest(), "type");

		return AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
			type);
	}

	public AssetEntry getAssetEntryFromRequest() throws PortalException {
		long assetEntryId = ParamUtil.getLong(
			_workflowTaskRequestHelper.getRequest(), "assetEntryId");

		AssetRendererFactory assetRendererFactory =
			getAssetRendererFactoryFromRequest();

		return assetRendererFactory.getAssetEntry(assetEntryId);
	}

	public AssetRenderer getAssetRendererFromRequest() throws PortalException {
		long assetEntryVersionId = ParamUtil.getLong(
			_workflowTaskRequestHelper.getRequest(), "assetEntryVersionId");

		return getAssetRendererFactoryFromRequest().getAssetRenderer(
			assetEntryVersionId, AssetRendererFactory.TYPE_LATEST);
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

	public WindowState getWindowState() {
		return _renderRequest.getWindowState();
	}

	public String getCurrentURL() {
		return getPortletURL().toString();
	}

	public String getActorName(long actorId) {
		return HtmlUtil.escape(
			PortalUtil.getUserName(actorId, StringPool.BLANK));
	}

	public long[] getActorsIds(WorkflowTask workflowTask)
		throws PortalException {

		long[] pooledActorsIds = getPooledActorsIds(workflowTask);
		List<Long> actors = new ArrayList<>();

		for (long pooledActorId : pooledActorsIds) {
			if (pooledActorId != _workflowTaskRequestHelper.getUserId()) {
				actors.add(pooledActorId);
			}
		}

		return ArrayUtil.toLongArray(actors);
	}

	public WorkflowTaskSearch getPendingTasksAssignedToMyRoles()
		throws PortalException {

		int total = 0;
		List<WorkflowTask> results = null;

		String[] assetTypes = WorkflowHandlerUtil.getSearchableAssetTypes();

		WorkflowTaskSearch searchContainer = new WorkflowTaskSearch(
			_renderRequest, WorkflowTaskConstants.DEFAULT_CUR_PARAM2,
			getPortletURL());

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
				searchTerms.getKeywords(), assetTypes, false, true);

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
					_workflowTaskRequestHelper.getCompanyId(),
					_workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(), assetTypes, false, true,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		validateSearchTerms(searchContainer);

		searchContainer.setEmptyResultsMessage(
	WorkflowTaskConstants.THERE_ARE_NO_PENDING_TASKS_ASSIGNED_TO_YOUR_ROLES);

		return searchContainer;
	}

	public String getTaskName(WorkflowTask workflowTask) {
		return HtmlUtil.escape(workflowTask.getName());
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

	public String getLastActivityDate(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowLog workflowLog = getWorkflowLog(workflowTask);

		if (workflowLog == null) {
			return LanguageUtil.get(
				_workflowTaskRequestHelper.getRequest(),
				WorkflowTaskConstants.NEVER);
		}
		else {
			return _dateFormatDateTime.format(workflowLog.getCreateDate());
		}
	}

	public String getDueDate(WorkflowTask workflowTask) {
		if (workflowTask.getDueDate() == null) {
			return LanguageUtil.get(
				_workflowTaskRequestHelper.getRequest(),
				WorkflowTaskConstants.NEVER);
		}
		else {
			return _dateFormatDateTime.format(workflowTask.getDueDate());
		}
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

	public Object getTransitionMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return new Object[] {HtmlUtil.escape(actorName), HtmlUtil.escape(
			workflowLog.getPreviousState()),
			HtmlUtil.escape(workflowLog.getState())
			};
	}

	public Object getTaskUpdateMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return HtmlUtil.escape(actorName);
	}

	public String getCreateDate(WorkflowLog workflowLog) {
		return _dateFormatDateTime.format(workflowLog.getCreateDate());
	}

	public Object getTaskCompletionMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return new Object[] {HtmlUtil.escape(actorName), HtmlUtil.escape(
			workflowLog.getState())
		};
	}

	public String getUserFullName(WorkflowLog workflowLog) {
		User curUser = _userMap.get(workflowLog.getUserId());
		return HtmlUtil.escape(curUser.getFullName());
	}

	public String getAssignedTheTaskMessageKey(WorkflowLog workflowLog)
		throws PortalException {

		User curUser = _userMap.get(workflowLog.getUserId());
		return curUser.isMale() ?
			WorkflowTaskConstants.X_ASSIGNED_THE_TASK_TO_HIMSELF :
				WorkflowTaskConstants.X_ASSIGNED_THE_TASK_TO_HERSELF;
	}

	public Object getPreviousAssigneeMessageArguments(WorkflowLog workflowLog) {
		return HtmlUtil.escape(
			PortalUtil.getUserName(
				workflowLog.getPreviousUserId(), StringPool.BLANK));
	}

	public Object getTaskInitiallyAssignedMessageArguments(
			WorkflowLog workflowLog)
		throws PortalException {

		String actorName = getActorName(workflowLog);
		return HtmlUtil.escape(actorName);
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

	public String getWorkflowLogComment(WorkflowLog workflowLog) {
		return HtmlUtil.escape(workflowLog.getComment());
	}

	public String getWorkflowLogCreateDate(WorkflowLog workflowLog) {
		return _dateFormatDateTime.format(workflowLog.getCreateDate());
	}

	public boolean isAuditUser(WorkflowLog workflowLog) {
		User curUser = null;

		if (workflowLog.getUserId() != 0) {
			curUser = _userMap.get(workflowLog.getUserId());
		}

		return (curUser != null) &&
			(workflowLog.getAuditUserId() == curUser.getUserId());
	}

	public WorkflowInstance getWorkflowInstance(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowInstanceManagerUtil.getWorkflowInstance(
			_workflowTaskRequestHelper.getCompanyId(),
			getWorkflowInstanceId(workflowTask));
	}

	public long getWorkflowCompanyId(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(workflowTask);

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		return GetterUtil.getLong((String)workflowContext.get(
			WorkflowConstants.CONTEXT_COMPANY_ID));
	}

	public long getWorkflowGroupId(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(workflowTask);

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		return GetterUtil.getLong((String)workflowContext.get(
			WorkflowConstants.CONTEXT_GROUP_ID));
	}

	public long getWorkflowContextEntryClassPK(WorkflowTask workflowTask)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			workflowTask);

		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
	}

	public String getWorkflowContextEntryClassName(WorkflowTask workflowTask)
		throws PortalException {

		Map<String, Serializable> workflowContext = getWorkflowContext(
			workflowTask);

		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}

	protected Map<String, Serializable> getWorkflowContext(
		WorkflowTask workflowTask)
		throws PortalException {

		return getWorkflowInstance(workflowTask).getWorkflowContext();
	}

	public WorkflowHandler<?> getWorkflowHandler(WorkflowTask workflowTask)
		throws PortalException {

		String className = getWorkflowContextEntryClassName(workflowTask);

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
	}

	public String getNameForAssignedToSingleUser(WorkflowTask workflowTask) {
		return PortalUtil.getUserName(
			workflowTask.getAssigneeUserId(), StringPool.BLANK);
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

	public String getNameForAssignedToAnyone() {
		return LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(),
			WorkflowTaskConstants.NOBODY);
	}

	public String getCreateDate(WorkflowTask workflowTask) {
		return _dateFormatDateTime.format(workflowTask.getCreateDate());
	}

	public String getState(WorkflowTask workflowTask) throws PortalException {
		long companyId = getWorkflowCompanyId(workflowTask);
		long groupId = getWorkflowGroupId(workflowTask);
		String className = getWorkflowContextEntryClassName(workflowTask);
		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(),
			HtmlUtil.escape(
				WorkflowInstanceLinkLocalServiceUtil.getState(
					companyId, groupId, className, classPK)));
	}

	public WorkflowTask getWorkflowTask() {
		ResultRow row = (ResultRow)_renderRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		WorkflowTask workflowTask = null;

		if (Validator.isNotNull(row)) {
			workflowTask = (WorkflowTask)row.getParameter(
				WorkflowTaskConstants.WORKFLOW_TASK);
		}
		else {
			workflowTask = (WorkflowTask)_renderRequest.getAttribute(
				WebKeys.WORKFLOW_TASK);
		}

		return workflowTask;
	}

	public long getWorkflowInstanceId(WorkflowTask workflowTask) {
		return workflowTask.getWorkflowInstanceId();
	}

	public String getDescription(WorkflowTask workflowTask) {
		return HtmlUtil.escape(workflowTask.getDescription());
	}

	public String getTaglibViewDiffsURL(WorkflowTask workflowTask)
		throws PortalException, PortletException {

		PortletURL viewDiffsPortletURL = getURLViewDiffs(workflowTask);

		viewDiffsPortletURL.setParameter(
			WorkflowTaskConstants.REDIRECT, getCurrentURL());
		viewDiffsPortletURL.setParameter(
			WorkflowTaskConstants.HIDE_CONTROLS, Boolean.TRUE.toString());
		viewDiffsPortletURL.setWindowState(LiferayWindowState.POP_UP);
		viewDiffsPortletURL.setPortletMode(PortletMode.VIEW);

		return "javascript:Liferay.Util.openWindow({id: '" +
			_renderResponse.getNamespace() + "viewDiffs', title: '" +
			HtmlUtil.escapeJS(
				LanguageUtil.get(
					_workflowTaskRequestHelper.getRequest(),
					WorkflowTaskConstants.DIFFS)) +
					"', uri:'" + HtmlUtil.escapeJS(
						viewDiffsPortletURL.toString()) + "'});";
	}

	public AssetRenderer getAssetRenderer(WorkflowTask workflowTask)
		throws PortalException, PortletException {

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		return workflowHandler.getAssetRenderer(classPK);
	}

	public String getTaskContentTitle(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return HtmlUtil.escape(
			workflowHandler.getTitle(
				classPK, _workflowTaskRequestHelper.getLocale()));
	}

	public String[] getMetadataFields() {
		return new String[] {
			WorkflowTaskConstants.AUTHOR, WorkflowTaskConstants.CATEGORIES,
			WorkflowTaskConstants.TAGS
		};
	}

	public String getTaglibEditURL(WorkflowTask workflowTask)
		throws PortalException, PortletException {

		PortletURL editPortletURL = getEditPortletURL(workflowTask);

		editPortletURL.setWindowState(LiferayWindowState.POP_UP);
		editPortletURL.setPortletMode(PortletMode.VIEW);

		String editPortletURLString = editPortletURL.toString();

		AssetRenderer assetRenderer = getAssetRenderer(workflowTask);

		editPortletURLString = HttpUtil.addParameter(
			editPortletURLString, WorkflowTaskConstants.DO_AS_GROUP_ID,
			assetRenderer.getGroupId());

		editPortletURLString = HttpUtil.addParameter(
			editPortletURLString, WorkflowTaskConstants.REFERER_PLID,
			_workflowTaskRequestHelper.getThemeDisplay().getPlid());

		return "javascript:Liferay.Util.openWindow({id: '" +
			_renderResponse.getNamespace() +
			"editAsset', title: '" +
			HtmlUtil.escapeJS(
				LanguageUtil.format(
					_workflowTaskRequestHelper.getRequest(),
					WorkflowTaskConstants.EDIT_X,
					HtmlUtil.escape(
						assetRenderer.getTitle(
							_workflowTaskRequestHelper.getLocale())),
							false)) + "', uri:'" +
							HtmlUtil.escapeJS(editPortletURLString) + "'});";
	}

	public String getEditTaskName(WorkflowTask workflowTask) {
		return LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(),
			HtmlUtil.escape(workflowTask.getName()));
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

	public boolean hasEditPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		return Validator.isNotNull(getEditPortletURL(workflowTask));
	}

	public boolean hasViewDiffsPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		return Validator.isNotNull(getURLViewDiffs(workflowTask));
	}

	public String getPreviewOfTitle(WorkflowTask workflowTask)
		throws PortalException {

		String className = getWorkflowContextEntryClassName(workflowTask);

		return LanguageUtil.format(
			_workflowTaskRequestHelper.getRequest(),
			WorkflowTaskConstants.PREVIEW_OF_X,
			ResourceActionsUtil.getModelResource(
				_workflowTaskRequestHelper.getLocale(), className), false);
	}

	public String getHeaderTitle(WorkflowTask workflowTask)
		throws PortalException {

		String headerTitle = LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(), workflowTask.getName());

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return headerTitle.concat(
			StringPool.COLON + StringPool.SPACE + workflowHandler.getTitle(
				classPK, _workflowTaskRequestHelper.getLocale()));
	}

	public PortletURL getURLViewDiffs(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getURLViewDiffs(
			classPK, (LiferayPortletRequest)_renderRequest,
			(LiferayPortletResponse)_renderResponse);
	}

	public PortletURL getEditPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getURLEdit(
			classPK, (LiferayPortletRequest)_renderRequest,
			(LiferayPortletResponse)_renderResponse);
	}

	public boolean showEditURL(WorkflowTask workflowTask) {
		boolean showEditURL = false;

		if ((workflowTask.getAssigneeUserId() ==
				_workflowTaskRequestHelper.getUserId()) &&
			!workflowTask.isCompleted()) {

			showEditURL = true;
		}

		return showEditURL;
	}

	protected WorkflowTaskSearch searchTasksAssignedToMe(boolean completedTasks)
		throws PortalException {

		int total = 0;
		List<WorkflowTask> results = null;

		String curParam = SearchContainer.DEFAULT_CUR_PARAM;

		if (!completedTasks) {
			curParam = WorkflowTaskConstants.DEFAULT_CUR_PARAM1;
		}

		String[] assetTypes = WorkflowHandlerUtil.getSearchableAssetTypes();

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
				searchTerms.getKeywords(), assetTypes, completedTasks, false);

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(
				_workflowTaskRequestHelper.getCompanyId(),
				_workflowTaskRequestHelper.getUserId(),
				searchTerms.getKeywords(), assetTypes, completedTasks, false,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		validateSearchTerms(searchContainer);

		if (completedTasks) {
			searchContainer.setEmptyResultsMessage(
				WorkflowTaskConstants.THERE_ARE_NO_COMPLETED_TASKS);
		}
		else {
			searchContainer.setEmptyResultsMessage(
		WorkflowTaskConstants.THERE_ARE_NO_PENDING_TASKS_ASSIGNED_TO_YOU);
		}

		return searchContainer;
	}

	protected void validateSearchTerms(WorkflowTaskSearch searchContainer) {

		WorkflowTaskDisplayTerms searchTerms =
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();

		if (Validator.isNotNull(searchTerms.getKeywords()) ||
			Validator.isNotNull(searchTerms.getName()) ||
			Validator.isNotNull(searchTerms.getType())) {

			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() +
				WorkflowTaskConstants.WITH_THE_SPECIFIED_SEARCH_CRITERIA);
		}
	}

	private final Format _dateFormatDateTime;

	private final RenderRequest _renderRequest;

	private final RenderResponse _renderResponse;

	private Map<Long, Role> _roleMap;

	private Map<Long, User> _userMap;

	private final WorkflowTaskRequestHelper _workflowTaskRequestHelper;

}