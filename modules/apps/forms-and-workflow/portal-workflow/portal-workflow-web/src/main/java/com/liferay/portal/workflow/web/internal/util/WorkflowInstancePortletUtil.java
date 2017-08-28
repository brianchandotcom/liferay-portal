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

package com.liferay.portal.workflow.web.internal.util;

import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.web.internal.configuration.WorkflowInstanceWebConfiguration;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

import javax.portlet.PortletRequest;

/**
 * @author Marcellus Tavares
 */
public class WorkflowInstancePortletUtil {

	public static String getDisplayStyle(
		PortletRequest portletRequest, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			WorkflowInstanceWebConfiguration workflowTaskWebConfiguration =
				(WorkflowInstanceWebConfiguration)portletRequest.getAttribute(
					WorkflowInstanceWebConfiguration.class.getName());

			displayStyle = portalPreferences.getValue(
				WorkflowPortletKeys.USER_WORKFLOW, "instance-display-style",
				workflowTaskWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				WorkflowPortletKeys.USER_WORKFLOW, "instance-display-style",
				displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	public static OrderByComparator<WorkflowInstance>
		getWorkflowInstanceOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WorkflowInstance> orderByComparator = null;

		if (orderByCol.equals("last-activity-date")) {
			orderByComparator =
				WorkflowComparatorFactoryUtil.getInstanceStartDateComparator(
					orderByAsc);
		}
		else {
			orderByComparator =
				WorkflowComparatorFactoryUtil.getInstanceEndDateComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}