/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../contexts/StateContext';
import buildObjectDefinition from '../utils/buildObjectDefinition';
import {Field} from '../utils/field';
import getRandomId from '../utils/getRandomId';
import ApiHelper from './ApiHelper';

async function createStructure({
	erc = getRandomId(),
	fields,
	label,
	name,
	spaces,
}: {
	erc?: State['erc'];
	fields: Field[];
	label: State['label'];
	name?: State['name'];
	spaces: State['spaces'];
}) {
	const objectDefinition = buildObjectDefinition({
		erc,
		fields,
		label,
		name,
		spaces,
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
	erc,
	fields,
	id,
	label,
	name,
	spaces,
}: {
	erc: State['erc'];
	fields: Field[];
	id: State['id'];
	label: State['label'];
	name: State['name'];
	spaces: State['spaces'];
}) {
	const objectDefinition = buildObjectDefinition({
		erc,
		fields,
		id,
		label,
		name,
		spaces,
	});

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
