/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_PriceEntry} from './HeadlessCommerceAdminPricing_v1_0_PriceEntry';
import type {HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup} from './HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup';
export type HeadlessCommerceAdminPricing_v1_0_PriceList = {
	'active'?: boolean;
	'catalogId'?: number;
	'currencyCode': string;
	'customFields'?: Record<string, Record<string, any>>;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'name': string;
	'neverExpire'?: boolean;
	'priceEntries'?: Array<HeadlessCommerceAdminPricing_v1_0_PriceEntry>;
	'priceListAccountGroups'?: Array<HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup>;
	'priority'?: number;
	readonly 'x-class-name'?: string;
};
