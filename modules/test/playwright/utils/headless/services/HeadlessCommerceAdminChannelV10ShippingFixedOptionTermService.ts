/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_PageShippingFixedOptionTerm} from '../models/HeadlessCommerceAdminChannel_v1_0_PageShippingFixedOptionTerm';
import type {HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionTerm} from '../models/HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionTerm';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10ShippingFixedOptionTermService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteShippingFixedOptionTermBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-terms/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param shippingFixedOptionTermId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteShippingFixedOptionTerm(
		shippingFixedOptionTermId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-terms/{shippingFixedOptionTermId}',
			path: {
				shippingFixedOptionTermId: shippingFixedOptionTermId,
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageShippingFixedOptionTerm default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetShippingFixedOptionIdShippingFixedOptionTermsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageShippingFixedOptionTerm> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-options/{id}/shipping-fixed-option-terms',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionTerm default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostShippingFixedOptionIdShippingFixedOptionTerm(
		id: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionTerm
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionTerm> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-options/{id}/shipping-fixed-option-terms',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
