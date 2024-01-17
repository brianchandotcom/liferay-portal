/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PagePriceListChannel} from '../models/HeadlessCommerceAdminPricing_v2_0_PagePriceListChannel';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListChannel} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceListChannel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20PriceListChannelService {

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceListChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListIdPriceListChannelsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceListChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/{id}/price-list-channels',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceListChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceListChannel(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceListChannel
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceListChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/{id}/price-list-channels',
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
	public static headlessCommerceAdminPricingV20DeletePriceListChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-list-channels/batch',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PagePriceListChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListByExternalReferenceCodePriceListChannelsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PagePriceListChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/by-externalReferenceCode/{externalReferenceCode}/price-list-channels',
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
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceListChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceListByExternalReferenceCodePriceListChannel(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceListChannel
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceListChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/by-externalReferenceCode/{externalReferenceCode}/price-list-channels',
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
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceListChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/price-list-channels/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param priceListChannelId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20DeletePriceListChannel(
		priceListChannelId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-pricing/v2.0/price-list-channels/{priceListChannelId}',
			path: {
				priceListChannelId: priceListChannelId,
			},
		});
	}
}
