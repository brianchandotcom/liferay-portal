/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PagePriceListOrderType} from '../models/HeadlessCommerceAdminPricing_v2_0_PagePriceListOrderType';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListOrderType} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceListOrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20PriceListOrderTypeService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceListOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-list-order-types/batch',
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
	 * @param search
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceListOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListIdPriceListOrderTypesPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceListOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/{id}/price-list-order-types',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
			},
		});
	}

	/**
	 * @param priceListOrderTypeId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceListOrderType(
		priceListOrderTypeId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-list-order-types/{priceListOrderTypeId}',
			path: {
				priceListOrderTypeId: priceListOrderTypeId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceListOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListByExternalReferenceCodePriceListOrderTypesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceListOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/by-externalReferenceCode/{externalReferenceCode}/price-list-order-types',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceListOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceListByExternalReferenceCodePriceListOrderType(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceListOrderType
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceListOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/by-externalReferenceCode/{externalReferenceCode}/price-list-order-types',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
