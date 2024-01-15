/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_ContentDocument} from './HeadlessAdminContent_v1_0_ContentDocument';
import type {HeadlessAdminContent_v1_0_Geo} from './HeadlessAdminContent_v1_0_Geo';
import type {HeadlessAdminContent_v1_0_StructuredContentLink} from './HeadlessAdminContent_v1_0_StructuredContentLink';

/**
 * The localized field's values.
 */
export type HeadlessAdminContent_v1_0_ContentFieldValue = {

	/**
	 * The field's content for simple types.
	 */
	'data'?: string;
	'document'?: HeadlessAdminContent_v1_0_ContentDocument;
	'geo'?: HeadlessAdminContent_v1_0_Geo;
	'image'?: HeadlessAdminContent_v1_0_ContentDocument;

	/**
	 * A link to a page on the server.
	 */
	'link'?: string;
	'structuredContentLink'?: HeadlessAdminContent_v1_0_StructuredContentLink;

	/**
	 * The field's visible value
	 */
	'value'?: string;
	readonly 'x-class-name'?: string;
};
