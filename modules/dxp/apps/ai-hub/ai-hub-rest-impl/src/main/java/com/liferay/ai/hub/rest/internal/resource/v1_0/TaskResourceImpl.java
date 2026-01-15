/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.Task;
import com.liferay.ai.hub.rest.internal.resource.v1_0.util.GroupUtil;
import com.liferay.ai.hub.rest.internal.resource.v1_0.util.WorkflowContextUtil;
import com.liferay.ai.hub.rest.resource.v1_0.TaskResource;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/task.properties",
	scope = ServiceScope.PROTOTYPE, service = TaskResource.class
)
public class TaskResourceImpl extends BaseTaskResourceImpl {

	@Override
	public void getTaskSubscribe(SseEventSink sseEventSink) {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		SseUtil.initialize(_sse, sseEventSink);
	}

	@Override
	public Task postTask(Task task) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		Map<String, Serializable> workflowContext =
			WorkflowContextUtil.toWorkflowContext(
				task.getContext(), contextHttpServletRequest, _sse,
				task.getSseEventSinkKey());

		workflowContext.put("outBoundEventName", task.getType());
		workflowContext.put(
			"userToken",
			contextHttpServletRequest.getHeader("Liferay-AI-Hub-On-Behalf-Of"));

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.startWorkflowInstance(
				contextCompany.getCompanyId(),
				GroupUtil.getGroupId(
					contextCompany.getCompanyId(), _groupService,
					task.getScope()),
				contextUser.getUserId(), task.getType(), 1, null,
				workflowContext);

		return new Task() {
			{
				setExternalReferenceCode(
					() -> String.valueOf(
						workflowInstance.getWorkflowInstanceId()));
				setScope(task::getScope);
				setType(task::getType);
			}
		};
	}

	@Reference
	private GroupService _groupService;

	@Context
	private Sse _sse;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}