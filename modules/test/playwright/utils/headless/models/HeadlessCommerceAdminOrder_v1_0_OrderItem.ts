/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_CustomField} from './HeadlessCommerceAdminOrder_v1_0_CustomField';
import type {HeadlessCommerceAdminOrder_v1_0_ShippingAddress} from './HeadlessCommerceAdminOrder_v1_0_ShippingAddress';
export type HeadlessCommerceAdminOrder_v1_0_OrderItem = {
	'bookedQuantityId'?: number;
	'customFields'?: Array<HeadlessCommerceAdminOrder_v1_0_CustomField>;
	'decimalQuantity'?: number;
	'deliveryGroup'?: string;
	'discountAmount'?: number;
	readonly 'discountManuallyAdjusted'?: boolean;
	'discountPercentageLevel1'?: number;
	'discountPercentageLevel1WithTaxAmount'?: number;
	'discountPercentageLevel2'?: number;
	'discountPercentageLevel2WithTaxAmount'?: number;
	'discountPercentageLevel3'?: number;
	'discountPercentageLevel3WithTaxAmount'?: number;
	'discountPercentageLevel4'?: number;
	'discountPercentageLevel4WithTaxAmount'?: number;
	'discountWithTaxAmount'?: number;
	'externalReferenceCode'?: string;
	'finalPrice'?: number;
	'finalPriceWithTaxAmount'?: number;
	readonly 'formattedQuantity'?: string;
	'id'?: number;
	'name'?: Record<string, string>;
	'options'?: string;
	'orderExternalReferenceCode'?: string;
	'orderId'?: number;
	readonly 'priceManuallyAdjusted'?: boolean;
	'printedNote'?: string;
	'promoPrice'?: number;
	'promoPriceWithTaxAmount'?: number;
	'quantity'?: number;
	readonly 'replacedSku'?: string;
	'replacedSkuId'?: number;
	'requestedDeliveryDate'?: string;
	'shippedQuantity'?: number;
	'shippingAddress'?: HeadlessCommerceAdminOrder_v1_0_ShippingAddress;
	'shippingAddressId'?: number;
	'sku'?: string;
	'skuExternalReferenceCode'?: string;
	'skuId'?: number;
	'subscription'?: boolean;
	'unitOfMeasure'?: string;
	'unitOfMeasureKey'?: string;
	'unitPrice'?: number;
	'unitPriceWithTaxAmount'?: number;
	readonly 'virtualItemURLs'?: Array<string>;
	readonly 'x-class-name'?: string;
};
