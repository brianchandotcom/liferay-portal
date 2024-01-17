/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_AggregateRating} from './HeadlessDelivery_v1_0_AggregateRating';
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
import type {HeadlessDelivery_v1_0_CustomField} from './HeadlessDelivery_v1_0_CustomField';
import type {HeadlessDelivery_v1_0_RelatedContent} from './HeadlessDelivery_v1_0_RelatedContent';
import type {HeadlessDelivery_v1_0_TaxonomyCategoryBrief} from './HeadlessDelivery_v1_0_TaxonomyCategoryBrief';

/**
 * Represents a wiki page.
 */
export type HeadlessDelivery_v1_0_WikiPage = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'aggregateRating'?: HeadlessDelivery_v1_0_AggregateRating;

	/**
	 * The wiki page's content.
	 */
	'content'?: string;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * A list of the custom fields associated with the wiki page.
	 */
	'customFields'?: Array<HeadlessDelivery_v1_0_CustomField>;

	/**
	 * The date the wiki page was created.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any of the wiki page's fields changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The wiki page's description.
	 */
	'description'?: string;

	/**
	 * The wiki page's media format (e.g., HTML, BBCode, etc.).
	 */
	'encodingFormat': string;

	/**
	 * The wiki page's external reference code.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The wiki page's main title.
	 */
	'headline': string;

	/**
	 * The wiki page's ID.
	 */
	readonly 'id'?: number;

	/**
	 * A list of keywords describing the blog post.
	 */
	'keywords'?: Array<string>;

	/**
	 * The wiki page's number attachments.
	 */
	readonly 'numberOfAttachments'?: number;

	/**
	 * The number of child wiki page on this wiki page.
	 */
	readonly 'numberOfWikiPages'?: number;

	/**
	 * The ID of the wiki page's parent, if it exists.
	 */
	'parentWikiPageId'?: number;

	/**
	 * A list of related contents to this wiki page.
	 */
	readonly 'relatedContents'?: Array<HeadlessDelivery_v1_0_RelatedContent>;

	/**
	 * The ID of the site to which this wiki page is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * A flag that indicates whether the user making the requests is subscribed to this wiki page.
	 */
	readonly 'subscribed'?: boolean;

	/**
	 * The categories associated with this wiki page.
	 */
	readonly 'taxonomyCategoryBriefs'?: Array<HeadlessDelivery_v1_0_TaxonomyCategoryBrief>;
	'taxonomyCategoryIds'?: Array<number>;
	'viewableBy'?: HeadlessDelivery_v1_0_WikiPage.viewableBy;

	/**
	 * The ID of the wiki node to which the wiki page belongs.
	 */
	'wikiNodeId'?: number;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessDelivery_v1_0_WikiPage {
	export enum viewableBy {
		ANYONE = 'Anyone',
		MEMBERS = 'Members',
		OWNER = 'Owner',
	}
}
