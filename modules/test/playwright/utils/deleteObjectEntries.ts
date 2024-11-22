import {expect} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function deleteObjectEntries({
	apiHelpers,
	entityName,
	excludeERC = [],
	scopeKey,
}: {
	apiHelpers: ApiHelpers;
	entityName: string;
	excludeERC?: string[];
	scopeKey: Site['key'];
}) {
	const {items: entries} =
		await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
			`c/${entityName}`,
			scopeKey
		);

	const promises = [];

	for (const {externalReferenceCode} of entries) {
		if (excludeERC.includes(externalReferenceCode)) {
			continue;
		}

		promises.push(
			apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
				`c/${entityName}`,
				scopeKey,
				externalReferenceCode
			)
		);
	}

	const results = await Promise.all(promises);

	for (const result of results) {
		await expect(result).toBeOK();
	}
}
