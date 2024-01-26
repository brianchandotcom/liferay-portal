/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
export type AccountGroup = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    customFields?: Array<CustomField>;
    description?: string;
    externalReferenceCode?: string;
    readonly id?: number;
    name: string;
};

