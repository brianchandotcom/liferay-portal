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

export const FIELDS = [
	{
		dataType: 'string',
		fieldName: 'date',
		label: 'date',
		name: 'date',
		options: [],
		repeatable: true,
		selector: '.date-picker',
		title: 'date',
		type: 'date',
		value: 'date',
	},
	{
		dataType: 'string',
		fieldName: 'text',
		label: 'text',
		name: 'text',
		options: [],
		repeatable: true,
		selector: 'input.ddm-field-text',
		title: 'text',
		type: 'text',
		value: 'text',
	},
	{
		dataType: 'string',
		fieldName: 'select',
		label: 'select',
		name: 'select',
		options: [],
		repeatable: false,
		selector: '.form-builder-select-field',
		title: 'select',
		type: 'select',
		value: 'select',
	},
	{
		dataType: 'string',
		fieldName: 'grid',
		label: 'grid',
		name: 'grid',
		options: [],
		repeatable: false,
		selector: 'table',
		title: 'grid',
		type: 'grid',
		value: 'grid',
	},
	{
		dataType: 'image',
		fieldName: 'image',
		label: 'image',
		name: 'image',
		options: [],
		repeatable: false,
		selector: '.input',
		title: 'image',
		type: 'image',
		value: 'image',
	},
	{
		dataType: 'string',
		fieldName: 'radio',
		label: 'radio',
		name: 'radio',
		options: [],
		repeatable: false,
		selector: '.form-builder-select-field',
		title: 'radio',
		type: 'radio',
		value: 'radio',
	},
	{
		dataType: 'string',
		fieldName: 'color',
		label: 'color',
		name: 'color',
		options: [],
		repeatable: false,
		selector: '.clay-color-picker',
		title: 'color',
		type: 'color',
		value: 'color',
	},
	{
		dataType: 'string',
		fieldName: 'rich_text',
		label: 'rich_text',
		name: 'rich_text',
		options: [],
		repeatable: false,
		selector: '#undefinedContainer',
		title: 'rich_text',
		type: 'rich_text',
		value: 'rich_text',
	},
	{
		dataType: 'string',
		fieldName: 'checkboxMultiple',
		label: 'checkboxMultiple',
		name: 'checkboxMultiple',
		options: [],
		repeatable: false,
		selector: '.form-builder-select-field',
		title: 'checkboxMultiple',
		type: 'checkbox_multiple',
		value: 'checkboxMultiple',
	},
	{
		dataType: 'integer',
		fieldName: 'integer',
		label: 'integer',
		name: 'integer',
		options: [],
		repeatable: false,
		selector: 'input.form-control',
		title: 'integer',
		type: 'numeric',
		value: 'integer',
	},
	{
		dataType: 'double',
		fieldName: 'double',
		label: 'double',
		name: 'double',
		options: [],
		repeatable: false,
		selector: 'input.form-control',
		title: 'double',
		type: 'numeric',
		value: 'double',
	},
	{
		dataType: 'document_library',
		fieldName: 'document_library',
		label: 'document_library',
		name: 'document_library',
		options: [],
		repeatable: false,
		selector: '.liferay-ddm-form-field-document-library',
		title: 'document_library',
		type: 'document_library',
		value: 'document_library',
	},
];

export const FIELDS_TYPES = [
	{
		javaScriptModule: 'checkbox_multiple',
		name: 'checkbox_multiple',
	},
	{
		javaScriptModule: 'color',
		name: 'color',
	},
	{
		javaScriptModule: 'date',
		name: 'date',
	},
	{
		javaScriptModule: 'document_library',
		name: 'document_library',
	},
	{
		javaScriptModule: 'grid',
		name: 'grid',
	},
	{
		javaScriptModule: 'image',
		name: 'image',
	},
	{
		javaScriptModule: 'numeric',
		name: 'numeric',
	},
	{
		javaScriptModule: 'select',
		name: 'select',
	},
	{
		javaScriptModule: 'text',
		name: 'text',
	},
	{
		javaScriptModule: 'rich_text',
		name: 'rich_text',
	},
];

export const OPERATORS_BY_TYPE = {
	number: [
		{
			label: 'Is greater than',
			name: 'greater-than',
			parameterClassNames: ['number', 'number'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is greater than or equal to',
			name: 'greater-than-equals',
			parameterClassNames: ['number', 'number'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is less than',
			name: 'less-than',
			parameterClassNames: ['number', 'number'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is less than or equal to',
			name: 'less-than-equals',
			parameterClassNames: ['number', 'number'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is equal to',
			name: 'equals-to',
			parameterClassNames: ['number', 'number'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is not equal to',
			name: 'not-equals-to',
			parameterClassNames: ['number', 'number'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is empty',
			name: 'is-empty',
			parameterClassNames: ['number'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is not empty',
			name: 'not-is-empty',
			parameterClassNames: ['number'],
			returnClassName: 'boolean',
		},
	],
	text: [
		{
			label: 'Is equal to',
			name: 'equals-to',
			parameterClassNames: ['text', 'text'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is not equal to',
			name: 'not-equals-to',
			parameterClassNames: ['text', 'text'],
			returnClassName: 'boolean',
		},
		{
			label: 'Contains',
			name: 'contains',
			parameterClassNames: ['text', 'text'],
			returnClassName: 'boolean',
		},
		{
			label: 'Does not contain',
			name: 'not-contains',
			parameterClassNames: ['text', 'text'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is empty',
			name: 'is-empty',
			parameterClassNames: ['text'],
			returnClassName: 'boolean',
		},
		{
			label: 'Is not empty',
			name: 'not-is-empty',
			parameterClassNames: ['text'],
			returnClassName: 'boolean',
		},
	],
	user: [
		{
			label: 'Belongs to',
			name: 'belongs-to',
			parameterClassNames: ['user', 'list'],
			returnClassName: 'boolean',
		},
	],
};
