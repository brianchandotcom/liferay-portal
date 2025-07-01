/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from '../../common/services/ApiHelper';
import {Structure} from '../types/Structure';
import buildObjectDefinition from '../utils/buildObjectDefinition';
import getRandomId from '../utils/getRandomId';

async function createStructure({
	children,
	erc = getRandomId(),
	label,
	name,
	spaces,
	status,
}: {
	children: Structure['children'];
	erc?: Structure['erc'];
	label: Structure['label'];
	name: Structure['name'];
	spaces: Structure['spaces'];
	status: Structure['status'];
}) {
	const objectDefinition = buildObjectDefinition({
		children,
		erc,
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

async function updateStructure({
	children,
	erc,
	id,
	label,
	name,
	spaces,
	status,
}: {
	children: Structure['children'];
	erc: Structure['erc'];
	id: Structure['id'];
	label: Structure['label'];
	name: Structure['name'];
	spaces: Structure['spaces'];
	status: Structure['status'];
}) {
	const objectDefinition = buildObjectDefinition({
		children,
		erc,
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
	updateStructure,
};
