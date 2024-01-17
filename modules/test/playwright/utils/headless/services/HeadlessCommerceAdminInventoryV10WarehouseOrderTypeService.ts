/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_PageWarehouseOrderType} from '../models/HeadlessCommerceAdminInventory_v1_0_PageWarehouseOrderType';
import type {HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType} from '../models/HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminInventoryV10WarehouseOrderTypeService {

	/**
	 * @param warehouseOrderTypeId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10DeleteWarehouseOrderType(
		warehouseOrderTypeId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouse-order-types/{warehouseOrderTypeId}',
			path: {
				warehouseOrderTypeId: warehouseOrderTypeId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10DeleteWarehouseOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouse-order-types/batch',
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
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageWarehouseOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseByExternalReferenceCodeWarehouseOrderTypesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageWarehouseOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouse-order-types',
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
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseByExternalReferenceCodeWarehouseOrderType(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouse-order-types',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageWarehouseOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseIdWarehouseOrderTypesPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageWarehouseOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouse-order-types',
			path: {
				id: id,
			},
			query: {
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseIdWarehouseOrderType(
		id: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouse-order-types',
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
	public static headlessCommerceAdminInventoryV10PostWarehouseIdWarehouseOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/warehouse-order-types/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
