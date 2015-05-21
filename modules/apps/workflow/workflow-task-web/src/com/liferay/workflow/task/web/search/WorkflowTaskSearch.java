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
import com.liferay.workflow.task.web.constants.WorkflowTaskConstants;

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
		headerNames.add(WorkflowTaskConstants.TASK);
		headerNames.add(WorkflowTaskConstants.ASSET_TITLE);
		headerNames.add(WorkflowTaskConstants.LAST_ACTIVITY_DATE);
		headerNames.add(WorkflowTaskConstants.DUE_DATE);
		headerNames.add(WorkflowTaskConstants.STATE);

		orderableHeaders.put(
			WorkflowTaskConstants.TASK, WorkflowTaskConstants.TASK);
		orderableHeaders.put(
			WorkflowTaskConstants.DUE_DATE, WorkflowTaskConstants.DUE_DATE);
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

		iteratorURL.setParameter(
			WorkflowTaskConstants.NAME, displayTerms.getName());
		iteratorURL.setParameter(
			WorkflowTaskConstants.TYPE, displayTerms.getType());

		String orderByCol = ParamUtil.getString(
			portletRequest, WorkflowTaskConstants.ORDER_BY_COL);

		String orderByType = ParamUtil.getString(
			portletRequest, WorkflowTaskConstants.ORDER_BY_TYPE);

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

		if (orderByType.equals(WorkflowTaskConstants.ASC)) {
			orderByAsc = true;
		}

		OrderByComparator<WorkflowTask> orderByComparator = null;

		if (orderByCol.equals(WorkflowTaskConstants.NAME)) {
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