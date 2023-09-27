/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.TransitionUtil;
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
				transitionName -> TransitionUtil.toTransition(
					contextAcceptLanguage.getPreferredLocale(),
					transitionName)),
			pagination, nextTransitionNames.size());
	}

	@Override
	public Page<Transition> getWorkflowTaskNextTransitionsPage(
			Long workflowTaskId, Pagination pagination)
		throws Exception {

		List<String> nextTransitionNames =
			_workflowTaskManager.getNextTransitionNames(
				contextUser.getUserId(), workflowTaskId);

		return Page.of(
			transform(
				ListUtil.subList(
					nextTransitionNames, pagination.getStartPosition(),
					pagination.getEndPosition()),
				transitionName -> TransitionUtil.toTransition(
					contextAcceptLanguage.getPreferredLocale(),
					transitionName)),
			pagination, nextTransitionNames.size());
	}

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}