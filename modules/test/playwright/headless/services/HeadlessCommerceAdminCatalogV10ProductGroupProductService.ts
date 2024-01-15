/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PageProductGroupProduct} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageProductGroupProduct';
import type {HeadlessCommerceAdminCatalog_v1_0_ProductGroupProduct} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductGroupProduct';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductGroupProductService {

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteProductGroupProduct(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/product-group-products/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductGroupIdProductGroupProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/product-groups/product-group-products/batch',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageProductGroupProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductGroupByExternalReferenceCodeProductGroupProductsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageProductGroupProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/product-groups/by-externalReferenceCode/{externalReferenceCode}/product-group-products',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductGroupProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductGroupByExternalReferenceCodeProductGroupProduct(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductGroupProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductGroupProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/product-groups/by-externalReferenceCode/{externalReferenceCode}/product-group-products',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageProductGroupProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductGroupIdProductGroupProductsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageProductGroupProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/product-groups/{id}/product-group-products',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductGroupProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductGroupIdProductGroupProduct(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductGroupProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductGroupProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/product-groups/{id}/product-group-products',
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
	public static headlessCommerceAdminCatalogV10DeleteProductGroupProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/product-group-products/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
