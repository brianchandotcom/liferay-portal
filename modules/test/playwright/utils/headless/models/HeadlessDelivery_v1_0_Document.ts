/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_AdaptedImage} from './HeadlessDelivery_v1_0_AdaptedImage';
import type {HeadlessDelivery_v1_0_AggregateRating} from './HeadlessDelivery_v1_0_AggregateRating';
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
import type {HeadlessDelivery_v1_0_CustomField} from './HeadlessDelivery_v1_0_CustomField';
import type {HeadlessDelivery_v1_0_DocumentType} from './HeadlessDelivery_v1_0_DocumentType';
import type {HeadlessDelivery_v1_0_RelatedContent} from './HeadlessDelivery_v1_0_RelatedContent';
import type {HeadlessDelivery_v1_0_RenderedContent} from './HeadlessDelivery_v1_0_RenderedContent';
import type {HeadlessDelivery_v1_0_TaxonomyCategoryBrief} from './HeadlessDelivery_v1_0_TaxonomyCategoryBrief';
export type HeadlessDelivery_v1_0_Document = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * An array of images in several resolutions and sizes, created by the Adaptive Media framework.
	 */
	readonly 'adaptedImages'?: Array<HeadlessDelivery_v1_0_AdaptedImage>;
	'aggregateRating'?: HeadlessDelivery_v1_0_AggregateRating;

	/**
	 * The key of the asset library to which the document is scoped.
	 */
	readonly 'assetLibraryKey'?: string;

	/**
	 * The document's relative URL.
	 */
	readonly 'contentUrl'?: string;

	/**
	 * The optional field with the content of the document in Base64, can be embedded with nestedFields.
	 */
	readonly 'contentValue'?: string;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * A list of the custom fields associated with the document.
	 */
	'customFields'?: Array<HeadlessDelivery_v1_0_CustomField>;

	/**
	 * The document's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time a field of the document changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The document's description.
	 */
	'description'?: string;

	/**
	 * The ID of the `DocumentFolder` where this document is stored.
	 */
	'documentFolderId'?: number;
	'documentType'?: HeadlessDelivery_v1_0_DocumentType;

	/**
	 * The document's content type (e.g., `application/pdf`, etc.).
	 */
	readonly 'encodingFormat'?: string;

	/**
	 * The document's external reference code.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The document's file extension.
	 */
	readonly 'fileExtension'?: string;

	/**
	 * The document's file name.
	 */
	'fileName'?: string;

	/**
	 * The document's ID.
	 */
	readonly 'id'?: number;

	/**
	 * A list of keywords describing the document.
	 */
	'keywords'?: Array<string>;

	/**
	 * The number of comments on the document.
	 */
	readonly 'numberOfComments'?: number;

	/**
	 * A list of related contents to this document.
	 */
	readonly 'relatedContents'?: Array<HeadlessDelivery_v1_0_RelatedContent>;

	/**
	 * A list of rendered documents, which results from using a display page to process the document and return HTML.
	 */
	readonly 'renderedContents'?: Array<HeadlessDelivery_v1_0_RenderedContent>;

	/**
	 * The ID of the site to which this document is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * The document's size in bytes.
	 */
	readonly 'sizeInBytes'?: number;

	/**
	 * The categories associated with this document.
	 */
	readonly 'taxonomyCategoryBriefs'?: Array<HeadlessDelivery_v1_0_TaxonomyCategoryBrief>;
	'taxonomyCategoryIds'?: Array<number>;

	/**
	 * The document's main title/name.
	 */
	'title'?: string;
	'viewableBy'?: 'Anyone' | 'Members' | 'Owner';
	readonly 'x-class-name'?: string;
};
