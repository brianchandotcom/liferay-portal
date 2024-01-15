/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_Status} from './HeadlessCommerceDeliveryOrder_v1_0_Status';
export type HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItemShipment = {
	readonly 'accountId'?: number;
	readonly 'author'?: string;
	readonly 'carrier'?: string;
	readonly 'createDate'?: string;
	readonly 'estimatedDeliveryDate'?: string;
	readonly 'estimatedShippingDate'?: string;
	readonly 'id'?: number;
	readonly 'modifiedDate'?: string;
	readonly 'orderId'?: number;
	readonly 'quantity'?: number;
	readonly 'shippingAddressId'?: number;
	readonly 'shippingMethodId'?: number;
	readonly 'shippingOptionName'?: string;
	'status'?: HeadlessCommerceDeliveryOrder_v1_0_Status;
	readonly 'supplierShipment'?: boolean;
	readonly 'trackingNumber'?: string;
	readonly 'trackingURL'?: string;
	readonly 'unitOfMeasureKey'?: string;
	readonly 'x-class-name'?: string;
};
