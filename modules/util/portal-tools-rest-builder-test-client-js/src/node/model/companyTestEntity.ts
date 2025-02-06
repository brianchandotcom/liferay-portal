/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Permission } from './permission';

export class CompanyTestEntity {
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'description'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'permissions'?: Array<Permission>;

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
			"name": "permissions",
			"baseName": "permissions",
			"type": "Array<Permission>"
		}
	];

	static getAttributeTypeMap() {
		return CompanyTestEntity.attributeTypeMap;
	}
}
