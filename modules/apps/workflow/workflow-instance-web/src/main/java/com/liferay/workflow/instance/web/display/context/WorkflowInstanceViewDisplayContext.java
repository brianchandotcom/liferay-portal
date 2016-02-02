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

package com.liferay.workflow.instance.web.display.context;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.workflow.instance.web.search.WorkflowInstanceSearch;
import com.liferay.workflow.instance.web.util.WorkflowInstancePortletUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Leonardo Barros
 */
public class WorkflowInstanceViewDisplayContext
	extends BaseWorkflowInstanceDisplayContext {

	public WorkflowInstanceViewDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		super(liferayPortletRequest, liferayPortletResponse);

		PortletURL portletURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_searchContainer = new WorkflowInstanceSearch(
			liferayPortletRequest, portletURL);

		_searchContainer.setResults(
			getSearchContainerResults(
				_searchContainer.getStart(), _searchContainer.getEnd(),
				_searchContainer.getOrderByComparator()));

		_searchContainer.setTotal(getSearchContainerTotal());

		setSearchContainerEmptyResultsMessage(_searchContainer);
	}

	public String getAssetIconCssClass(WorkflowInstance workflowInstance)
		throws PortalException {

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowInstance);

		return workflowHandler.getIconCssClass();
	}

	public String getAssetTitle(WorkflowInstance workflowInstance) {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowInstance);

		long classPK = getWorkflowContextEntryClassPK(
			workflowInstance.getWorkflowContext());

		return HtmlUtil.escape(
			workflowHandler.getTitle(
				classPK, workflowInstanceRequestHelper.getLocale()));
	}

	public String getAssetType(WorkflowInstance workflowInstance) {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowInstance);

		return workflowHandler.getType(
			workflowInstanceRequestHelper.getLocale());
	}

	public String getDefinition(WorkflowInstance workflowInstance) {
		return LanguageUtil.get(
			workflowInstanceRequestHelper.getRequest(),
			HtmlUtil.escape(workflowInstance.getWorkflowDefinitionName()));
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = WorkflowInstancePortletUtil.getDisplayStyle(
				liferayPortletRequest, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public Date getEndDate(WorkflowInstance workflowInstance) {
		return workflowInstance.getEndDate();
	}

	public String getHeaderTitle() {
		return "workflow-submissions";
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(liferayPortletRequest, "keywords");

		return _keywords;
	}

	public Date getLastActivityDate(WorkflowInstance workflowInstance)
		throws PortalException {

		WorkflowLog workflowLog = getLatestWorkflowLog(workflowInstance);

		if (workflowLog == null) {
			return null;
		}

		return workflowLog.getCreateDate();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(request, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(request, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = portalPreferences.getValue(
				PortletKeys.MY_WORKFLOW_INSTANCE, "order-by-col",
				"last-activity-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					PortletKeys.MY_WORKFLOW_INSTANCE, "order-by-col",
					_orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(request, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = portalPreferences.getValue(
				PortletKeys.MY_WORKFLOW_INSTANCE, "order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					PortletKeys.MY_WORKFLOW_INSTANCE, "order-by-type",
					_orderByType);
			}
		}

		return _orderByType;
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

	public WorkflowInstanceSearch getSearchContainer() {
		return _searchContainer;
	}

	public String getStatus(WorkflowInstance workflowInstance) {
		return LanguageUtil.get(
			workflowInstanceRequestHelper.getRequest(),
			HtmlUtil.escape(workflowInstance.getState()));
	}

	public PortletURL getViewPortletURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		return portletURL;
	}

	public boolean isNavigationAll() {
		if (Validator.equals(getNavigation(), "all")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationCompleted() {
		if (Validator.equals(getNavigation(), "completed")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationPending() {
		if (Validator.equals(getNavigation(), "pending")) {
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

	protected String getAssetType(String keywords) {
		for (WorkflowHandler<?> workflowHandler :
				getSearchableAssetsWorkflowHandlers()) {

			String assetType = workflowHandler.getType(
				workflowInstanceRequestHelper.getLocale());

			if (StringUtil.equalsIgnoreCase(keywords, assetType)) {
				return workflowHandler.getClassName();
			}
		}

		return StringPool.BLANK;
	}

	protected Boolean getCompleted() {
		if (isNavigationAll()) {
			return null;
		}

		if (isNavigationCompleted()) {
			return Boolean.TRUE;
		}
		else {
			return Boolean.FALSE;
		}
	}

	protected WorkflowLog getLatestWorkflowLog(
			WorkflowInstance workflowInstance)
		throws PortalException {

		List<WorkflowLog> workflowLogs =
			WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				workflowInstanceRequestHelper.getCompanyId(),
				workflowInstance.getWorkflowInstanceId(), null, 0, 1,
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator());

		if (workflowLogs.isEmpty()) {
			return null;
		}

		return workflowLogs.get(0);
	}

	protected List<WorkflowInstance> getSearchContainerResults(
			int start, int end, OrderByComparator<WorkflowInstance> comparator)
		throws PortalException {

		return WorkflowInstanceManagerUtil.search(
			workflowInstanceRequestHelper.getCompanyId(), null,
			getAssetType(getKeywords()), getKeywords(), getKeywords(),
			getCompleted(), start, end, comparator);
	}

	protected int getSearchContainerTotal() throws PortalException {
		return WorkflowInstanceManagerUtil.searchCount(
			workflowInstanceRequestHelper.getCompanyId(), null,
			getAssetType(getKeywords()), getKeywords(), getKeywords(),
			getCompleted());
	}

	protected String getWorkflowContextEntryClassName(
		Map<String, Serializable> workflowContext) {

		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}

	protected long getWorkflowContextEntryClassPK(
		Map<String, Serializable> workflowContext) {

		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
	}

	protected WorkflowHandler<?> getWorkflowHandler(
		WorkflowInstance workflowInstance) {

		String className = getWorkflowContextEntryClassName(
			workflowInstance.getWorkflowContext());

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
	}

	protected void setSearchContainerEmptyResultsMessage(
		WorkflowInstanceSearch searchContainer) {

		DisplayTerms searchTerms = searchContainer.getDisplayTerms();

		if (isNavigationAll()) {
			searchContainer.setEmptyResultsMessage("there-are-no-instances");
		}
		else if (isNavigationPending()) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-pending-instances");
		}
		else {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-completed-instances");
		}

		if (Validator.isNotNull(searchTerms.getKeywords())) {
			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() +
					"-with-the-specified-search-criteria");
		}
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private String _displayStyle;
	private String _keywords;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final WorkflowInstanceSearch _searchContainer;

}