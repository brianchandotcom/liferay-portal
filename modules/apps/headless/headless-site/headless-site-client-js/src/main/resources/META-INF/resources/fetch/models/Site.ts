/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents the site being created.
 */
export type Site = {
    /**
     * The site's external reference code.
     */
    externalReferenceCode?: string;
    readonly friendlyUrlPath?: string;
    readonly id?: number;
    readonly key?: string;
    /**
     * The default value is open.
     */
    membershipType?: 'open' | 'private' | 'restricted';
    name: string;
    parentSiteKey?: string;
    templateKey?: string;
    templateType?: 'site-initializer' | 'site-template';
};

