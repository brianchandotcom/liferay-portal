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

package com.liferay.portal.workflow.web.internal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionSearch
	extends SearchContainer<WorkflowDefinition> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-entries-were-found";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("title");
			add("description");
			add("modifiedDate");
		}
	};
	public static Map<String, String> orderableHeaders =
		new HashMap<String, String>() {
			{
				put("title", "modifiedDate");
			}
		};

	public WorkflowDefinitionSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new WorkflowDefinitionDisplayTerms(portletRequest),
			new WorkflowDefinitionSearchTerms(portletRequest),
			DEFAULT_CUR_PARAM, DEFAULT_DELTA, iteratorURL, headerNames,
			EMPTY_RESULTS_MESSAGE);

		WorkflowDefinitionDisplayTerms displayTerms =
			(WorkflowDefinitionDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			WorkflowDefinitionDisplayTerms.DESCRIPTION,
			displayTerms.getDescription());
		iteratorURL.setParameter(
			WorkflowDefinitionDisplayTerms.TITLE, displayTerms.getTitle());

		setOrderableHeaders(orderableHeaders);
	}

}