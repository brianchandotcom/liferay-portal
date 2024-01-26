/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Category } from '../models/Category';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CategoryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Category related to a Product.
     * @returns Category Successful operation
     * @throws ApiError
     */
    public getChannelProductCategoriesPage({
        channelId,
        productId,
        page,
        pageSize,
    }: {
        channelId: number,
        productId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Category>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/categories',
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
