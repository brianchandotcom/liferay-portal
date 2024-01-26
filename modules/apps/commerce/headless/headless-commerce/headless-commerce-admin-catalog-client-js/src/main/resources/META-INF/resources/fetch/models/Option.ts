/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
import type { OptionValue } from './OptionValue';
export type Option = {
    readonly actions?: Record<string, Record<string, string>>;
    catalogId?: number;
    customFields?: Array<CustomField>;
    description?: Record<string, string>;
    externalReferenceCode?: string;
    facetable?: boolean;
    fieldType: 'checkbox' | 'checkbox_multiple' | 'date' | 'numeric' | 'radio' | 'select' | 'text';
    readonly id?: number;
    key: string;
    name: Record<string, string>;
    optionValues?: Array<OptionValue>;
    priority?: number;
    required?: boolean;
    skuContributor?: boolean;
};

