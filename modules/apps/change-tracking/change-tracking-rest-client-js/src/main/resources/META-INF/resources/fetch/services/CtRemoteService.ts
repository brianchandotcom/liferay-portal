/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CTRemote } from '../models/CTRemote';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CtRemoteService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns CTRemote
     * @throws ApiError
     */
    public getCtRemotesPage({
        page,
        pageSize,
        search,
        sort,
    }: {
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<CTRemote>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-remotes',
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns CTRemote
     * @throws ApiError
     */
    public postCtRemote({
        requestBody,
    }: {
        requestBody?: CTRemote,
    }): CancelablePromise<CTRemote> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/change-tracking-rest/v1.0/ct-remotes',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteCtRemote({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/change-tracking-rest/v1.0/ct-remotes/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @returns CTRemote
     * @throws ApiError
     */
    public getCtRemote({
        id,
    }: {
        id: number,
    }): CancelablePromise<CTRemote> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-remotes/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @returns CTRemote
     * @throws ApiError
     */
    public patchCtRemote({
        id,
        requestBody,
    }: {
        id: number,
        requestBody?: CTRemote,
    }): CancelablePromise<CTRemote> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/change-tracking-rest/v1.0/ct-remotes/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns CTRemote
     * @throws ApiError
     */
    public putCtRemote({
        id,
        requestBody,
    }: {
        id: number,
        requestBody?: CTRemote,
    }): CancelablePromise<CTRemote> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/change-tracking-rest/v1.0/ct-remotes/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
