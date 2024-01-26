/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Describes a specific language in the platform.
 */
export type Language = {
    /**
     * The language's contry name.
     */
    countryName?: string;
    /**
     * The localized language's country name values.
     */
    countryName_i18n?: Record<string, string>;
    /**
     * The language's ID.
     */
    id?: string;
    /**
     * A flag that indicates whether the language has benn marked as default language.
     */
    markedAsDefault?: boolean;
    /**
     * The language's name.
     */
    name?: string;
    /**
     * The localized language's name values.
     */
    name_i18n?: Record<string, string>;
};

