/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import buildObjectDefinition from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/buildObjectDefinition';
import buildState from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/buildState';
import {Field} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/field';

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
};

const TEXT_FIELD: Field = {
	erc: 'text-field',
	indexableConfig: {indexed: true, indexedAsKeyword: true},
	label: {en_US: 'Text Field'},
	localized: false,
	name: 'textField',
	required: true,
	settings: {},
	type: 'text',
};

describe('buildState', () => {
	it('Builds state with two fields ', () => {
		const initialState = {
			erc: 'structureERC',
			error: null,
			fields: new Map([
				[TEXT_FIELD.name, TEXT_FIELD],
				[DATE_TIME_FIELD.name, DATE_TIME_FIELD],
			]),
			id: 1,
			label: {en_US: 'Structure'},
			name: 'myStructure',
			publishedFields: new Set(),
			selection: [],
			status: 'draft',
		};

		const objectDefinition = buildObjectDefinition({
			erc: initialState.erc,
			fields: Array.from(initialState.fields.values()),
			id: initialState.id,
			label: initialState.label,
			name: initialState.name,
		});

		const result = buildState(objectDefinition);

		expect(result).toEqual(initialState);
	});
});
