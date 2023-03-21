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
