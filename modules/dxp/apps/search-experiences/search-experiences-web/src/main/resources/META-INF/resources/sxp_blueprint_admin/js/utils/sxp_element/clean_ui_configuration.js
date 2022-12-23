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

/**
 * Cleans up the uiConfiguration to prevent page load failures
 * - Checks that `fieldSets` and `fields` are arrays
 * - Removes fields without a `name` property
 * - Removes fields with a duplicate `name` property
 *
 * Example:
 *	cleanUIConfiguration({
 *		fieldSets: [
 *			{
 *				fields: [
 *					{
 *						defaultValue: 1,
 *						label: 'Boost',
 *						name: 'boost',
 *						type: 'number',
 *					},
 *					{
 *						label: 'Text',
 *						type: 'text',
 *					},
 *				],
 *			},
 *		],
 *	});
 *	=> {
 *		fieldSets: [
 *			{
 *				fields: [
 *					{
 *						defaultValue: 1,
 *						label: 'Boost',
 *						name: 'boost',
 *						type: 'number',
 *					},
 *				],
 *			},
 *		],
 *	}
 *
 * @param {object} uiConfiguration Object with UI configuration
 * @return {object}
 */
export default function cleanUIConfiguration(uiConfiguration = {}) {
	const fieldSets = [];

	if (Array.isArray(uiConfiguration.fieldSets)) {
		const fieldNames = [];

		uiConfiguration.fieldSets.forEach((fieldSet) => {
			if (Array.isArray(fieldSet.fields)) {
				const fields = [];

				fieldSet.fields.forEach((config) => {
					if (config.name && !fieldNames.includes(config.name)) {
						fieldNames.push(config.name);
						fields.push(config);
					}
				});

				if (fields.length) {
					fieldSets.push({fields});
				}
			}
		});
	}

	return {fieldSets};
}
