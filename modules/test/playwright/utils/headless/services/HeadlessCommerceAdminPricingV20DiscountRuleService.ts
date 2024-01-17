/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_DiscountRule} from '../models/HeadlessCommerceAdminPricing_v2_0_DiscountRule';
import type {HeadlessCommerceAdminPricing_v2_0_PageDiscountRule} from '../models/HeadlessCommerceAdminPricing_v2_0_PageDiscountRule';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20DiscountRuleService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageDiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountByExternalReferenceCodeDiscountRulesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageDiscountRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discount-rules',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountByExternalReferenceCodeDiscountRule(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountRule
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountRule> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discount-rules',
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
	public static headlessCommerceAdminPricingV20DeleteDiscountRuleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/discount-rules/batch',
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
	public static headlessCommerceAdminPricingV20PostDiscountIdDiscountRuleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/discount-rules/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountRule(
		id: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-rules/{id}',
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
	public static headlessCommerceAdminPricingV20DeleteDiscountRule(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/discount-rules/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PatchDiscountRule(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountRule
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountRule> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v2.0/discount-rules/{id}',
			path: {
				id: id,
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageDiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountIdDiscountRulesPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageDiscountRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/{id}/discount-rules',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountIdDiscountRule(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountRule
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountRule> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/{id}/discount-rules',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
