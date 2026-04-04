/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {Chatbot} from '../types/Chatbot';

const CHATBOT_BASE_URI = '/o/ai-hub/chatbots';

const CHATBOT_BY_ERC_URI = `${CHATBOT_BASE_URI}/by-external-reference-code/`;

async function getChatbot(externalReferenceCode: string) {
	const response = await fetch(
		`${CHATBOT_BY_ERC_URI}${externalReferenceCode}?nestedFields=agentDefinitionsToChatbots`,
		{
			method: 'GET',
		}
	);

	if (!response.ok) {
		throw new Error();
	}

	return response.json();
}

async function postChatbot(chatbot: Chatbot) {
	const response = await fetch(CHATBOT_BASE_URI, {
		body: JSON.stringify(chatbot),
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});

	if (!response.ok) {
		throw new Error();
	}

	return response.json();
}

async function putChatbot(
	existingExternalReferenceCode: string,
	chatbot: Chatbot
) {
	const response = await fetch(
		`${CHATBOT_BY_ERC_URI}${existingExternalReferenceCode}`,
		{
			body: JSON.stringify(chatbot),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'PUT',
		}
	);

	if (!response.ok) {
		throw new Error();
	}

	return response.json();
}

async function deleteChatbotAgentDefinitionRelationship(
	chatbotERC: string,
	agentERC: string
) {
	return fetch(
		`${CHATBOT_BY_ERC_URI}${chatbotERC}/agentDefinitionsToChatbots/${agentERC}`,
		{method: 'DELETE'}
	);
}

async function putChatbotAgentDefinitionRelationship(
	chatbotERC: string,
	agentERC: string
) {
	return fetch(
		`${CHATBOT_BY_ERC_URI}${chatbotERC}/agentDefinitionsToChatbots/${agentERC}`,
		{method: 'PUT'}
	);
}

export {
	deleteChatbotAgentDefinitionRelationship,
	getChatbot,
	postChatbot,
	putChatbot,
	putChatbotAgentDefinitionRelationship,
};
