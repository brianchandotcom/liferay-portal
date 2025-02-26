/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field, State} from '../contexts/StateContext';
import buildObjectDefinition from '../utils/buildObjectDefinition';
import ApiHelper from './ApiHelper';

async function createStructure({
	fields,
	label,
	name,
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

	return await ApiHelper.post(
		'/o/object-admin/v1.0/object-definitions',
		objectDefinition
	);
}

async function publishStructure({id}: {id: State['id']}) {
	if (!id) {
		return;
	}

	return await ApiHelper.post(
		`/o/object-admin/v1.0/object-definitions/${id}/publish`
	);
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

	return await ApiHelper.put(
		`/o/object-admin/v1.0/object-definitions/${id}`,
		objectDefinition
	);
}

export default {
	createStructure,
	publishStructure,
	updateStructure,
};
