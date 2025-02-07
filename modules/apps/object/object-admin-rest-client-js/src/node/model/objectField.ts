/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectFieldSetting } from './objectFieldSetting';

export class ObjectField {
	'DBType'?: 'BigDecimal' | 'Boolean' | 'Clob' | 'Date' | 'DateTime' | 'Double' | 'Integer' | 'Long' | 'String';
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'businessType'?: 'Aggregation' | 'Attachment' | 'AutoIncrement' | 'Boolean' | 'Date' | 'DateTime' | 'Decimal' | 'Encrypted' | 'Formula' | 'Integer' | 'LongInteger' | 'LongText' | 'MultiselectPicklist' | 'Picklist' | 'PrecisionDecimal' | 'Relationship' | 'RichText' | 'Text';
	'defaultValue'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'indexed'?: boolean;
	'indexedAsKeyword'?: boolean;
	'indexedLanguageId'?: string;
	'label'?: { [key: string]: string; };
	'listTypeDefinitionExternalReferenceCode'?: string;
	'listTypeDefinitionId'?: number;
	'localized'?: boolean;
	'name'?: string;
	'objectDefinitionExternalReferenceCode1'?: string;
	'objectFieldSettings'?: Array<ObjectFieldSetting>;
	'objectRelationshipExternalReferenceCode'?: string;
	'readOnly'?: 'conditional' | 'false' | 'true';
	'readOnlyConditionExpression'?: string;
	'relationshipType'?: 'oneToMany' | 'oneToOne';
	'required'?: boolean;
	'state'?: boolean;
	'system'?: boolean;
	'type'?: 'BigDecimal' | 'Boolean' | 'Clob' | 'Date' | 'DateTime' | 'Double' | 'Integer' | 'Long' | 'String';
	'unique'?: boolean;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "DBType",
			"baseName": "DBType",
			"type": "'BigDecimal' | 'Boolean' | 'Clob' | 'Date' | 'DateTime' | 'Double' | 'Integer' | 'Long' | 'String'"
		},
		{
			"name": "actions",
			"baseName": "actions",
			"type": "{ [key: string]: { [key: string]: string; }; }"
		},
		{
			"name": "businessType",
			"baseName": "businessType",
			"type": "'Aggregation' | 'Attachment' | 'AutoIncrement' | 'Boolean' | 'Date' | 'DateTime' | 'Decimal' | 'Encrypted' | 'Formula' | 'Integer' | 'LongInteger' | 'LongText' | 'MultiselectPicklist' | 'Picklist' | 'PrecisionDecimal' | 'Relationship' | 'RichText' | 'Text'"
		},
		{
			"name": "defaultValue",
			"baseName": "defaultValue",
			"type": "string"
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
			"name": "indexed",
			"baseName": "indexed",
			"type": "boolean"
		},
		{
			"name": "indexedAsKeyword",
			"baseName": "indexedAsKeyword",
			"type": "boolean"
		},
		{
			"name": "indexedLanguageId",
			"baseName": "indexedLanguageId",
			"type": "string"
		},
		{
			"name": "label",
			"baseName": "label",
			"type": "{ [key: string]: string; }"
		},
		{
			"name": "listTypeDefinitionExternalReferenceCode",
			"baseName": "listTypeDefinitionExternalReferenceCode",
			"type": "string"
		},
		{
			"name": "listTypeDefinitionId",
			"baseName": "listTypeDefinitionId",
			"type": "number"
		},
		{
			"name": "localized",
			"baseName": "localized",
			"type": "boolean"
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
			"name": "objectFieldSettings",
			"baseName": "objectFieldSettings",
			"type": "Array<ObjectFieldSetting>"
		},
		{
			"name": "objectRelationshipExternalReferenceCode",
			"baseName": "objectRelationshipExternalReferenceCode",
			"type": "string"
		},
		{
			"name": "readOnly",
			"baseName": "readOnly",
			"type": "'conditional' | 'false' | 'true'"
		},
		{
			"name": "readOnlyConditionExpression",
			"baseName": "readOnlyConditionExpression",
			"type": "string"
		},
		{
			"name": "relationshipType",
			"baseName": "relationshipType",
			"type": "'oneToMany' | 'oneToOne'"
		},
		{
			"name": "required",
			"baseName": "required",
			"type": "boolean"
		},
		{
			"name": "state",
			"baseName": "state",
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
			"type": "'BigDecimal' | 'Boolean' | 'Clob' | 'Date' | 'DateTime' | 'Double' | 'Integer' | 'Long' | 'String'"
		},
		{
			"name": "unique",
			"baseName": "unique",
			"type": "boolean"
		}
	];

	static getAttributeTypeMap() {
		return ObjectField.attributeTypeMap;
	}
}
