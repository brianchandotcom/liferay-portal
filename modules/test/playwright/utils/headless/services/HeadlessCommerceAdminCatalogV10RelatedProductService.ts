/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PageRelatedProduct} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageRelatedProduct';
import type {HeadlessCommerceAdminCatalog_v1_0_RelatedProduct} from '../models/HeadlessCommerceAdminCatalog_v1_0_RelatedProduct';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10RelatedProductService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteRelatedProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/relatedProducts/batch',
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
	public static headlessCommerceAdminCatalogV10PostProductIdRelatedProductBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/relatedProducts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_RelatedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetRelatedProduct(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_RelatedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/relatedProducts/{id}',
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
	public static headlessCommerceAdminCatalogV10DeleteRelatedProduct(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/relatedProducts/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param type
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageRelatedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeRelatedProductsPage(
		externalReferenceCode: string,
		type?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageRelatedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/relatedProducts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				type: type,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_RelatedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductByExternalReferenceCodeRelatedProduct(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_RelatedProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_RelatedProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/relatedProducts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param type
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageRelatedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdRelatedProductsPage(
		id: string,
		type?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageRelatedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/relatedProducts',
			path: {
				id: id,
			},
			query: {
				type: type,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_RelatedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductIdRelatedProduct(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_RelatedProduct
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_RelatedProduct> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/relatedProducts',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
