/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';

import {getSettingValue} from '../../utils/objectDefinitionSettings';

/**
 * Returns true if disabling inheritance would orphan child entries.
 *
 * We iterate the parent-nested route because the child endpoint is
 * hidden in strict mode and `rootObjectEntryId` is not filterable on
 * the headless layer. Parent listing defaults to `pageSize=20`; beyond
 * that, BE rejects on save (LPD-87998).
 */
export async function checkDisableInheritanceBlocked(
	values: Partial<ObjectRelationship>
): Promise<boolean> {
	if (!values.id || values.type !== 'oneToMany') {
		return false;
	}

	const childExternalReferenceCode =
		values.objectDefinitionExternalReferenceCode2;
	const parentExternalReferenceCode =
		values.objectDefinitionExternalReferenceCode1;

	if (!childExternalReferenceCode || !parentExternalReferenceCode) {
		return false;
	}

	const childObjectDefinition =
		await API.getObjectDefinitionByExternalReferenceCode(
			childExternalReferenceCode
		);

	const allowStandaloneObjectEntry =
		(getSettingValue(
			childObjectDefinition.objectDefinitionSettings,
			'allowStandaloneObjectEntry'
		) ?? 'true') === 'true';

	if (allowStandaloneObjectEntry) {
		return false;
	}

	const rootExternalReferenceCodes =
		getSettingValue(
			childObjectDefinition.objectDefinitionSettings,
			'rootObjectDefinitionExternalReferenceCodes'
		) ?? '';

	const edgeCount = rootExternalReferenceCodes
		? rootExternalReferenceCodes.split(',').filter(Boolean).length
		: 0;

	if (edgeCount <= 1) {
		return false;
	}

	const parentObjectDefinition =
		await API.getObjectDefinitionByExternalReferenceCode(
			parentExternalReferenceCode
		);

	if (!parentObjectDefinition.restContextPath || !values.name) {
		return false;
	}

	const {items = []} = await API.fetchJSON<{
		items?: Array<{id: number}>;
	}>(`${parentObjectDefinition.restContextPath}?fields=id`);

	const linkedEntryCounts = await Promise.all(
		items.map((parentEntry) =>
			API.fetchJSON<{totalCount: number}>(
				`${parentObjectDefinition.restContextPath}/${parentEntry.id}/${values.name}?pageSize=1`
			)
		)
	);

	return linkedEntryCounts.some(({totalCount = 0}) => totalCount > 0);
}
