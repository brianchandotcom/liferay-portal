/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isDefined from '../functions/is_defined';

/**
 * Used to remove `elementDefinition.uiConfiguration` `*Localized` properties.
 * @param {object} elementDefinition
 * @returns {object}
 */
export default function deleteLocalizedProperties(elementDefinition) {
	if (!isDefined(elementDefinition)) {
		return {};
	}

	// This creates a deep copy so that the `delete` function doesn't affect any
	// references to the `elementDefinition` object.

	let elementDefinitionDeepCopy = elementDefinition;

	try {
		elementDefinitionDeepCopy = JSON.parse(
			JSON.stringify(elementDefinition)
		);

		// Iterate through all `fields`.

		elementDefinitionDeepCopy.uiConfiguration?.fieldSets?.forEach(
			(fieldSet) => {
				fieldSet?.fields?.forEach((field) => {

					// Find property names that end with `Localized`.

					const localizedPropertyNames = Object.keys(
						field
					).filter((key) => key.endsWith('Localized'));

					// Remove the found property names.

					localizedPropertyNames.forEach((propertyName) => {
						delete field[propertyName];
					});
				});
			}
		);
	}
	catch (error) {
		if (process.env.NODE_ENV === 'development') {
			console.error(error);
		}
	}

	return elementDefinitionDeepCopy;
}
