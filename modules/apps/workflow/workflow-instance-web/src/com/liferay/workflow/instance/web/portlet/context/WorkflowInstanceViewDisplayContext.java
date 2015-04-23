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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.workflow.instance.web.portlet.constants.WorkflowInstancePortletKeys;

import java.io.Serializable;

import java.text.Format;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class WorkflowInstanceViewDisplayContext {

	public WorkflowInstanceViewDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		RenderResponse renderResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_renderResponse = renderResponse;
		_allInstances = false;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		_company = themeDisplay.getCompany();
		_locale = themeDisplay.getLocale();
		_userId = themeDisplay.getUserId();
		_portletName = themeDisplay.getPortletDisplay().getPortletName();

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			_locale, themeDisplay.getTimeZone());

		_tabs2 = ParamUtil.getString(
			_liferayPortletRequest, "tabs2", "pending");
	}

	public String getAssetTitle(WorkflowInstance workflowInstance) {
		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		long classPK = GetterUtil.getLong((String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowInstance);

		return HtmlUtil.escape(workflowHandler.getTitle(classPK, _locale));
	}

	public String getAssetType(WorkflowInstance workflowInstance) {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowInstance);
		return workflowHandler.getType(_locale);
	}

	public String getDefinition(WorkflowInstance workflowInstance) {
		return LanguageUtil.get(
			_liferayPortletRequest.getHttpServletRequest(),
			HtmlUtil.escape(workflowInstance.getWorkflowDefinitionName()));
	}

	public String getEndDate(WorkflowInstance workflowInstance)
		throws WorkflowException {

		if (workflowInstance.getEndDate() == null ) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "never");
		}
		else {
			return _dateFormatDateTime.format(workflowInstance.getEndDate());
		}
	}

	public String getLastActivityDate(WorkflowInstance workflowInstance)
		throws WorkflowException {

		WorkflowLog workflowLog = getWorkflowLog(workflowInstance);
		return (workflowLog == null) ? LanguageUtil.get(
			_liferayPortletRequest.getHttpServletRequest(), "never") :
				_dateFormatDateTime.format(workflowLog.getCreateDate());
	}

	public String getStatus(WorkflowInstance workflowInstance) {
		return LanguageUtil.get(
			_liferayPortletRequest.getHttpServletRequest(),
			HtmlUtil.escape(workflowInstance.getState()));
	}

	public PortletURL getViewPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();
		portletURL.setParameter("tabs1", "submissions");
		portletURL.setParameter("tabs2", _tabs2);
		return portletURL;
	}

	public String getViewTab2() {
		return _tabs2;
	}

	public boolean isCompletedInstances() {
		return !_tabs2.equals("pending");
	}

	public void loadSearchContainer(
			SearchContainer<WorkflowInstance> searchContainer)
		throws WorkflowException {

		String[] assetTypes = WorkflowHandlerUtil.getSearchableAssetTypes();
		List<WorkflowInstance> results = null;
		int total = 0;

		if (_portletName.equals(PortletKeys.WORKFLOW_DEFINITIONS) ||
			_portletName.equals(
				WorkflowInstancePortletKeys.WORKFLOW_INSTANCE)) {

			if (isCompletedInstances()) {
				searchContainer.setEmptyResultsMessage(
					"there-are-no-completed-instances");
			}
			else {
				searchContainer.setEmptyResultsMessage(
					"there-are-no-pending-instances");
			}

			_allInstances = true;

			total = WorkflowInstanceManagerUtil.getWorkflowInstanceCount(
				_company.getCompanyId(), null, assetTypes,
				isCompletedInstances());

			results = WorkflowInstanceManagerUtil.getWorkflowInstances(
				_company.getCompanyId(), null, assetTypes,
				isCompletedInstances(), searchContainer.getStart(),
				searchContainer.getEnd(),
				WorkflowComparatorFactoryUtil.getInstanceStartDateComparator());
		}
		else {
			if (isCompletedInstances()) {
				searchContainer.setEmptyResultsMessage(
					"there-are-no-completed-instances-started-by-me");
			}
			else {
				searchContainer.setEmptyResultsMessage(
					"there-are-no-pending-instances-started-by-me");
			}

			total = WorkflowInstanceManagerUtil.getWorkflowInstanceCount(
				_company.getCompanyId(), _userId, assetTypes,
				isCompletedInstances());

			searchContainer.setTotal(total);

			results = WorkflowInstanceManagerUtil.getWorkflowInstances(
				_company.getCompanyId(), _userId, assetTypes,
				isCompletedInstances(), searchContainer.getStart(),
				searchContainer.getEnd(),
				WorkflowComparatorFactoryUtil.getInstanceStartDateComparator());
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(results);
	}

	public boolean showEntryAction() {
		return !_allInstances && !_tabs2.equals("completed");
	}

	protected WorkflowHandler<?> getWorkflowHandler(
		WorkflowInstance workflowInstance) {

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		String className = (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
	}

	protected WorkflowLog getWorkflowLog(WorkflowInstance workflowInstance)
		throws WorkflowException {

		List<WorkflowLog> workflowLogs =
			WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				_company.getCompanyId(),
				workflowInstance.getWorkflowInstanceId(), null, 0, 1,
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator());

		WorkflowLog workflowLog = null;

		if (!workflowLogs.isEmpty()) {
			workflowLog = workflowLogs.get(0);
		}

		return workflowLog;
	}

	private boolean _allInstances;
	private final Company _company;
	private final Format _dateFormatDateTime;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final Locale _locale;
	private final String _portletName;
	private final RenderResponse _renderResponse;
	private String _tabs2;
	private final long _userId;

}