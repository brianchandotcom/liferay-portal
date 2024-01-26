/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FormRecord } from '../models/FormRecord';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class FormRecordService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @deprecated
     * @returns FormRecord
     * @throws ApiError
     */
    public getFormRecord({
        formRecordId,
    }: {
        formRecordId: number,
    }): CancelablePromise<FormRecord> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-form/v1.0/form-records/{formRecordId}',
            path: {
                'formRecordId': formRecordId,
            },
        });
    }
    /**
     * @deprecated
     * @returns FormRecord
     * @throws ApiError
     */
    public putFormRecord({
        formRecordId,
        acceptLanguage,
        requestBody,
    }: {
        formRecordId: number,
        acceptLanguage?: string,
        requestBody?: FormRecord,
    }): CancelablePromise<FormRecord> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-form/v1.0/form-records/{formRecordId}',
            path: {
                'formRecordId': formRecordId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @deprecated
     * @returns FormRecord
     * @throws ApiError
     */
    public getFormFormRecordsPage({
        formId,
        page,
        pageSize,
    }: {
        formId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<FormRecord>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-form/v1.0/forms/{formId}/form-records',
            path: {
                'formId': formId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @deprecated
     * @returns FormRecord
     * @throws ApiError
     */
    public postFormFormRecord({
        formId,
        acceptLanguage,
        requestBody,
    }: {
        formId: number,
        acceptLanguage?: string,
        requestBody?: FormRecord,
    }): CancelablePromise<FormRecord> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-form/v1.0/forms/{formId}/form-records',
            path: {
                'formId': formId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @deprecated
     * @returns FormRecord
     * @throws ApiError
     */
    public getFormFormRecordByLatestDraft({
        formId,
    }: {
        formId: number,
    }): CancelablePromise<FormRecord> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-form/v1.0/forms/{formId}/form-records/by-latest-draft',
            path: {
                'formId': formId,
            },
        });
    }
}
