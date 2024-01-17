/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_DiscountAccount} from '../models/HeadlessCommerceAdminPricing_v2_0_DiscountAccount';
import type {HeadlessCommerceAdminPricing_v2_0_PageDiscountAccount} from '../models/HeadlessCommerceAdminPricing_v2_0_PageDiscountAccount';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20DiscountAccountService {

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageDiscountAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountIdDiscountAccountsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageDiscountAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/{id}/discount-accounts',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountIdDiscountAccount(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountAccount
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/{id}/discount-accounts',
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
	public static headlessCommerceAdminPricingV20DeleteDiscountAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/discount-accounts/batch',
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
	public static headlessCommerceAdminPricingV20PostDiscountIdDiscountAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/discount-accounts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param discountAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeleteDiscountAccount(
		discountAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/discount-accounts/{discountAccountId}',
			path: {
				discountAccountId: discountAccountId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageDiscountAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountByExternalReferenceCodeDiscountAccountsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageDiscountAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discount-accounts',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_DiscountAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostDiscountByExternalReferenceCodeDiscountAccount(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_DiscountAccount
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_DiscountAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discount-accounts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
