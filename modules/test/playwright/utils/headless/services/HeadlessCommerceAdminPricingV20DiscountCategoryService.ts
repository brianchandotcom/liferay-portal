/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_DiscountCategory} from '../models/HeadlessCommerceAdminPricing_v2_0_DiscountCategory';
import type {HeadlessCommerceAdminPricing_v2_0_PageDiscountCategory} from '../models/HeadlessCommerceAdminPricing_v2_0_PageDiscountCategory';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20DiscountCategoryService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeleteDiscountCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/discount-categories/batch',
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
	public static headlessCommerceAdminPricingV20PostDiscountIdDiscountCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/discount-categories/batch',
			query: {
				callbackURL: callbackUrl,
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageDiscountCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountIdDiscountCategoriesPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageDiscountCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/{id}/discount-categories',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountIdDiscountCategory(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountCategory
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/{id}/discount-categories',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param discountCategoryId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeleteDiscountCategory(
		discountCategoryId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/discount-categories/{discountCategoryId}',
			path: {
				discountCategoryId: discountCategoryId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageDiscountCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountByExternalReferenceCodeDiscountCategoriesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageDiscountCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discount-categories',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountByExternalReferenceCodeDiscountCategory(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountCategory
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discount-categories',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
