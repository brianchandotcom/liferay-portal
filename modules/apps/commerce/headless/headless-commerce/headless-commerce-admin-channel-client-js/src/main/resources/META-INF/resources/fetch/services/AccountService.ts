/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Account } from '../models/Account';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets account associated to the channel account object.
     * @returns Account Successful operation
     * @throws ApiError
     */
    public getChannelAccountAccount({
        channelAccountId,
    }: {
        channelAccountId: number,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-channel/v1.0/channel-accounts/{channelAccountId}/account',
            path: {
                'channelAccountId': channelAccountId,
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
