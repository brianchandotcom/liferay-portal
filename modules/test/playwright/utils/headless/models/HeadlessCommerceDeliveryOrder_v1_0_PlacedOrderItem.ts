/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItemShipment} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItemShipment';
import type {HeadlessCommerceDeliveryOrder_v1_0_Price} from './HeadlessCommerceDeliveryOrder_v1_0_Price';
import type {HeadlessCommerceDeliveryOrder_v1_0_Settings} from './HeadlessCommerceDeliveryOrder_v1_0_Settings';
import type {HeadlessCommerceDeliveryOrder_v1_0_VirtualItem} from './HeadlessCommerceDeliveryOrder_v1_0_VirtualItem';
export type HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem = {
	readonly 'adaptiveMediaImageHTMLTag'?: string;
	readonly 'customFields'?: Record<string, Record<string, any>>;
	readonly 'errorMessages'?: Array<string>;
	readonly 'id'?: number;
	readonly 'name'?: string;
	readonly 'options'?: string;
	readonly 'parentOrderItemId'?: number;
	readonly 'placedOrderItemShipments'?: Array<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItemShipment>;
	'price'?: HeadlessCommerceDeliveryOrder_v1_0_Price;
	readonly 'productId'?: number;
	readonly 'productURLs'?: Record<string, string>;
	readonly 'quantity'?: number;
	readonly 'replacedSku'?: string;
	'settings'?: HeadlessCommerceDeliveryOrder_v1_0_Settings;
	readonly 'sku'?: string;
	readonly 'skuId'?: number;
	readonly 'subscription'?: boolean;
	readonly 'thumbnail'?: string;
	readonly 'unitOfMeasureKey'?: string;
	readonly 'valid'?: boolean;
	readonly 'virtualItemURLs'?: Array<string>;
	readonly 'virtualItems'?: Array<HeadlessCommerceDeliveryOrder_v1_0_VirtualItem>;
	readonly 'x-class-name'?: string;
};
