/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectViewColumn } from './objectViewColumn';
import { ObjectViewFilterColumn } from './objectViewFilterColumn';
import { ObjectViewSortColumn } from './objectViewSortColumn';

export class ObjectView {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'defaultObjectView'?: boolean;
	'id'?: number;
	'name'?: { [key: string]: string; };
	'objectDefinitionExternalReferenceCode'?: string;
	'objectDefinitionId'?: number;
	'objectViewColumns'?: Array<ObjectViewColumn>;
	'objectViewFilterColumns'?: Array<ObjectViewFilterColumn>;
	'objectViewSortColumns'?: Array<ObjectViewSortColumn>;

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
			"name": "defaultObjectView",
			"baseName": "defaultObjectView",
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
			"name": "objectViewColumns",
			"baseName": "objectViewColumns",
			"type": "Array<ObjectViewColumn>"
		},
		{
			"name": "objectViewFilterColumns",
			"baseName": "objectViewFilterColumns",
			"type": "Array<ObjectViewFilterColumn>"
		},
		{
			"name": "objectViewSortColumns",
			"baseName": "objectViewSortColumns",
			"type": "Array<ObjectViewSortColumn>"
		}
	];

	static getAttributeTypeMap() {
		return ObjectView.attributeTypeMap;
	}
}
