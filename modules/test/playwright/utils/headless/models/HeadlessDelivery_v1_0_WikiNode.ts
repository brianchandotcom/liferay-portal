/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
export type HeadlessDelivery_v1_0_WikiNode = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * The date the wiki node was created.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any of the wiki node's fields changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The wiki node's description.
	 */
	'description'?: string;

	/**
	 * The wiki node's external reference code.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The wiki node's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The wiki node's name.
	 */
	'name'?: string;

	/**
	 * The number of child wiki page on this wiki node.
	 */
	readonly 'numberOfWikiPages'?: number;

	/**
	 * The ID of the site to which this wiki node is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * A flag that indicates whether the user making the requests is subscribed to this wiki node.
	 */
	readonly 'subscribed'?: boolean;
	'viewableBy'?: HeadlessDelivery_v1_0_WikiNode.viewableBy;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessDelivery_v1_0_WikiNode {
	export enum viewableBy {
		ANYONE = 'Anyone',
		MEMBERS = 'Members',
		OWNER = 'Owner',
	}
}
