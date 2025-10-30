/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.Task;
import com.liferay.ai.hub.rest.resource.v1_0.TaskResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

import java.io.Serializable;

import java.util.HashMap;
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
	public Task postTask(Task task) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		Map<String, Serializable> workflowContext = _toWorkflowContext(
			task.getContext());

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.startWorkflowInstance(
				contextCompany.getCompanyId(),
				WorkflowConstants.DEFAULT_GROUP_ID, contextUser.getUserId(),
				task.getType(), 1, null, workflowContext);

		return new Task() {
			{
				setExternalReferenceCode(
					() -> String.valueOf(
						workflowInstance.getWorkflowInstanceId()));
				setType(task::getType);
			}
		};
	}

	private Map<String, Serializable> _toWorkflowContext(Map<String, ?> context)
		throws Exception {

		Map<String, Serializable> workflowContext = new HashMap<>();

		for (Map.Entry<String, ?> entry : context.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof Serializable) {
				workflowContext.put(entry.getKey(), (Serializable)value);
			}
		}

		workflowContext.put(
			WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
			ServiceContextFactory.getInstance(contextHttpServletRequest));

		return workflowContext;
	}

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}