/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_OptionValue} from '../models/HeadlessCommerceAdminCatalog_v1_0_OptionValue';
import type {HeadlessCommerceAdminCatalog_v1_0_PageOptionValue} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageOptionValue';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10OptionValueService {

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageOptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetOptionIdOptionValuesPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageOptionValue> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/options/{id}/optionValues',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_OptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostOptionIdOptionValue(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_OptionValue
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_OptionValue> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/options/{id}/optionValues',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminCatalog_v1_0_OptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetOptionValueByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_OptionValue> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/optionValues/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteOptionValueByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/optionValues/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchOptionValueByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_OptionValue
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/optionValues/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_OptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetOptionValue(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_OptionValue> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/optionValues/{id}',
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
	public static headlessCommerceAdminCatalogV10DeleteOptionValue(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/optionValues/{id}',
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
	public static headlessCommerceAdminCatalogV10PatchOptionValue(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_OptionValue
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/optionValues/{id}',
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
	public static headlessCommerceAdminCatalogV10PostOptionIdOptionValueBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/options/optionValues/batch',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageOptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetOptionByExternalReferenceCodeOptionValuesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageOptionValue> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}/optionValues',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_OptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostOptionByExternalReferenceCodeOptionValue(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_OptionValue
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_OptionValue> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}/optionValues',
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
	public static headlessCommerceAdminCatalogV10DeleteOptionValueBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/optionValues/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
