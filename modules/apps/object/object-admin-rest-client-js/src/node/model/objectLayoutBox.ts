/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectLayoutRow } from './objectLayoutRow';

export class ObjectLayoutBox {
	'collapsable'?: boolean;
	'id'?: number;
	'name'?: { [key: string]: string; };
	'objectLayoutRows'?: Array<ObjectLayoutRow>;
	'priority'?: number;
	'type'?: 'categorization' | 'regular';

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "collapsable",
			"baseName": "collapsable",
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
			"name": "objectLayoutRows",
			"baseName": "objectLayoutRows",
			"type": "Array<ObjectLayoutRow>"
		},
		{
			"name": "priority",
			"baseName": "priority",
			"type": "number"
		},
		{
			"name": "type",
			"baseName": "type",
			"type": "'categorization' | 'regular'"
		}
	];

	static getAttributeTypeMap() {
		return ObjectLayoutBox.attributeTypeMap;
	}
}
