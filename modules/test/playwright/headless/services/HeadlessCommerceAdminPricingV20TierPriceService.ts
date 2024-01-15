/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PageTierPrice} from '../models/HeadlessCommerceAdminPricing_v2_0_PageTierPrice';
import type {HeadlessCommerceAdminPricing_v2_0_TierPrice} from '../models/HeadlessCommerceAdminPricing_v2_0_TierPrice';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20TierPriceService {

	/**
	 * @param priceEntryId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceEntryIdTierPriceBatch(
		priceEntryId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/{priceEntryId}/tier-prices/batch',
			path: {
				priceEntryId: priceEntryId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageTierPrice default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceEntryByExternalReferenceCodeTierPricesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageTierPrice> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/by-externalReferenceCode/{externalReferenceCode}/tier-prices',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_TierPrice default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceEntryByExternalReferenceCodeTierPrice(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_TierPrice
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_TierPrice> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/by-externalReferenceCode/{externalReferenceCode}/tier-prices',
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
	public static headlessCommerceAdminPricingV20DeleteTierPriceBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/tier-prices/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminPricing_v2_0_TierPrice default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetTierPriceByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_TierPrice> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/tier-prices/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeleteTierPriceByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/tier-prices/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PatchTierPriceByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_TierPrice
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v2.0/tier-prices/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminPricing_v2_0_TierPrice default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetTierPrice(
		id: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_TierPrice> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/tier-prices/{id}',
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
	public static headlessCommerceAdminPricingV20DeleteTierPrice(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/tier-prices/{id}',
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
	public static headlessCommerceAdminPricingV20PatchTierPrice(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_TierPrice
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v2.0/tier-prices/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param priceEntryId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v2_0_PageTierPrice default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceEntryIdTierPricesPage(
		priceEntryId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PageTierPrice> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/{priceEntryId}/tier-prices',
			path: {
				priceEntryId: priceEntryId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param priceEntryId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v2_0_TierPrice default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceEntryIdTierPrice(
		priceEntryId: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_TierPrice
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_TierPrice> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/{priceEntryId}/tier-prices',
			path: {
				priceEntryId: priceEntryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
