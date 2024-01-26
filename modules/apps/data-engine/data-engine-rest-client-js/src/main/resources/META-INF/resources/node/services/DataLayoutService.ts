/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataLayout } from '../models/DataLayout';
import type { DataLayoutRenderingContext } from '../models/DataLayoutRenderingContext';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DataLayoutService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteDataDefinitionDataLayout({
        dataDefinitionId,
    }: {
        dataDefinitionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-layouts',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
        });
    }
    /**
     * @returns DataLayout
     * @throws ApiError
     */
    public getDataDefinitionDataLayoutsPage({
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
    }): CancelablePromise<Array<DataLayout>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-layouts',
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
     * @returns DataLayout
     * @throws ApiError
     */
    public postDataDefinitionDataLayout({
        dataDefinitionId,
        requestBody,
    }: {
        dataDefinitionId: number,
        requestBody?: DataLayout,
    }): CancelablePromise<DataLayout> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-layouts',
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
    public deleteDataLayout({
        dataLayoutId,
    }: {
        dataLayoutId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/data-engine/v2.0/data-layouts/{dataLayoutId}',
            path: {
                'dataLayoutId': dataLayoutId,
            },
        });
    }
    /**
     * @returns DataLayout
     * @throws ApiError
     */
    public getDataLayout({
        dataLayoutId,
    }: {
        dataLayoutId: number,
    }): CancelablePromise<DataLayout> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-layouts/{dataLayoutId}',
            path: {
                'dataLayoutId': dataLayoutId,
            },
        });
    }
    /**
     * @returns DataLayout
     * @throws ApiError
     */
    public putDataLayout({
        dataLayoutId,
        requestBody,
    }: {
        dataLayoutId: number,
        requestBody?: DataLayout,
    }): CancelablePromise<DataLayout> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/data-engine/v2.0/data-layouts/{dataLayoutId}',
            path: {
                'dataLayoutId': dataLayoutId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public postDataLayoutContext({
        dataLayoutId,
        requestBody,
    }: {
        dataLayoutId: number,
        requestBody?: DataLayoutRenderingContext,
    }): CancelablePromise<Record<string, Record<string, any>>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/data-layouts/{dataLayoutId}/context',
            path: {
                'dataLayoutId': dataLayoutId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns DataLayout
     * @throws ApiError
     */
    public getSiteDataLayoutByContentTypeByDataLayoutKey({
        siteId,
        contentType,
        dataLayoutKey,
    }: {
        siteId: number,
        contentType: string,
        dataLayoutKey: string,
    }): CancelablePromise<DataLayout> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/sites/{siteId}/data-layouts/by-content-type/{contentType}/by-data-layout-key/{dataLayoutKey}',
            path: {
                'siteId': siteId,
                'contentType': contentType,
                'dataLayoutKey': dataLayoutKey,
            },
        });
    }
}
