/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AnalyticsDxpEntityBatchExporterService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns void
     * @throws ApiError
     */
    public postAnalyticsSettingsRestV10ConfigurationWizardMode(): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/analytics-settings-rest/v1.0/configuration/wizard-mode',
        });
    }
}
