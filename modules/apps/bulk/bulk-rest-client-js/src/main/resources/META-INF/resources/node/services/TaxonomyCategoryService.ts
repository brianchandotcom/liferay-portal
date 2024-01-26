/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TaxonomyCategoryBulkSelection } from '../models/TaxonomyCategoryBulkSelection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TaxonomyCategoryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns void
     * @throws ApiError
     */
    public patchBulkV10TaxonomyCategoriesBatch({
        requestBody,
    }: {
        requestBody?: TaxonomyCategoryBulkSelection,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/bulk/v1.0/taxonomy-categories/batch',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public putBulkV10TaxonomyCategoriesBatch({
        requestBody,
    }: {
        requestBody?: TaxonomyCategoryBulkSelection,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/bulk/v1.0/taxonomy-categories/batch',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
