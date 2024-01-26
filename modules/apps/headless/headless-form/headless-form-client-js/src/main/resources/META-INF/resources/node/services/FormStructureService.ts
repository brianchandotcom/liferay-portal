/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FormStructure } from '../models/FormStructure';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class FormStructureService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @deprecated
     * @returns FormStructure
     * @throws ApiError
     */
    public getFormStructure({
        formStructureId,
    }: {
        formStructureId: number,
    }): CancelablePromise<FormStructure> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-form/v1.0/form-structures/{formStructureId}',
            path: {
                'formStructureId': formStructureId,
            },
        });
    }
    /**
     * @deprecated
     * @returns FormStructure
     * @throws ApiError
     */
    public getSiteFormStructuresPage({
        siteId,
        page,
        pageSize,
    }: {
        siteId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<FormStructure>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-form/v1.0/sites/{siteId}/form-structures',
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
