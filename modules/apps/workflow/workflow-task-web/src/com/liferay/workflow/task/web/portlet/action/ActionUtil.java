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

package com.liferay.workflow.task.web.portlet.action;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowHandlerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.workflow.task.web.portlet.context.WorkflowTaskViewDisplayContext;
import com.liferay.workflow.task.web.portlet.search.WorkflowTaskDisplayTerms;
import com.liferay.workflow.task.web.portlet.search.WorkflowTaskSearch;

/**
 * @author Marcellus Tavares
 * @author Leonardo Barros
 */
public class ActionUtil {

	public static void getWorkflowTask(LiferayPortletRequest request, 
		long workflowTaskId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		WorkflowTask workflowTask = WorkflowTaskManagerUtil.getWorkflowTask(
			themeDisplay.getCompanyId(), workflowTaskId);

		request.setAttribute(WebKeys.WORKFLOW_TASK, workflowTask);
	}
	
	public static void searchWorkflowTasks(LiferayPortletRequest request,
		LiferayPortletResponse response)
		throws Exception {
		
		String tabs = ParamUtil.getString(request, _TABS1, _PENDING);
		
		String[] assetTypes = WorkflowHandlerUtil.getSearchableAssetTypes();
		
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();
		User user = themeDisplay.getUser();
		
		PortletURL currentURLObj = PortletURLUtil.getCurrent(
				request, response);
		
		List<WorkflowTask> tasks = new ArrayList<WorkflowTask>();
		
		if(tabs.equals(_COMPLETED)) {
			
			List<WorkflowTask> workflowTasks = searchTasksAssignedToMe(request, 
				company, user, currentURLObj, assetTypes, true);
			
			tasks.addAll(workflowTasks);
			
		}
		else if(tabs.equals(_PENDING)) {
			
			List<WorkflowTask> workflowTasks = 
				searchPendingTasksAssignedToMyRoles(request, company, user, 
				currentURLObj, assetTypes);
			
			tasks.addAll(workflowTasks);
			
			workflowTasks = searchTasksAssignedToMe(request, company, user, 
				currentURLObj, assetTypes, false);
			
			tasks.addAll(workflowTasks);
		}
		
		WorkflowTaskViewDisplayContext displayContext = 
			new WorkflowTaskViewDisplayContext(request, response, tasks);
		
		request.setAttribute(WebKeys.WORKFLOW_TASK_DISPLAY_CONTEXT, 
			displayContext);
	}
	
