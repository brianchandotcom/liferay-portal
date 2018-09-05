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
import java.util.function.Consumer;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

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

		_request = PortalUtil.getHttpServletRequest(liferayPortletRequest);

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_request);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());

		_workflowTaskRequestHelper = new WorkflowTaskRequestHelper(_request);
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

		String actorName = getActorName(workflowLog);

		return new Object[] {
			HtmlUtil.escape(
				PortalUtil.getUserName(
					workflowLog.getAuditUserId(), StringPool.BLANK)),
			HtmlUtil.escape(actorName)
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

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
				_liferayPortletRequest, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
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

	public PortletURL getEditPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		return workflowHandler.getURLEdit(
			classPK, _liferayPortletRequest, _liferayPortletResponse);
	}

	public String getEditTaskName(WorkflowTask workflowTask) {
		return LanguageUtil.get(
			_workflowTaskRequestHelper.getRequest(),
			HtmlUtil.escape(workflowTask.getName()));
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

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_liferayPortletRequest, "keywords");

		return _keywords;
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

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_request, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				PortletKeys.MY_WORKFLOW_TASK, "order-by-col",
				"last-activity-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					PortletKeys.MY_WORKFLOW_TASK, "order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portalPreferences.getValue(
				PortletKeys.MY_WORKFLOW_TASK, "order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					PortletKeys.MY_WORKFLOW_TASK, "order-by-type",
					_orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", getTabs1());

		String navigation = ParamUtil.getString(_request, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter("navigation", getNavigation());
		}

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

	public WorkflowTaskSearch getSearchContainer() throws PortalException {
		boolean searchByUserRoles = isAssignedToMyRolesTabSelected();

		WorkflowTaskSearch searchContainer = new WorkflowTaskSearch(
			_liferayPortletRequest, getCurParam(searchByUserRoles),
			getPortletURL());

		DisplayTerms searchTerms = searchContainer.getDisplayTerms();

		int total = WorkflowTaskManagerUtil.searchCount(
			_workflowTaskRequestHelper.getCompanyId(),
			_workflowTaskRequestHelper.getUserId(), searchTerms.getKeywords(),
			getAssetType(searchTerms.getKeywords()), getCompleted(),
			searchByUserRoles);

		searchContainer.setTotal(total);

		List<WorkflowTask> results = WorkflowTaskManagerUtil.search(
			_workflowTaskRequestHelper.getCompanyId(),
			_workflowTaskRequestHelper.getUserId(), searchTerms.getKeywords(),
			getAssetType(searchTerms.getKeywords()), getCompleted(),
			searchByUserRoles, searchContainer.getStart(),
			searchContainer.getEnd(), searchContainer.getOrderByComparator());

		searchContainer.setResults(results);

		setSearchContainerEmptyResultsMessage(
			searchContainer, searchByUserRoles, getCompleted());

		return searchContainer;
	}

	public String getSearchURL() {
		PortletURL portletURL = getPortletURL();

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

		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter("orderByCol", getOrderByCol());

		String orderByType = ParamUtil.getString(
			_request, "orderByType", "asc");

		portletURL.setParameter(
			"orderByType", Objects.equals(orderByType, "asc") ? "desc" : "asc");

		return portletURL.toString();
	}

	public String getState(WorkflowTask workflowTask) throws PortalException {
		long companyId = getWorkflowCompanyId(workflowTask);
		long groupId = getWorkflowGroupId(workflowTask);
		String className = getWorkflowContextEntryClassName(workflowTask);
		long classPK = getWorkflowContextEntryClassPK(workflowTask);

		String state = HtmlUtil.escape(
			WorkflowInstanceLinkLocalServiceUtil.getState(
				companyId, groupId, className, classPK));

		return LanguageUtil.get(_workflowTaskRequestHelper.getRequest(), state);
	}

	public String getTabs1() {
		return ParamUtil.getString(
			_liferayPortletRequest, "tabs1", "assigned-to-me");
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

		PortletURL editPortletURL = getEditPortletURL(workflowTask);

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

	public Locale getTaskContentLocale() {
		String languageId = LanguageUtil.getLanguageId(_request);

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

	public int getTotalItems() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		return searchContainer.getTotal();
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
			classPK, _liferayPortletRequest, _liferayPortletResponse);
	}

	public ViewTypeItemList getViewTypes() {
		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public WindowState getWindowState() {
		return _liferayPortletRequest.getWindowState();
	}

	public long getWorkflowCompanyId(WorkflowTask workflowTask)
		throws PortalException {

		WorkflowInstance workflowInstance = getWorkflowInstance(workflowTask);

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		return GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_COMPANY_ID));
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

		return GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
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
		Map<String, Object> data = new HashMap<>();

		return data;
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

		if (workflowTask.isCompleted()) {
			return false;
		}

		return WorkflowTaskManagerUtil.hasOtherAssignees(
			workflowTask.getWorkflowTaskId(),
			_workflowTaskRequestHelper.getUserId());
	}

	public boolean hasViewDiffsPortletURL(WorkflowTask workflowTask)
		throws PortalException {

		PortletURL viewDiffsPortletURL = getViewDiffsPortletURL(workflowTask);

		if (viewDiffsPortletURL != null) {
			return true;
		}

		return false;
	}

	public boolean isAssignedToMeTabSelected() {
		String tabs1 = getTabs1();

		if (tabs1.equals("assigned-to-me")) {
			return true;
		}

		return false;
	}

	public boolean isAssignedToMyRolesTabSelected() {
		String tabs1 = getTabs1();

		if (tabs1.equals("assigned-to-my-roles")) {
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
		SearchContainer searchContainer = getSearchContainer();

		return !searchContainer.hasResults();
	}

	public boolean isNavigationAll() {
		if (Objects.equals(getNavigation(), "all")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationCompleted() {
		if (Objects.equals(getNavigation(), "completed")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationPending() {
		if (Objects.equals(getNavigation(), "pending")) {
			return true;
		}

		return false;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
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

	protected String[] getAssetType(String keywords) {
		for (WorkflowHandler<?> workflowHandler :
				getSearchableAssetsWorkflowHandlers()) {

			String assetType = workflowHandler.getType(
				_workflowTaskRequestHelper.getLocale());

			if (StringUtil.equalsIgnoreCase(keywords, assetType)) {
				return new String[] {workflowHandler.getClassName()};
			}
		}

		return null;
	}

	protected Boolean getCompleted() {
		if (isNavigationAll()) {
			return null;
		}

		if (isNavigationCompleted()) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	protected String getCurParam(boolean searchByUserRoles) {
		Boolean completedTasks = getCompleted();

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

	protected long[] getPooledActorsIds(WorkflowTask workflowTask)
		throws PortalException {

		return WorkflowTaskManagerUtil.getPooledActorsIds(
			_workflowTaskRequestHelper.getCompanyId(),
			workflowTask.getWorkflowTaskId());
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

	protected void setSearchContainerEmptyResultsMessage(
		WorkflowTaskSearch searchContainer, boolean searchByUserRoles,
		Boolean completedTasks) {

		DisplayTerms searchTerms = searchContainer.getDisplayTerms();

		if (!searchByUserRoles && (completedTasks == null)) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-tasks-assigned-to-you");
		}
		else if (!searchByUserRoles && !completedTasks) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-pending-tasks-assigned-to-you");
		}
		else if (searchByUserRoles && (completedTasks == null)) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-tasks-assigned-to-your-roles");
		}
		else if (searchByUserRoles && !completedTasks) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-pending-tasks-assigned-to-your-roles");
		}
		else {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-completed-tasks");
		}

		if (Validator.isNotNull(searchTerms.getKeywords())) {
			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() +
					"-with-the-specified-search-criteria");
		}
	}

	private Consumer<DropdownItem> _getFilterNavigationDropdownItem(
		String navigation) {

		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(getNavigation(), navigation));
			dropdownItem.setHref(
				getPortletURL(), "navigation", navigation, "mvcPath",
				"/view.jsp", "tabs1", getTabs1());
			dropdownItem.setLabel(
				LanguageUtil.get(
					_workflowTaskRequestHelper.getRequest(), navigation));

		};
	}

	private Consumer<DropdownItem> _getOrderByDropdownItem(String orderByCol) {
		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(getOrderByCol(), orderByCol));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_workflowTaskRequestHelper.getRequest(), orderByCol));
		};
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private final Format _dateFormatDateTime;
	private String _displayStyle;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final HttpServletRequest _request;
	private final Map<Long, Role> _roles = new HashMap<>();
	private final Map<Long, User> _users = new HashMap<>();
	private final WorkflowTaskRequestHelper _workflowTaskRequestHelper;

}