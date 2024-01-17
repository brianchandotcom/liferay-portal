/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProduct} from '../models/HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProduct';
import type {HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20PriceModifierProductService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceModifierIdPriceModifierProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/price-modifier-products/batch',
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
	public static headlessCommerceAdminPricingV20DeletePriceModifierProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-products/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierByExternalReferenceCodePriceModifierProductsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/by-externalReferenceCode/{externalReferenceCode}/price-modifier-products',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceModifierByExternalReferenceCodePriceModifierProduct(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/by-externalReferenceCode/{externalReferenceCode}/price-modifier-products',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param priceModifierProductId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceModifierProduct(
		priceModifierProductId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-products/{priceModifierProductId}',
			path: {
				priceModifierProductId: priceModifierProductId,
			},
		});
	}

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierIdPriceModifierProductsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/{id}/price-modifier-products',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceModifierIdPriceModifierProduct(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/{id}/price-modifier-products',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
