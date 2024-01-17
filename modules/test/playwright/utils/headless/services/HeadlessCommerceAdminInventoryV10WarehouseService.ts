/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_PageWarehouse} from '../models/HeadlessCommerceAdminInventory_v1_0_PageWarehouse';
import type {HeadlessCommerceAdminInventory_v1_0_Warehouse} from '../models/HeadlessCommerceAdminInventory_v1_0_Warehouse';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminInventoryV10WarehouseService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminInventory_v1_0_Warehouse default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_Warehouse> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}',
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
	public static headlessCommerceAdminInventoryV10DeleteWarehouseId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}',
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
	public static headlessCommerceAdminInventoryV10PatchWarehouseId(
		id: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_Warehouse
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehousesPageExportBatch(
		filter?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/export-batch',
			query: {
				filter: filter,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageWarehouse default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehousesPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageWarehouse> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses',
			query: {
				filter: filter,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessCommerceAdminInventory_v1_0_Warehouse default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouse(
		requestBody?: HeadlessCommerceAdminInventory_v1_0_Warehouse
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_Warehouse> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses',
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
	public static headlessCommerceAdminInventoryV10PostWarehouseBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminInventory_v1_0_Warehouse default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_Warehouse> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminInventoryV10DeleteWarehouseByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminInventoryV10PatchWarehouseByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_Warehouse
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
