/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.internal.agent.util.AgentUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import dev.langchain4j.agentic.internal.InternalAgent;
import dev.langchain4j.agentic.observability.AgentListenerProvider;
import dev.langchain4j.agentic.planner.AgentArgument;
import dev.langchain4j.agentic.planner.AgentInstance;
import dev.langchain4j.agentic.planner.AgenticSystemTopology;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class InternalAgentImpl implements InternalAgent, InvocationHandler {

	public InternalAgentImpl(
		AgentContext agentContext,
		WorkflowDefinitionManager workflowDefinitionManager,
		WorkflowInstanceManager workflowInstanceManager) {

		_agentContext = agentContext;
		_workflowDefinitionManager = workflowDefinitionManager;
		_workflowInstanceManager = workflowInstanceManager;
	}

	@Override
	public String agentId() {
		return _name;
	}

	@Override
	public void appendId(String idSuffix) {
	}

	@Override
	public List<AgentArgument> arguments() {
		return _agentArguments;
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
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		if ((method.getDeclaringClass() == AgentInstance.class) ||
			(method.getDeclaringClass() == InternalAgent.class)) {

			return method.invoke(
				ProxyUtil.getInvocationHandler(proxy), arguments);
		}
		else if (method.getDeclaringClass() == AgentListenerProvider.class) {
			return null;
		}

		try {
			Map<String, Serializable> workflowContext =
				HashMapBuilder.<String, Serializable>put(
					WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
					_agentContext.getServiceContext()
				).put(
					"accessToken", _agentContext.getAccessToken()
				).put(
					"agentDefinitionExternalReferenceCode", _name
				).put(
					"memoryId", _agentContext.getSseEventSinkKey()
				).put(
					"userToken", _agentContext.getUserToken()
				).build();

			for (AgentArgument agentArgument : arguments()) {
				String name = agentArgument.name();

				if (workflowContext.containsKey(name)) {
					continue;
				}

				workflowContext.put(
					name,
					MapUtil.getString((Map<String, Object>)arguments[0], name));
			}

			WorkflowDefinition workflowDefinition =
				_workflowDefinitionManager.liberalGetLatestWorkflowDefinition(
					_agentContext.getCompanyId(), _workflowDefinitionName);

			return AgentUtil.getOutput(
				_workflowInstanceManager.startWorkflowInstance(
					_agentContext.getCompanyId(), _agentContext.getGroupId(),
					_agentContext.getUserId(), _workflowDefinitionName,
					workflowDefinition.getVersion(), null, workflowContext));
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public String name() {
		return _name;
	}

	@Override
	public String outputKey() {
		return _outputKey;
	}

	@Override
	public Type outputType() {
		return String.class;
	}

	@Override
	public AgentInstance parent() {
		return _agentInstance;
	}

	public void setAgentArguments(List<AgentArgument> agentArguments) {
		_agentArguments = agentArguments;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOutputKey(String outputKey) {
		_outputKey = outputKey;
	}

	@Override
	public void setParent(AgentInstance agentInstance) {
		_agentInstance = agentInstance;
	}

	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		_workflowDefinitionName = workflowDefinitionName;
	}

	@Override
	public List<AgentInstance> subagents() {
		return List.of();
	}

	@Override
	public AgenticSystemTopology topology() {
		return AgenticSystemTopology.SINGLE_AGENT;
	}

	@Override
	public Class<?> type() {
		return null;
	}

	private List<AgentArgument> _agentArguments;
	private final AgentContext _agentContext;
	private AgentInstance _agentInstance;
	private String _description;
	private String _name;
	private String _outputKey;
	private final WorkflowDefinitionManager _workflowDefinitionManager;
	private String _workflowDefinitionName;
	private final WorkflowInstanceManager _workflowInstanceManager;

}