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

package com.liferay.workflow.task.web.portlet.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.workflow.task.web.portlet.search.WorkflowTaskDisplayTerms;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Leonardo Barros
 */
public class WorkflowTaskViewDisplayContext {

	public WorkflowTaskViewDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			List<WorkflowTask> workflowTasks)
		throws Exception {

		_liferayPortletRequest = liferayPortletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		_workflowHandlers = new HashMap<>();
		_workflowContextClassPK = new HashMap<>();
		_workflowLogMap = new HashMap<>();

		_selectedTab = ParamUtil.getString(
			_liferayPortletRequest, "tabs1", _PENDING);

		_portletURL = liferayPortletResponse.createRenderURL();
		_portletURL.setParameter("tabs1", _selectedTab);

		_displayTerms = new WorkflowTaskDisplayTerms(liferayPortletRequest);

		_locale = themeDisplay.getLocale();

		_dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			_locale, themeDisplay.getTimeZone());

		_workflowHandlersOfSearchableAssets = new ArrayList<>();

		List<WorkflowHandler<?>> workflowHandlers =
			WorkflowHandlerRegistryUtil.getWorkflowHandlers();

		for (WorkflowHandler<?> workflowHandler : workflowHandlers) {
			if (workflowHandler.isAssetTypeSearchable()) {
				_workflowHandlersOfSearchableAssets.add(workflowHandler);
			}
		}

		for (WorkflowTask workflowTask : workflowTasks) {
			if (!_workflowHandlers.containsKey(
					workflowTask.getWorkflowInstanceId())) {

				WorkflowInstance workflowInstance =
					WorkflowInstanceManagerUtil.getWorkflowInstance(
						company.getCompanyId(),
						workflowTask.getWorkflowInstanceId());

				Map<String, Serializable> workflowContext =
					workflowInstance.getWorkflowContext();

				String className = (String)workflowContext.get(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);

				long classPK = GetterUtil.getLong((String)workflowContext.get(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

				WorkflowHandler<?> workflowHandler =
					WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

				_workflowHandlers.put(
					workflowTask.getWorkflowInstanceId(), workflowHandler);
				_workflowContextClassPK.put(
					workflowTask.getWorkflowInstanceId(), classPK);
			}

			if (!_workflowLogMap.containsKey(
					workflowTask.getWorkflowInstanceId())) {

				List<WorkflowLog> workflowLogs =
					WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
						company.getCompanyId(),
						workflowTask.getWorkflowInstanceId(), null, 0, 1,
					WorkflowComparatorFactoryUtil.getLogCreateDateComparator());

				if (!workflowLogs.isEmpty()) {
					WorkflowLog workflowLog = workflowLogs.get(0);
					_workflowLogMap.put(
						workflowTask.getWorkflowInstanceId(), workflowLog);
				}
			}
		}
	}

	public String getAssetTitle(WorkflowTask workflowTask) {
		long classPK = getWorkflowContextClassPK(
			workflowTask.getWorkflowInstanceId());
		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowTask.getWorkflowInstanceId());

		return HtmlUtil.escape(workflowHandler.getTitle(classPK, _locale));
	}

	public String getAssetType(WorkflowTask workflowTask) {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowTask.getWorkflowInstanceId());
		return workflowHandler.getType(_locale);
	}

	public WorkflowTaskDisplayTerms getDisplayTerms() {
		return _displayTerms;
	}

	public String getDueDate(WorkflowTask workflowTask) {
		if (workflowTask.getDueDate() == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), _NEVER);
		}
		else {
			return _dateFormatDateTime.format(workflowTask.getDueDate());
		}
	}

	public String getLastActivityDate(WorkflowTask workflowTask) {
		WorkflowLog workflowLog = getWorkflowLog(
			workflowTask.getWorkflowInstanceId());

		if (workflowLog == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), _NEVER);
		}
		else {
			return _dateFormatDateTime.format(workflowLog.getCreateDate());
		}
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public String getSelectedTab() {
		return _selectedTab;
	}

	public String getTaskName(WorkflowTask workflowTask) {
		return HtmlUtil.escape(workflowTask.getName());
	}

	public long getWorkflowContextClassPK(long workflowInstanceId) {
		return _workflowContextClassPK.get(workflowInstanceId);
	}

	public WorkflowHandler<?> getWorkflowHandler(long workflowInstanceId) {
		return _workflowHandlers.get(workflowInstanceId);
	}

	public List<WorkflowHandler<?>> getWorkflowHandlersOfSearchableAssets() {
		return _workflowHandlersOfSearchableAssets;
	}

	public WorkflowLog getWorkflowLog(long workflowInstanceId) {
		if (_workflowLogMap.containsKey(workflowInstanceId)) {
			return _workflowLogMap.get(workflowInstanceId);
		}

		return null;
	}

	public boolean isCompletedTab() {
		return _selectedTab.equals(_COMPLETED);
	}

	public boolean isPendingTab() {
		return _selectedTab.equals(_PENDING);
	}

	private static final String _COMPLETED = "completed";

	private static final String _NEVER = "never";

	private static final String _PENDING = "pending";

	private final Format _dateFormatDateTime;
	private final WorkflowTaskDisplayTerms _displayTerms;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final Locale _locale;
	private final PortletURL _portletURL;
	private final String _selectedTab;
	private final Map<Long, Long> _workflowContextClassPK;
	private final Map<Long, WorkflowHandler<?>> _workflowHandlers;
	private final List<WorkflowHandler<?>> _workflowHandlersOfSearchableAssets;
	private final Map<Long, WorkflowLog> _workflowLogMap;

}