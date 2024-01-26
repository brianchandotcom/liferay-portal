/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CTEntry } from '../models/CTEntry';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CtEntryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns CTEntry
     * @throws ApiError
     */
    public getCtCollectionCtEntriesPage({
        ctCollectionId,
        showHideable,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        ctCollectionId: number,
        showHideable?: boolean,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<CTEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/ct-entries',
            path: {
                'ctCollectionId': ctCollectionId,
            },
            query: {
                'showHideable': showHideable,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns CTEntry
     * @throws ApiError
     */
    public getCtEntry({
        ctEntryId,
    }: {
        ctEntryId: number,
    }): CancelablePromise<CTEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-entries/{ctEntryId}',
            path: {
                'ctEntryId': ctEntryId,
            },
        });
    }
}
