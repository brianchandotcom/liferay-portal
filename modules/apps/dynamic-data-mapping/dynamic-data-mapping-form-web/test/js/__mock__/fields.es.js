/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
		settingsContext: {
			pages: [],
		},
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
