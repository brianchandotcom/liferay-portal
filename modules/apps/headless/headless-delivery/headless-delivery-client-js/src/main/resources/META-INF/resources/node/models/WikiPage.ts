/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AggregateRating } from './AggregateRating';
import type { Creator } from './Creator';
import type { CustomField } from './CustomField';
import type { RelatedContent } from './RelatedContent';
import type { TaxonomyCategoryBrief } from './TaxonomyCategoryBrief';
/**
 * Represents a wiki page.
 */
export type WikiPage = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The blog post's average rating.
     */
    readonly aggregateRating?: AggregateRating;
    /**
     * The wiki page's content.
     */
    content?: string;
    /**
     * The wiki page's creator.
     */
    readonly creator?: Creator;
    /**
     * A list of the custom fields associated with the wiki page.
     */
    customFields?: Array<CustomField>;
    /**
     * The date the wiki page was created.
     */
    readonly dateCreated?: string;
    /**
     * The last time any of the wiki page's fields changed.
     */
    readonly dateModified?: string;
    /**
     * The wiki page's description.
     */
    description?: string;
    /**
     * The wiki page's media format (e.g., HTML, BBCode, etc.).
     */
    encodingFormat: string;
    /**
     * The wiki page's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The wiki page's main title.
     */
    headline: string;
    /**
     * The wiki page's ID.
     */
    readonly id?: number;
    /**
     * A list of keywords describing the blog post.
     */
    keywords?: Array<string>;
    /**
     * The wiki page's number attachments.
     */
    readonly numberOfAttachments?: number;
    /**
     * The number of child wiki page on this wiki page.
     */
    readonly numberOfWikiPages?: number;
    /**
     * The ID of the wiki page's parent, if it exists.
     */
    parentWikiPageId?: number;
    /**
     * A list of related contents to this wiki page.
     */
    readonly relatedContents?: Array<RelatedContent>;
    /**
     * The ID of the site to which this wiki page is scoped.
     */
    readonly siteId?: number;
    /**
     * A flag that indicates whether the user making the requests is subscribed to this wiki page.
     */
    readonly subscribed?: boolean;
    /**
     * The categories associated with this wiki page.
     */
    readonly taxonomyCategoryBriefs?: Array<TaxonomyCategoryBrief>;
    /**
     * A write-only field that adds `TaxonomyCategory` instances to the wiki page.
     */
    taxonomyCategoryIds?: Array<number>;
    /**
     * A write-only property that specifies the default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
    /**
     * The ID of the wiki node to which the wiki page belongs.
     */
    wikiNodeId?: number;
};

