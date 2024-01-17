/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_CustomMetaTag} from './HeadlessDelivery_v1_0_CustomMetaTag';
import type {HeadlessDelivery_v1_0_OpenGraphSettings} from './HeadlessDelivery_v1_0_OpenGraphSettings';
import type {HeadlessDelivery_v1_0_SEOSettings} from './HeadlessDelivery_v1_0_SEOSettings';
import type {HeadlessDelivery_v1_0_SitePageNavigationMenuSettings} from './HeadlessDelivery_v1_0_SitePageNavigationMenuSettings';

/**
 * Settings of the page, such as SEO or OpenGraph.
 */
export type HeadlessDelivery_v1_0_PageSettings = {

	/**
	 * A list of custom metatags this page has.
	 */
	'customMetaTags'?: Array<HeadlessDelivery_v1_0_CustomMetaTag>;

	/**
	 * A flag that indicates whether the page is hidden from navigation.
	 */
	'hiddenFromNavigation'?: boolean;
	'openGraphSettings'?: HeadlessDelivery_v1_0_OpenGraphSettings;
	'seoSettings'?: HeadlessDelivery_v1_0_SEOSettings;
	'sitePageNavigationMenuSettings'?: HeadlessDelivery_v1_0_SitePageNavigationMenuSettings;
	readonly 'x-class-name'?: string;
};
