/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Channel } from '../models/Channel';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ChannelService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets channel associated to the pricing object.
     * @returns Channel Successful operation
     * @throws ApiError
     */
    public getWarehouseChannelChannel({
        warehouseChannelId,
    }: {
        warehouseChannelId: number,
    }): CancelablePromise<Channel> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouse-channels/{warehouseChannelId}/channel',
            path: {
                'warehouseChannelId': warehouseChannelId,
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
