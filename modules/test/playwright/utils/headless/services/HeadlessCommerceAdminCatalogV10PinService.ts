/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PagePin} from '../models/HeadlessCommerceAdminCatalog_v1_0_PagePin';
import type {HeadlessCommerceAdminCatalog_v1_0_Pin} from '../models/HeadlessCommerceAdminCatalog_v1_0_Pin';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10PinService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductIdPinBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/pins/batch',
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
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PagePin default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodePinsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PagePin> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/pins',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_Pin default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductByExternalReferenceCodePin(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_Pin
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_Pin> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/pins',
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
	public static headlessCommerceAdminCatalogV10DeletePinBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/pins/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param pinId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeletePin(
		pinId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/pins/{pinId}',
			path: {
				pinId: pinId,
			},
		});
	}

	/**
	 * @param pinId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_Pin default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchPin(
		pinId: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_Pin
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_Pin> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/pins/{pinId}',
			path: {
				pinId: pinId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PagePin default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdPinsPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PagePin> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/pins',
			path: {
				id: id,
			},
			query: {
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_Pin default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductIdPin(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_Pin
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_Pin> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/pins',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
