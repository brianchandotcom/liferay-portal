/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataDefinition } from '../models/DataDefinition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DataDefinitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns DataDefinition
     * @throws ApiError
     */
    public getDataDefinitionByContentTypeContentTypePage({
        contentType,
        keywords,
        page,
        pageSize,
        sort,
    }: {
        contentType: string,
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<DataDefinition>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/by-content-type/{contentType}',
            path: {
                'contentType': contentType,
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
     * @returns DataDefinition
     * @throws ApiError
     */
    public postDataDefinitionByContentType({
        contentType,
        requestBody,
    }: {
        contentType: string,
        requestBody?: DataDefinition,
    }): CancelablePromise<DataDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/data-definitions/by-content-type/{contentType}',
            path: {
                'contentType': contentType,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns string
     * @throws ApiError
     */
    public getDataDefinitionDataDefinitionFieldFieldTypes(): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/data-definition-fields/field-types',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteDataDefinition({
        dataDefinitionId,
    }: {
        dataDefinitionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
        });
    }
    /**
     * @returns DataDefinition
     * @throws ApiError
     */
    public getDataDefinition({
        dataDefinitionId,
    }: {
        dataDefinitionId: number,
    }): CancelablePromise<DataDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
        });
    }
    /**
     * @returns DataDefinition
     * @throws ApiError
     */
    public patchDataDefinition({
        dataDefinitionId,
        requestBody,
    }: {
        dataDefinitionId: number,
        requestBody?: DataDefinition,
    }): CancelablePromise<DataDefinition> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns DataDefinition
     * @throws ApiError
     */
    public putDataDefinition({
        dataDefinitionId,
        requestBody,
    }: {
        dataDefinitionId: number,
        requestBody?: DataDefinition,
    }): CancelablePromise<DataDefinition> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns DataDefinition
     * @throws ApiError
     */
    public postDataDefinitionCopy({
        dataDefinitionId,
    }: {
        dataDefinitionId: number,
    }): CancelablePromise<DataDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/copy',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getDataDefinitionPermissionsPage({
        dataDefinitionId,
        roleNames,
    }: {
        dataDefinitionId: number,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/permissions',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            query: {
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putDataDefinitionPermissionsPage({
        dataDefinitionId,
        requestBody,
    }: {
        dataDefinitionId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/permissions',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns DataDefinition
     * @throws ApiError
     */
    public getSiteDataDefinitionByContentTypeContentTypePage({
        siteId,
        contentType,
        keywords,
        page,
        pageSize,
        sort,
    }: {
        siteId: number,
        contentType: string,
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<DataDefinition>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}',
            path: {
                'siteId': siteId,
                'contentType': contentType,
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
     * @returns DataDefinition
     * @throws ApiError
     */
    public postSiteDataDefinitionByContentType({
        siteId,
        contentType,
        requestBody,
    }: {
        siteId: number,
        contentType: string,
        requestBody?: DataDefinition,
    }): CancelablePromise<DataDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}',
            path: {
                'siteId': siteId,
                'contentType': contentType,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns DataDefinition
     * @throws ApiError
     */
    public getSiteDataDefinitionByContentTypeByDataDefinitionKey({
        siteId,
        contentType,
        dataDefinitionKey,
    }: {
        siteId: number,
        contentType: string,
        dataDefinitionKey: string,
    }): CancelablePromise<DataDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}/by-data-definition-key/{dataDefinitionKey}',
            path: {
                'siteId': siteId,
                'contentType': contentType,
                'dataDefinitionKey': dataDefinitionKey,
            },
        });
    }
}
