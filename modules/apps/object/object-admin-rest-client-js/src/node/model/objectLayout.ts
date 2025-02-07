/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectLayoutTab } from './objectLayoutTab';

export class ObjectLayout {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'defaultObjectLayout'?: boolean;
	'id'?: number;
	'name'?: { [key: string]: string; };
	'objectDefinitionExternalReferenceCode'?: string;
	'objectDefinitionId'?: number;
	'objectLayoutTabs'?: Array<ObjectLayoutTab>;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "actions",
			"baseName": "actions",
			"type": "{ [key: string]: { [key: string]: string; }; }"
		},
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
			"name": "defaultObjectLayout",
			"baseName": "defaultObjectLayout",
			"type": "boolean"
		},
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "name",
			"baseName": "name",
			"type": "{ [key: string]: string; }"
		},
		{
			"name": "objectDefinitionExternalReferenceCode",
			"baseName": "objectDefinitionExternalReferenceCode",
			"type": "string"
		},
		{
			"name": "objectDefinitionId",
			"baseName": "objectDefinitionId",
			"type": "number"
		},
		{
			"name": "objectLayoutTabs",
			"baseName": "objectLayoutTabs",
			"type": "Array<ObjectLayoutTab>"
		}
	];

	static getAttributeTypeMap() {
		return ObjectLayout.attributeTypeMap;
	}
}
