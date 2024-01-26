/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents settings related with SEO.
 */
export type SEOSettings = {
    /**
     * The canonical URL of the page, if it exists.
     */
    customCanonicalURL?: string;
    /**
     * The localized canonical URL of the page, if it exists.
     */
    customCanonicalURL_i18n?: Record<string, string>;
    /**
     * The page's description to be used as summary for search engines.
     */
    description?: string;
    /**
     * The localized descriptions of the page to be used as summary for search engines.
     */
    description_i18n?: Record<string, string>;
    /**
     * The page's main title to be used by search engines.
     */
    htmlTitle?: string;
    /**
     * The localized main titles of the page to be used by search engines.
     */
    htmlTitle_i18n?: Record<string, string>;
    /**
     * A tag telling search engines if and how they should crawl the page.
     */
    robots?: string;
    /**
     * A localized tag telling search engines if and how they should crawl the page.
     */
    robots_i18n?: Record<string, string>;
    /**
     * A list of target keywords of the page to be used by search engines.
     */
    seoKeywords?: string;
    /**
     * A list of localized target keywords of the page to be used by search engines.
     */
    seoKeywords_i18n?: Record<string, string>;
    /**
     * Represents settings related with the site map.
     */
    siteMapSettings?: {
        /**
         * Indicates how often a page is updated.
         */
        changeFrequency?: 'Always' | 'Hourly' | 'Daily' | 'Weekly' | 'Monthly' | 'Yearly' | 'Never';
        /**
         * Whether search engines should crawl and index the page.
         */
        include?: boolean;
        /**
         * How the page should be prioritized relative to other pages.
         */
        pagePriority?: number;
    };
};

