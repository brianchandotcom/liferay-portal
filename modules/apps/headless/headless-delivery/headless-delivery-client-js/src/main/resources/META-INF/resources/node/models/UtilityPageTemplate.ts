/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a Utility Page Template.
 */
export type UtilityPageTemplate = {
    /**
     * Specifies if the utility page template should be the default for the given type.
     */
    defaultTemplate?: boolean;
    /**
     * The utility page template external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The utility page template name.
     */
    name?: string;
    /**
     * The utility page template type.
     */
    type?: 'Error' | 'ErrorCode404' | 'ErrorCode500' | 'TermsOfUse';
};

