/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate} from '../models/HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate';
import type {HeadlessCommerceAdminSiteSetting_v1_0_PageAvailabilityEstimate} from '../models/HeadlessCommerceAdminSiteSetting_v1_0_PageAvailabilityEstimate';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminSiteSettingV10AvailabilityEstimateService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetAvailabilityEstimate(
		id: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/availabilityEstimate/{id}',
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
	public static headlessCommerceAdminSiteSettingV10PutAvailabilityEstimate(
		id: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-site-setting/v1.0/availabilityEstimate/{id}',
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
	public static headlessCommerceAdminSiteSettingV10DeleteAvailabilityEstimate(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/availabilityEstimate/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param groupId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_PageAvailabilityEstimate default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetCommerceAdminSiteSettingGroupAvailabilityEstimatePage(
		groupId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_PageAvailabilityEstimate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/availabilityEstimate',
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
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PostCommerceAdminSiteSettingGroupAvailabilityEstimate(
		groupId: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/availabilityEstimate',
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
	public static headlessCommerceAdminSiteSettingV10PutAvailabilityEstimateBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-site-setting/v1.0/availabilityEstimate/batch',
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
	public static headlessCommerceAdminSiteSettingV10DeleteAvailabilityEstimateBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/availabilityEstimate/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
