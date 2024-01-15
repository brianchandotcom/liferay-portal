/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_Field} from '../models/AnalyticsSettingsRest_v1_0_Field';
import type {AnalyticsSettingsRest_v1_0_PageField} from '../models/AnalyticsSettingsRest_v1_0_PageField';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class AnalyticsSettingsRestV10FieldService {

	/**
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns AnalyticsSettingsRest_v1_0_PageField default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetFieldsPeoplePage(
		keyword?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<AnalyticsSettingsRest_v1_0_PageField> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/fields/people',
			query: {
				keyword: keyword,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PatchFieldPeople(
		requestBody?: Array<AnalyticsSettingsRest_v1_0_Field>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/analytics-settings-rest/v1.0/fields/people',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns AnalyticsSettingsRest_v1_0_PageField default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetFieldsProductsPage(
		keyword?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<AnalyticsSettingsRest_v1_0_PageField> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/fields/products',
			query: {
				keyword: keyword,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PatchFieldProduct(
		requestBody?: Array<AnalyticsSettingsRest_v1_0_Field>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/analytics-settings-rest/v1.0/fields/products',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns AnalyticsSettingsRest_v1_0_PageField default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetFieldsAccountsPage(
		keyword?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<AnalyticsSettingsRest_v1_0_PageField> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/fields/accounts',
			query: {
				keyword: keyword,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PatchFieldAccount(
		requestBody?: Array<AnalyticsSettingsRest_v1_0_Field>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/analytics-settings-rest/v1.0/fields/accounts',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns AnalyticsSettingsRest_v1_0_PageField default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetFieldsOrdersPage(
		keyword?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<AnalyticsSettingsRest_v1_0_PageField> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/fields/orders',
			query: {
				keyword: keyword,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PatchFieldOrder(
		requestBody?: Array<AnalyticsSettingsRest_v1_0_Field>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/analytics-settings-rest/v1.0/fields/orders',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
