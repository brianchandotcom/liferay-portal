/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


/**
* Test Component to test the REST Builder support for nested array items
*/
export class NestedArrayItemsTestEntity {
	'name'?: string;
	'values'?: Array<Array<string>>;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "values",
			"baseName": "values",
			"type": "Array<Array<string>>"
		}
	];

	static getAttributeTypeMap() {
		return NestedArrayItemsTestEntity.attributeTypeMap;
	}
}
