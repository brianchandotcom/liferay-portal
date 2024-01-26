/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { LinkedProduct } from '../models/LinkedProduct';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class LinkedProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of grouped product.
     * @returns LinkedProduct Successful operation
     * @throws ApiError
     */
    public getChannelProductLinkedProductsPage({
        channelId,
        productId,
        accountId,
        page,
        pageSize,
    }: {
        channelId: number,
        productId: number,
        accountId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<LinkedProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/linked-products',
            path: {
                'channelId': channelId,
                'productId': productId,
            },
            query: {
                'accountId': accountId,
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
