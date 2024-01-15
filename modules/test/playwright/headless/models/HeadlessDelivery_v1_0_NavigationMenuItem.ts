/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';

/**
 * The list of navigation menu items this navigation menu has.
 */
export type HeadlessDelivery_v1_0_NavigationMenuItem = {

	/**
	 * The list of languages the navigation menu item has a translation for.
	 */
	readonly 'availableLanguages'?: Array<string>;

	/**
	 * The navigation menu item's content API REST URL.
	 */
	'contentURL'?: string;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * The navigation menu item's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time the navigation menu item changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The navigation menu item's ID.
	 */
	'id'?: number;

	/**
	 * The link to a page on the server.
	 */
	'link'?: string;

	/**
	 * The localized links to a page on the server.
	 */
	'link_i18n'?: Record<string, string>;

	/**
	 * The navigation menu item's name.
	 */
	'name'?: string;

	/**
	 * The localized navigation menu item's names.
	 */
	'name_i18n'?: Record<string, string>;

	/**
	 * The navigation menu items this navigation menu item has.
	 */
	'navigationMenuItems'?: Array<HeadlessDelivery_v1_0_NavigationMenuItem>;

	/**
	 * The ID of the navigation menu item's parent.
	 */
	readonly 'parentNavigationMenuId'?: number;

	/**
	 * The navigation menu item's linked site page URL.
	 */
	readonly 'sitePageURL'?: string;

	/**
	 * The navigation menu item's type.
	 */
	readonly 'type'?: string;

	/**
	 * The navigation menu item's linked URL.
	 */
	'url'?: string;
	'useCustomName'?: boolean;
	readonly 'x-class-name'?: string;
};
