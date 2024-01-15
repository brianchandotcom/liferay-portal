/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPayment_v1_0_PagePayment} from '../models/HeadlessCommerceAdminPayment_v1_0_PagePayment';
import type {HeadlessCommerceAdminPayment_v1_0_Payment} from '../models/HeadlessCommerceAdminPayment_v1_0_Payment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPaymentV10PaymentService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminPayment_v1_0_Payment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10GetPayment(
		id: string
	): CancelablePromise<HeadlessCommerceAdminPayment_v1_0_Payment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-payment/v1.0/payments/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10DeletePayment(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-payment/v1.0/payments/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPayment_v1_0_Payment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10PatchPayment(
		id: string,
		requestBody?: HeadlessCommerceAdminPayment_v1_0_Payment
	): CancelablePromise<HeadlessCommerceAdminPayment_v1_0_Payment> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-payment/v1.0/payments/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminPayment_v1_0_Payment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10PostPaymentRefund(
		id: string
	): CancelablePromise<HeadlessCommerceAdminPayment_v1_0_Payment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-payment/v1.0/payments/{id}/refund',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminPayment_v1_0_Payment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10GetPaymentByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminPayment_v1_0_Payment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-payment/v1.0/payments/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10DeletePaymentByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-payment/v1.0/payments/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPayment_v1_0_Payment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10PatchPaymentByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminPayment_v1_0_Payment
	): CancelablePromise<HeadlessCommerceAdminPayment_v1_0_Payment> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-payment/v1.0/payments/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminPayment_v1_0_PagePayment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10GetPaymentsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminPayment_v1_0_PagePayment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-payment/v1.0/payments',
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
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPayment_v1_0_Payment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10PostPayment(
		requestBody?: HeadlessCommerceAdminPayment_v1_0_Payment
	): CancelablePromise<HeadlessCommerceAdminPayment_v1_0_Payment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-payment/v1.0/payments',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10PostPaymentsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-payment/v1.0/payments/export-batch',
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10PostPaymentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-payment/v1.0/payments/batch',
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
	public static headlessCommerceAdminPaymentV10DeletePaymentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-payment/v1.0/payments/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminPayment_v1_0_Payment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10PostPaymentByExternalReferenceCodeRefund(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminPayment_v1_0_Payment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-payment/v1.0/payments/by-externalReferenceCode/{externalReferenceCode}/refund',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}
}
