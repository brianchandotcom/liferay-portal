/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';
import {fetch} from 'frontend-js-web';

export function createEventSource() {
	return new EventSource('/o/ai-hub/v1.0/chats/subscribe', {
		fetch: (input, init) =>
			fetch(input as RequestInfo, {
				...init,
				headers: new Headers({
					'Accept': 'text/event-stream',
					'x-csrf-token': Liferay.authToken,
				}),
			}),
		withCredentials: true,
	});
}

export async function postChatByExternalReferenceCodeMessage(
	content: string,
	eventSourceReference: string,
	message: string,
	title: string
) {
	await fetch(
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
				'Content-Type': 'application/json',
			}),
			method: 'POST',
		}
	);
}
