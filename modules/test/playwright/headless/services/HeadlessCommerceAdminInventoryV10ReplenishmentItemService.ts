/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_PageReplenishmentItem} from '../models/HeadlessCommerceAdminInventory_v1_0_PageReplenishmentItem';
import type {HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem} from '../models/HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminInventoryV10ReplenishmentItemService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetReplenishmentItemByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10DeleteReplenishmentItemByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PatchReplenishmentItemByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param sku
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageReplenishmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetReplenishmentItemsPage(
		sku: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageReplenishmentItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/skus/by-sku/{sku}/replenishment-items',
			path: {
				sku: sku,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param warehouseId
	 * @param sku
	 * @param requestBody
	 * @returns HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostReplenishmentItem(
		warehouseId: string,
		sku: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{warehouseId}/skus/by-sku/{sku}/replenishment-items',
			path: {
				warehouseId: warehouseId,
				sku: sku,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param sku
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostReplenishmentItemsPageExportBatch(
		sku: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/skus/by-sku/{sku}/replenishment-items/export-batch',
			path: {
				sku: sku,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param warehouseId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageReplenishmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseIdReplenishmentItemsPage(
		warehouseId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageReplenishmentItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{warehouseId}/replenishment-items',
			path: {
				warehouseId: warehouseId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param warehouseId
	 * @param sku
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostReplenishmentItemBatch(
		warehouseId: string,
		sku: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{warehouseId}/skus/by-sku/{sku}/replenishment-items/batch',
			path: {
				warehouseId: warehouseId,
				sku: sku,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10DeleteReplenishmentItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param replenishmentItemId
	 * @returns HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetReplenishmentItem(
		replenishmentItemId: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/{replenishmentItemId}',
			path: {
				replenishmentItemId: replenishmentItemId,
			},
		});
	}

	/**
	 * @param replenishmentItemId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10DeleteReplenishmentItem(
		replenishmentItemId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/{replenishmentItemId}',
			path: {
				replenishmentItemId: replenishmentItemId,
			},
		});
	}

	/**
	 * @param replenishmentItemId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PatchReplenishmentItem(
		replenishmentItemId: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/{replenishmentItemId}',
			path: {
				replenishmentItemId: replenishmentItemId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
