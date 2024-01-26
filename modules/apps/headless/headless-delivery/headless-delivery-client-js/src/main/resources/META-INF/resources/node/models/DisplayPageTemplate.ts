/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a Display Page template.
 */
export type DisplayPageTemplate = {
    /**
     * The display page template's content subtype.
     */
    contentSubtype?: {
        /**
         * The content subtype's ID.
         */
        subtypeId?: number;
        /**
         * The content subtype's Key.
         */
        subtypeKey?: string;
    };
    /**
     * The type of content.
     */
    contentType?: {
        /**
         * The content type's class name.
         */
        className?: string;
    };
    /**
     * Specifies if the page template should be the default for the given content type/subtype.
     */
    defaultTemplate?: boolean;
    /**
     * The display page template's key.
     */
    key?: string;
    /**
     * The display page template's name.
     */
    name?: string;
};

