/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a URL to an external site. This is modeled internally as a `WebSite`.
 */
export type WebUrl = {
    /**
     * The URL's ID.
     */
    id?: number;
    /**
     * A flag that identifies whether this is the main web address of the user/organization.
     */
    primary?: boolean;
    /**
     * The absolute URL.
     */
    url?: string;
    /**
     * The URL's type.
     */
    urlType?: string;
};

