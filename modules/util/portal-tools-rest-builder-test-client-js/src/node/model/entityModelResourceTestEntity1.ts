/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


/**
* A EntityModelResource test entity with no filterable fields.
*/
export class EntityModelResourceTestEntity1 {
	'id'?: number;
	'name'?: string;

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
		}
	];

	static getAttributeTypeMap() {
		return EntityModelResourceTestEntity1.attributeTypeMap;
	}
}
