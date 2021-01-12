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

export const STRING_DATATYPE_FIELDS = FIELDS.filter(
	(field) => field.dataType === 'string'
).map((field) => ({selector: field.selector, type: field.value}));
export const NUMBER_TYPE_FIELDS = FIELDS.filter(
	(field) => field.type === 'numeric'
).map((field) => ({selector: field.selector, type: field.dataType}));
export const IMAGE_TYPE_FIELD = FIELDS.filter(
	(field) => field.type === 'image'
).map((field) => ({selector: field.selector, type: field.dataType}));
export const UPLOAD_TYPE_FIELD = FIELDS.filter(
	(field) => field.type === 'document_library'
).map((field) => ({selector: field.selector, type: field.dataType}));

export const OPERATORS_BY_TYPE = {
	number: [
		{
			label: 'Is greater than',
			name: 'greater-than',
			parameterTypes: ['number', 'number'],
			returnType: 'boolean',
		},
		{
			label: 'Is greater than or equal to',
			name: 'greater-than-equals',
			parameterTypes: ['number', 'number'],
			returnType: 'boolean',
		},
		{
			label: 'Is less than',
			name: 'less-than',
			parameterTypes: ['number', 'number'],
			returnType: 'boolean',
		},
		{
			label: 'Is less than or equal to',
			name: 'less-than-equals',
			parameterTypes: ['number', 'number'],
			returnType: 'boolean',
		},
		{
			label: 'Is equal to',
			name: 'equals-to',
			parameterTypes: ['number', 'number'],
			returnType: 'boolean',
		},
		{
			label: 'Is not equal to',
			name: 'not-equals-to',
			parameterTypes: ['number', 'number'],
			returnType: 'boolean',
		},
		{
			label: 'Is empty',
			name: 'is-empty',
			parameterTypes: ['number'],
			returnType: 'boolean',
		},
		{
			label: 'Is not empty',
			name: 'not-is-empty',
			parameterTypes: ['number'],
			returnType: 'boolean',
		},
	],
	text: [
		{
			label: 'Is equal to',
			name: 'equals-to',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
		},
		{
			label: 'Is not equal to',
			name: 'not-equals-to',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
		},
		{
			label: 'Contains',
			name: 'contains',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
		},
		{
			label: 'Does not contain',
			name: 'not-contains',
			parameterTypes: ['text', 'text'],
			returnType: 'boolean',
		},
		{
			label: 'Is empty',
			name: 'is-empty',
			parameterTypes: ['text'],
			returnType: 'boolean',
		},
		{
			label: 'Is not empty',
			name: 'not-is-empty',
			parameterTypes: ['text'],
			returnType: 'boolean',
		},
	],
	user: [
		{
			label: 'Belongs to',
			name: 'belongs-to',
			parameterTypes: ['user', 'list'],
			returnType: 'boolean',
		},
	],
};

export const NUMBER_OPERATORS = OPERATORS_BY_TYPE.number.map(
	(operator) => operator.label
);
export const TEXT_OPERATORS = OPERATORS_BY_TYPE.text.map(
	(operator) => operator.label
);
export const USER_OPERATORS = OPERATORS_BY_TYPE.user.map(
	(operator) => operator.label
);

export const ROLES = [
	{
		id: '38817',
		label: 'Account Manager',
		name: 'Account Manager',
		value: 'Account Manager',
	},
	{
		id: '20106',
		label: 'Administrator',
		name: 'Administrator',
		value: 'Administrator',
	},
	{
		id: '20107',
		label: 'Analytics Administrator',
		name: 'Analytics Administrator',
		value: 'Analytics Administrator',
	},
	{
		id: '39244',
		label: 'Buyer',
		name: 'Buyer',
		value: 'Buyer',
	},
	{
		id: '20108',
		label: 'Guest',
		name: 'Guest',
		value: 'Guest',
	},
	{
		id: '39245',
		label: 'Order Manager',
		name: 'Order Manager',
		value: 'Order Manager',
	},
	{
		id: '20112',
		label: 'Organization Administrator',
		name: 'Organization Administrator',
		value: 'Organization Administrator',
	},
	{
		id: '39194',
		label: 'Organization Content Reviewer',
		name: 'Organization Content Reviewer',
		value: 'Organization Content Reviewer',
	},
	{
		id: '20113',
		label: 'Organization Owner',
		name: 'Organization Owner',
		value: 'Organization Owner',
	},
	{
		id: '20114',
		label: 'Organization User',
		name: 'Organization User',
		value: 'Organization User',
	},
	{
		id: '20109',
		label: 'Owner',
		name: 'Owner',
		value: 'Owner',
	},
	{
		id: '39190',
		label: 'Portal Content Reviewer',
		name: 'Portal Content Reviewer',
		value: 'Portal Content Reviewer',
	},
	{
		id: '20110',
		label: 'Power User',
		name: 'Power User',
		value: 'Power User',
	},
	{
		id: '20115',
		label: 'Site Administrator',
		name: 'Site Administrator',
		value: 'Site Administrator',
	},
	{
		id: '39197',
		label: 'Site Content Reviewer',
		name: 'Site Content Reviewer',
		value: 'Site Content Reviewer',
	},
	{
		id: '20116',
		label: 'Site Member',
		name: 'Site Member',
		value: 'Site Member',
	},
	{
		id: '20117',
		label: 'Site Owner',
		name: 'Site Owner',
		value: 'Site Owner',
	},
	{
		id: '20111',
		label: 'User',
		name: 'User',
		value: 'User',
	},
];
