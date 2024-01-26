/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WarehouseItem } from '../models/WarehouseItem';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WarehouseItemService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Warehouse Item by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteWarehouseItemByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Warehouse Item by ID.
     * @returns WarehouseItem Successful operation
     * @throws ApiError
     */
    public getWarehouseItemByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<WarehouseItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates a Warehouse Item by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchWarehouseItemByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: WarehouseItem,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/by-externalReferenceCode/{externalReferenceCode}',
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
     * Creates a Warehouse Item by external reference code.
     * @returns WarehouseItem Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postWarehouseItemByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: WarehouseItem,
    }): CancelablePromise<WarehouseItem | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/by-externalReferenceCode/{externalReferenceCode}',
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
     * Retrieves the list of WarehouseItem that have been updated (added or
     * changed) within the given timespan. If only one parameter is used for searching
     * items a default timespan of 30 days is used.
     * @returns WarehouseItem Successful operation
     * @throws ApiError
     */
    public getWarehouseItemsUpdatedPage({
        end,
        start,
        page,
        pageSize,
    }: {
        end?: string,
        start?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WarehouseItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/updated',
            query: {
                'end': end,
                'start': start,
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                400: `Invalid input. Happens if the search parameters are not passed
                or if the end date is prior to start date`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Deletes a Warehouse Item by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteWarehouseItem({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Warehouse Item by ID.
     * @returns WarehouseItem Successful operation
     * @throws ApiError
     */
    public getWarehouseItem({
        id,
    }: {
        id: number,
    }): CancelablePromise<WarehouseItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/{id}',
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
     * Updates a Warehouse Item by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchWarehouseItem({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: WarehouseItem,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-inventory/v1.0/warehouseItems/{id}',
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
     * Gets a list of Warehouse Items related to an Warehouse.
     * @returns WarehouseItem Successful operation
     * @throws ApiError
     */
    public getWarehouseByExternalReferenceCodeWarehouseItemsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WarehouseItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouseItems',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
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
    /**
     * Creates or updates an Warehouse Item.
     * @returns WarehouseItem Created
     * @returns any Async
     * @throws ApiError
     */
    public postWarehouseByExternalReferenceCodeWarehouseItem({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: WarehouseItem,
    }): CancelablePromise<WarehouseItem | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouseItems',
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
     * Gets a list of Warehouse Items related to a Warehouse.
     * @returns WarehouseItem Successful operation
     * @throws ApiError
     */
    public getWarehouseIdWarehouseItemsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WarehouseItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouseItems',
            path: {
                'id': id,
            },
            query: {
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
    /**
     * Creates or updates an Warehouse Item.
     * @returns WarehouseItem Created
     * @returns any Async
     * @throws ApiError
     */
    public postWarehouseIdWarehouseItem({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: WarehouseItem,
    }): CancelablePromise<WarehouseItem | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouseItems',
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
