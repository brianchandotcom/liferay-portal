/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {
	getDefaultValue,
	getElementOutput,
	getUIConfigurationValues,
	isDefined,
	renameKeys,
	replaceStr,
} from '../../../src/main/resources/META-INF/resources/js/utils/utils';

describe('utils', () => {
	describe('isDefined', () => {
		it('returns false for undefined', () => {
			expect(isDefined(undefined)).toEqual(false);
		});

		it('returns false for null', () => {
			expect(isDefined(null)).toEqual(false);
		});

		it('returns true for empty string', () => {
			expect(isDefined('')).toEqual(true);
		});

		it('returns true for 0', () => {
			expect(isDefined(0)).toEqual(true);
		});

		it('returns true for []', () => {
			expect(isDefined([])).toEqual(true);
		});

		it('returns true for empty object', () => {
			expect(isDefined({})).toEqual(true);
		});

		it('returns true for object', () => {
			expect(isDefined({test: [1, 2, 3]})).toEqual(true);
		});
	});

	describe('replaceStr', () => {
		it('replaces the string for locale', () => {
			expect(
				replaceStr(
					'title_${configuration.language}',
					'${configuration.language}',
					'en_US'
				)
			).toEqual('title_en_US');
		});
	});

	describe('renameKeys', () => {
		it('replaces the string for locale', () => {
			expect(
				renameKeys({'en-US': 'Hello', 'zh-CN': 'Ni Hao'}, (str) =>
					str.replace('-', '_')
				)
			).toEqual({en_US: 'Hello', zh_CN: 'Ni Hao'});
		});
	});

	describe('getDefaultValue', () => {
		it('gets default value for MM-DD-YYYY dates', () => {
			expect(
				getDefaultValue({
					defaultValue: '01-01-2021',
					label: 'Create Date: From',
					name: 'start_date',
					type: 'date',
				})
			).toEqual(1609488000); // Unix time
		});

		it('gets default value for unix dates', () => {
			expect(
				getDefaultValue({
					defaultValue: 1615509523,
					label: 'Create Date: From',
					name: 'start_date',
					type: 'date',
				})
			).toEqual(1615509523); // Same number
		});

		it('gets default value for empty dates', () => {
			expect(
				getDefaultValue({
					label: 'Create Date: From',
					name: 'start_date',
					type: 'date',
				})
			).toEqual('');
		});

		it('gets default value for select', () => {
			expect(
				getDefaultValue({
					defaultValue: false,
					label: 'Enabled',
					name: 'enabled',
					type: 'select',
					typeOptions: {
						options: [
							{
								label: 'True',
								value: true,
							},
							{
								label: 'False',
								value: false,
							},
						],
					},
				})
			).toEqual(false);
		});

		it('gets default value for empty select with boolean', () => {
			expect(
				getDefaultValue({
					label: 'Enabled',
					name: 'enabled',
					type: 'select',
					typeOptions: {
						options: [
							{
								label: 'True',
								value: true,
							},
							{
								label: 'False',
								value: false,
							},
						],
					},
				})
			).toEqual(true); // Gets first value in options
		});

		it('gets default value for empty select with string', () => {
			expect(
				getDefaultValue({
					label: 'Value',
					name: 'value',
					type: 'select',
					typeOptions: {
						options: [
							{
								label: 'Best Value',
								value: 'best_value',
							},
							{
								label: 'Fuzzy Value',
								value: 'fuzzy_value',
							},
						],
					},
				})
			).toEqual('best_value'); //gets first value in options
		});

		it('gets default value for itemSelector', () => {
			expect(
				getDefaultValue({
					defaultValue: [{label: 'correct', value: 'correct'}],
					helpText: 'Select role',
					label: 'Role',
					name: 'role_id',
					type: 'itemSelector',
					typeOptions: {
						itemType: 'com.liferay.portal.kernel.model.Role',
					},
				})
			).toEqual([{label: 'correct', value: 'correct'}]);
		});

		it('gets default value for incorrect itemSelector', () => {
			expect(
				getDefaultValue({
					defaultValue: [{id: 'incorrect', value: 'incorrect'}],
					helpText: 'Select role',
					label: 'Role',
					name: 'role_id',
					type: 'itemSelector',
					typeOptions: {
						itemType: 'com.liferay.portal.kernel.model.Role',
					},
				})
			).toEqual([]);
		});

		it('gets default value for empty itemSelector', () => {
			expect(
				getDefaultValue({
					helpText: 'Select role',
					label: 'Role',
					name: 'role_id',
					type: 'itemSelector',
					typeOptions: {
						itemType: 'com.liferay.portal.kernel.model.Role',
					},
				})
			).toEqual([]);
		});

		it('gets default value for multiselect', () => {
			expect(
				getDefaultValue({
					defaultValue: [{label: 'one', value: 'one'}],
					label: 'Values',
					name: 'values',
					type: 'multiselect',
				})
			).toEqual([{label: 'one', value: 'one'}]);
		});

		it('gets default value for incorrect multiselect', () => {
			expect(
				getDefaultValue({
					defaultValue: [{field: 'one', label: 'one'}],
					label: 'Values',
					name: 'values',
					type: 'multiselect',
				})
			).toEqual([]);
		}); //multiselect requires label and value

		it('gets default value for empty multiselect', () => {
			expect(
				getDefaultValue({
					label: 'Values',
					name: 'values',
					type: 'multiselect',
				})
			).toEqual([]);
		});

		it('gets default value for number', () => {
			expect(
				getDefaultValue({
					defaultValue: 30,
					label: 'Time range',
					name: 'time_range',
					type: 'number',
					typeOptions: {
						unit: 'days',
						unitSuffix: 'd',
					},
				})
			).toEqual(30);
		});

		it('gets default value for incorrect number', () => {
			expect(
				getDefaultValue({
					defaultValue: 'thirty',
					label: 'Time range',
					name: 'time_range',
					type: 'number',
					typeOptions: {
						unit: 'days',
						unitSuffix: 'd',
					},
				})
			).toEqual('');
		});

		it('gets default value for empty number', () => {
			expect(
				getDefaultValue({
					label: 'Time range',
					name: 'time_range',
					type: 'number',
					typeOptions: {
						unit: 'days',
						unitSuffix: 'd',
					},
				})
			).toEqual('');
		});

		it('gets default value for slider', () => {
			expect(
				getDefaultValue({
					defaultValue: 10,
					label: 'Boost',
					name: 'boost',
					type: 'slider',
				})
			).toEqual(10);
		});

		it('gets default value for incorrect slider', () => {
			expect(
				getDefaultValue({
					defaultValue: 'ten',
					label: 'Boost',
					name: 'boost',
					type: 'slider',
				})
			).toEqual('');
		});

		it('gets default value for empty slider', () => {
			expect(
				getDefaultValue({
					label: 'Boost',
					name: 'Boost',
					type: 'slider',
				})
			).toEqual('');
		});

		it('gets default value for field mapping list', () => {
			expect(
				getDefaultValue({
					defaultValue: [
						{
							boost: 2,
							field: 'localized_title',
							locale: '${context.language_id}',
						},
					],
					label: 'Field',
					name: 'fields',
					type: 'fieldMappingList',
					typeOptions: {
						boost: true,
					},
				})
			).toEqual([
				{
					boost: 2,
					field: 'localized_title',
					locale: '${context.language_id}',
				},
			]);
		});

		it('returns an empty array if the field mapping structure is invalid', () => {
			expect(
				getDefaultValue({
					defaultValue: [
						{
							boost: 2,
							locale: '${context.language_id}',
							value: 'localized_title',
						},
					],
					label: 'Field',
					name: 'fields',
					type: 'fieldMappingList',
					typeOptions: {
						boost: true,
					},
				})
			).toEqual([]);
		});

		it('gets default value for empty field mapping list', () => {
			expect(
				getDefaultValue({
					label: 'Field',
					name: 'fields',
					type: 'fieldMappingList',
					typeOptions: {
						boost: true,
					},
				})
			).toEqual([]);
		});

		it('gets default value for field mapping', () => {
			expect(
				getDefaultValue({
					defaultValue: {
						boost: 2,
						field: 'localized_title',
						locale: '',
					},
					label: 'Field',
					name: 'field',
					type: 'fieldMapping',
				})
			).toEqual({
				boost: 2,
				field: 'localized_title',
				locale: '',
			});
		});

		it('returns an empty field mapping object if the field mapping structure is invalid', () => {
			expect(
				getDefaultValue({
					defaultValue: {
						boost: 2,
						locale: '${context.language_id}',
						value: 'localized_title',
					},
					label: 'Field',
					name: 'field',
					type: 'fieldMapping',
				})
			).toEqual({
				field: '',
				locale: '',
			});
		});

		it('gets default value for empty field mapping', () => {
			expect(
				getDefaultValue({
					label: 'Field',
					name: 'field',
					type: 'fieldMapping',
				})
			).toEqual({
				field: '',
				locale: '',
			});
		});

		it('gets default value for json', () => {
			expect(
				getDefaultValue({
					defaultValue: {test: 'abc'},
					name: 'query',
					type: 'json',
				})
			).toEqual({test: 'abc'});
		});

		it('gets default value for incorrect json', () => {
			expect(
				getDefaultValue({
					defaultValue: "{test: 'abc'}",
					name: 'query',
					type: 'json',
				})
			).toEqual({});
		});

		it('gets default value for empty json', () => {
			expect(
				getDefaultValue({
					name: 'query',
					type: 'json',
				})
			).toEqual({});
		});

		it('gets default value for text', () => {
			expect(
				getDefaultValue({
					defaultValue: 'simple text value',
					helpText: 'Add asset tag value',
					label: 'Asset Tag',
					name: 'asset_tag',
					type: 'text',
				})
			).toEqual('simple text value');
		});

		it('gets default value for incorrect text', () => {
			expect(
				getDefaultValue({
					defaultValue: 0,
					label: 'Asset Tag',
					name: 'asset_tag',
					type: 'text',
				})
			).toEqual('');
		});

		it('gets default value for empty text', () => {
			expect(
				getDefaultValue({
					label: 'Asset Tag',
					name: 'asset_tag',
					type: 'text',
				})
			).toEqual('');
		});

		it('gets default value for empty type and incorrect value', () => {
			expect(
				getDefaultValue({
					defaultValue: {test: 'abc'},
					label: 'Json',
					name: 'json',
				})
			).toEqual('');
		});

		it('gets default value for empty type and value', () => {
			expect(
				getDefaultValue({
					label: 'Tag',
					name: 'tag',
				})
			).toEqual('');
		});
	});

	describe('getUIConfigurationValues', () => {
		it('extracts the values within list of fieldsets', () => {
			expect(
				getUIConfigurationValues({
					fieldSets: [
						{
							fields: [
								{
									defaultValue: 10,
									label: 'Boost',
									name: 'boost',
									type: 'slider',
								},
							],
						},
						{
							fields: [
								{
									defaultValue: 'en_US',
									label: 'Language',
									name: 'language',
									type: 'text',
								},
							],
						},
					],
				})
			).toEqual({boost: 10, language: 'en_US'});
		});
	});

	describe('getElementOutput', () => {
		it('gets elementOutput of date', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						start_date: '${configuration.start_date}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										label: 'Create Date: From',
										name: 'start_date',
										type: 'date',
										typeOptions: {
											format: 'YYYYMMDD',
										},
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						start_date: 1609488000,
					},
				})
			).toEqual({
				start_date: 20210101,
			});
		});

		it('gets elementOutput of select', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						type: '${configuration.type}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue: 'best_fields',
										label: 'Match Type',
										name: 'type',
										type: 'select',
										typeOptions: {
											options: [
												{
													label: 'Best Fields',
													value: 'best_fields',
												},
												{
													label: 'Most Fields',
													value: 'most_fields',
												},
												{
													label: 'Cross Fields',
													value: 'cross_fields',
												},
											],
										},
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						type: 'best_fields',
					},
				})
			).toEqual({
				type: 'best_fields',
			});
		});

		it('gets elementOutput of itemSelector', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						role: '${configuration.role_id}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										label: 'Role',
										name: 'role_id',
										type: 'itemSelector',
										typeOptions: {
											itemType:
												'com.liferay.portal.kernel.model.Role',
										},
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						role_id: [{label: 'Administrator', value: '20107'}],
					},
				})
			).toEqual({
				role: ['20107'],
			});
		});

		it('gets elementOutput of multiselect', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						keywords: '${configuration.keywords}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue: [],
										label: 'Keywords',
										name: 'keywords',
										type: 'multiselect',
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						keywords: [{label: 'test', value: 'test'}],
					},
				})
			).toEqual({
				keywords: ['test'],
			});
		});

		it('gets elementOutput of number', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						asset_category_id: '${configuration.asset_category_id}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										helpText: 'Add asset category ID',
										label: 'Asset Category',
										name: 'asset_category_id',
										type: 'number',
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						asset_category_id: 1032490,
					},
				})
			).toEqual({
				asset_category_id: 1032490,
			});
		});

		it('gets elementOutput of number with suffix', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						time_range: '${configuration.time_range}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue: 30,
										label: 'Time range',
										name: 'time_range',
										type: 'number',
										typeOptions: {
											unit: 'days',
											unitSuffix: 'd',
										},
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						time_range: 30,
					},
				})
			).toEqual({
				time_range: '30d',
			});
		});

		it('gets elementOutput of slider', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						boost: '${configuration.boost}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue: 10,
										label: 'Boost',
										name: 'boost',
										type: 'slider',
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						boost: 20,
					},
				})
			).toEqual({
				boost: 20,
			});
		});

		it('gets elementOutput of field mapping', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						field: '${configuration.field}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue: {
											field: '',
											locale: '',
										},
										label: 'Field',
										name: 'field',
										type: 'fieldMapping',
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						field: {
							boost: 1,
							field: 'localized_title',
							locale: '${context.language_id}',
						},
					},
				})
			).toEqual({
				field: 'localized_title${context.language_id}^1',
			});
		});

		it('gets elementOutput of field mapping list', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						fields: '${configuration.fields}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue: [
											{
												boost: 2,
												field: 'localized_title',
												locale:
													'${context.language_id}',
											},
											{
												boost: 1,
												field: 'content',
												locale:
													'${context.language_id}',
											},
										],
										label: 'Field',
										name: 'fields',
										type: 'fieldMappingList',
										typeOptions: {
											boost: true,
										},
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						fields: [
							{
								boost: 2,
								field: 'localized_title',
								locale: '${context.language_id}',
							},
							{
								boost: 1,
								field: 'content',
								locale: '${context.language_id}',
							},
						],
					},
				})
			).toEqual({
				fields: [
					'localized_title${context.language_id}^2',
					'content${context.language_id}^1',
				],
			});
		});

		it('gets elementOutput of json', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						json: '${configuration.json}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue: {},
										name: 'json',
										type: 'json',
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						json: {
							category: 'custom',
						},
					},
				})
			).toEqual({
				json: {category: 'custom'},
			});
		});

		it('gets elementOutput of text', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						geopoint: '${configuration.geopoint}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue:
											'expando__keyword__custom_fields__location_geolocation',
										helpText: 'A geopoint field',
										label: 'Geopoint',
										name: 'geopoint',
										type: 'text',
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						geopoint:
							'expando__keyword__custom_fields__location_geolocation',
					},
				})
			).toEqual({
				geopoint:
					'expando__keyword__custom_fields__location_geolocation',
			});
		});

		it('gets elementOutput of configuration with multiple fields', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						boost: '${configuration.boost}',
						field: '${configuration.field}',
						json: '${configuration.json}',
					},
					uiConfigurationJSON: {
						fieldSets: [
							{
								fields: [
									{
										defaultValue: 10,
										label: 'Boost',
										name: 'boost',
										type: 'slider',
									},
									{
										defaultValue: {},
										name: 'json',
										type: 'json',
									},
								],
							},
							{
								fields: [
									{
										defaultValue: {
											field: '',
											locale: '',
										},
										label: 'Field',
										name: 'field',
										type: 'fieldMapping',
									},
								],
							},
						],
					},
					uiConfigurationValues: {
						boost: 20,
						field: {
							boost: 1,
							field: 'localized_title',
							locale: '${context.language_id}',
						},
						json: {
							category: 'custom',
						},
					},
				})
			).toEqual({
				boost: 20,
				field: 'localized_title${context.language_id}^1',
				json: {category: 'custom'},
			});
		});

		it('gets elementOutput of custom json with no configuration', () => {
			expect(
				getElementOutput({
					elementTemplateJSON: {
						category: 'custom',
						clauses: [],
						conditions: [],
						description: 'Editable JSON text area',
						enabled: true,
						icon: 'custom-field',
						title: 'Custom JSON Element',
					},
					uiConfigurationValues: {},
				})
			).toEqual({
				category: 'custom',
				clauses: [],
				conditions: [],
				description: 'Editable JSON text area',
				enabled: true,
				icon: 'custom-field',
				title: 'Custom JSON Element',
			});
		});
	});
});
