/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


export class ObjectViewFilterColumn {
	'filterType'?: 'excludes' | 'includes';
	'id'?: number;
	'json'?: string;
	'objectFieldName'?: string;
	'valueSummary'?: string;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "filterType",
			"baseName": "filterType",
			"type": "'excludes' | 'includes'"
		},
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "json",
			"baseName": "json",
			"type": "string"
		},
		{
			"name": "objectFieldName",
			"baseName": "objectFieldName",
			"type": "string"
		},
		{
			"name": "valueSummary",
			"baseName": "valueSummary",
			"type": "string"
		}
	];

	static getAttributeTypeMap() {
		return ObjectViewFilterColumn.attributeTypeMap;
	}
}
