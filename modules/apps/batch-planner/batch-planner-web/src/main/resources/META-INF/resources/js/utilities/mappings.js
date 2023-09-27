/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getAvailableMappings(newMappings, fileFields, dbFields) {
	const availableMappings = {};

	if (!fileFields || !dbFields) {
		return availableMappings;
	}

	const dbFieldNames = dbFields?.map((dbField) => dbField.name) || [];

	const newMappingsEntries = Object.entries(newMappings || {});

	if (newMappingsEntries.length) {
		newMappingsEntries.forEach(([mappedDbField, mappedFileField]) => {
			if (
				fileFields?.includes(mappedFileField) &&
				dbFieldNames?.includes(mappedDbField)
			) {
				availableMappings[mappedDbField] = mappedFileField;
			}
		});
	}
	else {
		dbFieldNames.map((dbFieldName) => {
			if (fileFields.includes(dbFieldName)) {
				availableMappings[dbFieldName] = dbFieldName;
			}
		});
	}

	return availableMappings;
}
