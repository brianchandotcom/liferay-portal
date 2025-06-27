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

export default function findChild(
	parent: Structure | RepeatableGroup,
	uuid: Uuid
): Field | ReferencedStructure | RepeatableGroup | null {
	for (const child of parent.children.values()) {
		if (child.uuid === uuid) {
			return child;
		}

		if (child.type === 'repeatable-group') {
			return findChild(child, uuid);
		}
	}

	return null;
}
