/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_CustomField} from './HeadlessCommerceAdminCatalog_v1_0_CustomField';
export type HeadlessCommerceAdminCatalog_v1_0_AttachmentBase64 = {

	/**
	 * Base64 encoded file
	 */
	'attachment'?: string;

	/**
	 * Content type of attachment
	 */
	'contentType'?: string;
	'customFields'?: Array<HeadlessCommerceAdminCatalog_v1_0_CustomField>;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'galleryEnabled'?: boolean;
	readonly 'id'?: number;
	'neverExpire'?: boolean;
	'options'?: Record<string, string>;
	'priority'?: number;

	/**
	 * URL of the location
	 */
	'src'?: string;
	'tags'?: Array<string>;
	'title'?: Record<string, string>;
	readonly 'type'?: number;
	readonly 'x-class-name'?: string;
};
