/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_DataSourceToken} from '../models/AnalyticsSettingsRest_v1_0_DataSourceToken';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class AnalyticsSettingsRestV10DataSourceService {

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PostDataSource(
		requestBody?: AnalyticsSettingsRest_v1_0_DataSourceToken
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/analytics-settings-rest/v1.0/data-sources',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10DeleteDataSource(): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/analytics-settings-rest/v1.0/data-sources',
		});
	}
}
