/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectLayoutColumn } from './objectLayoutColumn';

export class ObjectLayoutRow {
	'id'?: number;
	'objectLayoutColumns'?: Array<ObjectLayoutColumn>;
	'priority'?: number;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "objectLayoutColumns",
			"baseName": "objectLayoutColumns",
			"type": "Array<ObjectLayoutColumn>"
		},
		{
			"name": "priority",
			"baseName": "priority",
			"type": "number"
		}
	];

	static getAttributeTypeMap() {
		return ObjectLayoutRow.attributeTypeMap;
	}
}
