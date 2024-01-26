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
     * Gets a List of Warehouses.
     * @returns Warehouse Successful operation
     * @throws ApiError
     */
    public getWarehousesPage({
        filter,
        page,
        pageSize,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<Warehouse>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
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
     * Creates or updates a Warehouse.
     * @returns Warehouse Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postWarehouse({
        requestBody,
    }: {
        requestBody: Warehouse,
    }): CancelablePromise<Warehouse | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses',
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
     * Deletes a Warehouse by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteWarehouseByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Warehouse by external reference code.
     * @returns Warehouse Successful operation
     * @throws ApiError
     */
    public getWarehouseByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Warehouse> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
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
     * Updates a Warehouse by external reference code.
     * @returns any Async
     * @throws ApiError
     */
    public patchWarehouseByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Warehouse,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
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
    public deleteWarehouseId({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}',
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
    public getWarehouseId({
        id,
    }: {
        id: number,
    }): CancelablePromise<Warehouse> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}',
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
    public patchWarehouseId({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Warehouse,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}',
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
