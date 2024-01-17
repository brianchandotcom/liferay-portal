/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
import type {HeadlessDelivery_v1_0_NavigationMenuItem} from './HeadlessDelivery_v1_0_NavigationMenuItem';
export type HeadlessDelivery_v1_0_NavigationMenu = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * The navigation menu's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time the navigation menu changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The navigation menu's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The navigation menu's name.
	 */
	'name'?: string;

	/**
	 * The list of navigation menu items this navigation menu has.
	 */
	'navigationMenuItems'?: Array<HeadlessDelivery_v1_0_NavigationMenuItem>;

	/**
	 * The navigation menu's type (primary, secondary, social).
	 */
	'navigationType'?: HeadlessDelivery_v1_0_NavigationMenu.navigationType;

	/**
	 * The ID of the site to which this navigation menu is scoped.
	 */
	readonly 'siteId'?: number;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessDelivery_v1_0_NavigationMenu {

	/**
	 * The navigation menu's type (primary, secondary, social).
	 */
	export enum navigationType {
		PRIMARY = 'Primary',
		SECONDARY = 'Secondary',
		SOCIAL = 'Social',
	}
}
