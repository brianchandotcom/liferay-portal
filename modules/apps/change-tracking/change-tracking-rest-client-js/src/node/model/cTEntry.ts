/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Status } from './status';

export class CTEntry {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'changeType'?: string;
	'ctCollectionId'?: number;
	'ctCollectionName'?: string;
	'ctCollectionStatus'?: Status;
	'ctCollectionStatusDate'?: Date;
	'ctCollectionStatusUserName'?: string;
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'hideable'?: boolean;
	'id'?: number;
	'modelClassNameId'?: number;
	'modelClassPK'?: number;
	'ownerId'?: number;
	'ownerName'?: string;
	'siteId'?: number;
	'siteName'?: string;
	'status'?: Status;
	'statusMessage'?: string;
	'title'?: string;
	'typeName'?: string;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "actions",
			"baseName": "actions",
			"type": "{ [key: string]: { [key: string]: string; }; }"
		},
		{
			"name": "changeType",
			"baseName": "changeType",
			"type": "string"
		},
		{
			"name": "ctCollectionId",
			"baseName": "ctCollectionId",
			"type": "number"
		},
		{
			"name": "ctCollectionName",
			"baseName": "ctCollectionName",
			"type": "string"
		},
		{
			"name": "ctCollectionStatus",
			"baseName": "ctCollectionStatus",
			"type": "Status"
		},
		{
			"name": "ctCollectionStatusDate",
			"baseName": "ctCollectionStatusDate",
			"type": "Date"
		},
		{
			"name": "ctCollectionStatusUserName",
			"baseName": "ctCollectionStatusUserName",
			"type": "string"
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
			"name": "hideable",
			"baseName": "hideable",
			"type": "boolean"
		},
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "modelClassNameId",
			"baseName": "modelClassNameId",
			"type": "number"
		},
		{
			"name": "modelClassPK",
			"baseName": "modelClassPK",
			"type": "number"
		},
		{
			"name": "ownerId",
			"baseName": "ownerId",
			"type": "number"
		},
		{
			"name": "ownerName",
			"baseName": "ownerName",
			"type": "string"
		},
		{
			"name": "siteId",
			"baseName": "siteId",
			"type": "number"
		},
		{
			"name": "siteName",
			"baseName": "siteName",
			"type": "string"
		},
		{
			"name": "status",
			"baseName": "status",
			"type": "Status"
		},
		{
			"name": "statusMessage",
			"baseName": "statusMessage",
			"type": "string"
		},
		{
			"name": "title",
			"baseName": "title",
			"type": "string"
		},
		{
			"name": "typeName",
			"baseName": "typeName",
			"type": "string"
		}
	];

	static getAttributeTypeMap() {
		return CTEntry.attributeTypeMap;
	}
}
