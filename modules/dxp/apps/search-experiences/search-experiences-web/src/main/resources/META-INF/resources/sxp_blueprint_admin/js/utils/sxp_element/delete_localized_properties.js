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
