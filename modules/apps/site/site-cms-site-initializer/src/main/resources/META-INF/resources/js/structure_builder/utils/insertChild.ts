/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RepeatableGroup, Structure, StructureChild} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import sortChildren from './sortChildren';

export default function insertChild({
	child,
	parentUuid,
	root,
}: {
	child: StructureChild;
	parentUuid: Uuid;
	root: Structure | RepeatableGroup;
}): Structure['children'] | RepeatableGroup['children'] {
	const children = new Map();

	// Iterate over children

	for (const rootChild of root.children.values()) {

		// Insert the child. If it's a repeatable group, build it with recursive call

		if (rootChild.type === 'repeatable-group') {
			const group: RepeatableGroup = {
				...rootChild,
				children: insertChild({
					child,
					parentUuid,
					root: rootChild,
				}),
			};

			children.set(group.uuid, group);
		}
		else {
			children.set(rootChild.uuid, rootChild);
		}
	}

	// Insert child if this is the correct parent

	if (root.uuid === parentUuid) {
		children.set(child.uuid, child);
	}

	return sortChildren(children);
}
