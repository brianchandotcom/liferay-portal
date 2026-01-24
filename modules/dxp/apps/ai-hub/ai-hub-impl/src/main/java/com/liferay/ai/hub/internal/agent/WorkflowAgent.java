/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.workflow.instance.WorkflowInstanceActionExecutor;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.internal.AgentSpecsProvider;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.scope.AgenticScope;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public class WorkflowAgent implements AgentSpecsProvider {

	public WorkflowAgent(
		AgentContext agentContext, String description, String name, int version,
		WorkflowInstanceActionExecutor workflowInstanceActionExecutor,
		WorkflowInstanceManager workflowInstanceManager) {

		_agentContext = agentContext;
		_description = description;
		_name = name;
		_version = version;
		_workflowInstanceActionExecutor = workflowInstanceActionExecutor;
		_workflowInstanceManager = workflowInstanceManager;
	}

	@Override
	public boolean async() {
		return false;
	}

	@Override
	public String description() {
		return _description;
	}

	@Override
	public String inputKey() {
		return "context";
	}

	@Agent
	public String invoke(Map<String, Object> input) {
		try {
			CompletableFuture<Map<String, Serializable>> completableFuture =
				new CompletableFuture<>();

			WorkflowInstance workflowInstance =
				_workflowInstanceManager.startWorkflowInstance(
					_agentContext.getCompanyId(), _agentContext.getGroupId(),
					_agentContext.getUserId(), _name, _version, null,
					_getWorkflowContext(input));

			_workflowInstanceActionExecutor.addCompletionAction(
				workflowInstance.getWorkflowInstanceId(),
				completableFuture::complete);

			return MapUtil.getString(
				completableFuture.get(10, TimeUnit.SECONDS), outputKey());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public AgentListener listener() {
		return new AgentListener() {

			@Override
			public void beforeAgentInvocation(AgentRequest agentRequest) {
				AgenticScope agenticScope = agentRequest.agenticScope();

				Map<String, Object> context =
					(Map<String, Object>)agenticScope.readState("context");

				context.put("request", agenticScope.readState("request"));
			}

		};
	}

	@Override
	public String outputKey() {
		return "output";
	}

	private Map<String, Serializable> _getWorkflowContext(
		Map<String, Object> input) {

		Map<String, Serializable> workflowContext =
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
				_agentContext.getServiceContext()
			).build();

		for (Map.Entry<String, Object> entry : input.entrySet()) {
			workflowContext.put(
				entry.getKey(), GetterUtil.getString(entry.getValue()));
		}

		return workflowContext;
	}

	private final AgentContext _agentContext;
	private final String _description;
	private final String _name;
	private final int _version;
	private final WorkflowInstanceActionExecutor
		_workflowInstanceActionExecutor;
	private final WorkflowInstanceManager _workflowInstanceManager;

}