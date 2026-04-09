/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEFAULT_FETCH_HEADERS} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';

import {DesignLibrary} from '../types';

async function create({
	description,
	name,
}: {
	description: string;
	name: string;
}) {
	const response = await fetch(
		'/o/headless-asset-library/v1.0/asset-libraries',
		{
			body: JSON.stringify({
				description,
				name,
				type: 'DesignLibrary',
			}),
			headers: DEFAULT_FETCH_HEADERS,
			method: 'POST',
		}
	);

	if (!response.ok) {
		const errorData = await response.json().catch(() => {
			return null;
		});

		throw errorData;
	}

	return await response.json();
}

async function get(externalReferenceCode: string): Promise<DesignLibrary> {
	const response = await fetch(
		`/o/headless-asset-library/v1.0/asset-libraries/${externalReferenceCode}`,
		{
			headers: DEFAULT_FETCH_HEADERS,
			method: 'GET',
		}
	);

	if (!response.ok) {
		const errorData = await response.json().catch(() => {
			return null;
		});

		throw errorData;
	}

	return await response.json();
}

async function remove({href, method}: {href: string; method: string}) {
	const response = await fetch(href, {
		headers: DEFAULT_FETCH_HEADERS,
		method,
	});

	if (!response.ok) {
		const errorData = await response.json().catch(() => {
			return null;
		});

		throw errorData;
	}
}

async function update(externalReferenceCode: string, body: any) {
	const response = await fetch(
		`/o/headless-asset-library/v1.0/asset-libraries/${externalReferenceCode}`,
		{
			body: JSON.stringify(body),
			headers: DEFAULT_FETCH_HEADERS,
			method: 'PATCH',
		}
	);

	if (!response.ok) {
		const errorData = await response.json().catch(() => {
			return null;
		});

		throw errorData;
	}

	return await response.json();
}

export default {
	create,
	get,
	remove,
	update,
};
