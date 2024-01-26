/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EmbeddingProviderConfiguration } from '../models/EmbeddingProviderConfiguration';
import type { EmbeddingProviderValidationResult } from '../models/EmbeddingProviderValidationResult';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class EmbeddingProviderValidationResultService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns EmbeddingProviderValidationResult
     * @throws ApiError
     */
    public postTextEmbeddingValidateProviderConfiguration({
        requestBody,
    }: {
        requestBody?: EmbeddingProviderConfiguration,
    }): CancelablePromise<EmbeddingProviderValidationResult> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/text-embeddings/validate-provider-configuration',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
