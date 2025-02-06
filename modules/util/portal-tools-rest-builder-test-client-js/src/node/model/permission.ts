/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


export class Permission {
	'actionIds'?: Array<string>;
	'roleName'?: string;
	'xml'?: object;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "actionIds",
			"baseName": "actionIds",
			"type": "Array<string>"
		},
		{
			"name": "roleName",
			"baseName": "roleName",
			"type": "string"
		},
		{
			"name": "xml",
			"baseName": "xml",
			"type": "object"
		}
	];

	static getAttributeTypeMap() {
		return Permission.attributeTypeMap;
	}
}
