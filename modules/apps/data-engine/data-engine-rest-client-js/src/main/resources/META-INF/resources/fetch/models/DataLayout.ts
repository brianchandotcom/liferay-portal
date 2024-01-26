/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataLayoutPage } from './DataLayoutPage';
import type { DataRule } from './DataRule';
export type DataLayout = {
    contentType?: string;
    dataDefinitionId?: number;
    dataLayoutFields?: Record<string, Record<string, any>>;
    dataLayoutKey?: string;
    dataLayoutPages?: Array<DataLayoutPage>;
    dataRules?: Array<DataRule>;
    dateCreated?: string;
    dateModified?: string;
    description?: Record<string, Record<string, any>>;
    id?: number;
    name?: Record<string, Record<string, any>>;
    paginationMode?: string;
    siteId?: number;
    userId?: number;
};

