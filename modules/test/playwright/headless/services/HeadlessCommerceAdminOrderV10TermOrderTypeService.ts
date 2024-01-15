/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_PageTermOrderType} from '../models/HeadlessCommerceAdminOrder_v1_0_PageTermOrderType';
import type {HeadlessCommerceAdminOrder_v1_0_TermOrderType} from '../models/HeadlessCommerceAdminOrder_v1_0_TermOrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10TermOrderTypeService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageTermOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetTermByExternalReferenceCodeTermOrderTypesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageTermOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/terms/by-externalReferenceCode/{externalReferenceCode}/term-order-types',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_TermOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostTermByExternalReferenceCodeTermOrderType(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_TermOrderType
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_TermOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/terms/by-externalReferenceCode/{externalReferenceCode}/term-order-types',
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
	public static headlessCommerceAdminOrderV10DeleteTermOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/term-order-types/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param termOrderTypeId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteTermOrderType(
		termOrderTypeId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/term-order-types/{termOrderTypeId}',
			path: {
				termOrderTypeId: termOrderTypeId,
			},
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @param search
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageTermOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetTermIdTermOrderTypesPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageTermOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/terms/{id}/term-order-types',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_TermOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostTermIdTermOrderType(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_TermOrderType
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_TermOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/terms/{id}/term-order-types',
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
	public static headlessCommerceAdminOrderV10PostTermIdTermOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/terms/term-order-types/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
