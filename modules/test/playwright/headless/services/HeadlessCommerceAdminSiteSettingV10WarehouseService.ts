/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_PageWarehouse} from '../models/HeadlessCommerceAdminSiteSetting_v1_0_PageWarehouse';
import type {HeadlessCommerceAdminSiteSetting_v1_0_Warehouse} from '../models/HeadlessCommerceAdminSiteSetting_v1_0_Warehouse';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminSiteSettingV10WarehouseService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_Warehouse default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetWarehouse(
		id: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_Warehouse> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/warehouse/{id}',
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
	public static headlessCommerceAdminSiteSettingV10PutWarehouse(
		id: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_Warehouse
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-site-setting/v1.0/warehouse/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10DeleteWarehouse(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/warehouse/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PutWarehouseBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-site-setting/v1.0/warehouse/batch',
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
	public static headlessCommerceAdminSiteSettingV10DeleteWarehouseBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/warehouse/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param groupId
	 * @param active
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_PageWarehouse default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetCommerceAdminSiteSettingGroupWarehousePage(
		groupId: string,
		active?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_PageWarehouse> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/warehouse',
			path: {
				groupId: groupId,
			},
			query: {
				active: active,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param groupId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_Warehouse default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PostCommerceAdminSiteSettingGroupWarehouse(
		groupId: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_Warehouse
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_Warehouse> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/warehouse',
			path: {
				groupId: groupId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
