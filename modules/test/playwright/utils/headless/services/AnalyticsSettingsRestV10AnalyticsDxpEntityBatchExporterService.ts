/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class AnalyticsSettingsRestV10AnalyticsDxpEntityBatchExporterService {

	/**
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10PostConfigurationWizardMode(): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/analytics-settings-rest/v1.0/configuration/wizard-mode',
		});
	}
}
