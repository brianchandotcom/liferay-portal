/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

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
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'POST',
		}
	);

	const data = await response.json().catch(() => ({}));

	if (!response.ok) {
		throw {
			error: data.title || 'UNKNOWN_ERROR',
			status: data.status || response.status,
		};
	}

	return data as {id: string};
}

async function remove({href, method}: {href: string; method: string}) {
	const response = await fetch(href, {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
		method,
	});

	if (!response.ok) {
		throw new Error(Liferay.Language.get('an-unexpected-error-occurred'));
	}
}

export default {
	create,
	remove,
};
