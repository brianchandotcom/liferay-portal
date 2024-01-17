/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_PageTaxCategory} from '../models/HeadlessCommerceAdminSiteSetting_v1_0_PageTaxCategory';
import type {HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory} from '../models/HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminSiteSettingV10TaxCategoryService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetTaxCategory(
		id: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/taxCategory/{id}',
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
	public static headlessCommerceAdminSiteSettingV10PutTaxCategory(
		id: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-site-setting/v1.0/taxCategory/{id}',
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
	public static headlessCommerceAdminSiteSettingV10DeleteTaxCategory(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/taxCategory/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param groupId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_PageTaxCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetCommerceAdminSiteSettingGroupTaxCategoryPage(
		groupId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_PageTaxCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/taxCategory',
			path: {
				groupId: groupId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param groupId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PostCommerceAdminSiteSettingGroupTaxCategory(
		groupId: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/taxCategory',
			path: {
				groupId: groupId,
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
	public static headlessCommerceAdminSiteSettingV10PutTaxCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-site-setting/v1.0/taxCategory/batch',
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
	public static headlessCommerceAdminSiteSettingV10DeleteTaxCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/taxCategory/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
