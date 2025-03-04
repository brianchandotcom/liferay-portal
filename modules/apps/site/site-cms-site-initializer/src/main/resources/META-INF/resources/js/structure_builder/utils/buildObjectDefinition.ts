/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config';
import {State} from '../contexts/StateContext';
import {ObjectDefinition, ObjectField} from '../types/ObjectDefinition';
import {FIELD_TYPE_BUSINESS_TYPE, FIELD_TYPE_DB_TYPE, Field} from './field';

export default function buildObjectDefinition({
	erc,
	fields = [],
	id,
	label,
	name,
}: {
	erc: string;
	fields?: Field[];
	id?: State['id'];
	label: State['label'];
	name?: State['name'];
}): ObjectDefinition {
	const objectDefinition: ObjectDefinition = {
		enableObjectEntryDraft: true,
		externalReferenceCode: erc,
		label,
		objectFields: buildFields(fields),
		pluralLabel: label,
		scope: 'site',
	};

	if (id) {
		objectDefinition.id = id;
	}

	if (name) {
		objectDefinition.name = name;
	}

	if (config.objectFolderExternalReferenceCode) {
		objectDefinition.objectFolderExternalReferenceCode =
			config.objectFolderExternalReferenceCode;
	}

	return objectDefinition;
}

function buildFields(fields: Field[]) {
	return fields.map((field) => {
		const objectField: ObjectField = {
			DBType: FIELD_TYPE_DB_TYPE[field.type],
			businessType: FIELD_TYPE_BUSINESS_TYPE[field.type],
			externalReferenceCode: field.erc,
			indexed: field.indexableConfig.indexed,
			label: field.label,
			localized: field.localized,
			name: field.name,
			required: field.required,
		};

		if (field.indexableConfig.indexed) {
			objectField.indexedAsKeyword =
				field.indexableConfig.indexedAsKeyword;
			objectField.indexedLanguageId =
				field.indexableConfig.indexedLanguageId ?? '';
		}

		if ('settings' in field) {
			objectField.objectFieldSettings = Object.entries(
				field.settings
			).map(([name, value]) => ({name, value}));
		}

		return objectField;
	});
}
