/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_StructuredContent} from './HeadlessAdminContent_v1_0_StructuredContent';

/**
 * A link to structured content on the server.
 */
export type HeadlessAdminContent_v1_0_StructuredContentLink = {

	/**
	 * The type of content.
	 */
	readonly 'contentType'?: string;
	'embeddedStructuredContent'?: HeadlessAdminContent_v1_0_StructuredContent;

	/**
	 * The resource's ID.
	 */
	'id'?: number;

	/**
	 * The resource's title.
	 */
	'title'?: string;
	readonly 'x-class-name'?: string;
};
