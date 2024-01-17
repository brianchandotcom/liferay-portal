/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PagePriceModifierCategory} from '../models/HeadlessCommerceAdminPricing_v2_0_PagePriceModifierCategory';
import type {HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20PriceModifierCategoryService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceModifierCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-categories/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceModifierCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierByExternalReferenceCodePriceModifierCategoriesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceModifierCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/by-externalReferenceCode/{externalReferenceCode}/price-modifier-categories',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceModifierByExternalReferenceCodePriceModifierCategory(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/by-externalReferenceCode/{externalReferenceCode}/price-modifier-categories',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param priceModifierCategoryId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceModifierCategory(
		priceModifierCategoryId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-categories/{priceModifierCategoryId}',
			path: {
				priceModifierCategoryId: priceModifierCategoryId,
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceModifierCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierIdPriceModifierCategoriesPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceModifierCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/{id}/price-modifier-categories',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceModifierIdPriceModifierCategory(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceModifierCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/{id}/price-modifier-categories',
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
	public static headlessCommerceAdminPricingV20PostPriceModifierIdPriceModifierCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/price-modifier-categories/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
