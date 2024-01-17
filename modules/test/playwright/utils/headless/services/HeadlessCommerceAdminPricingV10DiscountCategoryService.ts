/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_DiscountCategory} from '../models/HeadlessCommerceAdminPricing_v1_0_DiscountCategory';
import type {HeadlessCommerceAdminPricing_v1_0_PageDiscountCategory} from '../models/HeadlessCommerceAdminPricing_v1_0_PageDiscountCategory';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV10DiscountCategoryService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostDiscountIdDiscountCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/discountCategories/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PageDiscountCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetDiscountIdDiscountCategoriesPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PageDiscountCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountCategories',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_DiscountCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostDiscountIdDiscountCategory(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_DiscountCategory
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_DiscountCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountCategories',
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
	public static headlessCommerceAdminPricingV10DeleteDiscountCategory(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/discountCategories/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v1_0_PageDiscountCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetDiscountByExternalReferenceCodeDiscountCategoriesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PageDiscountCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountCategories',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_DiscountCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostDiscountByExternalReferenceCodeDiscountCategory(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_DiscountCategory
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_DiscountCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountCategories',
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
	public static headlessCommerceAdminPricingV10DeleteDiscountCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/discountCategories/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
