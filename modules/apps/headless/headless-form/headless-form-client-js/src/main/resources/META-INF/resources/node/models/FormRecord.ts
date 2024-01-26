/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { FormFieldValue } from './FormFieldValue';
/**
 * https://www.schema.org/FormRecord
 */
export type FormRecord = {
    creator?: Creator;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    readonly datePublished?: string;
    draft?: boolean;
    formFieldValues?: Array<FormFieldValue>;
    formId?: number;
    readonly id?: number;
};

