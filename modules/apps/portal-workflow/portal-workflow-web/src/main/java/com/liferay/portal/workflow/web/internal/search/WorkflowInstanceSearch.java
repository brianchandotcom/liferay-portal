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

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.web.internal.util.WorkflowInstancePortletUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Leonardo Barros
 */
public class WorkflowInstanceSearch extends SearchContainer<WorkflowInstance> {

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("asset-title");
			add("asset-type");
			add("status");
			add("definition");
			add("last-activity-date");
			add("end-date");
		}
	};

	public WorkflowInstanceSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new DisplayTerms(portletRequest), null,
			DEFAULT_CUR_PARAM, DEFAULT_DELTA, iteratorURL, headerNames, null);

		String orderByCol = SearchOrderByUtil.getOrderByCol(
			portletRequest, WorkflowPortletKeys.USER_WORKFLOW,
			"instance-order-by-col", "last-activity-date");

		setOrderByCol(orderByCol);

		String orderByType = SearchOrderByUtil.getOrderByType(
			portletRequest, WorkflowPortletKeys.USER_WORKFLOW,
			"instance-order-by-type", "asc");

		setOrderByComparator(
			WorkflowInstancePortletUtil.getWorkflowInstanceOrderByComparator(
				orderByCol, orderByType));
		setOrderByType(orderByType);
	}

}