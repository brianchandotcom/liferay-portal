/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory} from './HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory';
import type {HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct} from './HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct';
import type {HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup} from './HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup';
export type HeadlessCommerceAdminPricing_v2_0_PriceModifier = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'modifierAmount': number;
	'modifierType': string;
	'neverExpire'?: boolean;
	'priceListExternalReferenceCode'?: string;
	'priceListId'?: number;
	'priceModifierCategories'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory>;
	'priceModifierProductGroups'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup>;
	'priceModifierProducts'?: Array<HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct>;
	'priority'?: number;
	'target': string;
	'title': string;
	readonly 'x-class-name'?: string;
};
