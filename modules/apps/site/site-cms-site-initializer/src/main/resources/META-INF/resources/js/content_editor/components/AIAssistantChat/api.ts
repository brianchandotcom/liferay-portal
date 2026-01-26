/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';
import {fetch} from 'frontend-js-web';

const AI_HUB_ENDPOINT = '/o/ai-hub/v1.0';

export async function createEventSource() {
	const tokens = await postToken();

	if (!tokens) {
		return null;
	}

	return new EventSource(`${AI_HUB_ENDPOINT}/tasks/subscribe`, {
		fetch: (input, init) =>
			fetch(input as RequestInfo, {
				...init,
				headers: new Headers({
					'Accept': 'text/event-stream',
					'Authorization': `Bearer ${tokens.accessToken}`,
					'Liferay-AI-Hub-On-Behalf-Of': `Bearer ${tokens.userToken}`,
				}),
			}),
		withCredentials: true,
	});
}

async function postToken() {
	try {
		const response = await fetch(`${AI_HUB_ENDPOINT}/tokens`, {
			method: 'POST',
		});

		if (!response.ok) {
			throw new Error(`Unable to generate token: ${response.statusText}`);
		}

		const data = await response.json();

		if (!data?.accessToken) {
			throw new Error('Unable to generate token.');
		}

		if (!data?.userToken) {
			throw new Error('Unable to generate user token.');
		}

		return data;
	}
	catch (error) {
		console.warn((error as Error).message);
	}
}

export async function postChatByExternalReferenceCodeMessage(
	content: string,
	eventSourceReference: string,
	message: string,
	title: string
) {
	const tokens = await postToken();

	if (!tokens) {
		return;
	}

	return await fetch(
		`${AI_HUB_ENDPOINT}/chats/by-external-reference-code/${eventSourceReference}/messages`,
		{
			body: JSON.stringify({
				context: {
					content,
					title,
				},
				text: message,
			}),
			headers: new Headers({
				'Accept': 'application/json',
				'Authorization': `Bearer ${tokens.accessToken}`,
				'Content-Type': 'application/json',
				'Liferay-AI-Hub-On-Behalf-Of': `Bearer ${tokens.userToken}`,
			}),
			method: 'POST',
		}
	);
}
