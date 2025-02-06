/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


/**
* Test Component to test the generation of getValue method on Entities when one or multiple JSON Maps are present.
*/
export class JSONMapAttributeTestEntity {
	'description'?: string;
	'name'?: string;
	'properties1'?: { [key: string]: object; };
	'properties2'?: { [key: string]: object; };

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "description",
			"baseName": "description",
			"type": "string"
		},
		{
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "properties1",
			"baseName": "properties1",
			"type": "{ [key: string]: object; }"
		},
		{
			"name": "properties2",
			"baseName": "properties2",
			"type": "{ [key: string]: object; }"
		}
	];

	static getAttributeTypeMap() {
		return JSONMapAttributeTestEntity.attributeTypeMap;
	}
}
