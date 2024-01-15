/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_DiscountAccount} from './HeadlessCommerceAdminPricing_v2_0_DiscountAccount';
import type {HeadlessCommerceAdminPricing_v2_0_DiscountAccountGroup} from './HeadlessCommerceAdminPricing_v2_0_DiscountAccountGroup';
import type {HeadlessCommerceAdminPricing_v2_0_DiscountCategory} from './HeadlessCommerceAdminPricing_v2_0_DiscountCategory';
import type {HeadlessCommerceAdminPricing_v2_0_DiscountChannel} from './HeadlessCommerceAdminPricing_v2_0_DiscountChannel';
import type {HeadlessCommerceAdminPricing_v2_0_DiscountOrderType} from './HeadlessCommerceAdminPricing_v2_0_DiscountOrderType';
import type {HeadlessCommerceAdminPricing_v2_0_DiscountProduct} from './HeadlessCommerceAdminPricing_v2_0_DiscountProduct';
import type {HeadlessCommerceAdminPricing_v2_0_DiscountProductGroup} from './HeadlessCommerceAdminPricing_v2_0_DiscountProductGroup';
import type {HeadlessCommerceAdminPricing_v2_0_DiscountRule} from './HeadlessCommerceAdminPricing_v2_0_DiscountRule';
export type HeadlessCommerceAdminPricing_v2_0_Discount = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'amountFormatted'?: string;
	'couponCode'?: string;
	'customFields'?: Record<string, Record<string, any>>;
	'discountAccountGroups'?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountAccountGroup>;
	'discountAccounts'?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountAccount>;
	'discountCategories'?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountCategory>;
	'discountChannels'?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountChannel>;
	'discountOrderTypes'?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountOrderType>;
	'discountProductGroups'?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountProductGroup>;
	'discountProducts'?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountProduct>;
	'discountRules'?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountRule>;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'level': string;
	'limitationTimes'?: number;
	'limitationTimesPerAccount'?: number;
	'limitationType': string;
	'maximumDiscountAmount'?: number;
	readonly 'modifiedDate'?: string;
	'neverExpire'?: boolean;
	'numberOfUse'?: number;
	'percentageLevel1'?: number;
	'percentageLevel2'?: number;
	'percentageLevel3'?: number;
	'percentageLevel4'?: number;
	'rulesConjunction'?: boolean;
	'target': string;
	'title': string;
	'useCouponCode'?: boolean;
	'usePercentage': boolean;
	readonly 'x-class-name'?: string;
};
