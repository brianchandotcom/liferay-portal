/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents each field in a content structure, backed by a content field.
 */
export type ContentStructureField = {
    /**
     * The form field's type (e.g., date, geolocation, text, etc.).
     */
    readonly dataType?: string;
    /**
     * The form field's input control type (e.g., text, textarea, select field, etc.).
     */
    readonly inputControl?: string;
    /**
     * The form field's label.
     */
    readonly label?: string;
    /**
     * The form field's labels.
     */
    readonly label_i18n?: Record<string, string>;
    /**
     * A flag that indicates whether the content is accessible in different languages.
     */
    readonly localizable?: boolean;
    /**
     * A flag that indicates whether the form field can have several values.
     */
    readonly multiple?: boolean;
    /**
     * The form field's name.
     */
    readonly name?: string;
    /**
     * The child content structure fields that depend on this form field.
     */
    readonly nestedContentStructureFields?: Array<ContentStructureField>;
    /**
     * The list of different possible values.
     */
    readonly options?: Array<{
        /**
         * The option's label.
         */
        label?: string;
        /**
         * The localized option's labels.
         */
        label_i18n?: Record<string, string>;
        /**
         * The internal value of the field option.
         */
        value?: string;
    }>;
    /**
     * The form field's default value.
     */
    readonly predefinedValue?: string;
    /**
     * The localized form field's default values.
     */
    readonly predefinedValue_i18n?: Record<string, string>;
    /**
     * A flag that indicates whether this content can be rendered (and answered) several times.
     */
    readonly repeatable?: boolean;
    /**
     * A flag that indicates whether this form field is required.
     */
    readonly required?: boolean;
    /**
     * A flag that indicates whether the structure's end target should render the field label.
     */
    readonly showLabel?: boolean;
};

