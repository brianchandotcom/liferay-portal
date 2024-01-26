/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Form } from '../models/Form';
import type { FormContext } from '../models/FormContext';
import type { FormDocument } from '../models/FormDocument';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class FormService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @deprecated
     * @returns Form
     * @throws ApiError
     */
    public getForm({
        formId,
    }: {
        formId: number,
    }): CancelablePromise<Form> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-form/v1.0/forms/{formId}',
            path: {
                'formId': formId,
            },
        });
    }
    /**
     * @deprecated
     * @returns FormContext
     * @throws ApiError
     */
    public postFormEvaluateContext({
        formId,
        acceptLanguage,
        requestBody,
    }: {
        formId: number,
        acceptLanguage?: string,
        requestBody?: FormContext,
    }): CancelablePromise<FormContext> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-form/v1.0/forms/{formId}/evaluate-context',
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
     * @returns FormDocument
     * @throws ApiError
     */
    public postFormFormDocument({
        formId,
        formData,
    }: {
        formId: number,
        formData?: {
            file?: Blob;
            formDocument?: FormDocument;
        },
    }): CancelablePromise<FormDocument> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-form/v1.0/forms/{formId}/form-document',
            path: {
                'formId': formId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * @deprecated
     * @returns Form
     * @throws ApiError
     */
    public getSiteFormsPage({
        siteId,
        page,
        pageSize,
    }: {
        siteId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Form>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-form/v1.0/sites/{siteId}/forms',
            path: {
                'siteId': siteId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
}
