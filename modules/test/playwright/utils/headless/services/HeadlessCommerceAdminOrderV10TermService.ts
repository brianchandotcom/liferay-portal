/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_PageTerm} from '../models/HeadlessCommerceAdminOrder_v1_0_PageTerm';
import type {HeadlessCommerceAdminOrder_v1_0_Term} from '../models/HeadlessCommerceAdminOrder_v1_0_Term';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10TermService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostTermBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/terms/batch',
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
	public static headlessCommerceAdminOrderV10DeleteTermBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/terms/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_Term default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetTerm(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Term> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/terms/{id}',
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
	public static headlessCommerceAdminOrderV10DeleteTerm(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/terms/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_Term default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PatchTerm(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_Term
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Term> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/terms/{id}',
			path: {
				id: id,
			},
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
	public static headlessCommerceAdminOrderV10PostTermsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/terms/export-batch',
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
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminOrder_v1_0_Term default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetTermByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Term> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/terms/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminOrderV10DeleteTermByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/terms/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_Term default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PatchTermByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_Term
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Term> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/terms/by-externalReferenceCode/{externalReferenceCode}',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageTerm default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetTermsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageTerm> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/terms',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_Term default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostTerm(
		requestBody?: HeadlessCommerceAdminOrder_v1_0_Term
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Term> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/terms',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
