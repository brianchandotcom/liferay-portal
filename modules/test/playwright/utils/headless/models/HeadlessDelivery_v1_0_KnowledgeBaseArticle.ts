/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_AggregateRating} from './HeadlessDelivery_v1_0_AggregateRating';
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
import type {HeadlessDelivery_v1_0_CustomField} from './HeadlessDelivery_v1_0_CustomField';
import type {HeadlessDelivery_v1_0_ParentKnowledgeBaseFolder} from './HeadlessDelivery_v1_0_ParentKnowledgeBaseFolder';
import type {HeadlessDelivery_v1_0_RelatedContent} from './HeadlessDelivery_v1_0_RelatedContent';
import type {HeadlessDelivery_v1_0_TaxonomyCategoryBrief} from './HeadlessDelivery_v1_0_TaxonomyCategoryBrief';

/**
 * Represents a Knowledge Base article (`KBArticle`), the main entity in the Knowledge Base API.
 */
export type HeadlessDelivery_v1_0_KnowledgeBaseArticle = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'aggregateRating'?: HeadlessDelivery_v1_0_AggregateRating;

	/**
	 * The article's main content.
	 */
	'articleBody': string;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * A list of the custom fields associated with the article.
	 */
	'customFields'?: Array<HeadlessDelivery_v1_0_CustomField>;

	/**
	 * The date the article was created.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time the article's content or metadata changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The article's scheduled publication date.
	 */
	'datePublished'?: string;

	/**
	 * The article's description.
	 */
	'description'?: string;

	/**
	 * The article's media type (e.g., HTML, BBCode, etc.).
	 */
	readonly 'encodingFormat'?: string;

	/**
	 * The article's external reference code.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The article's relative URL.
	 */
	'friendlyUrlPath'?: string;

	/**
	 * The article's ID.
	 */
	readonly 'id'?: number;

	/**
	 * A list of keywords describing the article.
	 */
	'keywords'?: Array<string>;

	/**
	 * The article's number attachments.
	 */
	readonly 'numberOfAttachments'?: number;

	/**
	 * The number of this article's child articles.
	 */
	readonly 'numberOfKnowledgeBaseArticles'?: number;

	/**
	 * The ID of the article's parent, if it exists.
	 */
	'parentKnowledgeBaseArticleId'?: number;
	'parentKnowledgeBaseFolder'?: HeadlessDelivery_v1_0_ParentKnowledgeBaseFolder;
	'parentKnowledgeBaseFolderId'?: number;

	/**
	 * A list of related contents to this article.
	 */
	readonly 'relatedContents'?: Array<HeadlessDelivery_v1_0_RelatedContent>;

	/**
	 * The ID of the site to which this article is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * A flag that indicates whether the user making the requests is subscribed to this article.
	 */
	readonly 'subscribed'?: boolean;

	/**
	 * The categories associated with this article.
	 */
	readonly 'taxonomyCategoryBriefs'?: Array<HeadlessDelivery_v1_0_TaxonomyCategoryBrief>;
	'taxonomyCategoryIds'?: Array<number>;

	/**
	 * The article's main title.
	 */
	'title': string;
	'viewableBy'?: 'Anyone' | 'Members' | 'Owner';
	readonly 'x-class-name'?: string;
};
