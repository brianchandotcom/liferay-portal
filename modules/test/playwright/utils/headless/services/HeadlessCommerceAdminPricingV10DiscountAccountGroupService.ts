/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup} from '../models/HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup';
import type {HeadlessCommerceAdminPricing_v1_0_PageDiscountAccountGroup} from '../models/HeadlessCommerceAdminPricing_v1_0_PageDiscountAccountGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV10DiscountAccountGroupService {

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10DeleteDiscountAccountGroup(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/discountAccountGroups/{id}',
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
	public static headlessCommerceAdminPricingV10PostDiscountIdDiscountAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/discountAccountGroups/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v1_0_PageDiscountAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetDiscountIdDiscountAccountGroupsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PageDiscountAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountAccountGroups',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostDiscountIdDiscountAccountGroup(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountAccountGroups',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v1_0_PageDiscountAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PageDiscountAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountAccountGroups',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostDiscountByExternalReferenceCodeDiscountAccountGroup(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_DiscountAccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountAccountGroups',
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
	public static headlessCommerceAdminPricingV10DeleteDiscountAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/discountAccountGroups/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
