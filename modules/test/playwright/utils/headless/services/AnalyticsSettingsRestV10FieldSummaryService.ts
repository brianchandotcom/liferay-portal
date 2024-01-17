/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_FieldSummary} from '../models/AnalyticsSettingsRest_v1_0_FieldSummary';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class AnalyticsSettingsRestV10FieldSummaryService {

	/**
	 * @returns AnalyticsSettingsRest_v1_0_FieldSummary default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetField(): CancelablePromise<AnalyticsSettingsRest_v1_0_FieldSummary> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/fields',
		});
	}
}
