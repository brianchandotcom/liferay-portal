/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import replaceTemplateVariable from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/sxp_element/replace_template_variable';

describe('replaceTemplateVariable', () => {
	it('gets configurationEntry of date', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							start_date: '${configuration.start_date}',
						},
						uiConfiguration: {
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
					},
				},
				uiConfigurationValues: {
					start_date: 1609488000,
				},
			})
		).toEqual({
			start_date: 20210101,
		});
	});

	it('gets configurationEntry of select', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							type: '${configuration.type}',
						},
						uiConfiguration: {
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
					},
				},
				uiConfigurationValues: {
					type: 'best_fields',
				},
			})
		).toEqual({
			type: 'best_fields',
		});
	});

	it('gets configurationEntry of itemSelector', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							role: '${configuration.role_id}',
						},
						uiConfiguration: {
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
					},
				},
				uiConfigurationValues: {
					role_id: [{label: 'Administrator', value: '20107'}],
				},
			})
		).toEqual({
			role: ['20107'],
		});
	});

	it('gets configurationEntry of multiselect', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							keywords: '${configuration.keywords}',
						},
						uiConfiguration: {
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
					},
				},
				uiConfigurationValues: {
					keywords: [{label: 'test', value: 'test'}],
				},
			})
		).toEqual({
			keywords: ['test'],
		});
	});

	it('gets configurationEntry of number', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							asset_category_id:
								'${configuration.asset_category_id}',
						},
						uiConfiguration: {
							fieldSets: [
								{
									fields: [
										{
											label: 'Asset Category ID',
											name: 'asset_category_id',
											type: 'number',
										},
									],
								},
							],
						},
					},
				},
				uiConfigurationValues: {
					asset_category_id: 1032490,
				},
			})
		).toEqual({
			asset_category_id: 1032490,
		});
	});

	it('gets configurationEntry of number with suffix', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							time_range: '${configuration.time_range}',
						},
						uiConfiguration: {
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
					},
				},
				uiConfigurationValues: {
					time_range: 30,
				},
			})
		).toEqual({
			time_range: '30d',
		});
	});

	it('gets configurationEntry of slider', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							boost: '${configuration.boost}',
						},
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
									],
								},
							],
						},
					},
				},
				uiConfigurationValues: {
					boost: 20,
				},
			})
		).toEqual({
			boost: 20,
		});
	});

	it('gets configurationEntry of field mapping', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							field: '${configuration.field}',
						},
						uiConfiguration: {
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
					},
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
			field: 'localized_title_${context.language_id}^1',
		});
	});

	it('gets configurationEntry of field mapping list', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							fields: '${configuration.fields}',
						},
						uiConfiguration: {
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
					},
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
				'localized_title_${context.language_id}^2',
				'content_${context.language_id}^1',
			],
		});
	});

	it('gets configurationEntry of field mapping list with undefined or blank locale', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							fields: '${configuration.fields}',
						},
						uiConfiguration: {
							fieldSets: [
								{
									fields: [
										{
											defaultValue: [
												{
													boost: 2,
													field: 'localized_title',
												},
												{
													boost: 1,
													field: 'content',
													locale: '',
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
					},
				},
				uiConfigurationValues: {
					fields: [
						{
							boost: 2,
							field: 'localized_title',
						},
						{
							boost: 1,
							field: 'content',
							locale: '',
						},
					],
				},
			})
		).toEqual({
			fields: ['localized_title^2', 'content^1'],
		});
	});

	it('gets configurationEntry of json', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							json: '${configuration.json}',
						},
						uiConfiguration: {
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
					},
				},
				uiConfigurationValues: {
					json: '{"category": "custom"}',
				},
			})
		).toEqual({
			json: {category: 'custom'},
		});
	});

	it('gets configurationEntry of text', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							geopoint: '${configuration.geopoint}',
						},
						uiConfiguration: {
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
					},
				},
				uiConfigurationValues: {
					geopoint:
						'expando__keyword__custom_fields__location_geolocation',
				},
			})
		).toEqual({
			geopoint: 'expando__keyword__custom_fields__location_geolocation',
		});
	});

	it('gets configurationEntry of configuration with multiple fields', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					elementDefinition: {
						configuration: {
							boost: '${configuration.boost}',
							field: '${configuration.field}',
							json: '${configuration.json}',
						},
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
					},
				},
				uiConfigurationValues: {
					boost: 20,
					field: {
						boost: 1,
						field: 'localized_title',
						locale: '${context.language_id}',
					},
					json: '{"category": "custom"}',
				},
			})
		).toEqual({
			boost: 20,
			field: 'localized_title_${context.language_id}^1',
			json: {category: 'custom'},
		});
	});

	it('gets configurationEntry of custom json with no configuration', () => {
		expect(
			replaceTemplateVariable({
				sxpElement: {
					description_i18n: {en_US: 'Editable JSON text area'},
					elementDefinition: {
						category: 'custom',
						configuration: {
							clauses: [],
							conditions: {},
						},
						enabled: true,
						icon: 'custom-field',
					},
					title_i18n: {en_US: 'Custom JSON Element'},
				},
			})
		).toEqual({
			clauses: [],
			conditions: {},
		});
	});
});
