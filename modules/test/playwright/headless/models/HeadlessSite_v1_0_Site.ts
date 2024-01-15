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
	'membershipType'?: HeadlessSite_v1_0_Site.membershipType;
	'name': string;
	'parentSiteKey'?: string;
	'templateKey'?: string;
	'templateType'?: HeadlessSite_v1_0_Site.templateType;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessSite_v1_0_Site {
	export enum membershipType {
		OPEN = 'open',
		PRIVATE = 'private',
		RESTRICTED = 'restricted',
	}
	export enum templateType {
		SITE_INITIALIZER = 'site-initializer',
		SITE_TEMPLATE = 'site-template',
	}
}
