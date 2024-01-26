/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AggregateRating } from './AggregateRating';
import type { ContentField } from './ContentField';
import type { Creator } from './Creator';
import type { CustomField } from './CustomField';
import type { RelatedContent } from './RelatedContent';
import type { RenderedContent } from './RenderedContent';
import type { TaxonomyCategoryBrief } from './TaxonomyCategoryBrief';
/**
 * Represents a Document Library file, storing binary and metadata information. Properties follow the [MediaObject](https://schema.org/MediaObject) specification.
 */
export type Document = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * An array of images in several resolutions and sizes, created by the Adaptive Media framework.
     */
    readonly adaptedImages?: Array<{
        /**
         * The image's relative URL.
         */
        contentUrl?: string;
        /**
         * Optional field with the content of the image in Base64, can be embedded with nestedFields.
         */
        readonly contentValue?: string;
        /**
         * The image's height in pixels.
         */
        height?: number;
        /**
         * The name of the image's Adaptive Media image resolution.
         */
        resolutionName?: string;
        /**
         * The image's size in bytes.
         */
        sizeInBytes?: number;
        /**
         * The image's width in pixels.
         */
        width?: number;
    }>;
    /**
     * The document's average rating.
     */
    readonly aggregateRating?: AggregateRating;
    /**
     * The key of the asset library to which the document is scoped.
     */
    readonly assetLibraryKey?: string;
    /**
     * The document's relative URL.
     */
    readonly contentUrl?: Blob;
    /**
     * The optional field with the content of the document in Base64, can be embedded with nestedFields.
     */
    readonly contentValue?: string;
    /**
     * The document's creator.
     */
    readonly creator?: Creator;
    /**
     * A list of the custom fields associated with the document.
     */
    customFields?: Array<CustomField>;
    /**
     * The document's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The last time a field of the document changed.
     */
    readonly dateModified?: string;
    /**
     * The document's description.
     */
    description?: string;
    /**
     * The ID of the `DocumentFolder` where this document is stored.
     */
    documentFolderId?: number;
    documentType?: {
        /**
         * The list of languages the document type has a translation for.
         */
        readonly availableLanguages?: Array<string>;
        /**
         * The list of content fields the document type has.
         */
        contentFields?: Array<ContentField>;
        /**
         * The document type's description.
         */
        description?: string;
        /**
         * The localized document type's description.
         */
        readonly description_i18n?: Record<string, string>;
        /**
         * The document type's name.
         */
        name?: string;
        /**
         * The localized document type's name.
         */
        readonly name_i18n?: Record<string, string>;
    };
    /**
     * The document's content type (e.g., `application/pdf`, etc.).
     */
    readonly encodingFormat?: string;
    /**
     * The document's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The document's file extension.
     */
    readonly fileExtension?: string;
    /**
     * The document's file name.
     */
    fileName?: string;
    /**
     * The document's ID.
     */
    readonly id?: number;
    /**
     * A list of keywords describing the document.
     */
    keywords?: Array<string>;
    /**
     * The number of comments on the document.
     */
    readonly numberOfComments?: number;
    /**
     * A list of related contents to this document.
     */
    readonly relatedContents?: Array<RelatedContent>;
    /**
     * A list of rendered documents, which results from using a display page to process the document and return HTML.
     */
    readonly renderedContents?: Array<RenderedContent>;
    /**
     * The ID of the site to which this document is scoped.
     */
    readonly siteId?: number;
    /**
     * The document's size in bytes.
     */
    readonly sizeInBytes?: number;
    /**
     * The categories associated with this document.
     */
    readonly taxonomyCategoryBriefs?: Array<TaxonomyCategoryBrief>;
    /**
     * A write-only field that adds `TaxonomyCategory` instances to the document.
     */
    taxonomyCategoryIds?: Array<number>;
    /**
     * The document's main title/name.
     */
    title?: string;
    /**
     * A write-only property that specifies the document's default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

