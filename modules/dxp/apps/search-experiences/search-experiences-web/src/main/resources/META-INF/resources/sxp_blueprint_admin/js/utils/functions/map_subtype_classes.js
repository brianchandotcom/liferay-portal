/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Function to create a subtypeClasses map, used to grab the proper
 * display names based on the values.
 *
 * @param {Array} subtypeClasses Array of objects with subtype classes
 * @return {Object} Labels of subtypeClasses mapped to the values
 */
export default function mapSubtypeClasses(subtypeClasses = []) {
	const subtypeClassesMap = {};

	subtypeClasses.forEach((subtypeClass) => {
		const {
			assetSubtypeExternalReferenceCode,
			assetSubtypeLocalizedName,
			entryClassName,
			groupExternalReferenceCode,
			groupLocalizedName,
		} = subtypeClass;

		const value = `${entryClassName}&&${groupExternalReferenceCode}&&${assetSubtypeExternalReferenceCode}`;
		const label = groupLocalizedName
			? `${assetSubtypeLocalizedName} (${groupLocalizedName})`
			: assetSubtypeLocalizedName;

		subtypeClassesMap[value] = label;
	});

	return subtypeClassesMap;
}
