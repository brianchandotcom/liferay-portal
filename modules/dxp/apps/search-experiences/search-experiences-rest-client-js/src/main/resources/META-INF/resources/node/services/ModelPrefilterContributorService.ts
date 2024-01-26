/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ModelPrefilterContributor } from '../models/ModelPrefilterContributor';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ModelPrefilterContributorService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ModelPrefilterContributor
     * @throws ApiError
     */
    public getModelPrefilterContributorsPage(): CancelablePromise<Array<ModelPrefilterContributor>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/model-prefilter-contributors',
        });
    }
}
