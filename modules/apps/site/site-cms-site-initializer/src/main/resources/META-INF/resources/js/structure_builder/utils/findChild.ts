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
	for (const field of parent.fields.values()) {
		if (field.uuid === uuid) {
			return field;
		}

		if (field.type === 'repeatable-group') {
			return findChild(field, uuid);
		}
	}

	return null;
}
