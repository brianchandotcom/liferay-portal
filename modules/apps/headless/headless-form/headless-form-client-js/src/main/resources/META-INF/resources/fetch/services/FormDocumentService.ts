/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FormDocument } from '../models/FormDocument';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class FormDocumentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @deprecated
     * @returns void
     * @throws ApiError
     */
    public deleteFormDocument({
        formDocumentId,
    }: {
        formDocumentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-form/v1.0/form-documents/{formDocumentId}',
            path: {
                'formDocumentId': formDocumentId,
            },
        });
    }
    /**
     * @deprecated
     * @returns FormDocument
     * @throws ApiError
     */
    public getFormDocument({
        formDocumentId,
    }: {
        formDocumentId: number,
    }): CancelablePromise<FormDocument> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-form/v1.0/form-documents/{formDocumentId}',
            path: {
                'formDocumentId': formDocumentId,
            },
        });
    }
}
