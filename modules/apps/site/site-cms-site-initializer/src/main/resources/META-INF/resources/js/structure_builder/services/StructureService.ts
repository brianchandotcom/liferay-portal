/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from '../../services/ApiHelper';
import {ObjectDefinition} from '../types/ObjectDefinition';
import {ReferencedStructure, Structure, Structures} from '../types/Structure';
import buildObjectDefinition from '../utils/buildObjectDefinition';
import buildStructures from '../utils/buildStructures';
import {Field} from '../utils/field';
import getRandomId from '../utils/getRandomId';

async function createStructure({
	erc = getRandomId(),
	fields,
	label,
	name,
	spaces,
	status,
}: {
	erc?: Structure['erc'];
	fields: (Field | ReferencedStructure)[];
	label: Structure['label'];
	name: Structure['name'];
	spaces: Structure['spaces'];
	status: Structure['status'];
}) {
	const objectDefinition = buildObjectDefinition({
		erc,
		fields,
		label,
		name,
		spaces,
		status,
	});

	return await ApiHelper.post<{id: number}>(
		'/o/object-admin/v1.0/object-definitions',
		objectDefinition
	);
}

async function getStructures(): Promise<Structures> {
	const filter =
		"(objectFolderExternalReferenceCode eq 'L_CMS_CONTENT_STRUCTURES') or (objectFolderExternalReferenceCode eq 'L_CMS_FILE_TYPES')";

	const {data, error} = await ApiHelper.get<{items: ObjectDefinition[]}>(
		`/o/object-admin/v1.0/object-definitions?filter=${filter}`
	);

	if (data) {
		return buildStructures(data.items);
	}

	throw new Error(error);
}

async function updateStructure({
	erc,
	fields,
	id,
	label,
	name,
	spaces,
	status,
}: {
	erc: Structure['erc'];
	fields: (Field | ReferencedStructure)[];
	id: Structure['id'];
	label: Structure['label'];
	name: Structure['name'];
	spaces: Structure['spaces'];
	status: Structure['status'];
}) {
	const objectDefinition = buildObjectDefinition({
		erc,
		fields,
		id,
		label,
		name,
		spaces,
		status,
	});

	return await ApiHelper.put(
		`/o/object-admin/v1.0/object-definitions/${id}`,
		objectDefinition
	);
}

export default {
	createStructure,
	getStructures,
	updateStructure,
};
