/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


export class ObjectViewSortColumn {
	'id'?: number;
	'objectFieldName'?: string;
	'priority'?: number;
	'sortOrder'?: 'asc' | 'desc';

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "objectFieldName",
			"baseName": "objectFieldName",
			"type": "string"
		},
		{
			"name": "priority",
			"baseName": "priority",
			"type": "number"
		},
		{
			"name": "sortOrder",
			"baseName": "sortOrder",
			"type": "'asc' | 'desc'"
		}
	];

	static getAttributeTypeMap() {
		return ObjectViewSortColumn.attributeTypeMap;
	}
}
