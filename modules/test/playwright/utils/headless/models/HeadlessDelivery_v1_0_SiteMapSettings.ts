/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents settings related with the site map.
 */
export type HeadlessDelivery_v1_0_SiteMapSettings = {

	/**
	 * Indicates how often a page is updated.
	 */
	'changeFrequency'?: HeadlessDelivery_v1_0_SiteMapSettings.changeFrequency;

	/**
	 * Whether search engines should crawl and index the page.
	 */
	'include'?: boolean;

	/**
	 * How the page should be prioritized relative to other pages.
	 */
	'pagePriority'?: number;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessDelivery_v1_0_SiteMapSettings {

	/**
	 * Indicates how often a page is updated.
	 */
	export enum changeFrequency {
		ALWAYS = 'Always',
		HOURLY = 'Hourly',
		DAILY = 'Daily',
		WEEKLY = 'Weekly',
		MONTHLY = 'Monthly',
		YEARLY = 'Yearly',
		NEVER = 'Never',
	}
}
