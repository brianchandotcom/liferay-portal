/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {ContentRetriever} from '../types/ContentRetriever';

async function getContentRetriever(externalReferenceCode: string) {
	const response = await fetch(
		`/o/ai-hub/content-retrievers/by-external-reference-code/${externalReferenceCode}`,
		{
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'GET',
		}
	);

	return response.json();
}

async function putContentRetriever(
	contentRetriever: ContentRetriever,
	externalReferenceCode: string
) {
	const response = await fetch(
		`/o/ai-hub/v1.0/content-retrievers/by-external-reference-code/${externalReferenceCode}`,
		{
			body: JSON.stringify(contentRetriever),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'PUT',
		}
	);

	if (!response.ok) {
		const error = await response
			.json()
			.catch(() => new Error(response.statusText));
		throw error;
	}

	return response.json();
}

export {putContentRetriever, getContentRetriever};
