/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_AggregateRating} from './HeadlessAdminContent_v1_0_AggregateRating';
import type {HeadlessAdminContent_v1_0_ContentField} from './HeadlessAdminContent_v1_0_ContentField';
import type {HeadlessAdminContent_v1_0_Creator} from './HeadlessAdminContent_v1_0_Creator';
import type {HeadlessAdminContent_v1_0_CustomField} from './HeadlessAdminContent_v1_0_CustomField';
import type {HeadlessAdminContent_v1_0_Permission} from './HeadlessAdminContent_v1_0_Permission';
import type {HeadlessAdminContent_v1_0_RelatedContent} from './HeadlessAdminContent_v1_0_RelatedContent';
import type {HeadlessAdminContent_v1_0_RenderedContent} from './HeadlessAdminContent_v1_0_RenderedContent';
import type {HeadlessAdminContent_v1_0_TaxonomyCategoryBrief} from './HeadlessAdminContent_v1_0_TaxonomyCategoryBrief';

/**
 * Represents content that has fields and is rendered by a template backed by a `ContentStructure`. This is modeled internally as a `JournalArticle`.
 */
export type HeadlessAdminContent_v1_0_StructuredContent = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'aggregateRating'?: HeadlessAdminContent_v1_0_AggregateRating;

	/**
	 * The key of the asset library to which the structure content is scoped.
	 */
	readonly 'assetLibraryKey'?: string;

	/**
	 * The list of languages the structured content has a translation for.
	 */
	readonly 'availableLanguages'?: Array<string>;

	/**
	 * The list of fields that store the structured content's information.
	 */
	'contentFields'?: Array<HeadlessAdminContent_v1_0_ContentField>;

	/**
	 * The ID of the `ContentStructure`.
	 */
	'contentStructureId': number;
	'creator'?: HeadlessAdminContent_v1_0_Creator;

	/**
	 * A list of the custom fields associated with the structured content.
	 */
	'customFields'?: Array<HeadlessAdminContent_v1_0_CustomField>;

	/**
	 * The structured content's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any field of the structured content was changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The structured content's most recent publication date.
	 */
	'datePublished'?: string;

	/**
	 * The structured content's description.
	 */
	'description'?: string;

	/**
	 * The localized structured content's descriptions.
	 */
	'description_i18n'?: Record<string, string>;

	/**
	 * The structured content's external reference code.
	 */
	'externalReferenceCode'?: string;

	/**
	 * A relative URL to the structured content's rendered content.
	 */
	'friendlyUrlPath'?: string;

	/**
	 * The localized relative URLs to the structured content's rendered content.
	 */
	'friendlyUrlPath_i18n'?: Record<string, string>;

	/**
	 * The structured content's ID.
	 */
	readonly 'id'?: number;

	/**
	 * An identifier, independent of the database, that can be used to reference the structured content.
	 */
	readonly 'key'?: string;

	/**
	 * A list of keywords describing the structured content.
	 */
	'keywords'?: Array<string>;

	/**
	 * The number of comments the structured content has received.
	 */
	readonly 'numberOfComments'?: number;
	'permissions'?: Array<HeadlessAdminContent_v1_0_Permission>;

	/**
	 * The structured content's priority.
	 */
	'priority'?: number;

	/**
	 * A list of related contents to this structured content.
	 */
	'relatedContents'?: Array<HeadlessAdminContent_v1_0_RelatedContent>;

	/**
	 * A list of rendered content, which results from using a template to process the content and return HTML.
	 */
	readonly 'renderedContents'?: Array<HeadlessAdminContent_v1_0_RenderedContent>;

	/**
	 * The ID of the site to which this structured content is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * The ID of the folder where structured content is stored.
	 */
	'structuredContentFolderId'?: number;

	/**
	 * A flag that indicates whether the user making the requests is subscribed to this structured content.
	 */
	readonly 'subscribed'?: boolean;

	/**
	 * The categories associated with this structured content.
	 */
	readonly 'taxonomyCategoryBriefs'?: Array<HeadlessAdminContent_v1_0_TaxonomyCategoryBrief>;
	'taxonomyCategoryIds'?: Array<number>;

	/**
	 * The structured content's main title.
	 */
	'title': string;

	/**
	 * The localized structured content's main titles.
	 */
	'title_i18n'?: Record<string, string>;

	/**
	 * A valid external identifier to reference this structured content.
	 */
	readonly 'uuid'?: string;
	'viewableBy'?: 'Anyone' | 'Members' | 'Owner';
	readonly 'x-class-name'?: string;
};
