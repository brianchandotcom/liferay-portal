/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_ContentDocument} from './HeadlessDelivery_v1_0_ContentDocument';
import type {HeadlessDelivery_v1_0_Geo} from './HeadlessDelivery_v1_0_Geo';
import type {HeadlessDelivery_v1_0_StructuredContentLink} from './HeadlessDelivery_v1_0_StructuredContentLink';

/**
 * The localized field's values.
 */
export type HeadlessDelivery_v1_0_ContentFieldValue = {

	/**
	 * The field's content for simple types.
	 */
	'data'?: string;
	'document'?: HeadlessDelivery_v1_0_ContentDocument;
	'geo'?: HeadlessDelivery_v1_0_Geo;
	'image'?: HeadlessDelivery_v1_0_ContentDocument;

	/**
	 * A link to a page on the server.
	 */
	'link'?: string;
	'structuredContentLink'?: HeadlessDelivery_v1_0_StructuredContentLink;

	/**
	 * The field's visible value
	 */
	'value'?: string;
	readonly 'x-class-name'?: string;
};
