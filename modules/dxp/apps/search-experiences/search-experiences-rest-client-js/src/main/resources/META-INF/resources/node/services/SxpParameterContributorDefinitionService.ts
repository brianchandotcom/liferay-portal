/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SXPParameterContributorDefinition } from '../models/SXPParameterContributorDefinition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SxpParameterContributorDefinitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SXPParameterContributorDefinition
     * @throws ApiError
     */
    public getSxpParameterContributorDefinitionsPage(): CancelablePromise<Array<SXPParameterContributorDefinition>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-parameter-contributor-definitions',
        });
    }
}
