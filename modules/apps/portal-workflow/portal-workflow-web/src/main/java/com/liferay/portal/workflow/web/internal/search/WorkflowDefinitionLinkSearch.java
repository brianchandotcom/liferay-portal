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

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionLinkSearch
	extends SearchContainer<WorkflowDefinitionLinkSearchEntry> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-entries-were-found";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("resource");
			add("workflow");
		}
	};

	public WorkflowDefinitionLinkSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest,
			new WorkflowDefinitionLinkDisplayTerms(portletRequest),
			new WorkflowDefinitionLinkSearchTerms(portletRequest),
			DEFAULT_CUR_PARAM, DEFAULT_DELTA, iteratorURL, headerNames,
			EMPTY_RESULTS_MESSAGE);

		WorkflowDefinitionLinkDisplayTerms displayTerms =
			(WorkflowDefinitionLinkDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			WorkflowDefinitionLinkDisplayTerms.RESOURCE,
			String.valueOf(displayTerms.getResource()));
		iteratorURL.setParameter(
			WorkflowDefinitionLinkDisplayTerms.WORKFLOW,
			displayTerms.getWorkflow());
	}

}