/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import deleteLocalizedProperties from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/sxp_element/delete_localized_properties';

const HELP_TEXT_TEST = 'help-text-test';
const HELP_TEXT_LOCALIZED_TEST = 'Help text test.';

const sampleElementDefinition = {
	category: 'match',
	configuration: {},
	icon: 'picture',
	uiConfiguration: {
		fieldSets: [
			{
				fields: [
					{
						defaultValue: [
							{
								boost: '2',
								field: 'localized_title',
								locale: '${context.language_id}',
							},
							{
								boost: '1',
								field: 'content',
								locale: '${context.language_id}',
							},
						],
						helpText: HELP_TEXT_TEST,
						helpTextLocalized: HELP_TEXT_LOCALIZED_TEST,
						label: 'label-test',
						labelLocalized: 'Label Test',
						name: 'fields',
						type: 'fieldMappingList',
						typeOptions: {
							boost: true,
						},
					},
					{
						defaultValue: 1,
						label: 'boost',
						labelLocalized: 'Boost',
						name: 'boost',
						type: 'number',
						typeOptions: {
							min: 0,
						},
					},
				],
			},
		],
	},
};

describe('deleteLocalizedProperties', () => {
	it('deletes helpTextLocalized', () => {

		// Check that `helpTextLocalized` exists.

		expect(
			sampleElementDefinition.uiConfiguration.fieldSets[0].fields[0]
				.helpTextLocalized
		).toEqual(HELP_TEXT_LOCALIZED_TEST);

		const cleanedElementDefinition = deleteLocalizedProperties(
			sampleElementDefinition
		);

		// Check that `helpText` still exists.

		expect(
			cleanedElementDefinition.uiConfiguration.fieldSets[0].fields[0]
				.helpText
		).toEqual(HELP_TEXT_TEST);

		// Check that `helpTextLocalized` is removed.

		expect(
			cleanedElementDefinition.uiConfiguration.fieldSets[0].fields[0]
				.helpTextLocalized
		).toBeUndefined();
	});

	it('deletes labelLocalized in multiple fields', () => {
		const cleanedElementDefinition = deleteLocalizedProperties(
			sampleElementDefinition
		);

		expect(
			cleanedElementDefinition.uiConfiguration.fieldSets[0].fields[0]
				.labelLocalized
		).toBeUndefined();

		expect(
			cleanedElementDefinition.uiConfiguration.fieldSets[0].fields[1]
				.labelLocalized
		).toBeUndefined();
	});
});
