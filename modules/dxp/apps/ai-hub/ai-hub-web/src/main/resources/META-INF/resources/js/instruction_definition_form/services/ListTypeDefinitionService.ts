/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export type ListTypeEntry = {
	key: string;
	name: string;
	name_i18n: {
		[key: string]: string;
	};
};

async function getListTypeEntries(
	listTypeDefinitionExternalReferenceCode: string
) {
	const response = await fetch(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/${listTypeDefinitionExternalReferenceCode}/list-type-entries`,
		{
			method: 'GET',
		}
	);

	if (!response.ok) {
		throw new Error();
	}

	return response.json();
}

export {getListTypeEntries};
