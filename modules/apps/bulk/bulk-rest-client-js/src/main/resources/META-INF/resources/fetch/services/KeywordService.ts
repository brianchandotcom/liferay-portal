/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DocumentBulkSelection } from '../models/DocumentBulkSelection';
import type { Keyword } from '../models/Keyword';
import type { KeywordBulkSelection } from '../models/KeywordBulkSelection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class KeywordService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns void
     * @throws ApiError
     */
    public patchBulkV10KeywordsBatch({
        requestBody,
    }: {
        requestBody?: KeywordBulkSelection,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/bulk/v1.0/keywords/batch',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putBulkV10KeywordsBatch({
        requestBody,
    }: {
        requestBody?: KeywordBulkSelection,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/bulk/v1.0/keywords/batch',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Keyword
     * @throws ApiError
     */
    public postKeywordsCommonPage({
        requestBody,
    }: {
        requestBody?: DocumentBulkSelection,
    }): CancelablePromise<Array<Keyword>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/bulk/v1.0/keywords/common',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
