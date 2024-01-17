/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_CustomField} from './HeadlessCommerceDeliveryCatalog_v1_0_CustomField';
export type HeadlessCommerceDeliveryCatalog_v1_0_Attachment = {

	/**
	 * Base64 encoded file
	 */
	'attachment'?: string;
	readonly 'customFields'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_CustomField>;
	'displayDate'?: string;
	'expirationDate'?: string;
	readonly 'galleryEnabled'?: boolean;
	readonly 'id'?: number;
	'neverExpire'?: boolean;
	'options'?: Record<string, string>;
	'priority'?: number;

	/**
	 * URL of the location
	 */
	'src'?: string;
	readonly 'tags'?: Array<string>;
	'title'?: string;
	readonly 'type'?: number;
	readonly 'x-class-name'?: string;
};
