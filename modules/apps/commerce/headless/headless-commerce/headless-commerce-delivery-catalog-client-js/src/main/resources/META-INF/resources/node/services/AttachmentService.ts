/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Attachment } from '../models/Attachment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AttachmentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Attachments related to a Product.
     * @returns Attachment Successful operation
     * @throws ApiError
     */
    public getChannelProductAttachmentsPage({
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
    }): CancelablePromise<Array<Attachment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/attachments',
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
     * Gets a list of Images related to a Product.
     * @returns Attachment Successful operation
     * @throws ApiError
     */
    public getChannelProductImagesPage({
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
    }): CancelablePromise<Array<Attachment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/images',
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
}
