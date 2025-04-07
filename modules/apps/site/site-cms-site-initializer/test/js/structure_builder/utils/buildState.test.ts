/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import buildObjectDefinition from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/buildObjectDefinition';
import buildState from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/buildState';
import {Field} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/field';
import getUuid from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/getUuid';

const DATE_TIME_FIELD_UUID = getUuid();
const TEXT_FIELD_UUID = getUuid();

const DATE_TIME_FIELD: Field = {
	erc: 'datetime-field',
	indexableConfig: {indexed: false},
	label: {en_US: 'Date and Time Field'},
	localized: true,
	name: 'datetimeField',
	required: false,
	settings: {
		timeStorage: 'convertToUTC',
	},
	type: 'datetime',
	uuid: DATE_TIME_FIELD_UUID,
};

const TEXT_FIELD: Field = {
	erc: 'text-field',
	indexableConfig: {
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: undefined,
	},
	label: {en_US: 'Text Field'},
	localized: false,
	name: 'textField',
	required: true,
	settings: {},
	type: 'text',
	uuid: TEXT_FIELD_UUID,
};

describe('buildState', () => {
	it('Builds state with two fields ', () => {
		const initialState = {
			erc: 'structureERC',
			error: null,
			history: {deletedFields: false},
			id: 1,
			invalids: new Map(),
			label: {en_US: 'Structure'},
			name: 'myStructure',
			publishedFields: new Set(),
			selection: [],
			spaces: [],
			status: 'draft',
		};

		const objectDefinition = buildObjectDefinition({
			erc: initialState.erc,
			fields: Array.from([TEXT_FIELD, DATE_TIME_FIELD]),
			id: initialState.id,
			label: initialState.label,
			name: initialState.name,
			spaces: initialState.spaces,
		});

		const result = buildState(objectDefinition);

		const {fields, uuid} = result!;

		expect(result).toEqual({...initialState, fields, uuid});
	});

	it('Takes into account the status of the object definition', () => {
		const initialState = {
			erc: 'structureERC',
			error: null,
			history: {deletedFields: false},
			id: 1,
			invalids: new Map(),
			label: {en_US: 'Structure'},
			name: 'myStructure',
			selection: [],
			spaces: [],
			status: 'published',
		};

		const objectDefinition = buildObjectDefinition({
			erc: initialState.erc,
			fields: Array.from([TEXT_FIELD, DATE_TIME_FIELD]),
			id: initialState.id,
			label: initialState.label,
			name: initialState.name,
			spaces: initialState.spaces,
		});

		const result = buildState({
			...objectDefinition,
			status: {
				label: 'approved',
			},
		});

		const {fields, uuid} = result!;

		const publishedFields = new Set(fields.keys());

		expect(result).toEqual({
			...initialState,
			fields,
			publishedFields,
			uuid,
		});
	});

	it('Takes into account saces', () => {
		const initialState = {
			erc: 'structureERC',
			error: null,
			history: {deletedFields: false},
			id: 1,
			invalids: new Map(),
			label: {en_US: 'Structure'},
			name: 'myStructure',
			selection: [],
			spaces: ['space-1-erc', 'space-2-erc'],
			status: 'published',
		};

		const objectDefinition = buildObjectDefinition({
			erc: initialState.erc,
			fields: Array.from([TEXT_FIELD, DATE_TIME_FIELD]),
			id: initialState.id,
			label: initialState.label,
			name: initialState.name,
			spaces: initialState.spaces,
		});

		const result = buildState({
			...objectDefinition,

			status: {
				label: 'approved',
			},
		});

		const {fields, uuid} = result!;

		const publishedFields = new Set(fields.keys());

		expect(result).toEqual({
			...initialState,
			fields,
			publishedFields,
			uuid,
		});
	});
});
