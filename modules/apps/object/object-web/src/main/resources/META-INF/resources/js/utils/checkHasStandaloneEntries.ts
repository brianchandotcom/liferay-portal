/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';

import {getSettingValue} from './objectDefinitionSettings';

export async function checkHasStandaloneEntries(
	isDescendant: boolean,
	objectDefinition: Partial<ObjectDefinition>
): Promise<boolean> {
	if (!isDescendant || !objectDefinition.restContextPath) {
		return false;
	}

	const allowStandaloneObjectEntry = getSettingValue(
		objectDefinition.objectDefinitionSettings,
		'allowStandaloneObjectEntry'
	);

	if ((allowStandaloneObjectEntry ?? 'true') !== 'true') {
		return false;
	}

	try {
		const {totalCount} = await API.fetchJSON<{totalCount: number}>(
			`${objectDefinition.restContextPath}/?pageSize=1`
		);

		return totalCount > 0;
	}
	catch (error) {
		return false;
	}
}
