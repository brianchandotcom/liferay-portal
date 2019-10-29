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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.Transitions;
import com.liferay.headless.admin.workflow.resource.v1_0.TransitionsResource;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/transitions.properties",
	scope = ServiceScope.PROTOTYPE, service = TransitionsResource.class
)
public class TransitionsResourceImpl extends BaseTransitionsResourceImpl {

	@Override
	public Page<Transitions> getWorkflowInstanceNextTransitionsPage(
			Long workflowInstanceId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowInstanceManager.getNextTransitionNames(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					workflowInstanceId),
				this::_toTransitions));
	}

	@Override
	public Page<Transitions> getWorkflowTaskNextTransitionsPage(
			Long workflowTaskId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getNextTransitionNames(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					workflowTaskId),
				this::_toTransitions));
	}

	private Transitions _toTransitions(String transitionName) {
		return new Transitions() {
			{
				name = transitionName;
			}
		};
	}

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}