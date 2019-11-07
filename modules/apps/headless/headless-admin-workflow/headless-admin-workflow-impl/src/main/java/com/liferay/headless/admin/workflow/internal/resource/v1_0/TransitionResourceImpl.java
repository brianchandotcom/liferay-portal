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

import com.liferay.headless.admin.workflow.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.resource.v1_0.TransitionResource;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/transition.properties",
	scope = ServiceScope.PROTOTYPE, service = TransitionResource.class
)
public class TransitionResourceImpl extends BaseTransitionResourceImpl {

	@Override
	public Page<Transition> getWorkflowInstanceNextTransitionsPage(
			Long workflowInstanceId, Pagination pagination)
		throws Exception {

		List<String> nextTransitionNames =
			_workflowInstanceManager.getNextTransitionNames(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowInstanceId);

		return Page.of(
			transform(
				ListUtil.subList(
					nextTransitionNames, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toTransitions),
			pagination, nextTransitionNames.size());
	}

	@Override
	public Page<Transition> getWorkflowTaskNextTransitionsPage(
			Long workflowTaskId, Pagination pagination)
		throws Exception {

		List<String> nextTransitionNames =
			_workflowTaskManager.getNextTransitionNames(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowTaskId);

		return Page.of(
			transform(
				ListUtil.subList(
					nextTransitionNames, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toTransitions),
			pagination, nextTransitionNames.size());
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