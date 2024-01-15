/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_Channel} from '../models/HeadlessCommerceAdminInventory_v1_0_Channel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminInventoryV10ChannelService {

	/**
	 * @param warehouseChannelId
	 * @returns HeadlessCommerceAdminInventory_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseChannelChannel(
		warehouseChannelId: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouse-channels/{warehouseChannelId}/channel',
			path: {
				warehouseChannelId: warehouseChannelId,
			},
		});
	}
}
