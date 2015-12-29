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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.workflow.instance.web.search.WorkflowInstanceSearch;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

	public Date getEndDate(WorkflowInstance workflowInstance) {
		return workflowInstance.getEndDate();
	}

	public Date getLastActivityDate(WorkflowInstance workflowInstance)
		throws PortalException {

		WorkflowLog workflowLog = getLatestWorkflowLog(workflowInstance);

		if (workflowLog == null) {
			return null;
		}

		return workflowLog.getCreateDate();
	}

	public WorkflowInstanceSearch getSearchContainer() {
		return _searchContainer;
	}

	public String getStatus(WorkflowInstance workflowInstance) {
		return LanguageUtil.get(
			workflowInstanceRequestHelper.getRequest(),
			HtmlUtil.escape(workflowInstance.getState()));
	}

	public String getTabs2() {
		return ParamUtil.getString(renderRequest, "tabs2", "pending");
	}

	public PortletURL getViewPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", "submissions");
		portletURL.setParameter("tabs2", getTabs2());

		return portletURL;
	}

	public boolean isShowEntryAction() {
		if (isShowCompletedInstances()) {
			return false;
		}

		return true;
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

	protected boolean isShowCompletedInstances() {
		String tabs2 = getTabs2();

		if (tabs2.equals("completed")) {
			return true;
		}

		return false;
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortalPreferences _portalPreferences;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private final WorkflowInstanceSearch _searchContainer;

}