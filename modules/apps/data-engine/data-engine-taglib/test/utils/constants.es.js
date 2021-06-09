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

const createItems = (size) => {
	const items = [];

	for (let i = 0; i < size; i++) {
		items.push({
			active: true,
			appDeployments: [
				{
					settings: {},
					type: 'standalone',
				},
			],
			dataDefinitionId: '123',
			dataDefinitionName: 'Object',
			dateCreated: '2020-03-26T11:26:54.262Z',
			dateModified: '2020-03-26T11:26:54.262Z',
			id: i + 1,
			name: {
				en_US: `Item ${i + 1}`,
			},
		});
	}

	return items;
};

export const ACTIONS = [
	{
		action: () => {},
		name: 'Delete',
	},
];

export const COLUMNS = [
	{
		key: 'name',
		value: 'Name',
	},
	{
		key: 'dateCreated',
		value: 'Created Date',
	},
	{
		key: 'dateModified',
		value: 'Modified Date',
	},
];

export const EMPTY_STATE = {
	description: 'description',
	title: 'title',
};

export const ENDPOINT = '/endpoint';

export const FILTERS = [
	{
		items: [
			{label: 'Product Menu', value: 'productMenu'},
			{label: 'Standalone', value: 'standalone'},
			{label: 'Widget', value: 'widget'},
		],
		key: 'deploymentTypes',
		multiple: true,
		name: 'deployment-type',
	},
	{
		items: [
			{label: 'Deployed', value: 'true'},
			{label: 'Undeployed', value: 'false'},
		],
		key: 'active',
		name: 'status',
	},
];

export const ITEMS = {
	MANY: (size) => createItems(size),
	ONE: createItems(1),
	TWENTY: createItems(20),
};

export const RESPONSES = {
	MANY_ITEMS: (size) => {
		const items = ITEMS.MANY(size);

		return {
			items,
			lastPage: 1,
			page: 1,
			pageSize: 20,
			totalCount: items.length,
		};
	},
	NO_ITEMS: {
		lastPage: 1,
		page: 1,
		pageSize: 20,
		totalCount: 0,
	},
	ONE_ITEM: {
		items: ITEMS.ONE,
		lastPage: 1,
		page: 1,
		pageSize: 20,
		totalCount: ITEMS.ONE.length,
	},
	TWENTY_ONE_ITEMS: {
		items: ITEMS.TWENTY,
		lastPage: 2,
		page: 1,
		pageSize: 20,
		totalCount: ITEMS.TWENTY.length + 1,
	},
};

const dataDefinitionField = {
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
		rows: '[]',
		tooltip: {
			en_US: '',
		},
		visibilityExpression: '',
	},
	defaultValue: {
		en_US: '',
	},
	description: {
		en_US: 'Enter your name',
	},
	fieldType: 'text',
	indexType: 'keyword',
	indexable: true,
	label: {
		en_US: 'Name',
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
};

const dataDefinition = {
	availableLanguageIds: ['en_US'],
	dataDefinitionFields: [dataDefinitionField],
	dataDefinitionKey: '36601',
	dateCreated: '2020-04-24T13:50:04Z',
	dateModified: '2020-04-24T13:50:13Z',
	defaultLanguageId: 'en_US',
	description: {},
	id: 36602,
	name: {
		en_US: 'My Custom Object',
	},
	siteId: 20125,
	storageType: 'json',
	userId: 20127,
};

