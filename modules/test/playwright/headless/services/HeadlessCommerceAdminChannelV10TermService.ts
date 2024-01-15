/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_Term} from '../models/HeadlessCommerceAdminChannel_v1_0_Term';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10TermService {

	/**
	 * @param shippingFixedOptionTermId
	 * @returns HeadlessCommerceAdminChannel_v1_0_Term default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetShippingFixedOptionTermTerm(
		shippingFixedOptionTermId: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Term> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-terms/{shippingFixedOptionTermId}/term',
			path: {
				shippingFixedOptionTermId: shippingFixedOptionTermId,
			},
		});
	}

	/**
	 * @param paymentMethodGroupRelTermId
	 * @returns HeadlessCommerceAdminChannel_v1_0_Term default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetPaymentMethodGroupRelTermTerm(
		paymentMethodGroupRelTermId: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Term> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rel-terms/{paymentMethodGroupRelTermId}/term',
			path: {
				paymentMethodGroupRelTermId: paymentMethodGroupRelTermId,
			},
		});
	}
}
