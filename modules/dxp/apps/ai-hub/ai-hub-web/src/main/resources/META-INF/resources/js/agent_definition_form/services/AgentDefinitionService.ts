/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {AgentDefinition} from '../types/AgentDefinition';

const AGENT_DEFINITION_BASE_URI =
	'/o/ai-hub/agent-definitions/by-external-reference-code/';

async function getAgentDefinition(externalReferenceCode: string) {
	const response = await fetch(
		`${AGENT_DEFINITION_BASE_URI}${externalReferenceCode}`,
		{
			method: 'GET',
		}
	);

	return response.json();
}

async function putAgentDefinition(agentDefinition: AgentDefinition) {
	const response = await fetch(
		`${AGENT_DEFINITION_BASE_URI}${agentDefinition.externalReferenceCode}`,
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

export {getAgentDefinition, putAgentDefinition};