export const DATA_DEFINITION_FIELDSET = {
	customProperties: {
		collapsible: false,
		ddmStructureId: 41628,
		ddmStructureLayoutId: '',
		rows: '[{"columns":[{"fields":["Campo95700329"],"size":12}]}]',
		upgradedStructure: false,
	},
	dataDefinitionKey: '11110',
	defaultLanguageId: 'en_US',
	fieldType: 'fieldset',
	label: {
		en_US: 'Address',
		pt_BR: 'Address',
	},
	name: 'Field53354166',
	nestedDataDefinitionFields: [
		{
			customProperties: {
				autocomplete: false,
				dataSourceType: 'manual',
				dataType: 'string',
				ddmDataProviderInstanceId: '[]',
				ddmDataProviderInstanceOutput: '[]',
				displayStyle: 'singleline',
				fieldNamespace: '',
				nativeField: false,
				options: {
					en_US: [
						{
							label: 'Option',
							value: '',
						},
					],
				},
				placeholder: {
					en_US: '',
					pt_BR: '',
				},
				tooltip: {
					en_US: '',
					pt_BR: '',
				},
				validation: {
					errorMessage: {},
					expression: {},
					parameter: {},
				},
				visibilityExpression: '',
			},
			defaultValue: {
				en_US: '',
				pt_BR: '',
			},
			fieldType: 'text',
			indexType: 'keyword',
			label: {
				en_US: 'Text Field',
				pt_BR: 'Text Field',
			},
			localizable: true,
			name: 'Field95700329',
			nestedDataDefinitionFields: [],
			readOnly: false,
			repeatable: false,
			required: false,
			showLabel: true,
			tip: {
				en_US: '',
				pt_BR: '',
			},
		},
	],
	repeatable: false,
	showLabel: true,
};

const dataDefinitionSelectField = {
	...dataDefinitionField,
	fieldType: 'select',
	label: {
		en_US: 'Options',
	},
	name: 'SelectFromList',
};

export const DATA_DEFINITION_RESPONSES = {
	ONE_ITEM: dataDefinition,
	THREE_ITEMS: {
		...dataDefinition,
		dataDefinitionFields: [
			dataDefinitionField,
			dataDefinitionSelectField,
			DATA_DEFINITION_FIELDSET,
		],
	},
	TWO_ITEMS: {
		...dataDefinition,
		dataDefinitionFields: [dataDefinitionField, dataDefinitionSelectField],
	},
};

