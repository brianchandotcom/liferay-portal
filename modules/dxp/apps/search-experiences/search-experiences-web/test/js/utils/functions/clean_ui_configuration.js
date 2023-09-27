/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
