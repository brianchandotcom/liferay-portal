/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_OrderType} from './HeadlessCommerceAdminInventory_v1_0_OrderType';
export type HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'orderType'?: HeadlessCommerceAdminInventory_v1_0_OrderType;
	'orderTypeExternalReferenceCode'?: string;
	'orderTypeId': number;
	'priority'?: number;
	'warehouseExternalReferenceCode'?: string;
	'warehouseId': number;
	readonly 'warehouseOrderTypeId'?: number;
	readonly 'x-class-name'?: string;
};
