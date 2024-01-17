/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PagePriceEntry} from '../models/HeadlessCommerceAdminPricing_v2_0_PagePriceEntry';
import type {HeadlessCommerceAdminPricing_v2_0_PriceEntry} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceEntry';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20PriceEntryService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceEntryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/batch',
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
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceEntryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/price-entries/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListIdPriceEntriesPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/{id}/price-entries',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceEntry(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceEntry
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/{id}/price-entries',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param priceEntryId
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceEntry(
		priceEntryId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/{priceEntryId}',
			path: {
				priceEntryId: priceEntryId,
			},
		});
	}

	/**
	 * @param priceEntryId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceEntry(
		priceEntryId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/{priceEntryId}',
			path: {
				priceEntryId: priceEntryId,
			},
		});
	}

	/**
	 * @param priceEntryId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PatchPriceEntry(
		priceEntryId: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceEntry
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/{priceEntryId}',
			path: {
				priceEntryId: priceEntryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListByExternalReferenceCodePriceEntriesPage(
		externalReferenceCode: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/by-externalReferenceCode/{externalReferenceCode}/price-entries',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceListByExternalReferenceCodePriceEntry(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceEntry
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/by-externalReferenceCode/{externalReferenceCode}/price-entries',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceEntryByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminPricingV20DeletePriceEntryByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PatchPriceEntryByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceEntry
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
