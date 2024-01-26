/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductOption } from '../models/ProductOption';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductOptionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets Product Options.
     * @returns ProductOption Successful operation
     * @throws ApiError
     */
    public getChannelProductProductOptionsPage({
        channelId,
        productId,
        page,
        pageSize,
    }: {
        channelId: number,
        productId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ProductOption>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/product-options',
            path: {
                'channelId': channelId,
                'productId': productId,
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
}
