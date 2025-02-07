/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Status } from './status';

/**
* Represents publications that have been published.
*/
export class CTProcess {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'ctCollectionId'?: number;
	'datePublished'?: Date;
	'description'?: string;
	'id'?: number;
	'name'?: string;
	'ownerName'?: string;
	'status'?: Status;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "actions",
			"baseName": "actions",
			"type": "{ [key: string]: { [key: string]: string; }; }"
		},
		{
			"name": "ctCollectionId",
			"baseName": "ctCollectionId",
			"type": "number"
		},
		{
			"name": "datePublished",
			"baseName": "datePublished",
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
			"name": "ownerName",
			"baseName": "ownerName",
			"type": "string"
		},
		{
			"name": "status",
			"baseName": "status",
			"type": "Status"
		}
	];

	static getAttributeTypeMap() {
		return CTProcess.attributeTypeMap;
	}
}
