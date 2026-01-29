/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.rest.dto.v1_0.TaskDefinition;
import com.liferay.ai.hub.rest.manager.v1_0.TaskDefinitionManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public class AgentsFactory {

	public AgentsFactory(
		AgentContext agentContext, TaskDefinitionManager taskDefinitionManager,
		WorkflowInstanceManager workflowInstanceManager) {

		_agentContext = agentContext;
		_taskDefinitionManager = taskDefinitionManager;
		_workflowInstanceManager = workflowInstanceManager;
	}

	public Object[] create() {
		try {
			Page<TaskDefinition> page =
				_taskDefinitionManager.getTaskDefinitions(
					_agentContext.getCompanyId(), null, null,
					Pagination.of(1, 20), null, null);

			return TransformUtil.transformToArray(
				page.getItems(),
				taskDefinition -> new AgentSpecsProviderImpl(
					_agentContext, taskDefinition.getDescription(),
					taskDefinition.getName(), taskDefinition.getVersion(),
					_workflowInstanceManager),
				Object.class);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return new AgentSpecsProviderImpl[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(AgentsFactory.class);

	private final AgentContext _agentContext;
	private final TaskDefinitionManager _taskDefinitionManager;
	private final WorkflowInstanceManager _workflowInstanceManager;

}