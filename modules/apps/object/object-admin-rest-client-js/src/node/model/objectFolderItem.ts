/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectDefinition } from './objectDefinition';

export class ObjectFolderItem {
	'linkedObjectDefinition'?: boolean;
	'objectDefinition'?: ObjectDefinition;
	'objectDefinitionExternalReferenceCode'?: string;
	'positionX'?: number;
	'positionY'?: number;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "linkedObjectDefinition",
			"baseName": "linkedObjectDefinition",
			"type": "boolean"
		},
		{
			"name": "objectDefinition",
			"baseName": "objectDefinition",
			"type": "ObjectDefinition"
		},
		{
			"name": "objectDefinitionExternalReferenceCode",
			"baseName": "objectDefinitionExternalReferenceCode",
			"type": "string"
		},
		{
			"name": "positionX",
			"baseName": "positionX",
			"type": "number"
		},
		{
			"name": "positionY",
			"baseName": "positionY",
			"type": "number"
		}
	];

	static getAttributeTypeMap() {
		return ObjectFolderItem.attributeTypeMap;
	}
}
