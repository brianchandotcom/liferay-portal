/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup} from './HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup';
import type {HeadlessCommerceAdminPricing_v1_0_DiscountCategory} from './HeadlessCommerceAdminPricing_v1_0_DiscountCategory';
import type {HeadlessCommerceAdminPricing_v1_0_DiscountProduct} from './HeadlessCommerceAdminPricing_v1_0_DiscountProduct';
import type {HeadlessCommerceAdminPricing_v1_0_DiscountRule} from './HeadlessCommerceAdminPricing_v1_0_DiscountRule';
export type HeadlessCommerceAdminPricing_v1_0_Discount = {
	'active'?: boolean;
	'couponCode'?: string;
	'customFields'?: Record<string, Record<string, any>>;
	'discountAccountGroups'?: Array<HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup>;
	'discountCategories'?: Array<HeadlessCommerceAdminPricing_v1_0_DiscountCategory>;
	'discountProducts'?: Array<HeadlessCommerceAdminPricing_v1_0_DiscountProduct>;
	'discountRules'?: Array<HeadlessCommerceAdminPricing_v1_0_DiscountRule>;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'limitationTimes'?: number;
	'limitationType': string;
	'maximumDiscountAmount'?: number;
	'neverExpire'?: boolean;
	'numberOfUse'?: number;
	'percentageLevel1'?: number;
	'percentageLevel2'?: number;
	'percentageLevel3'?: number;
	'percentageLevel4'?: number;
	'target': string;
	'title': string;
	'useCouponCode'?: boolean;
	'usePercentage'?: boolean;
	readonly 'x-class-name'?: string;
};
