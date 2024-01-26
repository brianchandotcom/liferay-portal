/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DocumentBulkSelection } from '../models/DocumentBulkSelection';
import type { Selection } from '../models/Selection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SelectionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Selection
     * @throws ApiError
     */
    public postBulkSelection({
        requestBody,
    }: {
        requestBody?: DocumentBulkSelection,
    }): CancelablePromise<Selection> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/bulk/v1.0/bulk-selection',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
