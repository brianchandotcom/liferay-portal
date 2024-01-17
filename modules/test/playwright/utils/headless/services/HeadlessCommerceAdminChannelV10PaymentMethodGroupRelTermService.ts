/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelTerm} from '../models/HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelTerm';
import type {HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm} from '../models/HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10PaymentMethodGroupRelTermService {

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelTerm default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetPaymentMethodGroupRelIdPaymentMethodGroupRelTermsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelTerm> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rels/{id}/payment-method-group-rel-terms',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostPaymentMethodGroupRelIdPaymentMethodGroupRelTerm(
		id: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rels/{id}/payment-method-group-rel-terms',
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
	public static headlessCommerceAdminChannelV10DeletePaymentMethodGroupRelTermBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rel-terms/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param paymentMethodGroupRelTermId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeletePaymentMethodGroupRelTerm(
		paymentMethodGroupRelTermId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rel-terms/{paymentMethodGroupRelTermId}',
			path: {
				paymentMethodGroupRelTermId: paymentMethodGroupRelTermId,
			},
		});
	}
}
