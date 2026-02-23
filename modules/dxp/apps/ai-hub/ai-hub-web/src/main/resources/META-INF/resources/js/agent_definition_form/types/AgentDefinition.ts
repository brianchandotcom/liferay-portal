/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type AgentDefinition = {
	active: boolean;
	description: string;
	externalReferenceCode: string;
	inputVariables: string;
	outputVariable: string;
	r_accountToAIHubAgentDefinitions_accountEntryERC: string;
	title_i18n: {
		[key: string]: string;
	};
	workflowDefinitionName: string;
};
