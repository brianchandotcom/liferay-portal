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

package com.liferay.portal.workflow.task.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.workflow.task.web.internal.util.WorkflowTaskPortletUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Marcellus Tavares
 */
public class WorkflowTaskSearch extends SearchContainer<WorkflowTask> {

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("asset-title");
			add("asset-type");
			add("task");
			add("last-activity-date");
			add("due-date");
		}
	};
	public static Map<String, String> orderableHeaders = HashMapBuilder.put(
		"due-date", "due-date"
	).put(
		"last-activity-date", "last-activity-date"
	).build();

	public WorkflowTaskSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		this(portletRequest, DEFAULT_CUR_PARAM, iteratorURL);
	}

	public WorkflowTaskSearch(
		PortletRequest portletRequest, String curParam,
		PortletURL iteratorURL) {

		super(
			portletRequest, new DisplayTerms(portletRequest),
			new DisplayTerms(portletRequest), curParam, DEFAULT_DELTA,
			iteratorURL, headerNames, null);

		setOrderableHeaders(orderableHeaders);

		String orderByCol = SearchOrderByUtil.getOrderByCol(
			portletRequest, PortletKeys.MY_WORKFLOW_TASK, "last-activity-date");

		setOrderByCol(orderByCol);

		String orderByType = SearchOrderByUtil.getOrderByType(
			portletRequest, PortletKeys.MY_WORKFLOW_TASK, "asc");

		setOrderByComparator(
			WorkflowTaskPortletUtil.getWorkflowTaskOrderByComparator(
				orderByCol, orderByType));
		setOrderByType(orderByType);
	}

}