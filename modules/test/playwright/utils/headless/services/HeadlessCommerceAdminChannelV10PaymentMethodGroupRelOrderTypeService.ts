/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelOrderType} from '../models/HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelOrderType';
import type {HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelOrderType} from '../models/HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelOrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10PaymentMethodGroupRelOrderTypeService {

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetPaymentMethodGroupRelIdPaymentMethodGroupRelOrderTypesPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rels/{id}/payment-method-group-rel-order-types',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostPaymentMethodGroupRelIdPaymentMethodGroupRelOrderType(
		id: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelOrderType
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rels/{id}/payment-method-group-rel-order-types',
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
	public static headlessCommerceAdminChannelV10DeletePaymentMethodGroupRelOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rel-order-types/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param paymentMethodGroupRelOrderTypeId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeletePaymentMethodGroupRelOrderType(
		paymentMethodGroupRelOrderTypeId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rel-order-types/{paymentMethodGroupRelOrderTypeId}',
			path: {
				paymentMethodGroupRelOrderTypeId:
					paymentMethodGroupRelOrderTypeId,
			},
		});
	}
}
