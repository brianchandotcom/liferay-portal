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

package com.liferay.portal.workflow.task.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.task.web.internal.display.context.util.WorkflowTaskRequestHelper;
import com.liferay.portal.workflow.task.web.internal.search.WorkflowTaskSearch;
import com.liferay.portal.workflow.task.web.internal.util.WorkflowTaskPortletUtil;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class WorkflowTaskDisplayContext {

	public WorkflowTaskDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());

		_workflowTaskRequestHelper = new WorkflowTaskRequestHelper(
			_httpServletRequest);
	}

	public String getActorName(long actorId) {
		return HtmlUtil.escape(
			PortalUtil.getUserName(actorId, StringPool.BLANK));
	}

	public long[] getActorsIds(WorkflowTask workflowTask)
		throws PortalException {

		List<Long> pooledActorIdsList = new ArrayList<>();

		long[] pooledActorsIds = _getPooledActorsIds(workflowTask);

		for (long pooledActorId : pooledActorsIds) {
			if (pooledActorId != _workflowTaskRequestHelper.getUserId()) {
				pooledActorIdsList.add(pooledActorId);
			}
		}

		return ArrayUtil.toLongArray(pooledActorIdsList);
	}

	public AssetEntry getAssetEntry() throws PortalException {
		long assetEntryId = ParamUtil.getLong(
			_liferayPortletRequest, "assetEntryId");

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getAssetEntry(assetEntryId);
	}

	public String getAssetIconCssClass(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		return workflowHandler.getIconCssClass();
	}

	public AssetRenderer<?> getAssetRenderer(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getAssetRenderer(classPK);
	}

	public AssetRendererFactory<?> getAssetRendererFactory() {
		String type = ParamUtil.getString(_liferayPortletRequest, "type");

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

		return "x-assigned-the-task-to-herself";
	}

	public Object getAssignedTheTaskToMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = _getActorName(workflowLog);

		return new Object[] {
			HtmlUtil.escape(
				PortalUtil.getUserName(
					workflowLog.getAuditUserId(), StringPool.BLANK)),
			HtmlUtil.escape(actorName)
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getCreateDate(WorkflowLog workflowLog) {
		return _dateFormatDateTime.format(workflowLog.getCreateDate());
	}

	public String getCreateDate(WorkflowTask workflowTask) {
		return _dateFormatDateTime.format(workflowTask.getCreateDate());
	}

	public String getCurrentURL() {
		PortletURL portletURL = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		return portletURL.toString();
	}

	public String getDescription(WorkflowTask workflowTask) {
		return HtmlUtil.escape(workflowTask.getDescription());
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = WorkflowTaskPortletUtil.getWorkflowTaskDisplayStyle(
				_liferayPortletRequest, _getDisplayViews());
		}

		return _displayStyle;
	}

	public Date getDueDate(WorkflowTask workflowTask) {
		return workflowTask.getDueDate();
	}

	public String getDueDateString(WorkflowTask workflowTask) {
		if (workflowTask.getDueDate() == null) {
			return LanguageUtil.get(
				_workflowTaskRequestHelper.getRequest(), "never");
		}

		return _dateFormatDateTime.format(workflowTask.getDueDate());
	}

	public DropdownItemList getFilterOptions() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							new DropdownItemList() {
								{
									add(
										_getFilterNavigationDropdownItem(
											"all"));

									add(
										_getFilterNavigationDropdownItem(
											"pending"));

									add(
										_getFilterNavigationDropdownItem(
											"completed"));
								}
							});
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_workflowTaskRequestHelper.getRequest(),
								"filter"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							new DropdownItemList() {
								{
									add(
										_getOrderByDropdownItem(
											"last-activity-date"));

									add(_getOrderByDropdownItem("due-date"));
								}
							});
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_workflowTaskRequestHelper.getRequest(),
								"order-by"));
					});
			}
		};
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

		WorkflowLog workflowLog = _getWorkflowLog(workflowTask);

		if (workflowLog != null) {
			return workflowLog.getCreateDate();
		}

		return null;
	}

	public String getPreviewOfTitle(WorkflowTask workflowTask)
		throws PortalException {

		String className = _getWorkflowContextEntryClassName(workflowTask);

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

	public String getSearchURL() {
		PortletURL portletURL = _getPortletURL();

		ThemeDisplay themeDisplay =
			_workflowTaskRequestHelper.getThemeDisplay();

		portletURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		return portletURL.toString();
	}

	public String getSortingURL() throws PortletException {
		LiferayPortletResponse response =
			_workflowTaskRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = response.createRenderURL();

		portletURL.setParameter("tabs1", _getTabs1());
		portletURL.setParameter("orderByCol", _getOrderByCol());

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		portletURL.setParameter(
			"orderByType", Objects.equals(orderByType, "asc") ? "desc" : "asc");

		return portletURL.toString();
	}

	public String getState(WorkflowTask workflowTask) throws PortalException {
		long companyId = _getWorkflowCompanyId(workflowTask);
		long groupId = _getWorkflowGroupId(workflowTask);
		String className = _getWorkflowContextEntryClassName(workflowTask);
		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		String state = HtmlUtil.escape(
			WorkflowInstanceLinkLocalServiceUtil.getState(
				companyId, groupId, className, classPK));

		return LanguageUtil.get(_workflowTaskRequestHelper.getRequest(), state);
	}

	public String getTaglibEditURL(WorkflowTask workflowTask)
		throws PortalException, PortletException {

		StringBundler sb = new StringBundler(7);

		sb.append("javascript:Liferay.Util.openWindow({id: '");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append("editAsset', title: '");

		AssetRenderer<?> assetRenderer = getAssetRenderer(workflowTask);

		String assetTitle = HtmlUtil.escape(
			assetRenderer.getTitle(_workflowTaskRequestHelper.getLocale()));

		sb.append(
			LanguageUtil.format(
				_workflowTaskRequestHelper.getRequest(), "edit-x", assetTitle));

		sb.append("', uri:'");

		PortletURL editPortletURL = _getEditPortletURL(workflowTask);

		ThemeDisplay themeDisplay =
			_workflowTaskRequestHelper.getThemeDisplay();

		editPortletURL.setParameter(
			"refererPlid", String.valueOf(themeDisplay.getPlid()));

		editPortletURL.setParameter(
			"workflowTaskId", String.valueOf(workflowTask.getWorkflowTaskId()));

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
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append("viewDiffs', title: '");

		String title = LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(), "diffs");

		sb.append(HtmlUtil.escapeJS(title));

		sb.append("', uri:'");

		PortletURL viewDiffsPortletURL = _getViewDiffsPortletURL(workflowTask);

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

		String actorName = _getActorName(workflowLog);

		return new Object[] {
			HtmlUtil.escape(actorName), HtmlUtil.escape(workflowLog.getState())
		};
	}

	public Locale getTaskContentLocale() {
		String languageId = LanguageUtil.getLanguageId(_httpServletRequest);

		if (Validator.isNotNull(languageId)) {
			return LocaleUtil.fromLanguageId(languageId);
		}

		return _workflowTaskRequestHelper.getLocale();
	}

	public String getTaskContentTitle(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return HtmlUtil.escape(
			workflowHandler.getTitle(classPK, getTaskContentLocale()));
	}

	public String getTaskInitiallyAssignedMessageArguments(
			WorkflowLog workflowLog)
		throws PortalException {

		String actorName = _getActorName(workflowLog);

		return HtmlUtil.escape(actorName);
	}

	public String getTaskName(WorkflowTask workflowTask) {
		return HtmlUtil.escape(workflowTask.getName());
	}

	public Object getTaskUpdateMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = _getActorName(workflowLog);

		return HtmlUtil.escape(actorName);
	}

	public int getTotalItems() throws PortalException {
		WorkflowTaskSearch workflowTaskSearch = getWorkflowTaskSearch();

		return workflowTaskSearch.getTotal();
	}

	public String getTransitionMessage(String transitionName) {
		if (Validator.isNull(transitionName)) {
			return "proceed";
		}

		return HtmlUtil.escape(transitionName);
	}

	public Object getTransitionMessageArguments(WorkflowLog workflowLog)
		throws PortalException {

		String actorName = _getActorName(workflowLog);

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

	public ViewTypeItemList getViewTypes() {
		return new ViewTypeItemList(_getPortletURL(), getDisplayStyle()) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public long getWorkflowContextEntryClassPK(WorkflowTask workflowTask)
		throws PortalException {

		Map<String, Serializable> workflowContext = _getWorkflowContext(
			workflowTask);

		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
	}

	public WorkflowHandler<?> getWorkflowHandler(WorkflowTask workflowTask)
		throws PortalException {

		String className = _getWorkflowContextEntryClassName(workflowTask);

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
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
			_getWorkflowInstanceId(workflowTask), logTypes, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS,
			WorkflowComparatorFactoryUtil.getLogCreateDateComparator(false));
	}

	public WorkflowTask getWorkflowTask() {
		ResultRow resultRow = (ResultRow)_liferayPortletRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		if (resultRow != null) {
			return (WorkflowTask)resultRow.getParameter("workflowTask");
		}

		return (WorkflowTask)_liferayPortletRequest.getAttribute(
			WebKeys.WORKFLOW_TASK);
	}

	public Map<String, Object> getWorkflowTaskActionLinkData() {
		return new HashMap<>();
	}

	public String getWorkflowTaskAssigneeUserName(WorkflowTask workflowTask) {
		return PortalUtil.getUserName(
			workflowTask.getAssigneeUserId(), StringPool.BLANK);
	}

	public String getWorkflowTaskRandomId() {
		String randomId = StringPool.BLANK;

		ResultRow resultRow = (ResultRow)_liferayPortletRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		if (resultRow != null) {
			randomId = StringUtil.randomId();
		}

		return randomId;
	}

	public WorkflowTaskSearch getWorkflowTaskSearch() throws PortalException {
		boolean searchByUserRoles = _isAssignedToMyRolesTabSelected();

		WorkflowTaskSearch workflowTaskSearch = new WorkflowTaskSearch(
			_liferayPortletRequest, _getCurParam(searchByUserRoles),
			_getPortletURL());

		DisplayTerms searchTerms = workflowTaskSearch.getDisplayTerms();

		int total = WorkflowTaskManagerUtil.searchCount(
			_workflowTaskRequestHelper.getCompanyId(),
			_workflowTaskRequestHelper.getUserId(), searchTerms.getKeywords(),
			_getAssetType(searchTerms.getKeywords()), _getCompleted(),
			searchByUserRoles);

		workflowTaskSearch.setTotal(total);

		List<WorkflowTask> results = WorkflowTaskManagerUtil.search(
			_workflowTaskRequestHelper.getCompanyId(),
			_workflowTaskRequestHelper.getUserId(), searchTerms.getKeywords(),
			_getAssetType(searchTerms.getKeywords()), _getCompleted(),
			searchByUserRoles, workflowTaskSearch.getStart(),
			workflowTaskSearch.getEnd(),
			workflowTaskSearch.getOrderByComparator());

		workflowTaskSearch.setResults(results);

		_setWorkflowTaskSearchEmptyResultsMessage(
			workflowTaskSearch, searchByUserRoles, _getCompleted());

		return workflowTaskSearch;
	}

	public String getWorkflowTaskUnassignedUserName() {
		return LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(), "nobody");
	}

	public boolean hasEditPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		PortletURL editPortletURL = _getEditPortletURL(workflowTask);

		if (editPortletURL != null) {
			return true;
		}

		return false;
	}

	public boolean hasOtherAssignees(WorkflowTask workflowTask)
		throws PortalException {

		if (workflowTask.isCompleted()) {
			return false;
		}

		return WorkflowTaskManagerUtil.hasOtherAssignees(
			workflowTask.getWorkflowTaskId(),
			_workflowTaskRequestHelper.getUserId());
	}

	public boolean hasViewDiffsPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		PortletURL viewDiffsPortletURL = _getViewDiffsPortletURL(workflowTask);

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

	public boolean isManagementBarDisabled() throws PortalException {
		WorkflowTaskSearch workflowTaskSearch = getWorkflowTaskSearch();

		return !workflowTaskSearch.hasResults();
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

	private String _getActorName(WorkflowLog workflowLog)
		throws PortalException {

		if (workflowLog.getRoleId() != 0) {
			Role role = _getRole(workflowLog.getRoleId());

			return role.getDescriptiveName();
		}
		else if (workflowLog.getUserId() != 0) {
			User user = _getUser(workflowLog.getUserId());

			return user.getFullName();
		}

		return StringPool.BLANK;
	}

	private String[] _getAssetType(String keywords) {
		for (WorkflowHandler<?> workflowHandler :
				_getSearchableAssetsWorkflowHandlers()) {

			String assetType = workflowHandler.getType(
				_workflowTaskRequestHelper.getLocale());

			if (StringUtil.equalsIgnoreCase(keywords, assetType)) {
				return new String[] {workflowHandler.getClassName()};
			}
		}

		return null;
	}

	private Boolean _getCompleted() {
		if (_isNavigationAll()) {
			return null;
		}

		if (_isNavigationCompleted()) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	private String _getCurParam(boolean searchByUserRoles) {
		Boolean completedTasks = _getCompleted();

		String curParam;

		if (!searchByUserRoles && (completedTasks == null)) {
			curParam = SearchContainer.DEFAULT_CUR_PARAM;
		}
		else if (!searchByUserRoles && completedTasks) {
			curParam = "cur1";
		}
		else if (!searchByUserRoles && !completedTasks) {
			curParam = "cur2";
		}
		else if (searchByUserRoles && (completedTasks == null)) {
			curParam = "cur3";
		}
		else if (searchByUserRoles && completedTasks) {
			curParam = "cur4";
		}
		else {
			curParam = "cur5";
		}

		return curParam;
	}

	private String[] _getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	private PortletURL _getEditPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getURLEdit(
			classPK, _liferayPortletRequest, _liferayPortletResponse);
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getFilterNavigationDropdownItem(String navigation) {

		return dropdownItem -> {
			dropdownItem.setActive(
				Objects.equals(_getNavigation(), navigation));
			dropdownItem.setHref(
				_getPortletURL(), "navigation", navigation, "mvcPath",
				"/view.jsp", "tabs1", _getTabs1());
			dropdownItem.setLabel(
				LanguageUtil.get(
					_workflowTaskRequestHelper.getRequest(), navigation));
		};
	}

	private String _getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return _navigation;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				PortletKeys.MY_WORKFLOW_TASK, "order-by-col",
				"last-activity-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					PortletKeys.MY_WORKFLOW_TASK, "order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(
				Objects.equals(_getOrderByCol(), orderByCol));
			dropdownItem.setHref(_getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_workflowTaskRequestHelper.getRequest(), orderByCol));
		};
	}

	private long[] _getPooledActorsIds(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowTaskManagerUtil.getPooledActorsIds(
			_workflowTaskRequestHelper.getCompanyId(),
			workflowTask.getWorkflowTaskId());
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", _getTabs1());

		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter("navigation", _getNavigation());
		}

		return portletURL;
	}

	private Role _getRole(long roleId) throws PortalException {
		Role role = _roles.get(roleId);

		if (role == null) {
			role = RoleLocalServiceUtil.getRole(roleId);

			_roles.put(roleId, role);
		}

		return role;
	}

	private List<WorkflowHandler<?>> _getSearchableAssetsWorkflowHandlers() {
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

	private String _getTabs1() {
		return ParamUtil.getString(
			_liferayPortletRequest, "tabs1", "assigned-to-me");
	}

	private User _getUser(long userId) throws PortalException {
		User user = _users.get(userId);

		if (user == null) {
			user = UserLocalServiceUtil.getUser(userId);

			_users.put(userId, user);
		}

		return user;
	}

	private PortletURL _getViewDiffsPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getURLViewDiffs(
			classPK, _liferayPortletRequest, _liferayPortletResponse);
	}

	private long _getWorkflowCompanyId(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = _getWorkflowInstance(workflowTask);

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		return GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_COMPANY_ID));
	}

	private Map<String, Serializable> _getWorkflowContext(
			WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = _getWorkflowInstance(workflowTask);

		return workflowInstance.getWorkflowContext();
	}

	private String _getWorkflowContextEntryClassName(WorkflowTask workflowTask)
		throws PortalException {

		Map<String, Serializable> workflowContext = _getWorkflowContext(
			workflowTask);

		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}

	private long _getWorkflowGroupId(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = _getWorkflowInstance(workflowTask);

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		return GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
	}

	private WorkflowInstance _getWorkflowInstance(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowInstanceManagerUtil.getWorkflowInstance(
			_workflowTaskRequestHelper.getCompanyId(),
			_getWorkflowInstanceId(workflowTask));
	}

	private long _getWorkflowInstanceId(WorkflowTask workflowTask) {
		return workflowTask.getWorkflowInstanceId();
	}

	private WorkflowLog _getWorkflowLog(WorkflowTask workflowTask)
		throws PortalException {

		List<WorkflowLog> workflowLogs =
			WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				_workflowTaskRequestHelper.getCompanyId(),
				_getWorkflowInstanceId(workflowTask), null, 0, 1,
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator());

		if (!workflowLogs.isEmpty()) {
			return workflowLogs.get(0);
		}

		return null;
	}

	private boolean _isAssignedToMyRolesTabSelected() {
		String tabs1 = _getTabs1();

		if (tabs1.equals("assigned-to-my-roles")) {
			return true;
		}

		return false;
	}

	private boolean _isNavigationAll() {
		if (Objects.equals(_getNavigation(), "all")) {
			return true;
		}

		return false;
	}

	private boolean _isNavigationCompleted() {
		if (Objects.equals(_getNavigation(), "completed")) {
			return true;
		}

		return false;
	}

	private void _setWorkflowTaskSearchEmptyResultsMessage(
		WorkflowTaskSearch workflowTaskSearch, boolean searchByUserRoles,
		Boolean completedTasks) {

		DisplayTerms searchTerms = workflowTaskSearch.getDisplayTerms();

		if (!searchByUserRoles && (completedTasks == null)) {
			workflowTaskSearch.setEmptyResultsMessage(
				"there-are-no-tasks-assigned-to-you");
		}
		else if (!searchByUserRoles && !completedTasks) {
			workflowTaskSearch.setEmptyResultsMessage(
				"there-are-no-pending-tasks-assigned-to-you");
		}
		else if (searchByUserRoles && (completedTasks == null)) {
			workflowTaskSearch.setEmptyResultsMessage(
				"there-are-no-tasks-assigned-to-your-roles");
		}
		else if (searchByUserRoles && !completedTasks) {
			workflowTaskSearch.setEmptyResultsMessage(
				"there-are-no-pending-tasks-assigned-to-your-roles");
		}
		else {
			workflowTaskSearch.setEmptyResultsMessage(
				"there-are-no-completed-tasks");
		}

		if (Validator.isNotNull(searchTerms.getKeywords())) {
			workflowTaskSearch.setEmptyResultsMessage(
				workflowTaskSearch.getEmptyResultsMessage() +
					"-with-the-specified-search-criteria");
		}
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private final Format _dateFormatDateTime;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private final PortalPreferences _portalPreferences;
	private final Map<Long, Role> _roles = new HashMap<>();
	private final Map<Long, User> _users = new HashMap<>();
	private final WorkflowTaskRequestHelper _workflowTaskRequestHelper;

}