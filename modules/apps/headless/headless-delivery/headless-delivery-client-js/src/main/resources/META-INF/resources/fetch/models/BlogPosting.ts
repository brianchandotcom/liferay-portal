/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AggregateRating } from './AggregateRating';
import type { Creator } from './Creator';
import type { CustomField } from './CustomField';
import type { RelatedContent } from './RelatedContent';
import type { RenderedContent } from './RenderedContent';
import type { TaxonomyCategoryBrief } from './TaxonomyCategoryBrief';
/**
 * Represents a blog post. See [BlogPosting](https://www.schema.org/BlogPosting) for more information.
 */
export type BlogPosting = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The blog post's average rating.
     */
    readonly aggregateRating?: AggregateRating;
    /**
     * The blog post's subtitle.
     */
    alternativeHeadline?: string;
    /**
     * The blog post's body (content).
     */
    articleBody: string;
    /**
     * The blog post's author.
     */
    readonly creator?: Creator;
    /**
     * A list of the custom fields associated with the blog post.
     */
    customFields?: Array<CustomField>;
    /**
     * The blog post's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The blog post's most recent modification date.
     */
    readonly dateModified?: string;
    /**
     * The blog post's publication date.
     */
    datePublished?: string;
    /**
     * The blog post's description.
     */
    description?: string;
    /**
     * The blog post's media format (e.g., HTML, BBCode, etc.).
     */
    readonly encodingFormat?: string;
    /**
     * The blog post's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The blog post's relative URL.
     */
    friendlyUrlPath?: string;
    /**
     * The blog post's main title.
     */
    headline: string;
    /**
     * The blog post's identifier.
     */
    readonly id?: number;
    /**
     * The blog post's cover image.
     */
    image?: {
        /**
         * The text describing the image.
         */
        caption?: string;
        /**
         * The image's relative URL.
         */
        readonly contentUrl?: string;
        /**
         * optional field with the content of the image in Base64, can be embedded with nestedFields
         */
        readonly contentValue?: string;
        /**
         * The image's ID. This can be used to retrieve more information in the `Document` API.
         */
        imageId?: number;
    };
    /**
     * A list of keywords describing the blog post.
     */
    keywords?: Array<string>;
    /**
     * The number of comments this blog post has received.
     */
    readonly numberOfComments?: number;
    /**
     * A list of related contents to this blog post.
     */
    relatedContents?: Array<RelatedContent>;
    /**
     * A list of rendered blogs posts, which results from using a display page to process the blogs post and return HTML.
     */
    readonly renderedContents?: Array<RenderedContent>;
    /**
     * The ID of the site to which this blog post is scoped.
     */
    readonly siteId?: number;
    /**
     * The categories associated with this blog post.
     */
    readonly taxonomyCategoryBriefs?: Array<TaxonomyCategoryBrief>;
    /**
     * A write-only field that adds `TaxonomyCategory` instances to the blog post.
     */
    taxonomyCategoryIds?: Array<number>;
    /**
     * A write-only property that specifies the default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

