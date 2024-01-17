/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * The organization's postal information (country and region).
 */
export type HeadlessAdminUser_v1_0_Location = {

	/**
	 * The organization's country. This follows the [`addressCountry`](https://schema.org/addressCountry) specification.
	 */
	'addressCountry'?: string;

	/**
	 * The organization's country isocode.
	 */
	readonly 'addressCountryCode'?: string;
	'addressCountry_i18n'?: Record<string, string>;

	/**
	 * The organization's region. This follows the [`addressRegion`](https://schema.org/addressRegion) specification.
	 */
	'addressRegion'?: string;

	/**
	 * The organization's region code.
	 */
	readonly 'addressRegionCode'?: string;

	/**
	 * The location's ID.
	 */
	readonly 'id'?: number;
	readonly 'x-class-name'?: string;
};