export const ENTRY = {
	DATA_DEFINITION: {
		availableLanguageIds: ['en_US'],
		contentType: 'app-builder',
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
					en_US: 'Name',
				},
				localizable: true,
				name: 'Text',
				nestedDataDefinitionFields: [],
				readOnly: false,
				repeatable: false,
				required: true,
				showLabel: true,
				tip: {
					en_US: '',
				},
			},
		],
		dataDefinitionKey: '38301',
		dateCreated: '2020-06-22T21:14:23Z',
		dateModified: '2020-06-22T21:14:44Z',
		defaultLanguageId: 'en_US',
		description: {},
		id: 1,
		name: {
			en_US: 'Request',
		},
		siteId: 1,
		storageType: 'json',
		userId: 1,
	},
	DATA_LAYOUT: {
		dataDefinitionId: 1,
		dataLayoutKey: '38309',
		dataLayoutPages: [
			{
				dataLayoutRows: [
					{
						dataLayoutColumns: [
							{
								columnSize: 12,
								fieldNames: ['Text'],
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
		dateCreated: '2020-06-22T21:14:44Z',
		dateModified: '2020-06-22T21:14:44Z',
		description: {},
		id: 1,
		name: {
			en_US: 'Request Form',
		},
		paginationMode: 'wizard',
	},
	DATA_LIST_VIEW: {
		appliedFilters: {},
		dataDefinitionId: 1,
		dateCreated: '2020-06-22T21:14:53Z',
		dateModified: '2020-06-22T21:14:53Z',
		fieldNames: ['Text'],
		id: 1,
		name: {
			en_US: 'Table',
		},
		sortField: '',
	},
	DATA_RECORDS: (size = 1) => ({
		items: Array.apply(null, Array(size)).map((_, id) => ({
			dataRecordCollectionId: id,
			dataRecordValues: {
				Text: {
					en_US: `Name Test ${id}`,
				},
			},
			id,
			status: 0,
		})),
		lastPage: 1,
		page: 1,
		pageSize: size,
		totalCount: size,
	}),
};

const fieldTypes = [
	{
		label: 'Date',
		name: 'date',
		scope: 'app-builder,forms',
	},
	{
		label: 'Select from List',
		name: 'select',
		scope: 'app-builder,forms',
	},
	{
		label: 'Fields Group',
		name: 'fieldset',
		scope: 'app-builder,forms',
	},
	{
		label: 'Numeric',
		name: 'numeric',
		scope: 'app-builder,forms',
	},
	{
		label: 'Multiple Selection',
		name: 'checkbox_multiple',
		scope: 'app-builder,forms',
	},
	{
		label: 'Single Selection',
		name: 'radio',
		scope: 'app-builder,forms',
	},
	{
		label: 'Text',
		name: 'text',
		scope: 'app-builder,forms',
	},
	{
		label: 'Upload',
		name: 'document_library',
		scope: 'app-builder,forms',
	},
];

const FORM_VIEW_CONTEXT = {
	appProps: {},
	config: {
		allowFieldSets: false,
		allowNestedFields: true,
		allowRules: false,
		disabledProperties: [],
		disabledTabs: [],
		multiPage: true,
		ruleSettings: {},
		unimplementedProperties: [],
	},
	dataDefinition: DATA_DEFINITION_RESPONSES.THREE_ITEMS,
	dataDefinitionId: 0,
	dataLayout: {
		dataLayoutPages: [],
		dataRules: [],
		name: {
			en_US: 'FormView',
		},
		paginationMode: 'wizard',
	},
	dataLayoutId: 0,
	editingDataDefinitionId: 0,
	editingLanguageId: themeDisplay.getLanguageId(),
	fieldSets: [],
	fieldTypes,
	focusedCustomObjectField: {},
	focusedField: {},
	hoveredField: {},
	sidebarOpen: true,
	sidebarPanelId: 'fields',
	spritemap: 'icons.svg',
};

export const dataLayoutBuilderConfig = {
	appContext: [FORM_VIEW_CONTEXT],
	config: {
		allowFieldSets: true,
		allowMultiplePages: false,
		allowRules: false,
		allowSuccessPage: false,
		disabledProperties: ['predefinedValue'],
		disabledTabs: ['Autocomplete'],
		unimplementedProperties: [
			'fieldNamespace',
			'indexType',
			'readOnly',
			'validation',
			'visibilityExpression',
		],
	},
	contentTypeConfig: {
		allowInvalidAvailableLocalesForProperty: false,
		allowReferencedDataDefinitionDeletion: true,
	},
	context: {},
	dataLayoutBuilderId:
		'_com_liferay_journal_web_portlet_JournalPortlet_dataLayoutBuilder',
	fieldTypes: [
		{
			name: 'Text',
		},
	],
	localizable: true,
	portletNamespace: 'com_liferay_journal_web_portlet_JournalPortlet',
};

const pages = [
	{
		rows: [
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'Text',
							},
						],
						size: 12,
					},
				],
			},
		],
	},
];

export const FORM_VIEW = {
	EDIT_FORM_VIEW_PROPS: {
		basePortletURL: 'localhost',
		customObjectSidebarElementId: 'customObject',
		dataDefinitionId: 1,
		dataLayoutBuilderElementId: '',
		dataLayoutBuilderId: 1,
		dataLayoutId: 1,
		newCustomObject: true,
	},
	FORM_VIEW_CONTEXT,
	getDataLayoutBuilderProps() {
		return {
			formBuilderWithLayoutProvider: {
				refs: {
					layoutProvider: {
						dispatch: jest.fn(),
						getEvents: () => ({
							fieldHovered: jest.fn(),
						}),
						on: () => ({
							removeListener: jest.fn(),
						}),
						state: {
							activePage: 0,
							pages,
						},
					},
				},
			},
			getDDMForm: () => ({pages}),
			getDefaultDataLayout: () => ({dataLayoutPages: pages}),
			on: jest.fn(),
			onEditingLanguageIdChange: jest.fn(),
			removeEventListener: jest.fn(),
		};
	},
	pages,
};
