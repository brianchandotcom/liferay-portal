/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FormFieldOption } from './FormFieldOption';
/**
 * https://www.schema.org/FormField
 */
export type FormField = Array<{
    autocomplete?: boolean;
    dataSourceType?: string;
    dataType?: string;
    displayStyle?: string;
    formFieldOptions?: Array<FormFieldOption>;
    grid?: {
        columns?: Array<FormFieldOption>;
        id?: number;
        rows?: Array<FormFieldOption>;
    };
    hasFormRules?: boolean;
    id?: number;
    immutable?: boolean;
    inline?: boolean;
    inputControl?: string;
    label?: string;
    label_i18n?: Record<string, string>;
    localizable?: boolean;
    multiple?: boolean;
    name?: string;
    placeholder?: string;
    predefinedValue?: string;
    predefinedValue_i18n?: Record<string, string>;
    readOnly?: boolean;
    repeatable?: boolean;
    required?: boolean;
    showAsSwitcher?: boolean;
    showLabel?: boolean;
    style?: string;
    text?: string;
    text_i18n?: Record<string, string>;
    tooltip?: string;
    /**
     * https://www.schema.org/FormFieldValidation
     */
    validation?: {
        errorMessage?: string;
        errorMessage_i18n?: Record<string, string>;
        expression?: string;
        id?: number;
    };
}>;
