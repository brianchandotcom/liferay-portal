/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, objectDefinitionUtils} from '@liferay/object-js-components-web';

async function saveStructure({name}: {name: string}) {
	const objectDefinition = {
		label: {
			en_US: name,
		},
		name: objectDefinitionUtils.normalizeName(name),
		pluralLabel: {
			en_US: name,
		},
		scope: 'company',
	};

	await API.postObjectDefinition(objectDefinition);
}

export default {
	saveStructure,
};
