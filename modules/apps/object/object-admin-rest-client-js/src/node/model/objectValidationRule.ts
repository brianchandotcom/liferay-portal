/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectValidationRuleSetting } from './objectValidationRuleSetting';

export class ObjectValidationRule {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'active'?: boolean;
	'dateCreated'?: Date;
	'dateModified'?: Date;
	'engine'?: string;
	'engineLabel'?: string;
	'errorLabel'?: { [key: string]: string; };
	'externalReferenceCode'?: string;
	'id'?: number;
	'name'?: { [key: string]: string; };
	'objectDefinitionExternalReferenceCode'?: string;
	'objectDefinitionId'?: number;
	'objectValidationRuleSettings'?: Array<ObjectValidationRuleSetting>;
	'outputType'?: 'fullValidation' | 'partialValidation';
	'script'?: string;
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
			"name": "engine",
			"baseName": "engine",
			"type": "string"
		},
		{
			"name": "engineLabel",
			"baseName": "engineLabel",
			"type": "string"
		},
		{
			"name": "errorLabel",
			"baseName": "errorLabel",
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
			"name": "name",
			"baseName": "name",
			"type": "{ [key: string]: string; }"
		},
		{
			"name": "objectDefinitionExternalReferenceCode",
			"baseName": "objectDefinitionExternalReferenceCode",
			"type": "string"
		},
		{
			"name": "objectDefinitionId",
			"baseName": "objectDefinitionId",
			"type": "number"
		},
		{
			"name": "objectValidationRuleSettings",
			"baseName": "objectValidationRuleSettings",
			"type": "Array<ObjectValidationRuleSetting>"
		},
		{
			"name": "outputType",
			"baseName": "outputType",
			"type": "'fullValidation' | 'partialValidation'"
		},
		{
			"name": "script",
			"baseName": "script",
			"type": "string"
		},
		{
			"name": "system",
			"baseName": "system",
			"type": "boolean"
		}
	];

	static getAttributeTypeMap() {
		return ObjectValidationRule.attributeTypeMap;
	}
}
