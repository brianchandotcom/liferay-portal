/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export interface CollaboratorItem {
	actionIds: string[];
	creator: {
		additionalName: string;
		contentType: string;
		familyName: string;
		givenName: string;
		id: number;
		name: string;
	};
	dateExpired?: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	portrait?: string;
	share: boolean;
	type: string;
}

export interface CollaboratorPayload {
	actionIds: string[];
	dateExpired?: string;
	emailAddress?: string;
	id?: string | number;
	share: boolean;
	type: string;
}

function _resolveURL(collaboratorURL: string, itemId: number): string {
	return collaboratorURL
		.replace('{objectEntryId}', itemId.toString())
		.replace('{objectEntryFolderId}', itemId.toString());
}

async function _readErrorMessage(response: Response): Promise<string> {
	try {
		const data = await response.json();

		return data.title || data.message || response.statusText;
	}
	catch (error) {
		return response.statusText;
	}
}

async function getCollaborators(
	collaboratorURL: string,
	itemId: number
): Promise<CollaboratorItem[]> {
	const response = await fetch(_resolveURL(collaboratorURL, itemId), {
		headers: new Headers({
			'Accept': 'application/json',
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
			'X-Accept-All-Languages': 'true',
		}),
		method: 'GET',
	});

	if (!response.ok) {
		throw new Error(await _readErrorMessage(response));
	}

	const data = await response.json();

	return data.items;
}

async function updateCollaborators(
	collaboratorURL: string,
	itemId: number,
	collaborators: CollaboratorPayload[]
): Promise<{error: string | null}> {
	const response = await fetch(_resolveURL(collaboratorURL, itemId), {
		body: JSON.stringify(collaborators),
		headers: new Headers({
			'Accept': 'application/json',
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
			'Content-Type': 'application/json',
		}),
		method: 'POST',
	});

	if (!response.ok) {
		return {error: await _readErrorMessage(response)};
	}

	return {error: null};
}

export default {
	getCollaborators,
	updateCollaborators,
};
