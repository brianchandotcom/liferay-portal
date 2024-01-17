/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_ContentDocument} from './HeadlessDelivery_v1_0_ContentDocument';

/**
 * The page's Open Graph settings.
 */
export type HeadlessDelivery_v1_0_OpenGraphSettings = {

	/**
	 * The Open Graph's description.
	 */
	'description'?: string;

	/**
	 * The localized Open Graph's descriptions.
	 */
	'description_i18n'?: Record<string, string>;
	'image'?: HeadlessDelivery_v1_0_ContentDocument;

	/**
	 * The Open Graph's image alt.
	 */
	'imageAlt'?: string;

	/**
	 * The localized Open Graph's image alts.
	 */
	'imageAlt_i18n'?: Record<string, string>;

	/**
	 * The Open Graph's title.
	 */
	'title'?: string;

	/**
	 * The localized Open Graph's titles.
	 */
	'title_i18n'?: Record<string, string>;
	readonly 'x-class-name'?: string;
};
