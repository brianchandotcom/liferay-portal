/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataListView } from '../models/DataListView';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DataListViewService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteDataDefinitionDataListView({
        dataDefinitionId,
    }: {
        dataDefinitionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-list-views',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
        });
    }
    /**
     * @returns DataListView
     * @throws ApiError
     */
    public getDataDefinitionDataListViewsPage({
        dataDefinitionId,
        keywords,
        page,
        pageSize,
        sort,
    }: {
        dataDefinitionId: number,
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<DataListView>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-list-views',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            query: {
                'keywords': keywords,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns DataListView
     * @throws ApiError
     */
    public postDataDefinitionDataListView({
        dataDefinitionId,
        requestBody,
    }: {
        dataDefinitionId: number,
        requestBody?: DataListView,
    }): CancelablePromise<DataListView> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-list-views',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteDataListView({
        dataListViewId,
    }: {
        dataListViewId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/data-engine/v2.0/data-list-views/{dataListViewId}',
            path: {
                'dataListViewId': dataListViewId,
            },
        });
    }
    /**
     * @returns DataListView
     * @throws ApiError
     */
    public getDataListView({
        dataListViewId,
    }: {
        dataListViewId: number,
    }): CancelablePromise<DataListView> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-list-views/{dataListViewId}',
            path: {
                'dataListViewId': dataListViewId,
            },
        });
    }
    /**
     * @returns DataListView
     * @throws ApiError
     */
    public putDataListView({
        dataListViewId,
        requestBody,
    }: {
        dataListViewId: number,
        requestBody?: DataListView,
    }): CancelablePromise<DataListView> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/data-engine/v2.0/data-list-views/{dataListViewId}',
            path: {
                'dataListViewId': dataListViewId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
