/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Function to create a ddmStructures map, used to grab the proper
 * display names based on the values.
 *
 * @param {Array} items Array of objects with ddm structures
 * @return {Object} Labels of ddmStructures mapped to the values
 */
export default function mapDDMStructures(ddmStructures = []) {
	const ddmStructureMap = {};

	ddmStructures.forEach((ddmStructure) => {
		const {
			className,
			ddmStructureExternalReferenceCode,
			ddmStructureLocalizedName,
			groupExternalReferenceCode,
			groupLocalizedName,
		} = ddmStructure;

		const value = `${className}#${groupExternalReferenceCode}#${ddmStructureExternalReferenceCode}`;
		const label = `${ddmStructureLocalizedName} (${groupLocalizedName})`;

		ddmStructureMap[value] = label;
	});

	return ddmStructureMap;
}
