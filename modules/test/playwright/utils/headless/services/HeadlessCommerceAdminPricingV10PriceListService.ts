/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_PagePriceList} from '../models/HeadlessCommerceAdminPricing_v1_0_PagePriceList';
import type {HeadlessCommerceAdminPricing_v1_0_PriceList} from '../models/HeadlessCommerceAdminPricing_v1_0_PriceList';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV10PriceListService {

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessCommerceAdminPricing_v1_0_PagePriceList default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceListsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PagePriceList> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists',
			query: {
				filter: filter,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceList default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostPriceList(
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceList
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceList> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostPriceListsPageExportBatch(
		filter?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/export-batch',
			query: {
				filter: filter,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceList default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceList(
		id: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceList> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}',
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
	public static headlessCommerceAdminPricingV10DeletePriceList(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}',
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
	public static headlessCommerceAdminPricingV10PatchPriceList(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceList
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}',
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
	public static headlessCommerceAdminPricingV10PostPriceListBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/batch',
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
	public static headlessCommerceAdminPricingV10DeletePriceListBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceList default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceListByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceList> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminPricingV10DeletePriceListByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminPricingV10PatchPriceListByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceList
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
