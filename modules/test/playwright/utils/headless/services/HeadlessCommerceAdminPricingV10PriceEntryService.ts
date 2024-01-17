/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_PagePriceEntry} from '../models/HeadlessCommerceAdminPricing_v1_0_PagePriceEntry';
import type {HeadlessCommerceAdminPricing_v1_0_PriceEntry} from '../models/HeadlessCommerceAdminPricing_v1_0_PriceEntry';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV10PriceEntryService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceEntryByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceEntries/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminPricingV10DeletePriceEntryByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/priceEntries/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminPricingV10PatchPriceEntryByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceEntry
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v1.0/priceEntries/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminPricingV10PostPriceListIdPriceEntryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/priceEntries/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PagePriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceListByExternalReferenceCodePriceEntriesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PagePriceEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}/priceEntries',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostPriceListByExternalReferenceCodePriceEntry(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceEntry
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}/priceEntries',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceEntry(
		id: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceEntries/{id}',
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
	public static headlessCommerceAdminPricingV10DeletePriceEntry(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/priceEntries/{id}',
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
	public static headlessCommerceAdminPricingV10PatchPriceEntry(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceEntry
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v1.0/priceEntries/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v1_0_PagePriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceListIdPriceEntriesPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PagePriceEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}/priceEntries',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostPriceListIdPriceEntry(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceEntry
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}/priceEntries',
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
	public static headlessCommerceAdminPricingV10DeletePriceEntryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/priceEntries/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
