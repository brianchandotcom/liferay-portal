/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminShipment_v1_0_CustomField} from './HeadlessCommerceAdminShipment_v1_0_CustomField';
import type {HeadlessCommerceAdminShipment_v1_0_ShipmentItem} from './HeadlessCommerceAdminShipment_v1_0_ShipmentItem';
import type {HeadlessCommerceAdminShipment_v1_0_ShippingAddress} from './HeadlessCommerceAdminShipment_v1_0_ShippingAddress';
import type {HeadlessCommerceAdminShipment_v1_0_Status} from './HeadlessCommerceAdminShipment_v1_0_Status';
export type HeadlessCommerceAdminShipment_v1_0_Shipment = {
	readonly 'accountId'?: number;
	readonly 'actions'?: Record<string, Record<string, string>>;
	'carrier'?: string;
	readonly 'createDate'?: string;
	'customFields'?: Array<HeadlessCommerceAdminShipment_v1_0_CustomField>;
	'expectedDate'?: string;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	readonly 'modifiedDate'?: string;
	'orderId'?: number;
	'shipmentItems'?: Array<HeadlessCommerceAdminShipment_v1_0_ShipmentItem>;
	'shippingAddress'?: HeadlessCommerceAdminShipment_v1_0_ShippingAddress;
	'shippingAddressId'?: number;
	'shippingDate'?: string;
	'shippingMethodId'?: number;
	readonly 'shippingOptionName'?: string;
	'status'?: HeadlessCommerceAdminShipment_v1_0_Status;
	'trackingNumber'?: string;
	'trackingURL'?: string;
	readonly 'userName'?: string;
	readonly 'x-class-name'?: string;
};
