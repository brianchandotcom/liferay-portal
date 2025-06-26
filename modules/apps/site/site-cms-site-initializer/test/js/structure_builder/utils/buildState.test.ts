/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/contexts/StateContext';
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
	parent: getUuid(),
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
	parent: getUuid(),
	required: true,
	settings: {},
	type: 'text',
	uuid: TEXT_FIELD_UUID,
};

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/structure_builder/config',
	() => {
		return {
			config: {
				objectFolderExternalReferenceCode: 'L_CMS_CONTENT_STRUCTURES',
			},
		};
	}
);

describe('buildState', () => {
	it('Builds state with two fields ', () => {
		const structure: State['structure'] = {
			erc: 'structureERC',
			fields: new Map(),
			id: 1,
			label: {en_US: 'Structure'},
			name: 'myStructure',
			spaces: [],
			status: 'draft',
			type: 'L_CMS_CONTENT_STRUCTURES',
			uuid: getUuid(),
		};

		const initialState: State = {
			error: null,
			history: {deletedFields: false},
			invalids: new Map(),
			publishedFields: new Set(),
			selection: [],
			structure,
			unsavedChanges: false,
		};

		const objectDefinition = buildObjectDefinition({
			erc: structure.erc,
			fields: Array.from([TEXT_FIELD, DATE_TIME_FIELD]),
			id: structure.id,
			label: structure.label,
			name: structure.name,
			spaces: structure.spaces,
		});

		const result = buildState(objectDefinition);

		const {fields, uuid} = result!.structure;

		const nextState = {
			...initialState,
			structure: {
				...structure,
				fields,
				uuid,
			},
		};

		expect(result).toEqual(nextState);
	});

	it('Takes into account the status of the object definition', () => {
		const structure: State['structure'] = {
			erc: 'structureERC',
			fields: new Map(),
			id: 1,
			label: {en_US: 'Structure'},
			name: 'myStructure',
			spaces: [],
			status: 'published',
			type: 'L_CMS_CONTENT_STRUCTURES',
			uuid: getUuid(),
		};

		const initialState: State = {
			error: null,
			history: {deletedFields: false},
			invalids: new Map(),
			publishedFields: new Set(),
			selection: [],
			structure,
			unsavedChanges: false,
		};

		const objectDefinition = buildObjectDefinition({
			erc: structure.erc,
			fields: Array.from([TEXT_FIELD, DATE_TIME_FIELD]),
			id: structure.id,
			label: structure.label,
			name: structure.name,
			spaces: structure.spaces,
		});

		const result = buildState({
			...objectDefinition,
			status: {
				code: 0,
			},
		});

		const {fields, uuid} = result!.structure;

		const publishedFields = new Set(fields.keys());

		const nextState = {
			...initialState,
			publishedFields,
			structure: {
				...structure,
				fields,
				uuid,
			},
		};

		expect(result).toEqual(nextState);
	});

	it('Takes into account spaces', () => {
		const structure: State['structure'] = {
			erc: 'structureERC',
			fields: new Map(),
			id: 1,
			label: {en_US: 'Structure'},
			name: 'myStructure',
			spaces: ['space-1-erc', 'space-2-erc'],
			status: 'published',
			type: 'L_CMS_CONTENT_STRUCTURES',
			uuid: getUuid(),
		};

		const initialState: State = {
			error: null,
			history: {deletedFields: false},
			invalids: new Map(),
			publishedFields: new Set(),
			selection: [],
			structure,
			unsavedChanges: false,
		};

		const objectDefinition = buildObjectDefinition({
			erc: structure.erc,
			fields: Array.from([TEXT_FIELD, DATE_TIME_FIELD]),
			id: structure.id,
			label: structure.label,
			name: structure.name,
			spaces: structure.spaces,
		});

		const result = buildState({
			...objectDefinition,
			status: {
				code: 0,
			},
		});

		const {fields, uuid} = result!.structure;

		const publishedFields = new Set(fields.keys());

		const nextState = {
			...initialState,
			publishedFields,
			structure: {
				...structure,
				fields,
				uuid,
			},
		};

		expect(result).toEqual(nextState);
	});

	it('It works with Double fields ', () => {
		const objectDefinition = {
			enableFriendlyURLCustomization: true,
			enableIndexSearch: true,
			enableLocalization: true,
			enableObjectEntryDraft: true,
			enableObjectEntryVersioning: true,
			externalReferenceCode: 'ca7f96e2-3436-4aa4-9626-265d006bea87',
			label: {
				en_US: 'Untitled Structure',
			},
			objectFields: [
				{
					DBType: 'Double',
					businessType: 'Decimal',
					externalReferenceCode: 'decimal-field',
					indexed: true,
					label: {
						en_US: 'Decimal',
					},
					localized: true,
					name: 'decimal',
					required: false,
					type: 'Double',
				},
			],
			pluralLabel: {
				en_US: 'Untitled Structure',
			},
			scope: 'depot' as const,
		};

		const state = buildState({
			...objectDefinition,
		});

		const [, field] = [...state!.structure.fields][0];

		expect(field).toEqual(expect.objectContaining({type: 'decimal'}));
	});
});
