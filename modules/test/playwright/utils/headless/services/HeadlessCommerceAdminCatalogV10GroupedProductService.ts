/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_GroupedProduct} from '../models/HeadlessCommerceAdminCatalog_v1_0_GroupedProduct';
import type {HeadlessCommerceAdminCatalog_v1_0_PageGroupedProduct} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageGroupedProduct';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10GroupedProductService {

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageGroupedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdGroupedProductsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageGroupedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/grouped-products',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_GroupedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductIdGroupedProduct(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_GroupedProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_GroupedProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/grouped-products',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageGroupedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeGroupedProductsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageGroupedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/grouped-products',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_GroupedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductByExternalReferenceCodeGroupedProduct(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_GroupedProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_GroupedProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/grouped-products',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param groupedProductId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteGroupedProduct(
		groupedProductId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/grouped-products/{groupedProductId}',
			path: {
				groupedProductId: groupedProductId,
			},
		});
	}

	/**
	 * @param groupedProductId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_GroupedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchGroupedProduct(
		groupedProductId: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_GroupedProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_GroupedProduct> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/grouped-products/{groupedProductId}',
			path: {
				groupedProductId: groupedProductId,
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
	public static headlessCommerceAdminCatalogV10DeleteGroupedProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/grouped-products/batch',
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
	public static headlessCommerceAdminCatalogV10PostProductIdGroupedProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/grouped-products/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
