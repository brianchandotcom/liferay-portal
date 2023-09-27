/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
