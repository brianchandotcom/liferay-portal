/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RepeatableGroup, Structure, StructureChild} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import getRandomId from './getRandomId';
import getRandomName from './getRandomName';
import getUuid from './getUuid';

const DEFAULT_GROUP_LABEL = Liferay.Language.get('repeatable-group');

export default function insertGroup({
	groupChildren,
	groupParent,
	root,
}: {
	groupChildren: StructureChild[];
	groupParent: Uuid;
	root: Structure | RepeatableGroup;
}): Structure['children'] | RepeatableGroup['children'] {
	const children = new Map();

	// Iterate over children

	for (const child of root.children.values()) {

		// Don't insert the child if it belongs to the new group

		if (groupChildren.some(({uuid}) => uuid === child.uuid)) {
			continue;
		}

		// Insert the child. If it's a repeatable group, build it with recursive call

		if (child.type === 'repeatable-group') {
			const group: RepeatableGroup = {
				...child,
				children: insertGroup({
					groupChildren,
					groupParent,
					root: child,
				}),
			};

			children.set(group.uuid, group);
		}
		else {
			children.set(child.uuid, child);
		}
	}

	// Insert new group if this is the correct parent

	if (root.uuid === groupParent) {
		const uuid = getUuid();

		const group: RepeatableGroup = {
			children: new Map(
				groupChildren.map((child) => [
					child.uuid,
					{...child, parent: uuid},
				])
			),
			erc: getRandomId(),
			label: {
				[Liferay.ThemeDisplay.getDefaultLanguageId()]:
					DEFAULT_GROUP_LABEL,
			},
			name: getRandomName(),
			parent: groupParent,
			type: 'repeatable-group',
			uuid,
		};

		children.set(group.uuid, group);
	}

	return children;
}
