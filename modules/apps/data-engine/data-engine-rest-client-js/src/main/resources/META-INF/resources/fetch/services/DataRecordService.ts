/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataRecord } from '../models/DataRecord';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DataRecordService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns DataRecord
     * @throws ApiError
     */
    public getDataDefinitionDataRecordsPage({
        dataDefinitionId,
        dataListViewId,
        keywords,
        page,
        pageSize,
        sort,
    }: {
        dataDefinitionId: number,
        dataListViewId?: number,
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<DataRecord>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-records',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            query: {
                'dataListViewId': dataListViewId,
                'keywords': keywords,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns DataRecord
     * @throws ApiError
     */
    public postDataDefinitionDataRecord({
        dataDefinitionId,
        requestBody,
    }: {
        dataDefinitionId: number,
        requestBody?: DataRecord,
    }): CancelablePromise<DataRecord> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-records',
            path: {
                'dataDefinitionId': dataDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns DataRecord
     * @throws ApiError
     */
    public getDataRecordCollectionDataRecordsPage({
        dataRecordCollectionId,
        dataListViewId,
        keywords,
        page,
        pageSize,
        sort,
    }: {
        dataRecordCollectionId: number,
        dataListViewId?: number,
        keywords?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<DataRecord>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records',
            path: {
                'dataRecordCollectionId': dataRecordCollectionId,
            },
            query: {
                'dataListViewId': dataListViewId,
                'keywords': keywords,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns DataRecord
     * @throws ApiError
     */
    public postDataRecordCollectionDataRecord({
        dataRecordCollectionId,
        requestBody,
    }: {
        dataRecordCollectionId: number,
        requestBody?: DataRecord,
    }): CancelablePromise<DataRecord> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records',
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
    public getDataRecordCollectionDataRecordExport({
        dataRecordCollectionId,
        page,
        pageSize,
    }: {
        dataRecordCollectionId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records/export',
            path: {
                'dataRecordCollectionId': dataRecordCollectionId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteDataRecord({
        dataRecordId,
    }: {
        dataRecordId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/data-engine/v2.0/data-records/{dataRecordId}',
            path: {
                'dataRecordId': dataRecordId,
            },
        });
    }
    /**
     * @returns DataRecord
     * @throws ApiError
     */
    public getDataRecord({
        dataRecordId,
    }: {
        dataRecordId: number,
    }): CancelablePromise<DataRecord> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/data-engine/v2.0/data-records/{dataRecordId}',
            path: {
                'dataRecordId': dataRecordId,
            },
        });
    }
    /**
     * @returns DataRecord
     * @throws ApiError
     */
    public patchDataRecord({
        dataRecordId,
        requestBody,
    }: {
        dataRecordId: number,
        requestBody?: DataRecord,
    }): CancelablePromise<DataRecord> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/data-engine/v2.0/data-records/{dataRecordId}',
            path: {
                'dataRecordId': dataRecordId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns DataRecord
     * @throws ApiError
     */
    public putDataRecord({
        dataRecordId,
        requestBody,
    }: {
        dataRecordId: number,
        requestBody?: DataRecord,
    }): CancelablePromise<DataRecord> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/data-engine/v2.0/data-records/{dataRecordId}',
            path: {
                'dataRecordId': dataRecordId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
