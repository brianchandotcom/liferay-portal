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

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class MyWorkflowInstanceEditDisplayContext
	extends WorkflowInstanceEditDisplayContext {

	public MyWorkflowInstanceEditDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	@Override
	public List<WorkflowTask> getWorkflowTasks() throws PortalException {
		if (workflowTasks == null) {
			workflowTasks =
				WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(
					workflowInstanceRequestHelper.getCompanyId(),
					workflowInstanceRequestHelper.getUserId(),
					getWorkflowInstanceId(), false, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);
		}

		return workflowTasks;
	}

}