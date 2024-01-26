/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataRecordCollection } from '../models/DataRecordCollection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DataRecordCollectionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns DataRecordCollection
     * @throws ApiError
     */
    public getDataDefinitionDataRecordCollection({
        dataDefinitionId,
    }: {
        dataDefinitionId: number,
    }): CancelablePromise<DataRecordCollection> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collection',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
        });
    }
    /**
     * @returns DataRecordCollection
     * @throws ApiError
     */
    public getDataDefinitionDataRecordCollectionsPage({
        dataDefinitionId,
        keywords,
        page,
        pageSize,
    }: {
        dataDefinitionId: number,
        keywords?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DataRecordCollection>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            query: {
                'keywords': keywords,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns DataRecordCollection
     * @throws ApiError
     */
    public postDataDefinitionDataRecordCollection({
        dataDefinitionId,
        requestBody,
    }: {
        dataDefinitionId: number,
        requestBody?: DataRecordCollection,
    }): CancelablePromise<DataRecordCollection> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections',
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
    public deleteDataRecordCollection({
        dataRecordCollectionId,
    }: {
        dataRecordCollectionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}',
            path: {
                'dataRecordCollectionId': dataRecordCollectionId,
            },
        });
    }
    /**
     * @returns DataRecordCollection
     * @throws ApiError
     */
    public getDataRecordCollection({
        dataRecordCollectionId,
    }: {
        dataRecordCollectionId: number,
    }): CancelablePromise<DataRecordCollection> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}',
            path: {
                'dataRecordCollectionId': dataRecordCollectionId,
            },
        });
    }
    /**
     * @returns DataRecordCollection
     * @throws ApiError
     */
    public putDataRecordCollection({
        dataRecordCollectionId,
        requestBody,
    }: {
        dataRecordCollectionId: number,
        requestBody?: DataRecordCollection,
    }): CancelablePromise<DataRecordCollection> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}',
            path: {
                'dataRecordCollectionId': dataRecordCollectionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getDataRecordCollectionPermissionsPage({
        dataRecordCollectionId,
        roleNames,
    }: {
        dataRecordCollectionId: number,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions',
            path: {
                'dataRecordCollectionId': dataRecordCollectionId,
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
    public putDataRecordCollectionPermissionsPage({
        dataRecordCollectionId,
        requestBody,
    }: {
        dataRecordCollectionId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions',
            path: {
                'dataRecordCollectionId': dataRecordCollectionId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns string
     * @throws ApiError
     */
    public getDataRecordCollectionPermissionByCurrentUser({
        dataRecordCollectionId,
    }: {
        dataRecordCollectionId: number,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions/by-current-user',
            path: {
                'dataRecordCollectionId': dataRecordCollectionId,
            },
        });
    }
    /**
     * @returns DataRecordCollection
     * @throws ApiError
     */
    public getSiteDataRecordCollectionByDataRecordCollectionKey({
        siteId,
        dataRecordCollectionKey,
    }: {
        siteId: number,
        dataRecordCollectionKey: string,
    }): CancelablePromise<DataRecordCollection> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/sites/{siteId}/data-record-collections/by-data-record-collection-key/{dataRecordCollectionKey}',
            path: {
                'siteId': siteId,
                'dataRecordCollectionKey': dataRecordCollectionKey,
            },
        });
    }
}
