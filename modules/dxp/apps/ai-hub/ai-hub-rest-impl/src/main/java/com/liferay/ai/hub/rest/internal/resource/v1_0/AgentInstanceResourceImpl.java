/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.AgentDefinition;
import com.liferay.ai.hub.rest.dto.v1_0.AgentInstance;
import com.liferay.ai.hub.rest.manager.v1_0.AgentDefinitionManager;
import com.liferay.ai.hub.rest.resource.v1_0.AgentInstanceResource;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

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
	properties = "OSGI-INF/liferay/rest/v1_0/agent-instance.properties",
	scope = ServiceScope.PROTOTYPE, service = AgentInstanceResource.class
)
public class AgentInstanceResourceImpl extends BaseAgentInstanceResourceImpl {

	@Override
	public void getAgentInstanceSubscribe(SseEventSink sseEventSink) {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		SseUtil.initialize(_sse, sseEventSink);
	}

	@Override
	public AgentInstance postAgentInstance(AgentInstance agentInstance)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		AgentDefinition agentDefinition =
			_agentDefinitionManager.getAgentDefinition(
				contextCompany.getCompanyId(),
				new DefaultDTOConverterContext(
					contextAcceptLanguage.isAcceptAllLanguages(), null,
					_dtoConverterRegistry, contextHttpServletRequest, null,
					contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
					contextUser),
				agentInstance.getAgentDefinitionExternalReferenceCode());

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				contextCompany.getCompanyId(),
				agentDefinition.getWorkflowDefinitionName());

		Map<String, Serializable> workflowContext =
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
				ServiceContextFactory.getInstance(contextHttpServletRequest)
			).put(
				"accessToken",
				contextHttpServletRequest.getHeader("Authorization")
			).put(
				"agentDefinitionExternalReferenceCode",
				agentDefinition.getExternalReferenceCode()
			).put(
				"instructionDefinitionScope",
				agentInstance.getInstructionDefinitionScopeAsString()
			).put(
				"outBoundEventName", agentDefinition.getExternalReferenceCode()
			).put(
				"sseEventSinkKey", agentInstance.getSseEventSinkKey()
			).put(
				"userToken",
				contextHttpServletRequest.getHeader(
					"Liferay-AI-Hub-Cell-On-Behalf-Of")
			).build();

		MapUtil.isNotEmptyForEach(
			agentInstance.getContext(),
			(key, value) -> {
				if ((value instanceof Serializable serializableValue) &&
					!workflowContext.containsKey(key)) {

					workflowContext.put(key, serializableValue);
				}
			});

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.startWorkflowInstance(
				contextCompany.getCompanyId(),
				AccountEntryUtil.getUserAccountEntryGroupId(
					contextUser.getUserId()),
				contextUser.getUserId(), workflowDefinition.getName(),
				workflowDefinition.getVersion(), null, workflowContext);

		return new AgentInstance() {
			{
				setAgentDefinitionExternalReferenceCode(
					agentDefinition::getExternalReferenceCode);
				setExternalReferenceCode(
					() -> String.valueOf(
						workflowInstance.getWorkflowInstanceId()));
			}
		};
	}

	@Reference
	private AgentDefinitionManager _agentDefinitionManager;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Context
	private Sse _sse;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}