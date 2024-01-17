/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_AggregateRating} from './HeadlessDelivery_v1_0_AggregateRating';
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
import type {HeadlessDelivery_v1_0_CustomField} from './HeadlessDelivery_v1_0_CustomField';
import type {HeadlessDelivery_v1_0_Image} from './HeadlessDelivery_v1_0_Image';
import type {HeadlessDelivery_v1_0_RelatedContent} from './HeadlessDelivery_v1_0_RelatedContent';
import type {HeadlessDelivery_v1_0_RenderedContent} from './HeadlessDelivery_v1_0_RenderedContent';
import type {HeadlessDelivery_v1_0_TaxonomyCategoryBrief} from './HeadlessDelivery_v1_0_TaxonomyCategoryBrief';

/**
 * Represents a blog post. See [BlogPosting](https://www.schema.org/BlogPosting) for more information.
 */
export type HeadlessDelivery_v1_0_BlogPosting = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'aggregateRating'?: HeadlessDelivery_v1_0_AggregateRating;

	/**
	 * The blog post's subtitle.
	 */
	'alternativeHeadline'?: string;

	/**
	 * The blog post's body (content).
	 */
	'articleBody': string;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * A list of the custom fields associated with the blog post.
	 */
	'customFields'?: Array<HeadlessDelivery_v1_0_CustomField>;

	/**
	 * The blog post's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The blog post's most recent modification date.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The blog post's publication date.
	 */
	'datePublished'?: string;

	/**
	 * The blog post's description.
	 */
	'description'?: string;

	/**
	 * The blog post's media format (e.g., HTML, BBCode, etc.).
	 */
	readonly 'encodingFormat'?: string;

	/**
	 * The blog post's external reference code.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The blog post's relative URL.
	 */
	'friendlyUrlPath'?: string;

	/**
	 * The blog post's main title.
	 */
	'headline': string;

	/**
	 * The blog post's identifier.
	 */
	readonly 'id'?: number;
	'image'?: HeadlessDelivery_v1_0_Image;

	/**
	 * A list of keywords describing the blog post.
	 */
	'keywords'?: Array<string>;

	/**
	 * The number of comments this blog post has received.
	 */
	readonly 'numberOfComments'?: number;

	/**
	 * A list of related contents to this blog post.
	 */
	'relatedContents'?: Array<HeadlessDelivery_v1_0_RelatedContent>;

	/**
	 * A list of rendered blogs posts, which results from using a display page to process the blogs post and return HTML.
	 */
	readonly 'renderedContents'?: Array<HeadlessDelivery_v1_0_RenderedContent>;

	/**
	 * The ID of the site to which this blog post is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * The categories associated with this blog post.
	 */
	readonly 'taxonomyCategoryBriefs'?: Array<HeadlessDelivery_v1_0_TaxonomyCategoryBrief>;
	'taxonomyCategoryIds'?: Array<number>;
	'viewableBy'?: HeadlessDelivery_v1_0_BlogPosting.viewableBy;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessDelivery_v1_0_BlogPosting {
	export enum viewableBy {
		ANYONE = 'Anyone',
		MEMBERS = 'Members',
		OWNER = 'Owner',
	}
}
