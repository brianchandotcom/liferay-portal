/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


export class ObjectFieldSetting {
	'id'?: number;
	'name'?: string;
	'objectFieldId'?: number;
	'value'?: object;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "objectFieldId",
			"baseName": "objectFieldId",
			"type": "number"
		},
		{
			"name": "value",
			"baseName": "value",
			"type": "object"
		}
	];

	static getAttributeTypeMap() {
		return ObjectFieldSetting.attributeTypeMap;
	}
}
