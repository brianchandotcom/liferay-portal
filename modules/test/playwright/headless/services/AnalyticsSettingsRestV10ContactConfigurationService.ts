/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_ContactConfiguration} from '../models/AnalyticsSettingsRest_v1_0_ContactConfiguration';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class AnalyticsSettingsRestV10ContactConfigurationService {

	/**
	 * @returns AnalyticsSettingsRest_v1_0_ContactConfiguration default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetContactConfiguration(): CancelablePromise<AnalyticsSettingsRest_v1_0_ContactConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/contacts/configuration',
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PutContactConfiguration(
		requestBody?: AnalyticsSettingsRest_v1_0_ContactConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/analytics-settings-rest/v1.0/contacts/configuration',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
