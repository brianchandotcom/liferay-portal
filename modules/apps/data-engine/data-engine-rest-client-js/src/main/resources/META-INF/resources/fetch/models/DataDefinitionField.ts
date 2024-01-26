/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents the value of each field in data definition.
 */
export type DataDefinitionField = {
    customProperties?: Record<string, Record<string, any>>;
    defaultValue?: Record<string, Record<string, any>>;
    fieldType?: string;
    id?: number;
    indexType?: 'all' | 'keyword' | 'none' | 'text';
    indexable?: boolean;
    label?: Record<string, Record<string, any>>;
    localizable?: boolean;
    name?: string;
    /**
     * A list of child data definition fields that depend on this resource.
     */
    nestedDataDefinitionFields?: Array<DataDefinitionField>;
    readOnly?: boolean;
    repeatable?: boolean;
    required?: boolean;
    showLabel?: boolean;
    tip?: Record<string, Record<string, any>>;
    visible?: boolean;
};

