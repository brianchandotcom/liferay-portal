/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessCommerceAdminPricing_v2_0_TierPrice = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'customFields'?: Record<string, Record<string, any>>;
	'discountDiscovery'?: boolean;
	'discountLevel1'?: number;
	'discountLevel2'?: number;
	'discountLevel3'?: number;
	'discountLevel4'?: number;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'minimumQuantity': number;
	'neverExpire'?: boolean;
	'price': number;
	'priceEntryExternalReferenceCode'?: string;
	'priceEntryId': number;
	'priceFormatted'?: string;
	'unitOfMeasureKey'?: string;
	readonly 'x-class-name'?: string;
};
