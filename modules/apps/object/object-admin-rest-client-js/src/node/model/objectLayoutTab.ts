/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectLayoutBox } from './objectLayoutBox';

export class ObjectLayoutTab {
	'id'?: number;
	'name'?: { [key: string]: string; };
	'objectLayoutBoxes'?: Array<ObjectLayoutBox>;
	'objectRelationshipExternalReferenceCode'?: string;
	'objectRelationshipId'?: number;
	'priority'?: number;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
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
			"name": "objectLayoutBoxes",
			"baseName": "objectLayoutBoxes",
			"type": "Array<ObjectLayoutBox>"
		},
		{
			"name": "objectRelationshipExternalReferenceCode",
			"baseName": "objectRelationshipExternalReferenceCode",
			"type": "string"
		},
		{
			"name": "objectRelationshipId",
			"baseName": "objectRelationshipId",
			"type": "number"
		},
		{
			"name": "priority",
			"baseName": "priority",
			"type": "number"
		}
	];

	static getAttributeTypeMap() {
		return ObjectLayoutTab.attributeTypeMap;
	}
}
