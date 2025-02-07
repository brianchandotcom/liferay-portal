/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Status } from './status';

/**
* Represents a set of changes tracked for a publication.
*/
export class CTCollection {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'dateScheduled'?: Date;
	'description'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'name'?: string;
	'ownerName'?: string;
	'status'?: Status;
	'statusMessage'?: string;

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
			"name": "dateScheduled",
			"baseName": "dateScheduled",
			"type": "Date"
		},
		{
			"name": "description",
			"baseName": "description",
			"type": "string"
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
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "ownerName",
			"baseName": "ownerName",
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
		}
	];

	static getAttributeTypeMap() {
		return CTCollection.attributeTypeMap;
	}
}
