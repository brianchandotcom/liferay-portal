/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PageProductSpecification} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageProductSpecification';
import type {HeadlessCommerceAdminCatalog_v1_0_ProductSpecification} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductSpecification';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductSpecificationService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteProductSpecificationBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/productSpecifications/batch',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageProductSpecification default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdProductSpecificationsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageProductSpecification> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/productSpecifications',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductSpecification default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostProductIdProductSpecification(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductSpecification
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductSpecification> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/productSpecifications',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductSpecification default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductSpecification(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductSpecification> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/productSpecifications/{id}',
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
	public static headlessCommerceAdminCatalogV10DeleteProductSpecification(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/productSpecifications/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductSpecification default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchProductSpecification(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductSpecification
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductSpecification> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/productSpecifications/{id}',
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
	public static headlessCommerceAdminCatalogV10PostProductIdProductSpecificationBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/products/productSpecifications/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
