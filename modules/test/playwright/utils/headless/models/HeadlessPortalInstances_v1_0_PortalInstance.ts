/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessPortalInstances_v1_0_Admin} from './HeadlessPortalInstances_v1_0_Admin';
export type HeadlessPortalInstances_v1_0_PortalInstance = {
	readonly 'active'?: boolean;
	'admin'?: HeadlessPortalInstances_v1_0_Admin;

	/**
	 * internal unique key.
	 */
	'companyId'?: number;

	/**
	 * domain used for email authentication.
	 */
	'domain'?: string;

	/**
	 * public unique key (corresponds to company's webId field)
	 */
	'portalInstanceId'?: string;
	'siteInitializerKey'?: string;
	'virtualHost'?: string;
	readonly 'x-class-name'?: string;
};
