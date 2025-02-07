/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectStateTransition } from './objectStateTransition';

export class ObjectState {
	'id'?: number;
	'key'?: string;
	'objectStateTransitions'?: Array<ObjectStateTransition>;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "id",
			"baseName": "id",
			"type": "number"
		},
		{
			"name": "key",
			"baseName": "key",
			"type": "string"
		},
		{
			"name": "objectStateTransitions",
			"baseName": "objectStateTransitions",
			"type": "Array<ObjectStateTransition>"
		}
	];

	static getAttributeTypeMap() {
		return ObjectState.attributeTypeMap;
	}
}
