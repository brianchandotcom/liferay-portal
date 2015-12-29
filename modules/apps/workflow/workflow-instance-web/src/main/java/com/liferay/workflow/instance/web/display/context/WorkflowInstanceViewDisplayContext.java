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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
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
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.workflow.instance.web.configuration.WorkflowInstanceWebConfiguration;
import com.liferay.workflow.instance.web.search.WorkflowInstanceSearch;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class WorkflowInstanceViewDisplayContext
	extends BaseWorkflowInstanceDisplayContext {

	public WorkflowInstanceViewDisplayContext(
			HttpServletRequest request,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			PortletPreferences portletPreferences)
		throws PortalException {

		super(
			request, liferayPortletRequest, liferayPortletResponse,
			portletPreferences);

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletPreferences = portletPreferences;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_request);

		PortletURL portletURL = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		_searchContainer = new WorkflowInstanceSearch(
			_liferayPortletRequest, portletURL);
		_searchContainer.setEmptyResultsMessage(
			getSearchContainerEmptyResultsMessage());

		_searchContainer.setResults(
			getSearchContainerResults(
				_searchContainer.getStart(), _searchContainer.getEnd(),
				_searchContainer.getOrderByComparator()));

		_searchContainer.setTotal(getSearchContainerTotal());
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
			_displayStyle = getDisplayStyle(_request, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			WorkflowInstanceWebConfiguration workflowInstanceWebConfiguration =
				(WorkflowInstanceWebConfiguration) _liferayPortletRequest.
					getAttribute(
						WorkflowInstanceWebConfiguration.class.getName());

			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _liferayPortletRequest, "displayViews",
					StringUtil.merge(
						workflowInstanceWebConfiguration.displayViews())));
		}

		return _displayViews;
	}

	public Date getEndDate(WorkflowInstance workflowInstance) {
		return workflowInstance.getEndDate();
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_liferayPortletRequest, "keywords");

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

	public WorkflowInstanceSearch getSearchContainer() {
		return _searchContainer;
	}

	public String getStatus(WorkflowInstance workflowInstance) {
		return LanguageUtil.get(
			workflowInstanceRequestHelper.getRequest(),
			HtmlUtil.escape(workflowInstance.getState()));
	}

	public PortletURL getViewPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

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

	protected String getDisplayStyle(
		HttpServletRequest request, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		String displayStyle = ParamUtil.getString(request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			WorkflowInstanceWebConfiguration workflowTaskWebConfiguration =
				(WorkflowInstanceWebConfiguration)_request.getAttribute(
					WorkflowInstanceWebConfiguration.class.getName());

			displayStyle = portalPreferences.getValue(
				PortletKeys.MY_WORKFLOW_INSTANCE, "display-style",
				workflowTaskWebConfiguration.defaultDisplayView());
		}
		else {
			if (ArrayUtil.contains(displayViews, displayStyle)) {
				portalPreferences.setValue(
					PortletKeys.MY_WORKFLOW_INSTANCE, "display-style",
					displayStyle);

				request.setAttribute(
					WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
			}
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
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

	protected String getSearchContainerEmptyResultsMessage() {
		if (isShowCompletedInstances()) {
			return "there-are-no-completed-instances";
		}
		else {
			return "there-are-no-pending-instances";
		}
	}

	protected List<WorkflowInstance> getSearchContainerResults(
			int start, int end, OrderByComparator<WorkflowInstance> comparator)
		throws PortalException {

		return WorkflowInstanceManagerUtil.getWorkflowInstances(
			workflowInstanceRequestHelper.getCompanyId(), null,
			WorkflowHandlerUtil.getSearchableAssetTypes(),
			isShowCompletedInstances(), start, end, comparator);
	}

	protected int getSearchContainerTotal() throws PortalException {
		return WorkflowInstanceManagerUtil.getWorkflowInstanceCount(
			workflowInstanceRequestHelper.getCompanyId(), null,
			WorkflowHandlerUtil.getSearchableAssetTypes(),
			isShowCompletedInstances());
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


	private String _displayStyle;
	private String[] _displayViews;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private final WorkflowInstanceSearch _searchContainer;

}