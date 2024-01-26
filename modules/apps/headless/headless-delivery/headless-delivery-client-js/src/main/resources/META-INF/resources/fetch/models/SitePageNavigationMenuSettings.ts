/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents settings related with the site navigation menu of a page.
 */
export type SitePageNavigationMenuSettings = {
    /**
     * The default parameter for a page.
     */
    queryString?: string;
    /**
     * The page's description to be used as summary for search engines.
     */
    target?: string;
    /**
     * The target's type (specific frame or new tab).
     */
    targetType?: 'SpecificFrame' | 'NewTab';
};

