/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PageSpecification} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageSpecification';
import type {HeadlessCommerceAdminCatalog_v1_0_Specification} from '../models/HeadlessCommerceAdminCatalog_v1_0_Specification';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10SpecificationService {

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageSpecification default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSpecificationsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageSpecification> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/specifications',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_Specification default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostSpecification(
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_Specification
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_Specification> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/specifications',
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
	public static headlessCommerceAdminCatalogV10PostSpecificationBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/specifications/batch',
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
	public static headlessCommerceAdminCatalogV10DeleteSpecificationBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/specifications/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_Specification default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSpecification(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_Specification> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/specifications/{id}',
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
	public static headlessCommerceAdminCatalogV10DeleteSpecification(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/specifications/{id}',
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
	public static headlessCommerceAdminCatalogV10PatchSpecification(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_Specification
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/specifications/{id}',
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
	public static headlessCommerceAdminCatalogV10PostSpecificationsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/specifications/export-batch',
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
}
