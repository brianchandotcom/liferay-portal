/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_DiscountRule} from '../models/HeadlessCommerceAdminPricing_v1_0_DiscountRule';
import type {HeadlessCommerceAdminPricing_v1_0_PageDiscountRule} from '../models/HeadlessCommerceAdminPricing_v1_0_PageDiscountRule';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV10DiscountRuleService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10DeleteDiscountRuleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/discountRules/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminPricing_v1_0_DiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetDiscountRule(
		id: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_DiscountRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/discountRules/{id}',
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
	public static headlessCommerceAdminPricingV10DeleteDiscountRule(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/discountRules/{id}',
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
	public static headlessCommerceAdminPricingV10PatchDiscountRule(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_DiscountRule
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v1.0/discountRules/{id}',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PageDiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetDiscountByExternalReferenceCodeDiscountRulesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PageDiscountRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/discount/by-externalReferenceCode/{externalReferenceCode}/discountRules',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_DiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostDiscountByExternalReferenceCodeDiscountRule(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_DiscountRule
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_DiscountRule> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discount/by-externalReferenceCode/{externalReferenceCode}/discountRules',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v1_0_PageDiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetDiscountIdDiscountRulesPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PageDiscountRule> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountRules',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_DiscountRule default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostDiscountIdDiscountRule(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_DiscountRule
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_DiscountRule> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountRules',
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
	public static headlessCommerceAdminPricingV10PostDiscountIdDiscountRuleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/discounts/discountRules/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
