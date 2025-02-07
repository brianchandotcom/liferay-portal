/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Status } from './status';

export class ObjectAction {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'active'?: boolean;
	'conditionExpression'?: string;
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'description'?: string;
	'errorMessage'?: { [key: string]: string; };
	'externalReferenceCode'?: string;
	'id'?: number;
	'label'?: { [key: string]: string; };
	'name'?: string;
	'objectActionExecutorKey'?: string;
	'objectActionTriggerKey'?: string;
	'parameters'?: { [key: string]: any; };
	'status'?: Status;
	'system'?: boolean;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "actions",
			"baseName": "actions",
			"type": "{ [key: string]: { [key: string]: string; }; }"
		},
		{
			"name": "active",
			"baseName": "active",
			"type": "boolean"
		},
		{
			"name": "conditionExpression",
			"baseName": "conditionExpression",
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
			"name": "errorMessage",
			"baseName": "errorMessage",
			"type": "{ [key: string]: string; }"
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
			"name": "label",
			"baseName": "label",
			"type": "{ [key: string]: string; }"
		},
		{
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "objectActionExecutorKey",
			"baseName": "objectActionExecutorKey",
			"type": "string"
		},
		{
			"name": "objectActionTriggerKey",
			"baseName": "objectActionTriggerKey",
			"type": "string"
		},
		{
			"name": "parameters",
			"baseName": "parameters",
			"type": "{ [key: string]: any; }"
		},
		{
			"name": "status",
			"baseName": "status",
			"type": "Status"
		},
		{
			"name": "system",
			"baseName": "system",
			"type": "boolean"
		}
	];

	static getAttributeTypeMap() {
		return ObjectAction.attributeTypeMap;
	}
}
