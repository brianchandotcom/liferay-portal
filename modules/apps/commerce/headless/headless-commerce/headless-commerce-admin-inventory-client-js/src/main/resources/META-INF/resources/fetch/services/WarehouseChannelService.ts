/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WarehouseChannel } from '../models/WarehouseChannel';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WarehouseChannelService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Warehouse Channel by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteWarehouseChannel({
        warehouseChannelId,
    }: {
        warehouseChannelId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-inventory/v1.0/warehouse-channels/{warehouseChannelId}',
            path: {
                'warehouseChannelId': warehouseChannelId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Warehouse Channels.
     * @returns WarehouseChannel Successful operation
     * @throws ApiError
     */
    public getWarehouseByExternalReferenceCodeWarehouseChannelsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WarehouseChannel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouse-channels',
            path: {
                'externalReferenceCode': externalReferenceCode,
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
     * Creates or updates a Warehouse Channel.
     * @returns WarehouseChannel Created
     * @returns any Async
     * @throws ApiError
     */
    public postWarehouseByExternalReferenceCodeWarehouseChannel({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: WarehouseChannel,
    }): CancelablePromise<WarehouseChannel | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouse-channels',
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
     * Gets a list of Warehouse Channels.
     * @returns WarehouseChannel Successful operation
     * @throws ApiError
     */
    public getWarehouseIdWarehouseChannelsPage({
        id,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        id: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<WarehouseChannel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouse-channels',
            path: {
                'id': id,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a Warehouse Channel.
     * @returns WarehouseChannel Created
     * @returns any Async
     * @throws ApiError
     */
    public postWarehouseIdWarehouseChannel({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: WarehouseChannel,
    }): CancelablePromise<WarehouseChannel | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouse-channels',
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
