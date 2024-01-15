/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem';
import type {HeadlessCommerceDeliveryOrder_v1_0_Status} from './HeadlessCommerceDeliveryOrder_v1_0_Status';
import type {HeadlessCommerceDeliveryOrder_v1_0_Summary} from './HeadlessCommerceDeliveryOrder_v1_0_Summary';
export type HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder = {
	readonly 'account'?: string;
	readonly 'accountId'?: number;
	readonly 'author'?: string;
	readonly 'channelId'?: number;
	readonly 'couponCode'?: string;
	readonly 'createDate'?: string;
	readonly 'currencyCode'?: string;
	readonly 'customFields'?: Record<string, Record<string, any>>;
	readonly 'errorMessages'?: Array<string>;
	readonly 'id'?: number;
	readonly 'lastPriceUpdateDate'?: string;
	readonly 'modifiedDate'?: string;
	'orderStatusInfo'?: HeadlessCommerceDeliveryOrder_v1_0_Status;
	readonly 'orderTypeExternalReferenceCode'?: string;
	readonly 'orderTypeId'?: number;
	readonly 'orderUUID'?: string;
	readonly 'paymentMethod'?: string;
	readonly 'paymentMethodLabel'?: string;
	readonly 'paymentStatus'?: number;
	'paymentStatusInfo'?: HeadlessCommerceDeliveryOrder_v1_0_Status;
	readonly 'paymentStatusLabel'?: string;
	'placedOrderBillingAddress'?: HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress;
	readonly 'placedOrderBillingAddressId'?: number;
	readonly 'placedOrderComments'?: Array<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment>;
	readonly 'placedOrderItems'?: Array<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem>;
	'placedOrderShippingAddress'?: HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress;
	readonly 'placedOrderShippingAddressId'?: number;
	readonly 'printedNote'?: string;
	readonly 'purchaseOrderNumber'?: string;
	readonly 'shippingMethod'?: string;
	readonly 'shippingOption'?: string;
	readonly 'status'?: string;
	'summary'?: HeadlessCommerceDeliveryOrder_v1_0_Summary;
	readonly 'useAsBilling'?: boolean;
	readonly 'valid'?: boolean;
	'workflowStatusInfo'?: HeadlessCommerceDeliveryOrder_v1_0_Status;
	readonly 'x-class-name'?: string;
};
