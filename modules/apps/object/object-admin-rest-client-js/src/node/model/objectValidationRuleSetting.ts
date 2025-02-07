/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


export class ObjectValidationRuleSetting {
	'name'?: string;
	'value'?: object;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "value",
			"baseName": "value",
			"type": "object"
		}
	];

	static getAttributeTypeMap() {
		return ObjectValidationRuleSetting.attributeTypeMap;
	}
}
