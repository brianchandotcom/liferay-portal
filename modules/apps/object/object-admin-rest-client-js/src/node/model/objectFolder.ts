/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectFolderItem } from './objectFolderItem';

export class ObjectFolder {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'externalReferenceCode'?: string;
	'id'?: number;
	'label'?: { [key: string]: string; };
	'name'?: string;
	'objectFolderItems'?: Array<ObjectFolderItem>;

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
			"name": "externalReferenceCode",
			"baseName": "externalReferenceCode",
			"type": "string"
		},
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "label",
			"baseName": "label",
			"type": "{ [key: string]: string; }"
		},
		{
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "objectFolderItems",
			"baseName": "objectFolderItems",
			"type": "Array<ObjectFolderItem>"
		}
	];

	static getAttributeTypeMap() {
		return ObjectFolder.attributeTypeMap;
	}
}