	private static List<WorkflowTask> searchTasksAssignedToMe(
		LiferayPortletRequest request, Company company, User user, 
		PortletURL currentURLObj, String[] assetTypes, boolean completedTasks)
		throws Exception {
		
		int total = 0;
		List<WorkflowTask> results = null;
		
		String curParam = SearchContainer.DEFAULT_CUR_PARAM;
		
		if(!completedTasks) {
			curParam = _DEFAULT_CUR_PARAM1;
		}
		
		WorkflowTaskSearch searchContainer = new WorkflowTaskSearch(
				request, curParam, currentURLObj);
		WorkflowTaskDisplayTerms searchTerms = 
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();
		
		if (searchTerms.isAdvancedSearch()) {
			total = WorkflowTaskManagerUtil.searchCount(company.getCompanyId(), 
				user.getUserId(), searchTerms.getName(), searchTerms.getType(), 
				null, null, null, false, false, searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(company.getCompanyId(), 
				user.getUserId(), searchTerms.getName(), searchTerms.getType(), 
				null, null, null, completedTasks, false, 
				searchTerms.isAndOperator(), searchContainer.getStart(), 
				searchContainer.getEnd(), 
				searchContainer.getOrderByComparator());
		}
		else {
			total = WorkflowTaskManagerUtil.searchCount(company.getCompanyId(), 
				user.getUserId(), searchTerms.getKeywords(), assetTypes, 
				completedTasks, false);

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(company.getCompanyId(), 
				user.getUserId(), searchTerms.getKeywords(), assetTypes, 
				completedTasks, false, searchContainer.getStart(), 
				searchContainer.getEnd(), 
				searchContainer.getOrderByComparator());
		}
		
		searchContainer.setTotal(total);
		searchContainer.setResults(results);
		
		validateSearchTerms(searchContainer);
		
		if(completedTasks) {
			
			searchContainer.setEmptyResultsMessage(
				_THERE_ARE_NO_COMPLETED_TASKS);
			
			request.setAttribute(WebKeys.WORKFLOW_MY_COMPLETED_TASKS, 
				searchContainer);
			
		}
		else {
		
			searchContainer.setEmptyResultsMessage(
				_THERE_ARE_NO_PENDING_TASKS_ASSIGNED_TO_YOU);
			
			request.setAttribute(WebKeys.WORKFLOW_PENDING_TASKS_ASSIGNED_TO_ME, 
				searchContainer);
		}
		
		return results;
	}
	
	private static List<WorkflowTask> searchPendingTasksAssignedToMyRoles(
			LiferayPortletRequest request, Company company, User user, 
			PortletURL currentURLObj, String[] assetTypes)
			throws Exception {
		
		int total = 0;
		List<WorkflowTask> results = null;
		
		WorkflowTaskSearch searchContainer = new WorkflowTaskSearch(
				request, _DEFAULT_CUR_PARAM2, currentURLObj);
		WorkflowTaskDisplayTerms searchTerms = 
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();
		
		if (searchTerms.isAdvancedSearch()) {
			total = WorkflowTaskManagerUtil.searchCount(company.getCompanyId(), 
				user.getUserId(), searchTerms.getName(), searchTerms.getType(), 
				null, null, null, false, true, searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(company.getCompanyId(), 
				user.getUserId(), searchTerms.getName(), searchTerms.getType(), 
				null, null, null, false, true, searchTerms.isAndOperator(), 
				searchContainer.getStart(), searchContainer.getEnd(), 
				searchContainer.getOrderByComparator());
		}
		else {
			total = WorkflowTaskManagerUtil.searchCount(company.getCompanyId(), 
				user.getUserId(), searchTerms.getKeywords(), assetTypes, false, 
				true);

			searchContainer.setTotal(total);

			results = WorkflowTaskManagerUtil.search(company.getCompanyId(), 
				user.getUserId(), searchTerms.getKeywords(), assetTypes, false, 
				true, searchContainer.getStart(), searchContainer.getEnd(), 
				searchContainer.getOrderByComparator());
		}
		
		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		validateSearchTerms(searchContainer);
		
		searchContainer.setEmptyResultsMessage(
				_THERE_ARE_NO_PENDING_TASKS_ASSIGNED_TO_YOUR_ROLES);
		
		request.setAttribute(
			WebKeys.WORKFLOW_PENDING_TASKS_ASSIGNED_TO_MY_ROLES, 
			searchContainer);
		
		return results;
	}
	
	private static void validateSearchTerms(
		WorkflowTaskSearch searchContainer) {
		
		WorkflowTaskDisplayTerms searchTerms = 
			(WorkflowTaskDisplayTerms)searchContainer.getDisplayTerms();
		
		if (Validator.isNotNull(searchTerms.getKeywords()) || 
			Validator.isNotNull(searchTerms.getName()) || 
			Validator.isNotNull(searchTerms.getType())) {
			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() + 
				_WITH_THE_SPECIFIED_SEARCH_CRITERIA);
		}
	}

	public static final String WORKFLOW_TASK_ID = "workflowTaskId";
	private static final String _COMPLETED = "completed";
	private static final String _DEFAULT_CUR_PARAM1 = "cur1";
	private static final String _DEFAULT_CUR_PARAM2 = "cur2";
	private static final String _PENDING = "pending";
	private static final String _TABS1 = "tabs1";
	private static final String _THERE_ARE_NO_COMPLETED_TASKS = "there-are-no-completed-tasks";
	private static final String _THERE_ARE_NO_PENDING_TASKS_ASSIGNED_TO_YOU = "there-are-no-pending-tasks-assigned-to-you";
	private static final String _THERE_ARE_NO_PENDING_TASKS_ASSIGNED_TO_YOUR_ROLES = "there-are-no-pending-tasks-assigned-to-your-roles";
	private static final String _WITH_THE_SPECIFIED_SEARCH_CRITERIA = "-with-the-specified-search-criteria";
	
}