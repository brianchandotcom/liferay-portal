/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Segment} from './HeadlessDelivery_v1_0_Segment';

/**
 * Experience of the page that it's being retrieved.
 */
export type HeadlessDelivery_v1_0_Experience = {

	/**
	 * the experience's key.
	 */
	'key'?: string;

	/**
	 * the experience's name.
	 */
	'name'?: string;

	/**
	 * the localized experience's names.
	 */
	'name_i18n'?: Record<string, string>;

	/**
	 * A list of segments the experience is used for.
	 */
	'segments'?: Array<HeadlessDelivery_v1_0_Segment>;
	readonly 'x-class-name'?: string;
};
