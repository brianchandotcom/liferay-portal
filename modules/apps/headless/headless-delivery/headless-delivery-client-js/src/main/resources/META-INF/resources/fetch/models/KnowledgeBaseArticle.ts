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
 * Represents a Knowledge Base article (`KBArticle`), the main entity in the Knowledge Base API.
 */
export type KnowledgeBaseArticle = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The article's average rating.
     */
    readonly aggregateRating?: AggregateRating;
    /**
     * The article's main content.
     */
    articleBody: string;
    /**
     * The article's author.
     */
    readonly creator?: Creator;
    /**
     * A list of the custom fields associated with the article.
     */
    customFields?: Array<CustomField>;
    /**
     * The date the article was created.
     */
    readonly dateCreated?: string;
    /**
     * The last time the article's content or metadata changed.
     */
    readonly dateModified?: string;
    /**
     * The article's scheduled publication date.
     */
    datePublished?: string;
    /**
     * The article's description.
     */
    description?: string;
    /**
     * The article's media type (e.g., HTML, BBCode, etc.).
     */
    readonly encodingFormat?: string;
    /**
     * The article's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The article's relative URL.
     */
    friendlyUrlPath?: string;
    /**
     * The article's ID.
     */
    readonly id?: number;
    /**
     * A list of keywords describing the article.
     */
    keywords?: Array<string>;
    /**
     * The article's number attachments.
     */
    readonly numberOfAttachments?: number;
    /**
     * The number of this article's child articles.
     */
    readonly numberOfKnowledgeBaseArticles?: number;
    /**
     * The ID of the article's parent, if it exists.
     */
    parentKnowledgeBaseArticleId?: number;
    /**
     * The article's parent folder, if it exists.
     */
    readonly parentKnowledgeBaseFolder?: {
        /**
         * The folder's parent ID.
         */
        folderId?: number;
        /**
         * The folder's parent name.
         */
        folderName?: string;
    };
    /**
     * The ID of the article's parent folder, if that folder exists.
     */
    parentKnowledgeBaseFolderId?: number;
    /**
     * A list of related contents to this article.
     */
    readonly relatedContents?: Array<RelatedContent>;
    /**
     * The ID of the site to which this article is scoped.
     */
    readonly siteId?: number;
    /**
     * A flag that indicates whether the user making the requests is subscribed to this article.
     */
    readonly subscribed?: boolean;
    /**
     * The categories associated with this article.
     */
    readonly taxonomyCategoryBriefs?: Array<TaxonomyCategoryBrief>;
    /**
     * A write-only field that adds `TaxonomyCategory` instances to the article.
     */
    taxonomyCategoryIds?: Array<number>;
    /**
     * The article's main title.
     */
    title: string;
    /**
     * A write-only property that specifies the article's default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

