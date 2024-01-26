/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Warehouse } from '../models/Warehouse';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WarehouseService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Warehouse.
     * @returns Warehouse Successful operation
     * @throws ApiError
     */
    public getCommerceAdminSiteSettingGroupWarehousePage({
        groupId,
        active,
        page,
        pageSize,
    }: {
        groupId: number,
        active?: boolean,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Warehouse>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/warehouse',
            path: {
                'groupId': groupId,
            },
            query: {
                'active': active,
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
     * Creates a Warehouse.
     * @returns Warehouse Created
     * @returns any Async
     * @throws ApiError
     */
    public postCommerceAdminSiteSettingGroupWarehouse({
        groupId,
        requestBody,
    }: {
        groupId: number,
        requestBody: Warehouse,
    }): CancelablePromise<Warehouse | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/warehouse',
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
    /**
     * Deletes a Warehouse by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteWarehouse({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-site-setting/v1.0/warehouse/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Warehouse by ID.
     * @returns Warehouse Successful operation
     * @throws ApiError
     */
    public getWarehouse({
        id,
    }: {
        id: number,
    }): CancelablePromise<Warehouse> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/warehouse/{id}',
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
     * Updates a Warehouse by ID.
     * @returns any Async
     * @throws ApiError
     */
    public putWarehouse({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Warehouse,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-admin-site-setting/v1.0/warehouse/{id}',
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
}
