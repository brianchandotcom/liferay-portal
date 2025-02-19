/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {objectDefinitionUtils} from '@liferay/object-js-components-web';

import {Field, State} from '../contexts/StateContext';
import {ObjectDefinition} from '../types/ObjectDefinition';
import {FIELD_TYPE_BUSINESS_TYPE} from './fieldType';

export default function buildObjectDefinition({
	fields = [],
	id,
	name,
}: {
	fields?: Field[];
	id?: State['id'];
	name: State['name'];
}): ObjectDefinition {
	const objectDefinition: ObjectDefinition = {
		label: {
			en_US: name,
		},
		name: objectDefinitionUtils.normalizeName(name),
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
			en_US: name,
		},
		scope: 'company',
	};

	if (id) {
		objectDefinition.id = id;
	}

	return objectDefinition;
}
