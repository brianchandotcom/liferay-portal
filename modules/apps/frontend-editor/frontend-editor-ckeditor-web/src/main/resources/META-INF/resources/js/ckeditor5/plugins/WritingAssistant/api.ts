/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';
import {fetch} from 'frontend-js-web';

import {EActionType} from './types';

const AI_HUB_ENDPOINT = '/o/ai-hub/v1.0';

export async function createEventSource() {
	const token = await postToken();

	if (!token) {
		return;
	}

	return new EventSource(
		`${token.serviceURL}${AI_HUB_ENDPOINT}/tasks/subscribe`,
		{
			fetch: (input, init) =>
				fetch(input as RequestInfo, {
					...init,
					headers: new Headers({
						Accept: 'text/event-stream',
						Authorization: `Bearer ${token.accessToken}`,
					}),
				}),
			withCredentials: true,
		}
	);
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

		if (!data?.serviceURL) {
			throw new Error('Unable to find service URL.');
		}

		return data;
	}
	catch (error) {
		console.warn((error as Error).message);
	}
}

export async function postTask(
	content: string,
	eventSourceReference: string,
	type: EActionType
) {
	const token = await postToken();

	if (!token) {
		return;
	}

	await fetch(`${token.serviceURL}${AI_HUB_ENDPOINT}/tasks`, {
		body: JSON.stringify({
			context: {
				text: content,
			},
			scope: {
				externalReferenceCode: 'L_CMS',
			},
			sseEventSinkKey: eventSourceReference,
			type,
		}),
		headers: new Headers({
			'Accept': 'application/json',
			'Authorization': `Bearer ${token.accessToken}`,
			'Content-Type': 'application/json',
			'Liferay-AI-Hub-On-Behalf-Of': `Bearer ${token.userToken}`,
		}),
		method: 'POST',
	});
}
