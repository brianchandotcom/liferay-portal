/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getUIConfigurationValues, {
	getDefaultValue,
} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/sxp_element/get_ui_configuration_values';

describe('getDefaultValue', () => {
	xit('gets default value for MM-DD-YYYY dates', () => {
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
				defaultValue: 'fuzzy_value',
				label: 'Enabled',
				name: 'enabled',
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
		).toEqual('fuzzy_value');
	});

	it('gets default value for empty select', () => {
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
		).toEqual('best_value'); // gets first value in options
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
	}); // multiselect requires label and value

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
			}).replace(/\s/g, '')
		).toEqual(`{"test":"abc"}`);
	});

	it('gets default value for incorrect json', () => {
		expect(
			getDefaultValue({
				defaultValue: "{test: 'abc'}",
				name: 'query',
				type: 'json',
			})
		).toEqual('{}');
	});

	it('gets default value for empty json', () => {
		expect(
			getDefaultValue({
				name: 'query',
				type: 'json',
			})
		).toEqual('{}');
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
				description_i18n: {
					en_US: '',
				},
				elementDefinition: {
					category: 'match',
					configuration: {
						queryConfiguration: {
							queryEntries: [
								{
									clauses: [
										{
											context: 'query',
											occur: 'must',
											query: {
												multi_match: {
													boost:
														'${configuration.boost}',
													language:
														'${configuration.language}',
												},
											},
										},
									],
								},
							],
						},
					},
					icon: 'picture',
					uiConfiguration: {
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
										defaultValue: 'en_US',
										label: 'Language',
										name: 'language',
										type: 'text',
									},
								],
							},
						],
					},
				},
				title_i18n: {
					en_US: 'Text Match',
				},
			})
		).toEqual({boost: 10, language: 'en_US'});
	});
});
