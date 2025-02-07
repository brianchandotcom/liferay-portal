/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ObjectAction } from './objectAction';
import { ObjectDefinitionSetting } from './objectDefinitionSetting';
import { ObjectField } from './objectField';
import { ObjectLayout } from './objectLayout';
import { ObjectRelationship } from './objectRelationship';
import { ObjectValidationRule } from './objectValidationRule';
import { ObjectView } from './objectView';
import { Status } from './status';

export class ObjectDefinition {
    'accountEntryRestricted'?: boolean;
    'accountEntryRestrictedObjectFieldName'?: string;
    'actions'?: { [key: string]: { [key: string]: string; }; };
    'active'?: boolean;
    'className'?: string;
    'dateCreated'?: string;
    'dateModified'?: string;
    'defaultLanguageId'?: string;
    'enableCategorization'?: boolean;
    'enableComments'?: boolean;
    'enableFriendlyURLCustomization'?: boolean;
    'enableIndexSearch'?: boolean;
    'enableLocalization'?: boolean;
    'enableObjectEntryDraft'?: boolean;
    'enableObjectEntryHistory'?: boolean;
    'externalReferenceCode'?: string;
    'id'?: number;
    'label'?: { [key: string]: string; };
    'modifiable'?: boolean;
    'name'?: string;
    'objectActions'?: Array<ObjectAction>;
    'objectDefinitionSettings'?: Array<ObjectDefinitionSetting>;
    'objectFields'?: Array<ObjectField>;
    'objectFolderExternalReferenceCode'?: string;
    'objectLayouts'?: Array<ObjectLayout>;
    'objectRelationships'?: Array<ObjectRelationship>;
    'objectValidationRules'?: Array<ObjectValidationRule>;
    'objectViews'?: Array<ObjectView>;
    'panelAppOrder'?: string;
    'panelCategoryKey'?: string;
    'parameterRequired'?: boolean;
    'pluralLabel'?: { [key: string]: string; };
    'portlet'?: boolean;
    'restContextPath'?: string;
    'rootObjectDefinitionExternalReferenceCode'?: string;
    'scope'?: string;
    'status'?: Status;
    'storageType'?: string;
    'system'?: boolean;
    'titleObjectFieldName'?: string;

	static discriminator: string | undefined = undefined;

<<<<<<< HEAD
    static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
        {
            "name": "accountEntryRestricted",
            "baseName": "accountEntryRestricted",
            "type": "boolean"
        },
        {
            "name": "accountEntryRestrictedObjectFieldName",
            "baseName": "accountEntryRestrictedObjectFieldName",
            "type": "string"
        },
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
            "name": "className",
            "baseName": "className",
            "type": "string"
        },
        {
            "name": "dateCreated",
            "baseName": "dateCreated",
            "type": "string"
        },
        {
            "name": "dateModified",
            "baseName": "dateModified",
            "type": "string"
        },
        {
            "name": "defaultLanguageId",
            "baseName": "defaultLanguageId",
            "type": "string"
        },
        {
            "name": "enableCategorization",
            "baseName": "enableCategorization",
            "type": "boolean"
        },
        {
            "name": "enableComments",
            "baseName": "enableComments",
            "type": "boolean"
        },
        {
            "name": "enableFriendlyURLCustomization",
            "baseName": "enableFriendlyURLCustomization",
            "type": "boolean"
        },
        {
            "name": "enableIndexSearch",
            "baseName": "enableIndexSearch",
            "type": "boolean"
        },
        {
            "name": "enableLocalization",
            "baseName": "enableLocalization",
            "type": "boolean"
        },
        {
            "name": "enableObjectEntryDraft",
            "baseName": "enableObjectEntryDraft",
            "type": "boolean"
        },
        {
            "name": "enableObjectEntryHistory",
            "baseName": "enableObjectEntryHistory",
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
            "name": "modifiable",
            "baseName": "modifiable",
            "type": "boolean"
        },
        {
            "name": "name",
            "baseName": "name",
            "type": "string"
        },
        {
            "name": "objectActions",
            "baseName": "objectActions",
            "type": "Array<ObjectAction>"
        },
        {
            "name": "objectDefinitionSettings",
            "baseName": "objectDefinitionSettings",
            "type": "Array<ObjectDefinitionSetting>"
        },
        {
            "name": "objectFields",
            "baseName": "objectFields",
            "type": "Array<ObjectField>"
        },
        {
            "name": "objectFolderExternalReferenceCode",
            "baseName": "objectFolderExternalReferenceCode",
            "type": "string"
        },
        {
            "name": "objectLayouts",
            "baseName": "objectLayouts",
            "type": "Array<ObjectLayout>"
        },
        {
            "name": "objectRelationships",
            "baseName": "objectRelationships",
            "type": "Array<ObjectRelationship>"
        },
        {
            "name": "objectValidationRules",
            "baseName": "objectValidationRules",
            "type": "Array<ObjectValidationRule>"
        },
        {
            "name": "objectViews",
            "baseName": "objectViews",
            "type": "Array<ObjectView>"
        },
        {
            "name": "panelAppOrder",
            "baseName": "panelAppOrder",
            "type": "string"
        },
        {
            "name": "panelCategoryKey",
            "baseName": "panelCategoryKey",
            "type": "string"
        },
        {
            "name": "parameterRequired",
            "baseName": "parameterRequired",
            "type": "boolean"
        },
        {
            "name": "pluralLabel",
            "baseName": "pluralLabel",
            "type": "{ [key: string]: string; }"
        },
        {
            "name": "portlet",
            "baseName": "portlet",
            "type": "boolean"
        },
        {
            "name": "restContextPath",
            "baseName": "restContextPath",
            "type": "string"
        },
        {
            "name": "rootObjectDefinitionExternalReferenceCode",
            "baseName": "rootObjectDefinitionExternalReferenceCode",
            "type": "string"
        },
        {
            "name": "scope",
            "baseName": "scope",
            "type": "string"
        },
        {
            "name": "status",
            "baseName": "status",
            "type": "Status"
        },
        {
            "name": "storageType",
            "baseName": "storageType",
            "type": "string"
        },
        {
            "name": "system",
            "baseName": "system",
            "type": "boolean"
        },
        {
            "name": "titleObjectFieldName",
            "baseName": "titleObjectFieldName",
            "type": "string"
        }    ];
