/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { NestedTestEntity } from './nestedTestEntity';

/**
* https://www.schema.org/Document
*/
export class TestEntity {
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'description'?: string;
	'documentId'?: number;
	'id'?: number;
	'jsonProperty'?: string;
	'name'?: string;
	'nestedTestEntity'?: NestedTestEntity;
	'self'?: string;
	'testEntities'?: TestEntity;
	'type'?: 'ChildTestEntity1' | 'ChildTestEntity2' | 'ChildTestEntity3';

	static discriminator: string | undefined = "type";

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
			"name": "documentId",
			"baseName": "documentId",
			"type": "number"
		},
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "jsonProperty",
			"baseName": "jsonProperty",
			"type": "string"
		},
		{
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "nestedTestEntity",
			"baseName": "nestedTestEntity",
			"type": "NestedTestEntity"
		},
		{
			"name": "self",
			"baseName": "self",
			"type": "string"
		},
		{
			"name": "testEntities",
			"baseName": "testEntities",
			"type": "TestEntity"
		},
		{
			"name": "type",
			"baseName": "type",
			"type": "'ChildTestEntity1' | 'ChildTestEntity2' | 'ChildTestEntity3'"
		}
	];

	static getAttributeTypeMap() {
		return TestEntity.attributeTypeMap;
	}
}
