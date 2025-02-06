/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


export class UnreferencedTestEntity {
	'description'?: string;
	'id'?: number;
	'property_with_hyphens'?: string;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "description",
			"baseName": "description",
			"type": "string"
		},
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "property_with_hyphens",
			"baseName": "property_with_hyphens",
			"type": "string"
		}
	];

	static getAttributeTypeMap() {
		return UnreferencedTestEntity.attributeTypeMap;
	}
}
