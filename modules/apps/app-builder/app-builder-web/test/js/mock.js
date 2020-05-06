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

 
const fieldTypes = [
	{
		icon: 'calendar',
        javaScriptModule:
        'dynamic-data-mapping-form-field-type@5.0.20/DatePicker/DatePicker.es',
        name: 'date',
	},
	{
		icon: 'list',
        javaScriptModule:
        'dynamic-data-mapping-form-field-type@5.0.20/Select/Select.es',
        name: 'select',
	},
	{
		icon: 'adjust',
        javaScriptModule:
        'dynamic-data-mapping-form-field-type@5.0.20/FieldSet/FieldSet.es',
        name: 'fieldset',
	},
	{
		icon: 'integer',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Numeric/Numeric.es',
		name: 'numeric',
	},
	{
		icon: 'check-circle-full',
		javaScriptModule:
        'dynamic-data-mapping-form-field-type@5.0.20/CheckboxMultiple/CheckboxMultiple.es',
		name: 'checkbox_multiple',
	},
	{
		icon: 'radio-button',
		javaScriptModule:
        'dynamic-data-mapping-form-field-type@5.0.20/Radio/Radio.es',
		name: 'radio',
	},
	{
		icon: 'text',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Text/Text.es',
		name: 'text',
	},
	{
		icon: 'upload',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/DocumentLibrary/DocumentLibrary.es',
		name: 'document_library',
	},
];

export const fieldTypeResponse = fieldTypes.map((fieldType, index) => ({
    ...fieldType,
    displayOrder: index,
    group: 'basic',
    label: fieldType.name.toUpperCase(),
    scope: 'app-builder,forms',
    system: false
}))

export const tableViewResponseOneItem = {
	availableLanguageIds: ['en_US'],
	dataDefinitionFields: [
		{
			customProperties: {
				autocomplete: false,
                dataSourceType: 'manual',
				dataType: 'string',
				ddmDataProviderInstanceId: '[]',
				ddmDataProviderInstanceOutput: '[]',
				displayStyle: 'singleline',
				fieldNamespace: '',
				options: {
                    en_US: [
                        {
                            label: 'Option',
							value: 'Option',
						},
					],
				},
                placeholder: {
                    en_US: '',
                },
				tooltip: {
                    en_US: '',
				},
                visibilityExpression: '',
			},
			defaultValue: {
				en_US: '',
			},
			description: {
				en_US: 'Say your name Player',
			},
			fieldType: 'text',
			indexType: 'keyword',
			indexable: true,
			label: {
				en_US: 'Player',
			},
			localizable: true,
			name: 'Text',
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
	dataDefinitionKey: '36601',
	dateCreated: '2020-04-24T13:50:04Z',
	dateModified: '2020-04-24T13:50:13Z',
	defaultLanguageId: 'en_US',
	description: {},
	id: 36602,
	name: {
		en_US: 'teste',
	},
	siteId: 20125,
	storageType: 'json',
	userId: 20127,
};

export const tableViewResponseTwoItens = {
	availableLanguageIds: ['en_US'],
	dataDefinitionFields: [
		{
			customProperties: {
				autocomplete: false,
                dataSourceType: 'manual',
				dataType: 'string',
				ddmDataProviderInstanceId: '[]',
				ddmDataProviderInstanceOutput: '[]',
				displayStyle: 'singleline',
				fieldNamespace: '',
				options: {
                    en_US: [
                        {
                            label: 'Option',
							value: 'Option',
						},
					],
				},
				placeholder: {
                    en_US: '',
				},
                tooltip: {
                    en_US: '',
                },
                visibilityExpression: '',
			},
			defaultValue: {
				en_US: '',
			},
			fieldType: 'text',
			indexType: 'keyword',
			indexable: true,
			label: {
				en_US: 'Player',
			},
			localizable: true,
			name: 'Text',
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
                dataSourceType: '[manual]',
				dataType: 'string',
				ddmDataProviderInstanceId: '[]',
				ddmDataProviderInstanceOutput: '[]',
                fieldNamespace: '',
                multiple: false,
				options: {
                    en_US: [
                        {
                            label: 'INTZ',
							value: 'INTZ',
						},
						{
                            label: 'PAIN',
							value: 'PAIN',
						},
						{
                            label: 'SKT',
							value: 'SKT',
						},
					],
				},
                visibilityExpression: '',
			},
			defaultValue: {
				en_US: [],
			},
			fieldType: 'select',
			indexType: 'keyword',
			indexable: true,
			label: {
				en_US: 'Team',
			},
			localizable: true,
			name: 'SelectFromList',
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
	dataDefinitionKey: '36715',
	dateCreated: '2020-04-29T16:48:28Z',
	dateModified: '2020-04-29T16:49:03Z',
	defaultLanguageId: 'en_US',
	description: {},
	id: 36716,
	name: {
		en_US: 'teste',
	},
	siteId: 20125,
	storageType: 'json',
	userId: 20127,
};
