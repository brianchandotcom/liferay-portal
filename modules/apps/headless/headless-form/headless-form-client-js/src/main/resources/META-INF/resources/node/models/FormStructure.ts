/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { FormField } from './FormField';
/**
 * https://www.schema.org/FormStructure
 */
export type FormStructure = {
    availableLanguages?: Array<string>;
    creator?: Creator;
    dateCreated?: string;
    dateModified?: string;
    description?: string;
    description_i18n?: Record<string, string>;
    /**
     * https://www.schema.org/FormPage
     */
    formPages?: Array<{
        formFields?: Array<FormField>;
        headline?: string;
        headline_i18n?: Record<string, string>;
        id?: number;
        text?: string;
        text_i18n?: Record<string, string>;
    }>;
    /**
     * https://www.schema.org/FormSuccessPage
     */
    formSuccessPage?: {
        description?: string;
        description_i18n?: Record<string, string>;
        headline?: string;
        headline_i18n?: Record<string, string>;
        id?: number;
    };
    id?: number;
    name?: string;
    name_i18n?: Record<string, string>;
    siteId?: number;
};

