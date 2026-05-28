/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {AgentDefinition} from '../types/AgentDefinition';

const AGENT_DEFINITION_BASE_URI = '/o/ai-hub/agent-definitions';

const AGENT_DEFINITION_BY_ERC_URI = `${AGENT_DEFINITION_BASE_URI}/by-external-reference-code/`;

async function deleteAgentDefinitionToContentRetrievers(
	agentDefinitionERC: string,
	contentRetrieverERC: string
) {
	return fetch(
		`${AGENT_DEFINITION_BY_ERC_URI}${agentDefinitionERC}` +
			`/agentDefinitionsToContentRetrievers/${contentRetrieverERC}`,
		{method: 'DELETE'}
	);
}

async function deleteAgentDefinitionToModelArmorTemplates(
	agentDefinitionERC: string,
	modelArmorTemplateERC: string
) {
	return fetch(
		`${AGENT_DEFINITION_BY_ERC_URI}${agentDefinitionERC}` +
			`/aiHubAgentDefinitionsToAIHubMATemplates/${modelArmorTemplateERC}`,
		{method: 'DELETE'}
	);
}

async function getAgentDefinition(externalReferenceCode: string) {
	const response = await fetch(
		`${AGENT_DEFINITION_BY_ERC_URI}${externalReferenceCode}` +
			'?nestedFields=agentDefinitionsToContentRetrievers,aiHubAgentDefinitionsToAIHubMATemplates',
		{
			method: 'GET',
		}
	);

	return response.json();
}

async function getAgentDefinitions() {
	const response = await fetch(AGENT_DEFINITION_BASE_URI, {
		method: 'GET',
	});

	return response.json();
}

async function postAgentDefinition(agentDefinition: AgentDefinition) {
	const response = await fetch(AGENT_DEFINITION_BASE_URI, {
		body: JSON.stringify(agentDefinition),
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});

	if (!response.ok) {
		const errorBody = await response.json().catch(() => ({}));

		throw new Error(errorBody?.detail || errorBody?.title || '');
	}

	return response.json();
}

async function putAgentDefinition(agentDefinition: AgentDefinition) {
	const response = await fetch(
		`${AGENT_DEFINITION_BY_ERC_URI}${agentDefinition.externalReferenceCode}`,
		{
			body: JSON.stringify(agentDefinition),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'PUT',
		}
	);

	return response.json();
}

async function putAgentDefinitionToContentRetrievers(
	agentDefinitionERC: string,
	contentRetrieverERC: string
) {
	return fetch(
		`${AGENT_DEFINITION_BY_ERC_URI}${agentDefinitionERC}` +
			`/agentDefinitionsToContentRetrievers/${contentRetrieverERC}`,
		{method: 'PUT'}
	);
}

async function putAgentDefinitionToModelArmorTemplates(
	agentDefinitionERC: string,
	modelArmorTemplateERC: string
) {
	return fetch(
		`${AGENT_DEFINITION_BY_ERC_URI}${agentDefinitionERC}` +
			`/aiHubAgentDefinitionsToAIHubMATemplates/${modelArmorTemplateERC}`,
		{method: 'PUT'}
	);
}

export {
	deleteAgentDefinitionToContentRetrievers,
	deleteAgentDefinitionToModelArmorTemplates,
	getAgentDefinition,
	getAgentDefinitions,
	postAgentDefinition,
	putAgentDefinition,
	putAgentDefinitionToContentRetrievers,
	putAgentDefinitionToModelArmorTemplates,
};
