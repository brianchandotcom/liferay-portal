/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_PagePriceListAccountGroup} from '../models/HeadlessCommerceAdminPricing_v1_0_PagePriceListAccountGroup';
import type {HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup} from '../models/HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV10PriceListAccountGroupService {

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10DeletePriceListAccountGroup(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/priceListAccountGroups/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostPriceListIdPriceListAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/priceListAccountGroups/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PagePriceListAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceListIdPriceListAccountGroupsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PagePriceListAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}/priceListAccountGroups',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostPriceListIdPriceListAccountGroup(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}/priceListAccountGroups',
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
	public static headlessCommerceAdminPricingV10DeletePriceListAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v1.0/priceListAccountGroups/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PagePriceListAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetPriceListByExternalReferenceCodePriceListAccountGroupPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PagePriceListAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}/priceListAccountGroup',
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
	 * @returns HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10PostPriceListByExternalReferenceCodePriceListAccountGroup(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup
	): CancelablePromise<HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}/priceListAccountGroup',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
