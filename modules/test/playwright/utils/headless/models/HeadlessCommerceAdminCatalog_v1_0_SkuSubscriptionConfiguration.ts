/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration = {
	'deliverySubscriptionEnable'?: boolean;
	'deliverySubscriptionLength'?: number;
	'deliverySubscriptionNumberOfLength'?: number;
	'deliverySubscriptionType'?: HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration.deliverySubscriptionType;
	'deliverySubscriptionTypeSettings'?: Record<string, string>;
	'enable'?: boolean;
	'length'?: number;
	'numberOfLength'?: number;
	'overrideSubscriptionInfo'?: boolean;
	'subscriptionType'?: HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration.subscriptionType;
	'subscriptionTypeSettings'?: Record<string, string>;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration {
	export enum deliverySubscriptionType {
		DAILY = 'daily',
		MONTHLY = 'monthly',
		WEEKLY = 'weekly',
		YEARLY = 'yearly',
	}
	export enum subscriptionType {
		DAILY = 'daily',
		MONTHLY = 'monthly',
		WEEKLY = 'weekly',
		YEARLY = 'yearly',
	}
}
