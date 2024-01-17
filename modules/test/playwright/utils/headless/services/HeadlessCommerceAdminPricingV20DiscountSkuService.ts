/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_DiscountSku} from '../models/HeadlessCommerceAdminPricing_v2_0_DiscountSku';
import type {HeadlessCommerceAdminPricing_v2_0_PageDiscountSku} from '../models/HeadlessCommerceAdminPricing_v2_0_PageDiscountSku';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20DiscountSkuService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageDiscountSku default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountByExternalReferenceCodeDiscountSkusPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageDiscountSku> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discount-skus',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountSku default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountByExternalReferenceCodeDiscountSku(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountSku
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountSku> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discount-skus',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageDiscountSku default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountIdDiscountSkusPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageDiscountSku> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/{id}/discount-skus',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountSku default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountIdDiscountSku(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountSku
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountSku> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/{id}/discount-skus',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param discountSkuId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeleteDiscountSku(
		discountSkuId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/discount-skus/{discountSkuId}',
			path: {
				discountSkuId: discountSkuId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountIdDiscountSkuBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/discount-skus/batch',
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
	public static headlessCommerceAdminPricingV20DeleteDiscountSkuBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/discount-skus/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
