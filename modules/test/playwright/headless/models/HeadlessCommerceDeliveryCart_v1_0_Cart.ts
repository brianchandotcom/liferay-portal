/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_Address} from './HeadlessCommerceDeliveryCart_v1_0_Address';
import type {HeadlessCommerceDeliveryCart_v1_0_CartComment} from './HeadlessCommerceDeliveryCart_v1_0_CartComment';
import type {HeadlessCommerceDeliveryCart_v1_0_CartItem} from './HeadlessCommerceDeliveryCart_v1_0_CartItem';
import type {HeadlessCommerceDeliveryCart_v1_0_Status} from './HeadlessCommerceDeliveryCart_v1_0_Status';
import type {HeadlessCommerceDeliveryCart_v1_0_Summary} from './HeadlessCommerceDeliveryCart_v1_0_Summary';
export type HeadlessCommerceDeliveryCart_v1_0_Cart = {
	readonly 'account'?: string;
	'accountId'?: number;
	readonly 'author'?: string;
	'billingAddress'?: HeadlessCommerceDeliveryCart_v1_0_Address;
	'billingAddressId'?: number;
	'cartItems'?: Array<HeadlessCommerceDeliveryCart_v1_0_CartItem>;
	readonly 'channelId'?: number;
	'couponCode'?: string;
	readonly 'createDate'?: string;
	'currencyCode'?: string;
	'customFields'?: Record<string, Record<string, any>>;
	'errorMessages'?: Array<string>;
	readonly 'id'?: number;
	readonly 'lastPriceUpdateDate'?: string;
	readonly 'modifiedDate'?: string;
	'notes'?: Array<HeadlessCommerceDeliveryCart_v1_0_CartComment>;
	'orderStatusInfo'?: HeadlessCommerceDeliveryCart_v1_0_Status;
	'orderTypeExternalReferenceCode'?: string;
	'orderTypeId'?: number;
	readonly 'orderUUID'?: string;
	'paymentMethod'?: string;
	readonly 'paymentMethodLabel'?: string;
	readonly 'paymentStatus'?: number;
	'paymentStatusInfo'?: HeadlessCommerceDeliveryCart_v1_0_Status;
	readonly 'paymentStatusLabel'?: string;
	'printedNote'?: string;
	'purchaseOrderNumber'?: string;
	'shippingAddress'?: HeadlessCommerceDeliveryCart_v1_0_Address;
	'shippingAddressId'?: number;
	'shippingMethod'?: string;
	'shippingOption'?: string;
	readonly 'status'?: string;
	'summary'?: HeadlessCommerceDeliveryCart_v1_0_Summary;
	'useAsBilling'?: boolean;
	readonly 'valid'?: boolean;
	'workflowStatusInfo'?: HeadlessCommerceDeliveryCart_v1_0_Status;
	readonly 'x-class-name'?: string;
};
