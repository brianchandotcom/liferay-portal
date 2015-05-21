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

package com.liferay.workflow.task.web.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Marcellus Tavares
 */
public class WorkflowTaskSearch extends SearchContainer<WorkflowTask> {

	public static List<String> headerNames = new ArrayList<>();
	public static Map<String, String> orderableHeaders = new HashMap<>();

	static {
		headerNames.add("task");
		headerNames.add("asset-title");
		headerNames.add("last-activity-date");
		headerNames.add("due-date");
		headerNames.add("state");

		orderableHeaders.put("task", "task");
		orderableHeaders.put("due-date", "due-date");
	}

	public WorkflowTaskSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		this(portletRequest, DEFAULT_CUR_PARAM, iteratorURL);
	}

	public WorkflowTaskSearch(
		PortletRequest portletRequest, String curParam,
		PortletURL iteratorURL) {

		super(
			portletRequest, new WorkflowTaskDisplayTerms(portletRequest),
			new WorkflowTaskDisplayTerms(portletRequest), curParam,
			DEFAULT_DELTA, iteratorURL, headerNames, null);

		WorkflowTaskDisplayTerms displayTerms =
			(WorkflowTaskDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter("name", displayTerms.getName());
		iteratorURL.setParameter("type", displayTerms.getType());

		String orderByCol = ParamUtil.getString(portletRequest, "orderByCol");
		String orderByType = ParamUtil.getString(portletRequest, "orderByType");

		OrderByComparator<WorkflowTask> orderByComparator =
			getOrderByComparator(orderByCol, orderByType);

		setOrderableHeaders(orderableHeaders);
		setOrderByCol(orderByCol);
		setOrderByType(orderByType);
		setOrderByComparator(orderByComparator);
	}

	protected OrderByComparator<WorkflowTask> getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WorkflowTask> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator =
				WorkflowComparatorFactoryUtil.getTaskNameComparator(orderByAsc);
		}
		else {
			orderByComparator =
				WorkflowComparatorFactoryUtil.getTaskDueDateComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}