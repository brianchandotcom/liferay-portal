/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


/**
* Another server to create and publish publications.
*/
export class CTRemote {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'clientId'?: string;
	'clientSecret'?: string;
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'description'?: string;
	'id'?: number;
	'name'?: string;
	'ownerName'?: string;
	'url'?: string;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "actions",
			"baseName": "actions",
			"type": "{ [key: string]: { [key: string]: string; }; }"
		},
		{
			"name": "clientId",
			"baseName": "clientId",
			"type": "string"
		},
		{
			"name": "clientSecret",
			"baseName": "clientSecret",
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
			"name": "url",
			"baseName": "url",
			"type": "string"
		}
	];

	static getAttributeTypeMap() {
		return CTRemote.attributeTypeMap;
	}
}
