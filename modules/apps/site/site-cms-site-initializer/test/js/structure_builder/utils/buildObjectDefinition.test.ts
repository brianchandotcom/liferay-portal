/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import buildObjectDefinition from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/buildObjectDefinition';
import {Field} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/field';

const DATE_TIME_FIELD: Field = {
	erc: 'datetime-field',
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
	label: {en_US: 'Text Field'},
	localized: false,
	name: 'textField',
	required: true,
	type: 'text',
};

describe('buildObjectDefinition', () => {
	it('Builds objectDefinition with a field without settings', () => {
		const result = buildObjectDefinition({
			erc: 'structureERC',
			fields: [TEXT_FIELD],
			id: 1,
			label: 'Structure',
			name: 'myStructure',
		});

		expect(result).toEqual({
			externalReferenceCode: 'structureERC',
			id: 1,
			label: {en_US: 'Structure'},
			name: 'myStructure',
			objectFields: [
				{
					businessType: 'Text',
					externalReferenceCode: 'text-field',
					label: {en_US: 'Text Field'},
					localized: false,
					name: 'textField',
					required: true,
				},
			],
			pluralLabel: {en_US: 'Structure'},
			scope: 'site',
		});
	});

	it('Builds objectDefinition with a field with settings', () => {
		const result = buildObjectDefinition({
			erc: 'structureERC',
			fields: [DATE_TIME_FIELD],
			id: 1,
			label: 'Structure',
			name: 'myStructure',
		});

		expect(result).toEqual({
			externalReferenceCode: 'structureERC',
			id: 1,
			label: {en_US: 'Structure'},
			name: 'myStructure',
			objectFields: [
				{
					businessType: 'DateTime',
					externalReferenceCode: 'datetime-field',
					label: {en_US: 'Date and Time Field'},
					localized: true,
					name: 'datetimeField',
					objectFieldSettings: [
						{name: 'timeStorage', value: 'convertToUTC'},
					],
					required: false,
				},
			],
			pluralLabel: {en_US: 'Structure'},
			scope: 'site',
		});
	});
});
