/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {DataConverter} from 'data-engine-taglib';

import {normalizeDataLayout} from '../../../src/main/resources/META-INF/resources/js/utils/normalizers.es';

const dataDefinition = {
	availableLanguageIds: ['en_US'],
	dataDefinitionFields: [
		{
			customProperties: {
				displayStyle: 'singleline',
				fieldNamespace: '',
				fieldReference: 'Text97181041',
				placeholder: {
					en_US: 'Type your name',
				},
				tooltip: {
					en_US: '',
				},
			},
			defaultValue: {
				en_US: '',
			},
			fieldType: 'text',
			indexType: 'keyword',
			indexable: true,
			label: {
				en_US: 'Text',
			},
			localizable: true,
			name: 'Text97181041',
			nestedDataDefinitionFields: [],
			readOnly: false,
			repeatable: false,
			required: true,
			showLabel: true,
			tip: {
				en_US: '',
			},
		},
		{
			customProperties: {
				fieldReference: 'SelectFromList15352821',
				multiple: false,
				nativeField: false,
				options: {
					en_US: [
						{
							label: 'Option',
							value: '',
						},
					],
				},
			},
			defaultValue: {
				en_US: [],
			},
			fieldType: 'select',
			label: {
				en_US: 'Select from List',
			},
			localizable: true,
			name: 'SelectFromList15352821',
			nestedDataDefinitionFields: [],
			readOnly: false,
			repeatable: false,
			required: false,
			showLabel: true,
			tip: {
				en_US: '',
			},
		},
		{
			customProperties: {
				fieldReference: 'MultipleSelection22024132',
				inline: false,
				options: {
					en_US: [
						{
							label: 'Option',
							value: '',
						},
					],
				},
				showAsSwitcher: false,
			},
			defaultValue: {
				en_US: [],
			},
			fieldType: 'checkbox_multiple',
			label: {
				en_US: 'Multiple Selection',
			},
			localizable: true,
			name: 'MultipleSelection22024132',
			nestedDataDefinitionFields: [],
			readOnly: false,
			repeatable: false,
			required: false,
			showLabel: true,
			tip: {
				en_US: '',
			},
		},
	],
	defaultLanguageId: 'en_US',
	name: {
		en_US: 'test',
	},
};

const dataLayout = {
	dataLayoutFields: {
		MultipleSelection22024132: {
			required: false,
		},
		SelectFromList15352821: {
			required: false,
		},
		Text97181041: {
			required: true,
		},
	},
	dataLayoutPages: [
		{
			dataLayoutRows: [
				{
					dataLayoutColumns: [
						{
							columnSize: 12,
							fieldNames: ['Text97181041'],
						},
					],
				},
				{
					dataLayoutColumns: [
						{
							columnSize: 12,
							fieldNames: ['SelectFromList15352821'],
						},
					],
				},
				{
					dataLayoutColumns: [
						{
							columnSize: 12,
							fieldNames: ['MultipleSelection22024132'],
						},
					],
				},
			],
			description: {
				en_US: '',
			},
			title: {
				en_US: '',
			},
		},
	],
	dataRules: [],
	name: {
		en_US: 'Form',
	},
	paginationMode: 'single-page',
};

const fields = [
	'Text97181041',
	'SelectFromList15352821',
	'MultipleSelection22024132',
].map((fieldName) => {
	const dataDefinitionField = dataDefinition.dataDefinitionFields.find(
		(definitionField) => definitionField.name === fieldName
	);

	return {
		label: dataDefinitionField.label,
		placeholder: dataDefinitionField.customProperties.placeholder,
		required: dataDefinitionField.required,
	};
});

describe('normalizers', () => {
	it('Validate DataLayout visualProperties', () => {
		const defaultLanguageId = 'en_US';
		const editingLanguageId = 'en_US';

		const dataLayoutBuilder = {
			props: {
				appContext: [{}],
				fieldTypes: undefined,
			},
		};

		jest.spyOn(DataConverter, 'getDDMSettingsContextWithVisualProperties')
			.mockImplementationOnce(() => fields[0])
			.mockImplementationOnce(() => fields[1])
			.mockImplementationOnce(() => fields[2]);

		const normalizedDataLayout = normalizeDataLayout({
			dataDefinition,
			dataLayout,
			dataLayoutBuilder,
			defaultLanguageId,
			editingLanguageId,
		});

		expect(
			normalizedDataLayout.dataLayoutFields.Text97181041
		).toStrictEqual({
			placeholder: {
				en_US: 'Type your name',
			},
			required: true,
		});

		expect(
			normalizedDataLayout.dataLayoutFields.MultipleSelection22024132
		).toStrictEqual({
			placeholder: undefined,
			required: false,
		});

		expect(
			normalizedDataLayout.dataLayoutFields.SelectFromList15352821
		).toStrictEqual({
			placeholder: undefined,
			required: false,
		});
	});
});
