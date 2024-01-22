/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration = {
	'deliverySubscriptionEnable'?: boolean;
	'deliverySubscriptionLength'?: number;
	'deliverySubscriptionNumberOfLength'?: number;
	'deliverySubscriptionTypeSettings'?: Record<string, string>;
	'enable'?: boolean;
	'length'?: number;
	'numberOfLength'?: number;
	'overrideSubscriptionInfo'?: boolean;
	'subscriptionTypeSettings'?: Record<string, string>;
	readonly 'x-class-name'?: string;
	'deliverySubscriptionType'?: 'daily' | 'monthly' | 'weekly' | 'yearly';
	'subscriptionType'?: 'daily' | 'monthly' | 'weekly' | 'yearly';
};
