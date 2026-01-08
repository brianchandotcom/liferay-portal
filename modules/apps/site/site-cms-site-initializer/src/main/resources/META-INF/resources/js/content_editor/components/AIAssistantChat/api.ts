/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';
import {fetch} from 'frontend-js-web';

export async function createEventSource() {
	const token = await postToken();

	if (!token) {
		return null;
	}

	return new EventSource('/o/ai-hub/v1.0/chats/subscribe', {
		fetch: (input, init) =>
			fetch(input as RequestInfo, {
				...init,
				headers: new Headers({
					Accept: 'text/event-stream',
					Authorization: `Bearer ${token}`,
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

		return data.accessToken;
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
	const token = await postToken();

	if (!token) {
		return;
	}

	return await fetch(
		`/o/ai-hub/v1.0/chats/by-external-reference-code/${eventSourceReference}/messages`,
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
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			}),
			method: 'POST',
		}
	);
}
