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

package com.liferay.workflow.definition.web.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.workflow.definition.web.search.WorkflowDefinitionSearchTerms;
import com.liferay.workflow.definition.web.util.WorkflowDefinitionPortletUtil;

import java.util.Iterator;
import java.util.List;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionDisplayContext {

	public WorkflowDefinitionDisplayContext(RenderRequest renderRequest) {
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

	public String getName(WorkflowDefinition workflowDefinition) {
		return HtmlUtil.escape(workflowDefinition.getName());
	}

	public List<WorkflowDefinition> getSearchContainerResults(
			SearchContainer<WorkflowDefinition> searchContainer)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
				_workflowDefinitionRequestHelper.getCompanyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());

		WorkflowDefinitionSearchTerms searchTerms =
			(WorkflowDefinitionSearchTerms)searchContainer.getSearchTerms();

		if (searchTerms.isAdvancedSearch()) {
			filter(workflowDefinitions, searchTerms);
		}
		else {
			filter(
				workflowDefinitions,
				StringUtil.toLowerCase(searchTerms.getKeywords()));
		}

		searchContainer.setTotal(workflowDefinitions.size());

		return workflowDefinitions;
	}

	public String getTitle(WorkflowDefinition workflowDefinition) {
		ThemeDisplay themeDisplay =
			_workflowDefinitionRequestHelper.getThemeDisplay();

		return HtmlUtil.escape(
			workflowDefinition.getTitle(themeDisplay.getLanguageId()));
	}

	public String getVersion(WorkflowDefinition workflowDefinition) {
		return String.valueOf(workflowDefinition.getVersion());
	}

	protected void filter(
		List<WorkflowDefinition> workflowDefinitions, String keyword) {

		Iterator<WorkflowDefinition> iterator = workflowDefinitions.iterator();

		while (iterator.hasNext()) {
			WorkflowDefinition entry = iterator.next();

			String name = StringUtil.toLowerCase(entry.getName());
			String title = StringUtil.toLowerCase(entry.getTitle());

			if (!name.contains(keyword) && !title.contains(keyword)) {
				iterator.remove();
			}
		}
	}

	protected void filter(
		List<WorkflowDefinition> workflowDefinitions,
		WorkflowDefinitionSearchTerms searchTerms) {

		if (Validator.isNull(searchTerms.getName()) &&
			Validator.isNull(searchTerms.getTitle())) {

			return;
		}

		Iterator<WorkflowDefinition> iterator = workflowDefinitions.iterator();

		while (iterator.hasNext()) {
			WorkflowDefinition entry = iterator.next();

			String name = StringUtil.toLowerCase(entry.getName());
			String title = StringUtil.toLowerCase(entry.getTitle());

			if (Validator.isNotNull(searchTerms.getName()) &&
				Validator.isNotNull(searchTerms.getTitle())) {

				if (searchTerms.isAndOperator()) {
					if (!name.contains(searchTerms.getName()) ||
						!title.contains(searchTerms.getTitle())) {

						iterator.remove();
					}
				}
				else {
					if (!name.contains(searchTerms.getName()) &&
						!title.contains(searchTerms.getTitle())) {

						iterator.remove();
					}
				}
			}
			else if(Validator.isNotNull(searchTerms.getName())) {
				if (!name.contains(searchTerms.getName())) {
					iterator.remove();
				}
			}
			else if(Validator.isNotNull(searchTerms.getTitle())) {
				if (!title.contains(searchTerms.getTitle())) {
					iterator.remove();
				}
			}
		}
	}

	protected OrderByComparator<WorkflowDefinition> getOrderByComparator() {
		HttpServletRequest request =
			_workflowDefinitionRequestHelper.getRequest();

		ThemeDisplay themeDisplay =
			_workflowDefinitionRequestHelper.getThemeDisplay();

		String orderByCol = ParamUtil.getString(request, "orderByCol", "name");

		String orderByType = ParamUtil.getString(request, "orderByType", "asc");

		return WorkflowDefinitionPortletUtil.
			getWorkflowDefitionOrderByComparator(
				themeDisplay.getLanguageId(), orderByCol, orderByType);
	}

	private final WorkflowDefinitionRequestHelper
		_workflowDefinitionRequestHelper;

}