/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';

import buildObjectDefinition from '../utils/buildObjectDefinition';

async function saveStructure({name}: {name: string}) {
	const objectDefinition = buildObjectDefinition({name});

	await API.postObjectDefinition(objectDefinition);
}

export default {
	saveStructure,
};
