/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';
import {fetch} from 'frontend-js-web';

import {EActionType} from './types';

export async function createEventSource() {
	const tokens = await postToken();

	if (!tokens) {
		return;
	}

	return new EventSource('/o/ai-hub/v1.0/tasks/subscribe', {
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
		const response = await fetch('/o/ai-hub/v1.0/tokens', {method: 'POST'});

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

export async function postTask(
	content: string,
	eventSourceReference: string,
	type: EActionType
) {
	const tokens = await postToken();

	if (!tokens) {
		return;
	}

	await fetch(`/o/ai-hub/v1.0/tasks`, {
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
			'Authorization': `Bearer ${tokens.accessToken}`,
			'Content-Type': 'application/json',
			'Liferay-AI-Hub-On-Behalf-Of': `Bearer ${tokens.userToken}`,
		}),
		method: 'POST',
	});
}
