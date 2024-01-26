/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AggregateRating } from './AggregateRating';
import type { Creator } from './Creator';
import type { CustomField } from './CustomField';
import type { Experience } from './Experience';
import type { PageDefinition } from './PageDefinition';
import type { PageSettings } from './PageSettings';
import type { RenderedPage } from './RenderedPage';
import type { TaxonomyCategoryBrief } from './TaxonomyCategoryBrief';
/**
 * Represents a site page. This is modeled internally as a `Layout`.
 */
export type SitePage = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The page's average rating.
     */
    readonly aggregateRating?: AggregateRating;
    /**
     * The list of languages the page has a translation for.
     */
    readonly availableLanguages?: Array<string>;
    /**
     * The page's creator.
     */
    readonly creator?: Creator;
    /**
     * Custom fields associated with the page.
     */
    customFields?: Array<CustomField>;
    /**
     * The page's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The last time any field of the page was changed.
     */
    readonly dateModified?: string;
    /**
     * The page's most recent publication date.
     */
    datePublished?: string;
    /**
     * Experience of the page that it's being retrieved.
     */
    experience?: Experience;
    /**
     * A relative URL to the page's rendered content.
     */
    friendlyUrlPath?: string;
    /**
     * The localized relative URLs to the page's rendered content.
     */
    friendlyUrlPath_i18n?: Record<string, string>;
    /**
     * The page ID.
     */
    readonly id?: number;
    /**
     * A list of keywords describing the page.
     */
    keywords?: Array<string>;
    /**
     * Optional field with the structure of all the elements of the page. Can be embedded with nestedFields when retrieving the collection of site pages. When retrieving a single site page, it will automatically be included.
     */
    pageDefinition?: PageDefinition;
    /**
     * The page's permissions.
     */
    pagePermissions?: Array<{
        /**
         * The keys of the actions the role has permission for.
         */
        actionKeys?: Array<string>;
        /**
         * The role's key.
         */
        roleKey?: string;
    }>;
    /**
     * Settings of the page, such as SEO or OpenGraph.
     */
    pageSettings?: PageSettings;
    /**
     * The type of the page.
     */
    pageType?: string;
    /**
     * The parent page or null if it is a top level page.
     */
    parentSitePage?: {
        /**
         * The relative URL of the parent page.
         */
        friendlyUrlPath?: string;
    };
    /**
     * Metadata of the page such as it's master page and template.
     */
    renderedPage?: RenderedPage;
    /**
     * The ID of the site to which this page is scoped.
     */
    readonly siteId?: number;
    /**
     * The categories associated with this page.
     */
    taxonomyCategoryBriefs?: Array<TaxonomyCategoryBrief>;
    /**
     * A write-only field that adds `TaxonomyCategory` instances to the page. Deprecated as of Cavanaugh (7.4.x), replaced by `taxonomyCategoryBriefs.taxonomyCategoryReference`
     * @deprecated
     */
    taxonomyCategoryIds?: Array<number>;
    /**
     * The page's title.
     */
    title: string;
    /**
     * The localized page's titles.
     */
    title_i18n?: Record<string, string>;
    /**
     * A valid external identifier to reference this page.
     */
    readonly uuid?: string;
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

