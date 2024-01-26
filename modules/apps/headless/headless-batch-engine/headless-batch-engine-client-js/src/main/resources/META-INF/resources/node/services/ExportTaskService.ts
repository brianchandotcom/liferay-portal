/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ExportTask } from '../models/ExportTask';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ExportTaskService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the export task by external reference code.
     * @returns ExportTask
     * @throws ApiError
     */
    public getExportTaskByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ExportTask> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/export-task/by-external-reference-code/{externalReferenceCode}',
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
    public getExportTaskByExternalReferenceCodeContent({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/export-task/by-external-reference-code/{externalReferenceCode}/content',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Submits a request for exporting items to a file.
     * @returns ExportTask Async
     * @throws ApiError
     */
    public postExportTask({
        className,
        contentType,
        callbackUrl,
        externalReferenceCode,
        fieldNames,
        taskItemDelegateName,
    }: {
        className: string,
        contentType: string,
        callbackUrl?: string,
        externalReferenceCode?: string,
        fieldNames?: string,
        taskItemDelegateName?: string,
    }): CancelablePromise<ExportTask> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-batch-engine/v1.0/export-task/{className}/{contentType}',
            path: {
                'className': className,
                'contentType': contentType,
            },
            query: {
                'callbackURL': callbackUrl,
                'externalReferenceCode': externalReferenceCode,
                'fieldNames': fieldNames,
                'taskItemDelegateName': taskItemDelegateName,
            },
        });
    }
    /**
     * Retrieves the export task.
     * @returns ExportTask
     * @throws ApiError
     */
    public getExportTask({
        exportTaskId,
    }: {
        exportTaskId: number,
    }): CancelablePromise<ExportTask> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/export-task/{exportTaskId}',
            path: {
                'exportTaskId': exportTaskId,
            },
        });
    }
    /**
     * Retrieves the exported content.
     * @returns binary
     * @throws ApiError
     */
    public getExportTaskContent({
        exportTaskId,
    }: {
        exportTaskId: number,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-batch-engine/v1.0/export-task/{exportTaskId}/content',
            path: {
                'exportTaskId': exportTaskId,
            },
        });
    }
}
