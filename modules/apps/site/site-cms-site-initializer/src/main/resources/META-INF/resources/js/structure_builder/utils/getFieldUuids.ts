/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RepeatableGroup, Structure} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import getFieldsArray from './getFieldsArray';

export function getFieldUuids(
	item: Structure | RepeatableGroup,
	uuids: Set<Uuid> = new Set()
) {
	for (const field of getFieldsArray(item)) {
		uuids.add(field.uuid);

		if (field.type === 'repeatable-group') {
			getFieldUuids(field, uuids);
		}
	}

	return uuids;
}
