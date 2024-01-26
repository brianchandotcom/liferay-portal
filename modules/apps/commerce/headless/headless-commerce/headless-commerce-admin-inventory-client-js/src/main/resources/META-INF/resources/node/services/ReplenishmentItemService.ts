/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ReplenishmentItem } from '../models/ReplenishmentItem';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ReplenishmentItemService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Replenishment Item by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteReplenishmentItemByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Replenishment Item by external reference code.
     * @returns ReplenishmentItem Successful operation
     * @throws ApiError
     */
    public getReplenishmentItemByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ReplenishmentItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates a Replenishment Item by external reference code.
     * @returns ReplenishmentItem Successful operation
     * @throws ApiError
     */
    public patchReplenishmentItemByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ReplenishmentItem,
    }): CancelablePromise<ReplenishmentItem> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes a Replenishment Item by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteReplenishmentItem({
        replenishmentItemId,
    }: {
        replenishmentItemId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/{replenishmentItemId}',
            path: {
                'replenishmentItemId': replenishmentItemId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Replenishment Item by ID.
     * @returns ReplenishmentItem Successful operation
     * @throws ApiError
     */
    public getReplenishmentItem({
        replenishmentItemId,
    }: {
        replenishmentItemId: number,
    }): CancelablePromise<ReplenishmentItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/{replenishmentItemId}',
            path: {
                'replenishmentItemId': replenishmentItemId,
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
     * Updates a Replenishment Item by ID.
     * @returns ReplenishmentItem Successful operation
     * @throws ApiError
     */
    public patchReplenishmentItem({
        replenishmentItemId,
        requestBody,
    }: {
        replenishmentItemId: number,
        requestBody: ReplenishmentItem,
    }): CancelablePromise<ReplenishmentItem> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-inventory/v1.0/replenishment-items/{replenishmentItemId}',
            path: {
                'replenishmentItemId': replenishmentItemId,
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
     * Gets a list of Replenishment Items related to a SKU.
     * @returns ReplenishmentItem Successful operation
     * @throws ApiError
     */
    public getReplenishmentItemsPage({
        sku,
        page,
        pageSize,
    }: {
        sku: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ReplenishmentItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/skus/by-sku/{sku}/replenishment-items',
            path: {
                'sku': sku,
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
     * Gets a list of Replenishment Items related to a Warehouse.
     * @returns ReplenishmentItem Successful operation
     * @throws ApiError
     */
    public getWarehouseIdReplenishmentItemsPage({
        warehouseId,
        page,
        pageSize,
    }: {
        warehouseId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ReplenishmentItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{warehouseId}/replenishment-items',
            path: {
                'warehouseId': warehouseId,
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
     * Creates a Replenishment Item by warehouse ID and SKU.
     * @returns ReplenishmentItem Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postReplenishmentItem({
        warehouseId,
        sku,
        requestBody,
    }: {
        warehouseId: number,
        sku: string,
        requestBody: ReplenishmentItem,
    }): CancelablePromise<ReplenishmentItem | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{warehouseId}/skus/by-sku/{sku}/replenishment-items',
            path: {
                'warehouseId': warehouseId,
                'sku': sku,
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
