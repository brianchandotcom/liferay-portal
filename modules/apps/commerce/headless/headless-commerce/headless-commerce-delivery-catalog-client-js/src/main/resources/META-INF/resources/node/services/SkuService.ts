/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DDMOption } from '../models/DDMOption';
import type { Sku } from '../models/Sku';
import type { SkuOption } from '../models/SkuOption';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SkuService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves products from selected channel.
     * @returns Sku
     * @throws ApiError
     */
    public getChannelProductSkusPage({
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
    }): CancelablePromise<Array<Sku>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/skus',
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
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Retrieves a SKU from selected channel and product ID.
     * @returns Sku Created
     * @throws ApiError
     */
    public postChannelProductSku({
        channelId,
        productId,
        requestBody,
        accountId,
        quantity,
    }: {
        channelId: number,
        productId: number,
        requestBody: Array<DDMOption>,
        accountId?: number,
        quantity?: number,
    }): CancelablePromise<Sku> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/skus',
            path: {
                'channelId': channelId,
                'productId': productId,
            },
            query: {
                'accountId': accountId,
                'quantity': quantity,
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
     * Retrieves a SKU from selected channel and product ID.
     * @returns Sku Created
     * @throws ApiError
     */
    public postChannelProductSkuBySkuOption({
        channelId,
        productId,
        requestBody,
        accountId,
        quantity,
        skuUnitOfMeasureKey,
    }: {
        channelId: number,
        productId: number,
        requestBody: Array<SkuOption>,
        accountId?: number,
        quantity?: number,
        skuUnitOfMeasureKey?: string,
    }): CancelablePromise<Sku> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/skus/by-sku-option',
            path: {
                'channelId': channelId,
                'productId': productId,
            },
            query: {
                'accountId': accountId,
                'quantity': quantity,
                'skuUnitOfMeasureKey': skuUnitOfMeasureKey,
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
     * Retrieves a product from selected channel.
     * @returns Sku
     * @throws ApiError
     */
    public getChannelProductSku({
        channelId,
        productId,
        skuId,
        accountId,
    }: {
        channelId: number,
        productId: number,
        skuId: number,
        accountId?: number,
    }): CancelablePromise<Sku> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/skus/{skuId}',
            path: {
                'channelId': channelId,
                'productId': productId,
                'skuId': skuId,
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
