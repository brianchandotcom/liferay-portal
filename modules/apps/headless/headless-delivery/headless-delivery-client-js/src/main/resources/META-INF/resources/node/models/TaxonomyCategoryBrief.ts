/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type TaxonomyCategoryBrief = {
    /**
     * Optional field with the embedded taxonomy category, can be embedded with nestedFields
     */
    readonly embeddedTaxonomyCategory?: Record<string, any>;
    /**
     * The category's ID. This can be used to retrieve more information in the `TaxonomyCategory` API.
     */
    readonly taxonomyCategoryId?: number;
    /**
     * The category's name.
     */
    readonly taxonomyCategoryName?: string;
    /**
     * The localized category's names.
     */
    readonly taxonomyCategoryName_i18n?: Record<string, string>;
    /**
     * A unique reference to a taxonomy category.
     */
    taxonomyCategoryReference?: {
        /**
         * The taxonomy category's external reference code.
         */
        externalReferenceCode: string;
        /**
         * The key of the site or asset library where the taxonomy category is located. It can be left out if the taxonomy category is in the same site as the page.
         */
        siteKey?: string;
    };
};

