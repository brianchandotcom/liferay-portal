/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../contexts/StateContext';
import {ObjectField} from '../types/ObjectDefinition';

export default function updateFields(
	fields: State['fields'],
	objectFields: ObjectField[]
) {
	const nextFields = new Map(fields);

	for (const objectField of objectFields) {
		const field = fields.get(objectField.name);

		if (!field) {
			continue;
		}

		nextFields.set(field.name, {
			...field,
			erc: objectField.externalReferenceCode,
		});
	}

	return nextFields;
}
