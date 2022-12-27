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

import cleanUIConfiguration from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/sxp_element/clean_ui_configuration';

describe('cleanUIConfiguration', () => {
	it('returns a valid UIConfigurationJSON', () => {
		expect(
			cleanUIConfiguration({
				fieldSets: [
					{
						fields: [
							{
								defaultValue: 1,
								label: 'Boost',
								name: 'boost',
								type: 'number',
							},
						],
					},
					{
						fields: [
							{
								label: 'Text',
								name: 'text',
								type: 'text',
							},
						],
					},
				],
			})
		).toEqual({
			fieldSets: [
				{
					fields: [
						{
							defaultValue: 1,
							label: 'Boost',
							name: 'boost',
							type: 'number',
						},
					],
				},
				{fields: [{label: 'Text', name: 'text', type: 'text'}]},
			],
		});
	});

	it('cleans up UIConfigurationJSON when "fields" is an empty array', () => {
		expect(
			cleanUIConfiguration({
				fieldSets: [
					{
						fields: [],
					},
				],
			})
		).toEqual({fieldSets: []});
	});

	it('returns a valid UIConfigurationJSON when "fieldSets" is an invalid type', () => {
		expect(
			cleanUIConfiguration({
				fieldSets: '',
			})
		).toEqual({fieldSets: []});
	});

	it('returns a valid UIConfigurationJSON when "fields" is an invalid type', () => {
		expect(
			cleanUIConfiguration({
				fieldSets: [
					{
						fields: '',
					},
				],
			})
		).toEqual({fieldSets: []});
	});

	it('removes field with missing "name" property from UIConfigurationJSON', () => {
		expect(
			cleanUIConfiguration({
				fieldSets: [
					{
						fields: [
							{
								defaultValue: 1,
								label: 'Boost',
								name: 'boost',
								type: 'number',
							},
							{
								label: 'Text',
								type: 'text',
							},
						],
					},
				],
			})
		).toEqual({
			fieldSets: [
				{
					fields: [
						{
							defaultValue: 1,
							label: 'Boost',
							name: 'boost',
							type: 'number',
						},
					],
				},
			],
		});
	});

	it('removes field with non-unique "name" property from UIConfigurationJSON', () => {
		expect(
			cleanUIConfiguration({
				fieldSets: [
					{
						fields: [
							{
								defaultValue: 1,
								label: 'Boost',
								name: 'boost',
								type: 'number',
							},
							{
								label: 'Text',
								name: 'text',
								type: 'text',
							},
							{
								label: 'Duplicate Text',
								name: 'text',
								type: 'text',
							},
						],
					},
				],
			})
		).toEqual({
			fieldSets: [
				{
					fields: [
						{
							defaultValue: 1,
							label: 'Boost',
							name: 'boost',
							type: 'number',
						},
						{label: 'Text', name: 'text', type: 'text'},
					],
				},
			],
		});
	});
});
