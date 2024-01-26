/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductOptionValue } from '../models/ProductOptionValue';
import type { SkuOption } from '../models/SkuOption';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductOptionValueService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets Product Option Values.
     * @returns ProductOptionValue Successful operation
     * @throws ApiError
     */
    public getChannelProductProductOptionProductOptionValuesPage({
        channelId,
        productId,
        productOptionId,
        accountId,
        productOptionValueId,
        skuId,
        page,
        pageSize,
    }: {
        channelId: number,
        productId: number,
        productOptionId: number,
        accountId?: number,
        productOptionValueId?: number,
        skuId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ProductOptionValue>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/product-options/{productOptionId}/product-option-values',
            path: {
                'channelId': channelId,
                'productId': productId,
                'productOptionId': productOptionId,
            },
            query: {
                'accountId': accountId,
                'productOptionValueId': productOptionValueId,
                'skuId': skuId,
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
     * Retrieves a list of ProductOptionValue from selected channel, product ID and product option ID.
     * @returns ProductOptionValue Created
     * @throws ApiError
     */
    public postChannelProductProductOptionProductOptionValuesPage({
        channelId,
        productId,
        productOptionId,
        accountId,
        productOptionValueId,
        skuId,
        page,
        pageSize,
        requestBody,
    }: {
        channelId: number,
        productId: number,
        productOptionId: number,
        accountId?: number,
        productOptionValueId?: number,
        skuId?: number,
        page?: number,
        pageSize?: number,
        requestBody?: Array<SkuOption>,
    }): CancelablePromise<Array<ProductOptionValue>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/product-options/{productOptionId}/product-option-values',
            path: {
                'channelId': channelId,
                'productId': productId,
                'productOptionId': productOptionId,
            },
            query: {
                'accountId': accountId,
                'productOptionValueId': productOptionValueId,
                'skuId': skuId,
                'page': page,
                'pageSize': pageSize,
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
