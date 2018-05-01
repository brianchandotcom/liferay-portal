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

package com.liferay.portal.workflow.task.web.internal.util;

import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.task.web.configuration.WorkflowTaskWebConfiguration;

import javax.portlet.PortletRequest;

/**
 * @author Marcellus Tavares
 */
public class WorkflowTaskPortletUtil {

	public static String getWorkflowTaskDisplayStyle(
		PortletRequest portletRequest, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			WorkflowTaskWebConfiguration workflowTaskWebConfiguration =
				(WorkflowTaskWebConfiguration)portletRequest.getAttribute(
					WorkflowTaskWebConfiguration.class.getName());

			displayStyle = portalPreferences.getValue(
				PortletKeys.MY_WORKFLOW_TASK, "display-style",
				workflowTaskWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				PortletKeys.MY_WORKFLOW_TASK, "display-style", displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	public static OrderByComparator<WorkflowTask>
		getWorkflowTaskOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WorkflowTask> orderByComparator = null;

		if (orderByCol.equals("due-date")) {
			orderByComparator =
				WorkflowComparatorFactoryUtil.getTaskDueDateComparator(
					orderByAsc);
		}
		else {
			orderByComparator =
				WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}