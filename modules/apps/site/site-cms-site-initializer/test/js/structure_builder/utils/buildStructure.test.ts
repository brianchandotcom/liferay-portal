/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import buildObjectDefinition from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/buildObjectDefinition';
import buildStructure from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/buildStructure';
import {Field} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/field';
import getUuid from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/getUuid';

const parent = getUuid();

const SAMPLE_STRUCTURE_FIELDS: Field[] = [
	{
		erc: 'attachment-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'attachment-field'},
		localized: false,
		locked: false,
		name: 'attachment-field',
		parent,
		required: false,
		settings: {
			acceptedFileExtensions: 'jpg,png',
			fileSource: 'documentsAndMedia',
			maximumFileSize: 100,
		},
		type: 'upload',
		uuid: getUuid(),
	},
	{
		erc: 'boolean-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'boolean-field'},
		localized: false,
		locked: false,
		name: 'boolean-field',
		parent,
		required: false,
		settings: {},
		type: 'boolean',
		uuid: getUuid(),
	},
	{
		erc: 'date-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'date-field'},
		localized: false,
		locked: false,
		name: 'date-field',
		parent,
		required: false,
		settings: {},
		type: 'date',
		uuid: getUuid(),
	},
	{
		erc: 'datetime-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'datetime-field'},
		localized: false,
		locked: false,
		name: 'datetime-field',
		parent,
		required: false,
		settings: {timeStorage: 'convertToUTC'},
		type: 'datetime',
		uuid: getUuid(),
	},
	{
		erc: 'decimal-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'decimal-field'},
		localized: false,
		locked: false,
		name: 'decimal-field',
		parent,
		required: false,
		settings: {},
		type: 'decimal',
		uuid: getUuid(),
	},
	{
		erc: 'integer-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'integer-field'},
		localized: false,
		locked: false,
		name: 'integer-field',
		parent,
		required: false,
		settings: {},
		type: 'integer',
		uuid: getUuid(),
	},
	{
		erc: 'long-text-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'long-text-field'},
		localized: false,
		locked: false,
		name: 'long-text-field',
		parent,
		required: false,
		settings: {},
		type: 'long-text',
		uuid: getUuid(),
	},
	{
		erc: 'multi-picklist-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'multi-picklist-field'},
		localized: false,
		locked: false,
		multiselection: true,
		name: 'multi-picklist-field',
		parent,
		picklistId: 1,
		required: false,
		settings: {},
		type: 'select-from-list',
		uuid: getUuid(),
	},
	{
		erc: 'picklist-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'picklist-field'},
		localized: false,
		locked: false,
		multiselection: false,
		name: 'picklist-field',
		parent,
		picklistId: 2,
		required: false,
		settings: {},
		type: 'select-from-list',
		uuid: getUuid(),
	},
	{
		erc: 'rich-text-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'rich-text-field'},
		localized: false,
		locked: false,
		name: 'rich-text-field',
		parent,
		required: false,
		settings: {},
		type: 'rich-text',
		uuid: getUuid(),
	},
	{
		erc: 'text-field',
		indexableConfig: {indexed: false},
		label: {en_US: 'text-field'},
		localized: false,
		locked: false,
		name: 'text-field',
		parent,
		required: false,
		settings: {},
		type: 'text',
		uuid: getUuid(),
	},
];

function getChildren(fields: Field[]) {
	const children = new Map();

	for (const field of fields) {
		children.set(field.uuid, field);
	}

	return children;
}

describe('buildStructure', () => {
	it('Maps object field business types to structure field types', () => {
		const objectDefinition = buildObjectDefinition({
			children: getChildren(SAMPLE_STRUCTURE_FIELDS),
			erc: 'main-structure-erc',
			label: {en_US: 'Main Structure'},
			name: 'mainStructure',
			spaces: [],
		});

		const structure = buildStructure({
			mainObjectDefinition: objectDefinition,
			objectDefinitions: {},
		});

		const childrenMap = new Map(
			Array.from(structure.children.values()).map((child) => [
				child.erc,
				child,
			])
		);

		for (const {erc, type} of SAMPLE_STRUCTURE_FIELDS) {
			expect(childrenMap.get(erc)).toEqual(
				expect.objectContaining({type})
			);
		}
	});
});
