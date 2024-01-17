/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Currency} from '../models/HeadlessCommerceAdminCatalog_v1_0_Currency';
import type {HeadlessCommerceAdminCatalog_v1_0_PageCurrency} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageCurrency';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10CurrencyService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_Currency default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetCurrency(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_Currency> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/currencies/{id}',
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
	public static headlessCommerceAdminCatalogV10DeleteCurrency(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/currencies/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_Currency default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchCurrency(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_Currency
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_Currency> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/currencies/{id}',
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
	public static headlessCommerceAdminCatalogV10PostCurrenciesPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/currencies/export-batch',
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

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageCurrency default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetCurrenciesPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageCurrency> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/currencies',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_Currency default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostCurrency(
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_Currency
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_Currency> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/currencies',
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
	public static headlessCommerceAdminCatalogV10PostCurrencyBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/currencies/batch',
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
	public static headlessCommerceAdminCatalogV10DeleteCurrencyBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/currencies/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
