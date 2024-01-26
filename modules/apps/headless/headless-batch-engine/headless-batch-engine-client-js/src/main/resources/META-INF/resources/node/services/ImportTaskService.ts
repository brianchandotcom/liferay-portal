/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ImportTask } from '../models/ImportTask';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ImportTaskService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the import task by external reference code.
     * @returns ImportTask
     * @throws ApiError
     */
    public getImportTaskByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ImportTask> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/import-task/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the exported content by external reference code.
     * @returns binary
     * @throws ApiError
     */
    public getImportTaskByExternalReferenceCodeContent({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/import-task/by-external-reference-code/{externalReferenceCode}/content',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns binary
     * @throws ApiError
     */
    public getImportTaskByExternalReferenceCodeFailedItemReport({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/import-task/by-external-reference-code/{externalReferenceCode}/failed-items/report',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Uploads a new file for deleting items in batch.
     * @returns ImportTask Async
     * @throws ApiError
     */
    public deleteImportTask({
        className,
        requestBody,
        callbackUrl,
        externalReferenceCode,
        importStrategy,
        taskItemDelegateName,
    }: {
        className: string,
        requestBody: Record<string, any>,
        callbackUrl?: string,
        externalReferenceCode?: string,
        importStrategy?: 'ON_ERROR_CONTINUE' | 'ON_ERROR_FAIL',
        taskItemDelegateName?: string,
    }): CancelablePromise<ImportTask> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-batch-engine/v1.0/import-task/{className}',
            path: {
                'className': className,
            },
            query: {
                'callbackURL': callbackUrl,
                'externalReferenceCode': externalReferenceCode,
                'importStrategy': importStrategy,
                'taskItemDelegateName': taskItemDelegateName,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Uploads a new file for creating new items in batch.
     * @returns ImportTask Async
     * @throws ApiError
     */
    public postImportTask({
        className,
        requestBody,
        callbackUrl,
        createStrategy,
        externalReferenceCode,
        fieldNameMapping,
        importStrategy,
        taskItemDelegateName,
    }: {
        className: string,
        requestBody: Record<string, any>,
        callbackUrl?: string,
        createStrategy?: 'INSERT' | 'UPSERT',
        externalReferenceCode?: string,
        fieldNameMapping?: string,
        importStrategy?: 'ON_ERROR_CONTINUE' | 'ON_ERROR_FAIL',
        taskItemDelegateName?: string,
    }): CancelablePromise<ImportTask> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-batch-engine/v1.0/import-task/{className}',
            path: {
                'className': className,
            },
            query: {
                'callbackURL': callbackUrl,
                'createStrategy': createStrategy,
                'externalReferenceCode': externalReferenceCode,
                'fieldNameMapping': fieldNameMapping,
                'importStrategy': importStrategy,
                'taskItemDelegateName': taskItemDelegateName,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Uploads a new file for updating items in batch.
     * @returns ImportTask Async
     * @throws ApiError
     */
    public putImportTask({
        className,
        requestBody,
        callbackUrl,
        externalReferenceCode,
        importStrategy,
        taskItemDelegateName,
        updateStrategy,
    }: {
        className: string,
        requestBody: Record<string, any>,
        callbackUrl?: string,
        externalReferenceCode?: string,
        importStrategy?: 'ON_ERROR_CONTINUE' | 'ON_ERROR_FAIL',
        taskItemDelegateName?: string,
        updateStrategy?: 'PARTIAL_UPDATE' | 'UPDATE',
    }): CancelablePromise<ImportTask> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-batch-engine/v1.0/import-task/{className}',
            path: {
                'className': className,
            },
            query: {
                'callbackURL': callbackUrl,
                'externalReferenceCode': externalReferenceCode,
                'importStrategy': importStrategy,
                'taskItemDelegateName': taskItemDelegateName,
                'updateStrategy': updateStrategy,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the import task.
     * @returns ImportTask
     * @throws ApiError
     */
    public getImportTask({
        importTaskId,
    }: {
        importTaskId: number,
    }): CancelablePromise<ImportTask> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/import-task/{importTaskId}',
            path: {
                'importTaskId': importTaskId,
            },
        });
    }
    /**
     * Retrieves the exported content.
     * @returns binary
     * @throws ApiError
     */
    public getImportTaskContent({
        importTaskId,
    }: {
        importTaskId: number,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/import-task/{importTaskId}/content',
            path: {
                'importTaskId': importTaskId,
            },
        });
    }
    /**
     * @returns binary
     * @throws ApiError
     */
    public getImportTaskFailedItemReport({
        importTaskId,
    }: {
        importTaskId: number,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/import-task/{importTaskId}/failed-items/report',
            path: {
                'importTaskId': importTaskId,
            },
        });
    }
}