=======
	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "accountEntryRestricted",
			"baseName": "accountEntryRestricted",
			"type": "boolean"
		},
		{
			"name": "accountEntryRestrictedObjectFieldName",
			"baseName": "accountEntryRestrictedObjectFieldName",
			"type": "string"
		},
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
			"name": "className",
			"baseName": "className",
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
			"name": "defaultLanguageId",
			"baseName": "defaultLanguageId",
			"type": "string"
		},
		{
			"name": "enableCategorization",
			"baseName": "enableCategorization",
			"type": "boolean"
		},
		{
			"name": "enableComments",
			"baseName": "enableComments",
			"type": "boolean"
		},
		{
			"name": "enableFriendlyURLCustomization",
			"baseName": "enableFriendlyURLCustomization",
			"type": "boolean"
		},
		{
			"name": "enableIndexSearch",
			"baseName": "enableIndexSearch",
			"type": "boolean"
		},
		{
			"name": "enableLocalization",
			"baseName": "enableLocalization",
			"type": "boolean"
		},
		{
			"name": "enableObjectEntryDraft",
			"baseName": "enableObjectEntryDraft",
			"type": "boolean"
		},
		{
			"name": "enableObjectEntryHistory",
			"baseName": "enableObjectEntryHistory",
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
			"name": "modifiable",
			"baseName": "modifiable",
			"type": "boolean"
		},
		{
			"name": "name",
			"baseName": "name",
			"type": "string"
		},
		{
			"name": "objectActions",
			"baseName": "objectActions",
			"type": "Array<ObjectAction>"
		},
		{
			"name": "objectFields",
			"baseName": "objectFields",
			"type": "Array<ObjectField>"
		},
		{
			"name": "objectFolderExternalReferenceCode",
			"baseName": "objectFolderExternalReferenceCode",
			"type": "string"
		},
		{
			"name": "objectLayouts",
			"baseName": "objectLayouts",
			"type": "Array<ObjectLayout>"
		},
		{
			"name": "objectRelationships",
			"baseName": "objectRelationships",
			"type": "Array<ObjectRelationship>"
		},
		{
			"name": "objectValidationRules",
			"baseName": "objectValidationRules",
			"type": "Array<ObjectValidationRule>"
		},
		{
			"name": "objectViews",
			"baseName": "objectViews",
			"type": "Array<ObjectView>"
		},
		{
			"name": "panelAppOrder",
			"baseName": "panelAppOrder",
			"type": "string"
		},
		{
			"name": "panelCategoryKey",
			"baseName": "panelCategoryKey",
			"type": "string"
		},
		{
			"name": "parameterRequired",
			"baseName": "parameterRequired",
			"type": "boolean"
		},
		{
			"name": "pluralLabel",
			"baseName": "pluralLabel",
			"type": "{ [key: string]: string; }"
		},
		{
			"name": "portlet",
			"baseName": "portlet",
			"type": "boolean"
		},
		{
			"name": "restContextPath",
			"baseName": "restContextPath",
			"type": "string"
		},
		{
			"name": "rootObjectDefinitionExternalReferenceCode",
			"baseName": "rootObjectDefinitionExternalReferenceCode",
			"type": "string"
		},
		{
			"name": "scope",
			"baseName": "scope",
			"type": "string"
		},
		{
			"name": "status",
			"baseName": "status",
			"type": "Status"
		},
		{
			"name": "storageType",
			"baseName": "storageType",
			"type": "string"
		},
		{
			"name": "system",
			"baseName": "system",
			"type": "boolean"
		},
		{
			"name": "titleObjectFieldName",
			"baseName": "titleObjectFieldName",
			"type": "string"
		}
	];
>>>>>>> 6c2eec6 (LPD-48305 buildREST: Auto generate API client for object-admin-rest with new TypeScriptClientGenerator)

	static getAttributeTypeMap() {
		return ObjectDefinition.attributeTypeMap;
	}
}
