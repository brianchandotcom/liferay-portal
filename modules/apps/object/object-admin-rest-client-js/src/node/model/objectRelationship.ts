/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectField } from './objectField';

export class ObjectRelationship {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'deletionType'?: 'cascade' | 'disassociate' | 'prevent';
	'edge'?: boolean;
	'externalReferenceCode'?: string;
	'id'?: number;
	'label'?: { [key: string]: string; };
	'name'?: string;
	'objectDefinitionExternalReferenceCode1'?: string;
	'objectDefinitionExternalReferenceCode2'?: string;
	'objectDefinitionId1'?: number;
	'objectDefinitionId2'?: number;
	'objectDefinitionModifiable2'?: boolean;
	'objectDefinitionName2'?: string;
	'objectDefinitionScope2'?: string;
	'objectDefinitionSystem2'?: boolean;
	'objectField'?: ObjectField;
	'parameterObjectFieldId'?: number;
	'parameterObjectFieldName'?: string;
	'reverse'?: boolean;
	'system'?: boolean;
	'type'?: 'oneToMany' | 'oneToOne' | 'manyToMany';

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "actions",
			"baseName": "actions",
			"type": "{ [key: string]: { [key: string]: string; }; }"
		},
		{
			"name": "deletionType",
			"baseName": "deletionType",
			"type": "'cascade' | 'disassociate' | 'prevent'"
		},
		{
			"name": "edge",
			"baseName": "edge",
			"type": "boolean"
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
			"name": "objectDefinitionExternalReferenceCode1",
			"baseName": "objectDefinitionExternalReferenceCode1",
			"type": "string"
		},
		{
			"name": "objectDefinitionExternalReferenceCode2",
			"baseName": "objectDefinitionExternalReferenceCode2",
			"type": "string"
		},
		{
			"name": "objectDefinitionId1",
			"baseName": "objectDefinitionId1",
			"type": "number"
		},
		{
			"name": "objectDefinitionId2",
			"baseName": "objectDefinitionId2",
			"type": "number"
		},
		{
			"name": "objectDefinitionModifiable2",
			"baseName": "objectDefinitionModifiable2",
			"type": "boolean"
		},
		{
			"name": "objectDefinitionName2",
			"baseName": "objectDefinitionName2",
			"type": "string"
		},
		{
			"name": "objectDefinitionScope2",
			"baseName": "objectDefinitionScope2",
			"type": "string"
		},
		{
			"name": "objectDefinitionSystem2",
			"baseName": "objectDefinitionSystem2",
			"type": "boolean"
		},
		{
			"name": "objectField",
			"baseName": "objectField",
			"type": "ObjectField"
		},
		{
			"name": "parameterObjectFieldId",
			"baseName": "parameterObjectFieldId",
			"type": "number"
		},
		{
			"name": "parameterObjectFieldName",
			"baseName": "parameterObjectFieldName",
			"type": "string"
		},
		{
			"name": "reverse",
			"baseName": "reverse",
			"type": "boolean"
		},
		{
			"name": "system",
			"baseName": "system",
			"type": "boolean"
		},
		{
			"name": "type",
			"baseName": "type",
			"type": "'oneToMany' | 'oneToOne' | 'manyToMany'"
		}
	];

	static getAttributeTypeMap() {
		return ObjectRelationship.attributeTypeMap;
	}
}
