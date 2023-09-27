/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getFieldChildren(field: any) {
	if (field.rows?.every((row: any) => row.columns)) {
		return (
			field.rows?.map((row: any) => ({
				...row,
				columns: row.columns.map((column: any) => ({
					...column,
					fields: column.fields.map((fieldName: any) =>
						field.nestedFields.find(
							(field: any) => field.fieldName === fieldName
						)
					),
				})),
			})) || []
		);
	}

	return [];
}
