/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { FormRecord } from './FormRecord';
import type { FormStructure } from './FormStructure';
/**
 * https://www.schema.org/Form
 */
export type Form = {
    availableLanguages?: Array<string>;
    creator?: Creator;
    dateCreated?: string;
    dateModified?: string;
    datePublished?: string;
    defaultLanguage?: string;
    description?: string;
    description_i18n?: Record<string, string>;
    readonly formRecords?: Array<FormRecord>;
    formRecordsIds?: Array<number>;
    id?: number;
    name?: string;
    name_i18n?: Record<string, string>;
    siteId?: number;
    readonly structure?: FormStructure;
    structureId?: number;
};

