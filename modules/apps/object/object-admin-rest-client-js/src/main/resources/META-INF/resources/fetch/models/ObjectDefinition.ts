/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectAction } from './ObjectAction';
import type { ObjectField } from './ObjectField';
import type { ObjectLayout } from './ObjectLayout';
import type { ObjectRelationship } from './ObjectRelationship';
import type { ObjectValidationRule } from './ObjectValidationRule';
import type { ObjectView } from './ObjectView';
import type { Status } from './Status';
export type ObjectDefinition = {
    accountEntryRestricted?: boolean;
    accountEntryRestrictedObjectFieldName?: string;
    readonly actions?: Record<string, Record<string, string>>;
    active?: boolean;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    defaultLanguageId?: string;
    enableCategorization?: boolean;
    enableComments?: boolean;
    enableIndexSearch?: boolean;
    enableLocalization?: boolean;
    enableObjectEntryDraft?: boolean;
    enableObjectEntryHistory?: boolean;
    externalReferenceCode?: string;
    readonly id?: number;
    label?: Record<string, string>;
    modifiable?: boolean;
    name?: string;
    objectActions?: Array<ObjectAction>;
    objectFields?: Array<ObjectField>;
    objectFolderExternalReferenceCode?: string;
    objectLayouts?: Array<ObjectLayout>;
    objectRelationships?: Array<ObjectRelationship>;
    objectValidationRules?: Array<ObjectValidationRule>;
    objectViews?: Array<ObjectView>;
    panelAppOrder?: string;
    panelCategoryKey?: string;
    readonly parameterRequired?: boolean;
    pluralLabel?: Record<string, string>;
    portlet?: boolean;
    readonly restContextPath?: string;
    rootObjectDefinitionExternalReferenceCode?: string;
    scope?: string;
    status?: Status;
    storageType?: string;
    system?: boolean;
    titleObjectFieldName?: string;
};

