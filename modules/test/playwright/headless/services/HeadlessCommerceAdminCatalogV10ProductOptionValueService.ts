/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PageProductOptionValue} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageProductOptionValue';
import type {HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductOptionValueService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteProductOptionValueBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/product-option-values/batch',
			query: {
				callbackURL: callbackUrl,
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageProductOptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductOptionIdProductOptionValuesPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageProductOptionValue> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/productOptions/{id}/productOptionValues',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductOptionIdProductOptionValue(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/productOptions/{id}/productOptionValues',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductOptionValue(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/product-option-values/{id}',
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
	public static headlessCommerceAdminCatalogV10DeleteProductOptionValue(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/product-option-values/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchProductOptionValue(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/product-option-values/{id}',
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
	public static headlessCommerceAdminCatalogV10PostProductOptionIdProductOptionValueBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/productOptions/productOptionValues/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
