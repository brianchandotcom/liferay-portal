/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataDefinitionField } from './DataDefinitionField';
import type { DataLayout } from './DataLayout';
import type { DataRule } from './DataRule';
/**
 * https://www.schema.org/DataDefinition
 */
export type DataDefinition = {
    availableLanguageIds?: Array<string>;
    contentType?: string;
    dataDefinitionFields?: Array<DataDefinitionField>;
    dataDefinitionKey?: string;
    dataRules?: Array<DataRule>;
    dateCreated?: string;
    dateModified?: string;
    defaultDataLayout?: DataLayout;
    defaultLanguageId?: string;
    description?: Record<string, Record<string, any>>;
    id?: number;
    name?: Record<string, Record<string, any>>;
    siteId?: number;
    storageType?: string;
    userId?: number;
};

