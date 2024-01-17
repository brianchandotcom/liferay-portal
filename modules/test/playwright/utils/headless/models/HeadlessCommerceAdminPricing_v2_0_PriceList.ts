/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PriceEntry} from './HeadlessCommerceAdminPricing_v2_0_PriceEntry';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListAccount} from './HeadlessCommerceAdminPricing_v2_0_PriceListAccount';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup} from './HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListChannel} from './HeadlessCommerceAdminPricing_v2_0_PriceListChannel';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListDiscount} from './HeadlessCommerceAdminPricing_v2_0_PriceListDiscount';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListOrderType} from './HeadlessCommerceAdminPricing_v2_0_PriceListOrderType';
import type {HeadlessCommerceAdminPricing_v2_0_PriceModifier} from './HeadlessCommerceAdminPricing_v2_0_PriceModifier';
import type {HeadlessCommerceAdminPricing_v2_0_Status} from './HeadlessCommerceAdminPricing_v2_0_Status';
export type HeadlessCommerceAdminPricing_v2_0_PriceList = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'author'?: string;
	'catalogBasePriceList'?: boolean;
	'catalogId': number;
	'catalogName'?: string;
	'createDate'?: string;
	'currencyCode': string;
	'customFields'?: Record<string, Record<string, any>>;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'name': string;
	'netPrice'?: boolean;
	'neverExpire'?: boolean;
	'parentPriceListId'?: number;
	'priceEntries'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceEntry>;
	'priceListAccountGroups'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup>;
	'priceListAccounts'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceListAccount>;
	'priceListChannels'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceListChannel>;
	'priceListDiscounts'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceListDiscount>;
	'priceListOrderTypes'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceListOrderType>;
	'priceModifiers'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceModifier>;
	'priority'?: number;
	'type': HeadlessCommerceAdminPricing_v2_0_PriceList.type;
	'workflowStatusInfo'?: HeadlessCommerceAdminPricing_v2_0_Status;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessCommerceAdminPricing_v2_0_PriceList {
	export enum type {
		PRICE_LIST = 'price-list',
		PROMOTION = 'promotion',
		CONTRACT = 'contract',
	}
}
