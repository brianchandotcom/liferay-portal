/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, objectDefinitionUtils} from '@liferay/object-js-components-web';

import {Field, State} from '../contexts/StateContext';
import buildObjectDefinition from '../utils/buildObjectDefinition';

async function createStructure({
	fields,
	label,
	name = objectDefinitionUtils.normalizeName(label),
}: {
	fields: Field[];
	label: State['label'];
	name?: State['name'];
}) {
	const objectDefinition = buildObjectDefinition({
		fields,
		label,
		name,
	});

	return await API.postObjectDefinition(objectDefinition);
}

async function publishStructure({id}: {id: State['id']}) {
	if (!id) {
		return;
	}

	return await API.postObjectDefinitionPublish(id);
}

async function updateStructure({
	fields,
	id,
	label,
	name,
}: {
	fields: Field[];
	id: State['id'];
	label: State['label'];
	name: State['name'];
}) {
	const objectDefinition = buildObjectDefinition({fields, id, label, name});

	return await API.putObjectDefinition(objectDefinition);
}

export default {
	createStructure,
	publishStructure,
	updateStructure,
};
