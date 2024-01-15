/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_PageWarehouseChannel} from '../models/HeadlessCommerceAdminInventory_v1_0_PageWarehouseChannel';
import type {HeadlessCommerceAdminInventory_v1_0_WarehouseChannel} from '../models/HeadlessCommerceAdminInventory_v1_0_WarehouseChannel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminInventoryV10WarehouseChannelService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseIdWarehouseChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/warehouse-channels/batch',
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
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageWarehouseChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseByExternalReferenceCodeWarehouseChannelsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageWarehouseChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouse-channels',
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
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseByExternalReferenceCodeWarehouseChannel(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseChannel
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouse-channels',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	public static headlessCommerceAdminInventoryV10DeleteWarehouseChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouse-channels/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param warehouseChannelId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10DeleteWarehouseChannel(
		warehouseChannelId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-inventory/v1.0/warehouse-channels/{warehouseChannelId}',
			path: {
				warehouseChannelId: warehouseChannelId,
			},
		});
	}

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminInventory_v1_0_PageWarehouseChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetWarehouseIdWarehouseChannelsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_PageWarehouseChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouse-channels',
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
	 * @returns HeadlessCommerceAdminInventory_v1_0_WarehouseChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10PostWarehouseIdWarehouseChannel(
		id: string,
		requestBody?: HeadlessCommerceAdminInventory_v1_0_WarehouseChannel
	): CancelablePromise<HeadlessCommerceAdminInventory_v1_0_WarehouseChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouse-channels',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
