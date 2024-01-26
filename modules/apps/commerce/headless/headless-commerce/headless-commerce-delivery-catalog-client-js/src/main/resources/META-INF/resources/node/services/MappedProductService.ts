/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MappedProduct } from '../models/MappedProduct';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class MappedProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of mapped product.
     * @returns MappedProduct Successful operation
     * @throws ApiError
     */
    public getChannelProductMappedProductsPage({
        channelId,
        productId,
        accountId,
        page,
        pageSize,
        search,
        sort,
    }: {
        channelId: number,
        productId: number,
        accountId?: number,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<MappedProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/mapped-products',
            path: {
                'channelId': channelId,
                'productId': productId,
            },
            query: {
                'accountId': accountId,
                'page': page,
                'pageSize': pageSize,
                'search': search,
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
}
