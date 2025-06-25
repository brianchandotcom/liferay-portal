/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ReferencedStructure,
	RepeatableGroup,
	Structure,
} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import {Field} from './field';
import getFieldsArray from './getFieldsArray';
import getRandomId from './getRandomId';
import getRandomName from './getRandomName';
import getUuid from './getUuid';

const DEFAULT_GROUP_LABEL = Liferay.Language.get('repeatable-group');

export default function insertGroup({
	groupFields,
	groupParent,
	root,
}: {
	groupFields: (Field | ReferencedStructure | RepeatableGroup)[];
	groupParent: Uuid;
	root: Structure | RepeatableGroup;
}): Structure['fields'] | RepeatableGroup['fields'] {
	const fields = new Map();

	// Iterate over fields

	for (const field of getFieldsArray(root)) {

		// Don't insert the field if it belongs to the new group

		if (groupFields.some(({uuid}) => uuid === field.uuid)) {
			continue;
		}

		// Insert the field. If it's a repeatable group, build it with recursive call

		if (field.type === 'repeatable-group') {
			const group: RepeatableGroup = {
				...field,
				fields: insertGroup({
					groupFields,
					groupParent,
					root: field,
				}),
			};

			fields.set(group.uuid, group);
		}
		else {
			fields.set(field.uuid, field);
		}
	}

	// Insert new group if this is the correct parent

	if (root.uuid === groupParent) {
		const uuid = getUuid();

		const group: RepeatableGroup = {
			erc: getRandomId(),
			fields: new Map(
				groupFields.map((field) => [
					field.uuid,
					{...field, parent: uuid},
				])
			),
			label: {
				[Liferay.ThemeDisplay.getDefaultLanguageId()]:
					DEFAULT_GROUP_LABEL,
			},
			name: getRandomName(),
			parent: groupParent,
			type: 'repeatable-group',
			uuid,
		};

		fields.set(group.uuid, group);
	}

	return fields;
}
