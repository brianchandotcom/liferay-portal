/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomMetaTag } from './CustomMetaTag';
import type { OpenGraphSettings } from './OpenGraphSettings';
import type { SEOSettings } from './SEOSettings';
import type { SitePageNavigationMenuSettings } from './SitePageNavigationMenuSettings';
/**
 * Represents the settings of a Page.
 */
export type PageSettings = {
    /**
     * A list of custom metatags this page has.
     */
    customMetaTags?: Array<CustomMetaTag>;
    /**
     * A flag that indicates whether the page is hidden from navigation.
     */
    hiddenFromNavigation?: boolean;
    /**
     * The page's Open Graph settings.
     */
    openGraphSettings?: OpenGraphSettings;
    /**
     * The page's SEO settings.
     */
    seoSettings?: SEOSettings;
    /**
     * The page's site navigation menu settings.
     */
    sitePageNavigationMenuSettings?: SitePageNavigationMenuSettings;
};

