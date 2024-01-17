/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_PageShippingFixedOptionOrderType} from '../models/HeadlessCommerceAdminChannel_v1_0_PageShippingFixedOptionOrderType';
import type {HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionOrderType} from '../models/HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionOrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10ShippingFixedOptionOrderTypeService {

	/**
	 * @param shippingFixedOptionOrderTypeId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteShippingFixedOptionOrderType(
		shippingFixedOptionOrderTypeId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-order-types/{shippingFixedOptionOrderTypeId}',
			path: {
				shippingFixedOptionOrderTypeId: shippingFixedOptionOrderTypeId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteShippingFixedOptionOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-order-types/batch',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageShippingFixedOptionOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetShippingFixedOptionIdShippingFixedOptionOrderTypesPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageShippingFixedOptionOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-options/{id}/shipping-fixed-option-order-types',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostShippingFixedOptionIdShippingFixedOptionOrderType(
		id: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionOrderType
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-options/{id}/shipping-fixed-option-order-types',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
