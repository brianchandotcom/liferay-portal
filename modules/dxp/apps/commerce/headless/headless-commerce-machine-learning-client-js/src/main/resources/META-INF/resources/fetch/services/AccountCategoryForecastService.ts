/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountCategoryForecast } from '../models/AccountCategoryForecast';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountCategoryForecastService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Get the forecast points
     * @returns AccountCategoryForecast Successful operation
     * @throws ApiError
     */
    public getAccountCategoryForecastsByMonthlyRevenuePage({
        accountIds,
        categoryIds,
        forecastLength,
        forecastStartDate,
        historyLength,
        page,
        pageSize,
    }: {
        accountIds?: Array<number>,
        categoryIds?: Array<number>,
        forecastLength?: number,
        forecastStartDate?: string,
        historyLength?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountCategoryForecast>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-machine-learning/v1.0/accountCategoryForecasts/by-monthlyRevenue',
            query: {
                'accountIds': accountIds,
                'categoryIds': categoryIds,
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
