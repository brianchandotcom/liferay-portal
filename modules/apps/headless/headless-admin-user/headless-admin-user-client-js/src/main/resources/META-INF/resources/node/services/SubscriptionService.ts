/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Subscription } from '../models/Subscription';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SubscriptionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Subscription
     * @throws ApiError
     */
    public getMyUserAccountSubscriptionsPage({
        contentType,
        page,
        pageSize,
    }: {
        contentType?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Subscription>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/my-user-account/subscriptions',
            query: {
                'contentType': contentType,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteMyUserAccountSubscription({
        subscriptionId,
    }: {
        subscriptionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/my-user-account/subscriptions/{subscriptionId}',
            path: {
                'subscriptionId': subscriptionId,
            },
        });
    }
    /**
     * @returns Subscription
     * @throws ApiError
     */
    public getMyUserAccountSubscription({
        subscriptionId,
    }: {
        subscriptionId: number,
    }): CancelablePromise<Subscription> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/my-user-account/subscriptions/{subscriptionId}',
            path: {
                'subscriptionId': subscriptionId,
            },
        });
    }
}
