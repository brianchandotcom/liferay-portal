/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

import {config} from '../config';
import {
	ObjectDefinition,
	ObjectField,
	ObjectRelationship,
} from '../types/ObjectDefinition';
import {ReferencedStructure, Structure} from '../types/Structure';
import {
	FIELD_TYPE_TO_BUSINESS_TYPE,
	FIELD_TYPE_TO_DB_TYPE,
	Field,
} from './field';
import {isFieldTextSearchable} from './isFieldTextSearchable';

export default function buildObjectDefinition({
	children = new Map(),
	erc,
	id,
	label,
	name,
	spaces,
	status = 'draft',
}: {
	children?: Structure['children'];
	erc: Structure['erc'];
	id?: Structure['id'];
	label: Structure['label'];
	name: Structure['name'];
	spaces: Structure['spaces'];
	status?: Structure['status'];
}): ObjectDefinition {
	const objectDefinition: ObjectDefinition = {
		enableFriendlyURLCustomization: true,
		enableIndexSearch: true,
		enableLocalization: true,
		enableObjectEntryDraft: true,
		enableObjectEntryVersioning: true,
		externalReferenceCode: erc,
		label,
		objectFields: buildFields(getFields(children)),
		objectRelationships: buildRelationships(
			erc,
			getReferencedStructures(children)
		),
		pluralLabel: label,
		scope: 'depot',
		status: {
			code: status === 'published' ? 0 : 2,
		},
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

	if (spaces === 'all') {
		objectDefinition.objectDefinitionSettings = [
			{name: 'acceptAllGroups', value: 'true'},
		];
	}
	else if (spaces.length) {
		objectDefinition.objectDefinitionSettings = [
			{
				name: 'acceptedGroupExternalReferenceCodes',
				value: spaces.join(','),
			},
		];
	}

	return objectDefinition;
}

function getFields(children: Structure['children']): Field[] {
	return Array.from(children.values()).filter(
		(child) =>
			child.type !== 'referenced-structure' &&
			child.type !== 'repeatable-group'
	) as Field[];
}

function getReferencedStructures(
	children: Structure['children']
): ReferencedStructure[] {
	return Array.from(children.values()).filter(
		(child) => child.type === 'referenced-structure'
	) as ReferencedStructure[];
}

function buildFields(fields: Field[]) {
	return fields.map((field) => {
		const objectField: ObjectField = {
			DBType: FIELD_TYPE_TO_DB_TYPE[field.type],
			businessType: FIELD_TYPE_TO_BUSINESS_TYPE[field.type],
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

			if (isFieldTextSearchable(field)) {
				objectField.indexedLanguageId =
					field.indexableConfig.indexedLanguageId ?? '';
			}
		}

		if ('settings' in field) {
			objectField.objectFieldSettings = Object.entries(field.settings)
				.filter(([_, value]) => !isNullOrUndefined(value))
				.map(([name, value]) => ({name, value}));
		}

		if ('picklistId' in field) {
			objectField.listTypeDefinitionId = field.picklistId;
		}

		return objectField;
	});
}

function buildRelationships(
	erc: Structure['erc'],
	referencedStructures: ReferencedStructure[]
) {
	return referencedStructures.map((referencedStructure) => {
		const relationship: ObjectRelationship = {
			deletionType: 'cascade',
			label: {
				en_US: referencedStructure.name,
			},
			name: referencedStructure.relationshipName,
			objectDefinitionExternalReferenceCode1: erc,
			objectDefinitionExternalReferenceCode2: referencedStructure.erc,
			type: 'oneToMany',
		};

		return relationship;
	});
}
