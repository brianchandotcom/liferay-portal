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

package com.liferay.calendar.test.util;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Inácio Nery
 */
public class CalendarWorkflowTestUtil {

	public static void activateWorkflow(Group group) throws PortalException {
		WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			group.getGroupId(), CalendarBooking.class.getName(), 0, 0,
			"Single Approver", 1);
	}

	public static void deactivateWorkflow(Group group) throws PortalException {
		WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			group.getGroupId(), CalendarBooking.class.getName(), 0, 0, null);
	}

}