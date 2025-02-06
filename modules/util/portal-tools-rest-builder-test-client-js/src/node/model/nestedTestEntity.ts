/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { TestEntity } from './testEntity';

/**
* https://www.schema.org/Folder
*/
export class NestedTestEntity {
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'description'?: string;
	'id'?: number;
	'name'?: string;
	'testEntity'?: TestEntity;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "dateCreated",
			"baseName": "dateCreated",
			"type": "Date"
		},
		{
			"name": "dateModified",
			"baseName": "dateModified",
			"type": "Date"
		},
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
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "testEntity",
			"baseName": "testEntity",
			"type": "TestEntity"
		}
	];

	static getAttributeTypeMap() {
		return NestedTestEntity.attributeTypeMap;
	}
}
