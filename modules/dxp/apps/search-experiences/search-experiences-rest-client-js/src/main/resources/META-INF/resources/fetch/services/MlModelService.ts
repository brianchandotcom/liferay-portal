/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MLModel } from '../models/MLModel';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class MlModelService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns MLModel
     * @throws ApiError
     */
    public getSentenceTransformerMlModelsPage({
        limit,
        pipelineTag,
        query,
        tag,
    }: {
        limit?: number,
        pipelineTag?: string,
        query?: string,
        tag?: string,
    }): CancelablePromise<Array<MLModel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sentence-transformer/ml-models',
            query: {
                'limit': limit,
                'pipelineTag': pipelineTag,
                'query': query,
                'tag': tag,
            },
        });
    }
}
