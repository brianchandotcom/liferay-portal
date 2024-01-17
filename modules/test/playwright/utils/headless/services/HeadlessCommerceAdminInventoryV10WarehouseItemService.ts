/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_PageWarehouseItem} from '../models/HeadlessCommerceAdminInventory_v1_0_PageWarehouseItem';
import type {HeadlessCommerceAdminInventory_v1_0_WarehouseItem} from '../models/HeadlessCommerceAdminInventory_v1_0_WarehouseItem';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminInventoryV10WarehouseItemService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseIdWarehouseItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/warehouseItems/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageWarehouseItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseByExternalReferenceCodeWarehouseItemsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageWarehouseItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouseItems',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseByExternalReferenceCodeWarehouseItem(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseItem
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouseItems',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseItem(
		id: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10DeleteWarehouseItem(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PatchWarehouseItem(
		id: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseItem
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/{id}',
			path: {
				id: id,
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
	public static headlessCommerceAdminInventoryV10DeleteWarehouseItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseItemByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseItemByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseItem
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10DeleteWarehouseItemByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PatchWarehouseItemByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseItem
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageWarehouseItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseIdWarehouseItemsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageWarehouseItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouseItems',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseIdWarehouseItem(
		id: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseItem
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouseItems',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param end
	 * @param start
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageWarehouseItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseItemsUpdatedPage(
		end?: string,
		start?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageWarehouseItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/updated',
			query: {
				end: end,
				start: start,
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
