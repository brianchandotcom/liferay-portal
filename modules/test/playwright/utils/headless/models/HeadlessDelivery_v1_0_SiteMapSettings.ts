/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents settings related with the site map.
 */
export type HeadlessDelivery_v1_0_SiteMapSettings = {

	/**
	 * Whether search engines should crawl and index the page.
	 */
	'include'?: boolean;

	/**
	 * How the page should be prioritized relative to other pages.
	 */
	'pagePriority'?: number;
	readonly 'x-class-name'?: string;

	/**
	 * Indicates how often a page is updated.
	 */
	'changeFrequency'?:
		| 'Always'
		| 'Hourly'
		| 'Daily'
		| 'Weekly'
		| 'Monthly'
		| 'Yearly'
		| 'Never';
};
