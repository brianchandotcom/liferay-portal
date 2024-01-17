/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents the site being created.
 */
export type HeadlessSite_v1_0_Site = {

	/**
	 * The site's external reference code.
	 */
	'externalReferenceCode'?: string;
	readonly 'friendlyUrlPath'?: string;
	readonly 'id'?: number;
	readonly 'key'?: string;
	'membershipType'?: 'open' | 'private' | 'restricted';
	'name': string;
	'parentSiteKey'?: string;
	'templateKey'?: string;
	'templateType'?: 'site-initializer' | 'site-template';
	readonly 'x-class-name'?: string;
};
