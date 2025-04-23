/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Function to create a assetSubtypes map, used to grab the proper
 * display names based on the values.
 *
 * @param {Array} assetSubtypes Array of objects with subtype classes
 * @return {Object} Labels of assetSubtypes mapped to the values
 */
export default function mapAssetSubtypes(assetSubtypes = []) {
	const assetSubtypesMap = {};

	assetSubtypes.forEach((assetSubtype) => {
		const {
			assetSubtypeExternalReferenceCode,
			assetSubtypeLocalizedName,
			entryClassName,
			groupExternalReferenceCode,
			groupLocalizedName,
		} = assetSubtype;

		const value = `${entryClassName}&&${groupExternalReferenceCode}&&${assetSubtypeExternalReferenceCode}`;
		const label = groupLocalizedName
			? `${assetSubtypeLocalizedName} (${groupLocalizedName})`
			: assetSubtypeLocalizedName;

		assetSubtypesMap[value] = label;
	});

	return assetSubtypesMap;
}
