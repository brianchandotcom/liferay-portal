/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_OrderType} from '../models/HeadlessCommerceAdminInventory_v1_0_OrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminInventoryV10OrderTypeService {

	/**
	 * @param warehouseOrderTypeId
	 * @returns HeadlessCommerceAdminInventory_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseOrderTypeOrderType(
		warehouseOrderTypeId: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouse-order-types/{warehouseOrderTypeId}/order-type',
			path: {
				warehouseOrderTypeId: warehouseOrderTypeId,
			},
		});
	}
}
