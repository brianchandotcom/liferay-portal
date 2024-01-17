/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProductGroup} from '../models/HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProductGroup';
import type {HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20PriceModifierProductGroupService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceModifierProductGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-product-groups/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param priceModifierProductGroupId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceModifierProductGroup(
		priceModifierProductGroupId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-product-groups/{priceModifierProductGroupId}',
			path: {
				priceModifierProductGroupId: priceModifierProductGroupId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceModifierIdPriceModifierProductGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/price-modifier-product-groups/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProductGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierByExternalReferenceCodePriceModifierProductGroupsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProductGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/by-externalReferenceCode/{externalReferenceCode}/price-modifier-product-groups',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceModifierByExternalReferenceCodePriceModifierProductGroup(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/by-externalReferenceCode/{externalReferenceCode}/price-modifier-product-groups',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProductGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierIdPriceModifierProductGroupsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProductGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/{id}/price-modifier-product-groups',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceModifierIdPriceModifierProductGroup(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceModifierProductGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifiers/{id}/price-modifier-product-groups',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
