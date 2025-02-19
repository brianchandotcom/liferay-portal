/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field, State} from '../contexts/StateContext';
import {ObjectDefinition} from '../types/ObjectDefinition';
import {FIELD_TYPE_BUSINESS_TYPE} from './fieldType';

export default function buildObjectDefinition({
	fields = [],
	id,
	label,
	name,
}: {
	fields?: Field[];
	id?: State['id'];
	label: State['label'];
	name?: string;
}): ObjectDefinition {
	const objectDefinition: ObjectDefinition = {
		label: {
			en_US: label,
		},
		objectFields: fields.map((field) => ({
			businessType: FIELD_TYPE_BUSINESS_TYPE[field.type],
			externalReferenceCode: field.erc,
			label: {
				en_US: field.label,
			},
			localized: false,
			name: field.name,
			required: false,
		})),
		pluralLabel: {
			en_US: label,
		},
		scope: 'company',
	};

	if (id) {
		objectDefinition.id = id;
	}

	if (name) {
		objectDefinition.name = name;
	}

	return objectDefinition;
}
