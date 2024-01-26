/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Product } from '../models/Product';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves products from selected channel.
     * @returns Product
     * @throws ApiError
     */
    public getChannelProductsPage({
        channelId,
        accountId,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        channelId: number,
        accountId?: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Product>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products',
            path: {
                'channelId': channelId,
            },
            query: {
                'accountId': accountId,
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
     * Retrieves products from selected channel.
     * @returns Product
     * @throws ApiError
     */
    public getChannelProduct({
        channelId,
        productId,
        accountId,
    }: {
        channelId: number,
        productId: number,
        accountId?: number,
    }): CancelablePromise<Product> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}',
            path: {
                'channelId': channelId,
                'productId': productId,
            },
            query: {
                'accountId': accountId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
