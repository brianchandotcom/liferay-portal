/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_MappedProduct} from '../models/HeadlessCommerceAdminCatalog_v1_0_MappedProduct';
import type {HeadlessCommerceAdminCatalog_v1_0_PageMappedProduct} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageMappedProduct';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10MappedProductService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductIdMappedProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/mapped-products/batch',
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
	public static headlessCommerceAdminCatalogV10DeleteMappedProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/mapped-products/batch',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageMappedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdMappedProductsPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageMappedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/mapped-products',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_MappedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductIdMappedProduct(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_MappedProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_MappedProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/mapped-products',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param sequence
	 * @returns HeadlessCommerceAdminCatalog_v1_0_MappedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeMappedProductBySequence(
		externalReferenceCode: string,
		sequence: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_MappedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/mapped-products/by-sequence/{sequence}',
			path: {
				externalReferenceCode: externalReferenceCode,
				sequence: sequence,
			},
		});
	}

	/**
	 * @param mappedProductId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteMappedProduct(
		mappedProductId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/mapped-products/{mappedProductId}',
			path: {
				mappedProductId: mappedProductId,
			},
		});
	}

	/**
	 * @param mappedProductId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_MappedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchMappedProduct(
		mappedProductId: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_MappedProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_MappedProduct> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/mapped-products/{mappedProductId}',
			path: {
				mappedProductId: mappedProductId,
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageMappedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeMappedProductsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageMappedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/mapped-products',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_MappedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductByExternalReferenceCodeMappedProduct(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_MappedProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_MappedProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/mapped-products',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param sequence
	 * @returns HeadlessCommerceAdminCatalog_v1_0_MappedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdMappedProductBySequence(
		id: string,
		sequence: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_MappedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/mapped-products/by-sequence/{sequence}',
			path: {
				id: id,
				sequence: sequence,
			},
		});
	}
}
