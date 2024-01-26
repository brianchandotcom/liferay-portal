/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AvailabilityEstimate } from '../models/AvailabilityEstimate';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AvailabilityEstimateService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes an Availability Estimate by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteAvailabilityEstimate({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-site-setting/v1.0/availabilityEstimate/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Availability Estimate by ID.
     * @returns AvailabilityEstimate Successful operation
     * @throws ApiError
     */
    public getAvailabilityEstimate({
        id,
    }: {
        id: number,
    }): CancelablePromise<AvailabilityEstimate> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/availabilityEstimate/{id}',
            path: {
                'id': id,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates an Availability Estimate by ID.
     * @returns any Async
     * @throws ApiError
     */
    public putAvailabilityEstimate({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AvailabilityEstimate,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-admin-site-setting/v1.0/availabilityEstimate/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Gets a list of Availability Estimate.
     * @returns AvailabilityEstimate Successful operation
     * @throws ApiError
     */
    public getCommerceAdminSiteSettingGroupAvailabilityEstimatePage({
        groupId,
        page,
        pageSize,
    }: {
        groupId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AvailabilityEstimate>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/availabilityEstimate',
            path: {
                'groupId': groupId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or Updates an Availability Estimate.
     * @returns AvailabilityEstimate Created
     * @returns any Async
     * @throws ApiError
     */
    public postCommerceAdminSiteSettingGroupAvailabilityEstimate({
        groupId,
        requestBody,
    }: {
        groupId: number,
        requestBody: AvailabilityEstimate,
    }): CancelablePromise<AvailabilityEstimate | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/availabilityEstimate',
            path: {
                'groupId': groupId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
