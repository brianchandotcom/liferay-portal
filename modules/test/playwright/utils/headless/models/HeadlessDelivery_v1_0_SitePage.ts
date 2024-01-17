/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_AggregateRating} from './HeadlessDelivery_v1_0_AggregateRating';
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
import type {HeadlessDelivery_v1_0_CustomField} from './HeadlessDelivery_v1_0_CustomField';
import type {HeadlessDelivery_v1_0_Experience} from './HeadlessDelivery_v1_0_Experience';
import type {HeadlessDelivery_v1_0_PageDefinition} from './HeadlessDelivery_v1_0_PageDefinition';
import type {HeadlessDelivery_v1_0_PagePermission} from './HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_PageSettings} from './HeadlessDelivery_v1_0_PageSettings';
import type {HeadlessDelivery_v1_0_ParentSitePage} from './HeadlessDelivery_v1_0_ParentSitePage';
import type {HeadlessDelivery_v1_0_RenderedPage} from './HeadlessDelivery_v1_0_RenderedPage';
import type {HeadlessDelivery_v1_0_TaxonomyCategoryBrief} from './HeadlessDelivery_v1_0_TaxonomyCategoryBrief';

/**
 * Represents a site page. This is modeled internally as a `Layout`.
 */
export type HeadlessDelivery_v1_0_SitePage = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'aggregateRating'?: HeadlessDelivery_v1_0_AggregateRating;

	/**
	 * The list of languages the page has a translation for.
	 */
	readonly 'availableLanguages'?: Array<string>;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * Custom fields associated with the page.
	 */
	'customFields'?: Array<HeadlessDelivery_v1_0_CustomField>;

	/**
	 * The page's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any field of the page was changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The page's most recent publication date.
	 */
	'datePublished'?: string;
	'experience'?: HeadlessDelivery_v1_0_Experience;

	/**
	 * A relative URL to the page's rendered content.
	 */
	'friendlyUrlPath'?: string;

	/**
	 * The localized relative URLs to the page's rendered content.
	 */
	'friendlyUrlPath_i18n'?: Record<string, string>;

	/**
	 * The page ID.
	 */
	readonly 'id'?: number;

	/**
	 * A list of keywords describing the page.
	 */
	'keywords'?: Array<string>;
	'pageDefinition'?: HeadlessDelivery_v1_0_PageDefinition;

	/**
	 * The page's permissions.
	 */
	'pagePermissions'?: Array<HeadlessDelivery_v1_0_PagePermission>;
	'pageSettings'?: HeadlessDelivery_v1_0_PageSettings;

	/**
	 * The type of the page.
	 */
	'pageType'?: string;
	'parentSitePage'?: HeadlessDelivery_v1_0_ParentSitePage;
	'renderedPage'?: HeadlessDelivery_v1_0_RenderedPage;

	/**
	 * The ID of the site to which this page is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * The categories associated with this page.
	 */
	'taxonomyCategoryBriefs'?: Array<HeadlessDelivery_v1_0_TaxonomyCategoryBrief>;
	'taxonomyCategoryIds'?: Array<number>;

	/**
	 * The page's title.
	 */
	'title': string;

	/**
	 * The localized page's titles.
	 */
	'title_i18n'?: Record<string, string>;

	/**
	 * A valid external identifier to reference this page.
	 */
	readonly 'uuid'?: string;
	'viewableBy'?: 'Anyone' | 'Members' | 'Owner';
	readonly 'x-class-name'?: string;
};
