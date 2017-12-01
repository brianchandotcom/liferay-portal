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

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregatePredicateFilter;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.workflow.web.internal.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.web.internal.display.context.util.WorkflowDefinitionRequestHelper;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionSearchTerms;
import com.liferay.portal.workflow.web.internal.util.WorkflowDefinitionPortletUtil;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionActivePredicateFilter;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionDescriptionPredicateFilter;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionTitlePredicateFilter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionDisplayContext {

	public WorkflowDefinitionDisplayContext(
		RenderRequest renderRequest, UserLocalService userLocalService) {

		_userLocalService = userLocalService;
		_workflowDefinitionRequestHelper = new WorkflowDefinitionRequestHelper(
			renderRequest);
	}

	public String getActive(WorkflowDefinition workflowDefinition) {
		HttpServletRequest request =
			_workflowDefinitionRequestHelper.getRequest();

		if (workflowDefinition.isActive()) {
			return LanguageUtil.get(request, "yes");
		}

		return LanguageUtil.get(request, "no");
	}

	public String getDescription(WorkflowDefinition workflowDefinition) {
		return HtmlUtil.escape(workflowDefinition.getDescription());
	}

	public Date getModifiedDate(WorkflowDefinition workflowDefinition) {
		return workflowDefinition.getModifiedDate();
	}

	public String getName(WorkflowDefinition workflowDefinition) {
		return HtmlUtil.escape(workflowDefinition.getName());
	}

	public List<WorkflowDefinition> getSearchContainerResults(
			SearchContainer<WorkflowDefinition> searchContainer, int status)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getLatestWorkflowDefinitions(
				_workflowDefinitionRequestHelper.getCompanyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getWorkflowDefinitionOrderByComparator());

		WorkflowDefinitionSearchTerms searchTerms =
			(WorkflowDefinitionSearchTerms)searchContainer.getSearchTerms();

		if (searchTerms.isAdvancedSearch()) {
			workflowDefinitions = filter(
				workflowDefinitions, searchTerms.getDescription(),
				searchTerms.getTitle(), status, searchTerms.isAndOperator());
		}
		else {
			workflowDefinitions = filter(
				workflowDefinitions, searchTerms.getKeywords(),
				searchTerms.getKeywords(), status, false);
		}

		searchContainer.setTotal(workflowDefinitions.size());

		if (workflowDefinitions.size() >
				(searchContainer.getEnd() - searchContainer.getStart())) {

			workflowDefinitions = ListUtil.subList(
				workflowDefinitions, searchContainer.getStart(),
				searchContainer.getEnd());
		}

		return workflowDefinitions;
	}

	public String getTitle(WorkflowDefinition workflowDefinition) {
		ThemeDisplay themeDisplay =
			_workflowDefinitionRequestHelper.getThemeDisplay();

		return HtmlUtil.escape(
			workflowDefinition.getTitle(themeDisplay.getLanguageId()));
	}

	public String getUserName(WorkflowDefinition workflowDefinition) {
		User user = _userLocalService.fetchUser(workflowDefinition.getUserId());

		if ((user == null) || user.isDefaultUser() ||
			Validator.isNull(user.getFullName())) {

			return null;
		}

		return user.getFullName();
	}

	public String getUserNameOrBlank(WorkflowDefinition workflowDefinition) {
		String userName = getUserName(workflowDefinition);

		if (userName == null) {
			userName = StringPool.BLANK;
		}

		return userName;
	}

	public List<WorkflowDefinition> getWorkflowDefinitions(String name)
		throws PortalException {

		return WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
			_workflowDefinitionRequestHelper.getCompanyId(), name,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WorkflowDefinition> getWorkflowDefinitionsOrderByDesc(
			String name)
		throws PortalException {

		List<WorkflowDefinition> workFlowDefinitions = getWorkflowDefinitions(
			name);

		if (workFlowDefinitions.size() <= 1) {
			return workFlowDefinitions;
		}

		Collections.reverse(workFlowDefinitions);

		return workFlowDefinitions;
	}

	protected PredicateFilter<WorkflowDefinition> createPredicateFilter(
		String description, String title, int status, boolean andOperator) {

		AggregatePredicateFilter<WorkflowDefinition> aggregatePredicateFilter =
			new AggregatePredicateFilter<>(
				new WorkflowDefinitionTitlePredicateFilter(title));

		if (andOperator) {
			aggregatePredicateFilter.and(
				new WorkflowDefinitionDescriptionPredicateFilter(description));
		}
		else {
			aggregatePredicateFilter.or(
				new WorkflowDefinitionDescriptionPredicateFilter(description));
		}

		aggregatePredicateFilter.and(
			new WorkflowDefinitionActivePredicateFilter(status));

		return aggregatePredicateFilter;
	}

	protected List<WorkflowDefinition> filter(
		List<WorkflowDefinition> workflowDefinitions, String description,
		String title, int status, boolean andOperator) {

		if ((status == WorkflowDefinitionConstants.STATUS_ALL) &&
			Validator.isNull(title) && Validator.isNull(description)) {

			return workflowDefinitions;
		}

		PredicateFilter<WorkflowDefinition> predicateFilter =
			createPredicateFilter(description, title, status, andOperator);

		return ListUtil.filter(workflowDefinitions, predicateFilter);
	}

	protected OrderByComparator<WorkflowDefinition>
		getWorkflowDefinitionOrderByComparator() {

		String orderByCol = ParamUtil.getString(
			_workflowDefinitionRequestHelper.getRequest(), "orderByCol",
			"name");

		String orderByType = ParamUtil.getString(
			_workflowDefinitionRequestHelper.getRequest(), "orderByType",
			"asc");

		return WorkflowDefinitionPortletUtil.
			getWorkflowDefitionOrderByComparator(
				orderByCol, orderByType,
				_workflowDefinitionRequestHelper.getLocale());
	}

	private final UserLocalService _userLocalService;
	private final WorkflowDefinitionRequestHelper
		_workflowDefinitionRequestHelper;

}