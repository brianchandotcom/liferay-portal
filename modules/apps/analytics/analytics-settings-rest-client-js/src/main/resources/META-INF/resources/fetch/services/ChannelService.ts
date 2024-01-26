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
     * @returns Channel
     * @throws ApiError
     */
    public getChannelsPage({
        keywords,
        page,
        pageSize,
        sort,
    }: {
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<Channel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/channels',
            query: {
                'keywords': keywords,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns Channel
     * @throws ApiError
     */
    public patchChannel({
        requestBody,
    }: {
        requestBody?: Channel,
    }): CancelablePromise<Channel> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/analytics-settings-rest/v1.0/channels',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                500: `Unexpected error`,
            },
        });
    }
    /**
     * @returns Channel
     * @throws ApiError
     */
    public postChannel({
        requestBody,
    }: {
        requestBody?: Channel,
    }): CancelablePromise<Channel> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/analytics-settings-rest/v1.0/channels',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                500: `Unexpected error`,
            },
        });
    }
}
