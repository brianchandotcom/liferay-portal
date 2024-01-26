/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DiscountAccountGroup } from './DiscountAccountGroup';
import type { DiscountCategory } from './DiscountCategory';
import type { DiscountProduct } from './DiscountProduct';
import type { DiscountRule } from './DiscountRule';
export type Discount = {
    active?: boolean;
    couponCode?: string;
    customFields?: Record<string, any>;
    discountAccountGroups?: Array<DiscountAccountGroup>;
    discountCategories?: Array<DiscountCategory>;
    discountProducts?: Array<DiscountProduct>;
    discountRules?: Array<DiscountRule>;
    displayDate?: string;
    expirationDate?: string;
    externalReferenceCode?: string;
    id?: number;
    limitationTimes?: number;
    limitationType: string;
    maximumDiscountAmount?: number;
    neverExpire?: boolean;
    numberOfUse?: number;
    percentageLevel1?: number;
    percentageLevel2?: number;
    percentageLevel3?: number;
    percentageLevel4?: number;
    target: string;
    title: string;
    useCouponCode?: boolean;
    usePercentage?: boolean;
};

