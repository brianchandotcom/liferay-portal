/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountForecast } from '../models/AccountForecast';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountForecastService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Get the forecast points
     * @returns AccountForecast Successful operation
     * @throws ApiError
     */
    public getAccountForecastsByMonthlyRevenuePage({
        accountIds,
        forecastLength,
        forecastStartDate,
        historyLength,
        page,
        pageSize,
    }: {
        accountIds?: Array<number>,
        forecastLength?: number,
        forecastStartDate?: string,
        historyLength?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountForecast>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-machine-learning/v1.0/accountForecasts/by-monthlyRevenue',
            query: {
                'accountIds': accountIds,
                'forecastLength': forecastLength,
                'forecastStartDate': forecastStartDate,
                'historyLength': historyLength,
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
