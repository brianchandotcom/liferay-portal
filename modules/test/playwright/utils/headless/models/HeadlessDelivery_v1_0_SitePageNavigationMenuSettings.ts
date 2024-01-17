/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * The page's site navigation menu settings.
 */
export type HeadlessDelivery_v1_0_SitePageNavigationMenuSettings = {

	/**
	 * The default parameter for a page.
	 */
	'queryString'?: string;

	/**
	 * The page's description to be used as summary for search engines.
	 */
	'target'?: string;

	/**
	 * The target's type (specific frame or new tab).
	 */
	'targetType'?: HeadlessDelivery_v1_0_SitePageNavigationMenuSettings.targetType;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessDelivery_v1_0_SitePageNavigationMenuSettings {

	/**
	 * The target's type (specific frame or new tab).
	 */
	export enum targetType {
		SPECIFIC_FRAME = 'SpecificFrame',
		NEW_TAB = 'NewTab',
	}
}
