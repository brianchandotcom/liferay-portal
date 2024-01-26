/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FormDocument } from './FormDocument';
/**
 * https://www.schema.org/FormFieldValue
 */
export type FormFieldValue = Array<{
    readonly formDocument?: FormDocument;
    formDocumentId?: number;
    readonly id?: number;
    name?: string;
    value?: string;
}>;
