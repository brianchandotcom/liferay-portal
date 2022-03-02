/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
