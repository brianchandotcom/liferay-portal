/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PageProductOption} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageProductOption';
import type {HeadlessCommerceAdminCatalog_v1_0_ProductOption} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductOption';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductOptionService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteProductOptionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/productOptions/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductOption(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductOption> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/productOptions/{id}',
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
	public static headlessCommerceAdminCatalogV10DeleteProductOption(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/productOptions/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchProductOption(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductOption
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/productOptions/{id}',
			path: {
				id: id,
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageProductOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeProductOptionsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageProductOption> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/productOptions',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageProductOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductByExternalReferenceCodeProductOptionsPage(
		externalReferenceCode: string,
		requestBody?: Array<HeadlessCommerceAdminCatalog_v1_0_ProductOption>
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageProductOption> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/productOptions',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageProductOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdProductOptionsPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageProductOption> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/productOptions',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageProductOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductIdProductOptionsPage(
		id: string,
		requestBody?: Array<HeadlessCommerceAdminCatalog_v1_0_ProductOption>
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageProductOption> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/productOptions',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
