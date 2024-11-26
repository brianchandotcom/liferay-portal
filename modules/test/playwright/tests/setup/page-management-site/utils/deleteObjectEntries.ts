import {ApiHelpers} from '../../../../helpers/ApiHelpers';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function deleteObjectEntries({
	apiHelpers,
	entityName,
	site,
}: {
	apiHelpers: ApiHelpers;
	entityName: 'allfieldsobjects' | 'lemons' | 'lemonbaskets' | 'potatos';
	site: Site;
}) {
	const {items: entries} =
		await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
			`c/${entityName}`,
			site.key
		);

	const deleteObjectEntryPromises = [];

	for (const entry of entries) {
		deleteObjectEntryPromises.push(
			apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
				`c/${entityName}`,
				entry.externalReferenceCode
			)
		);
	}

	await Promise.all(deleteObjectEntryPromises);
}
